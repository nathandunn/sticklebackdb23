package edu.uoregon.sticklebackdb

import grails.converters.JSON
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDFont
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.springframework.dao.DataIntegrityViolationException

class IndividualController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static navigation = [
            title: 'Individuals', action: 'list', order: 2
    ]

    def stockService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [individualInstanceList: Individual.list(params), individualInstanceTotal: Individual.count()]
    }

    def query(Long pStockID) {
        render(view: "list", model: [individualInstanceList: Individual.findAllByStockID(pStockID), individualInstanceTotal: Individual.findAllByStockID(pStockID).size()])
    }

    def create() {
        [individualInstance: new Individual(params)]
    }

    def search(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        println "${params}"
        String query = params.q

        if(query.contains(".")){
            Integer stockID = query.split("\\.")[0] as Integer
            Stock stock = Stock.findByStockID(stockID)

            Integer individualID = query.split("\\.")[1] as Integer
            List<Individual> individuals = Individual.findAllByIndividualIDAndStock(individualID,stock,params)
            Integer individualCount = Individual.countByIndividualIDAndStock(individualID,stock)
            def model = [individualInstanceList: individuals, individualInstanceTotal: individualCount]

            switch (individualCount) {
                case 1:
                    render(view: "show", model:[individualInstance: individuals.get(0)])
                    return
                    break
                case 0:
                    flash.message = "No stocks found for individual ID [${query}]"
//                    break
                default:
                    render(view: "list", model: model)
                    return
            }
        }
        else{
            redirect(view:"list")
        }
    }

    def save() {
        params.fishSex = params.fishSexString
        def individualInstance = new Individual(params)

//        Stock stock = params.stock
        println "stock to evalute 2 ${params.stock}"
        if(!params.stock || !params.stock.id){
            flash.message = "Must provide a valid stock "
            render(view: "create", model: [individualInstance: individualInstance])
            return
        }
        Stock stock = Stock.findById(params.stock.id)

        println "stock to evalute ${stock}"
        println "stock service ${stockService}"

        Double individualID = stockService.getNextIndividualID(stock)
        println "individualID ${individualID}"
        individualInstance.individualID = individualID



        if (!individualInstance.save(flush: true)) {
            render(view: "create", model: [individualInstance: individualInstance])
            return
        }

//        stock.addToIndividuals(individualInstance)
//        individualInstance.stock = stock
//
//        stock.save(flush: true)
//        individualInstance.save(flush: true)

        flash.message = message(code: 'default.created.message', args: [message(code: 'individual.label', default: 'Individual'), individualInstance.individualIDLabel])
        redirect(action: "show", id: individualInstance.id)
    }

    def show(Long id) {
        def individualInstance = Individual.get(id)
        if (!individualInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'individual.label', default: 'Individual'), id])
            redirect(action: "list")
            return
        }
        def measuredValues = MeasuredValue.findAllByIndividual(individualInstance)

        [individualInstance: individualInstance,measuredValues:measuredValues]
    }

    def edit(Long id) {
        def individualInstance = Individual.get(id)
        if (!individualInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'individual.label', default: 'Individual'), id])
            redirect(action: "list")
            return
        }

        String fishSexString
        if(individualInstance.fishSex == "male" ||individualInstance.fishSex == "female" ){
            fishSexString = individualInstance.fishSex
        }
        else{
            fishSexString = 'N/A'
        }

        [individualInstance: individualInstance, Individual: Individual, Stock: Stock,fishSexString:fishSexString]
    }

    def update(Long id, Long version) {
        def individualInstance = Individual.get(id)
        if (!individualInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'individual.label', default: 'Individual'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (individualInstance.version > version) {
                individualInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'individual.label', default: 'Individual')] as Object[],
                        "Another user has updated this Individual while you were editing")
                render(view: "edit", model: [individualInstance: individualInstance])
                return
            }
        }

        params.fishSex = params.fishSexString

        individualInstance.properties = params

        println "fishSex: ${params.fishSex}"
        println "fishSexString: ${params.fishSexString}"

        if (!individualInstance.save(flush: true)) {
            render(view: "edit", model: [individualInstance: individualInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'individual.label', default: 'Individual'), individualInstance.individualIDLabel])
        redirect(action: "show", id: individualInstance.id)
    }

    def delete(Long id) {
        def individualInstance = Individual.get(id)
        if (!individualInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'individual.label', default: 'Individual'), id])
            redirect(action: "list")
            return
        }

        def label = individualInstance.individualIDLabel
        try {
            individualInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'individual.label', default: 'Individual'), label])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'individual.label', default: 'Individual'), label])
            redirect(action: "show", id: id)
        }
    }

    def addIndividualToStock(Long stockId,String somaLocation,String finclipLocation,String dnaLocation,String comment,String fishSex,String idStatus) {
        Stock stock = Stock.findById(stockId)
        Integer individualID = stockService.getNextIndividualID(stock)
        Individual individual = new Individual(
                somaLocation: somaLocation
                ,dnaLocation: dnaLocation
                ,finclipLocation: finclipLocation
                ,idStatus: idStatus
                ,comments: comment
                ,fishSex: fishSex
                ,individualID: individualID
                ,stock: stock
        )
        individual.validate()
        if(individual.hasErrors()){
            return "failed to add individual ${params}"
        }

        println "adding "
        individual.save flush: true, insert: true

        println "added? ${individual}"

        String excludeGender = fishSex=='male' ? 'female' : 'male'
        return findIndividualsForStock(stockId,excludeGender)
    }

    def findIndividualsForStock(Long stockId,String excludeGender) {
        Stock stock = Stock.findById(stockId)
        println "foudn stock ${stock} for ${stockId}"
//        List<Individual> individuals = Individual.findAllByStockAndFishSexNotEqual(stock, excludeGender,[order: "desc", sort: "individualID"])
        List<Individual> individuals = Individual.findAllByStock(stock, [order: "desc", sort: "individualID"])
        println "# of individuals ${individuals.size()}"

        Map<Long, String> strings = new HashMap<>()
        individuals.each { Individual it ->
            if(it?.fishSex!=excludeGender){
                strings.put(it.id, it.individualIDLabel)
            }
        }
        println "# of strings ${strings.size()}"

        render strings as JSON
    }

    def getNextIndividualID(Integer stockID) {

        List<Individual> ids = Individual.findAllByStockID(stockID)
        Integer max = stockID
        ids.each() { it ->
            Integer tmp = it.individualID
            if (tmp > max) {
                max = tmp
            }
        }
        Integer nextID = ++max
        render String.format("%.4f", nextID)
    }

    def print(Integer id){
        def individualInstance = Individual.get(id)
        if (!individualInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'individual.label', default: 'Individual'), id])
            redirect(action: "list")
            return
        }

        render (view:"label",model:[individualInstance:individualInstance])
    }

    def print3(Integer id) {
        def individual = Individual.get(id)
        if (!individual) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'individual.label', default: 'Individual'), id])
            redirect(action: "list")
            return
        }

        render(view: "label3", model: [individualInstance: individual])
    }

    def printPdf(Integer id) {
        def individual = Individual.get(id)
        if (!individual) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'individual.label', default: 'Individual'), id])
            redirect(action: "list")
            return
        }

        PDDocument document = new PDDocument();

// Create a new blank page and add it to the document
        PDPage page = new PDPage();
        //
//        1 pt = 1/72 inch
        PDRectangle printable = new PDRectangle(72*3,72)
        page.setMediaBox(printable)
        document.addPage( page );

        PDFont font = PDType1Font.COURIER;
        PDFont fontBold = PDType1Font.COURIER_BOLD;
        Integer leftMargin = 5
// Start a new content stream which will "hold" the to be created content
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

// Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
        contentStream.beginText();
        contentStream.setFont( font, 10 );
//        contentStream.moveTextPositionByAmount( 100, 700 );
        contentStream.moveTextPositionByAmount( leftMargin, 60 );
//        contentStream.setTextTranslation(10,20)
        contentStream.drawString( "Name: ${individual.stock.labelStockName}" );
        contentStream.endText()

        contentStream.beginText()
        contentStream.setFont(fontBold,14)
        contentStream.moveTextPositionByAmount( leftMargin, 45 );
        contentStream.drawString( "${individual.individualIDLabel}" );
        contentStream.endText();


        contentStream.beginText()
        contentStream.setFont(font,10)
        contentStream.moveTextPositionByAmount( 85, 45 );
        contentStream.drawString( "Fert: ${g:formatDate([date:individual.stock.fertilizationDate,type:"date",dateStyle: "short"])}" );
        contentStream.endText();

         if(individual.comments) {
            contentStream.beginText()
            contentStream.setFont(font,8)
            contentStream.moveTextPositionByAmount( leftMargin, 30 );
            String labelComments = individual.comments
            Integer maxWidth = 40
            if (labelComments.size() > maxWidth) {
                contentStream.drawString(labelComments.substring(0, maxWidth));
                contentStream.endText()

                contentStream.beginText()
                contentStream.moveTextPositionByAmount(leftMargin, 20)
                contentStream.drawString(labelComments.substring(maxWidth, labelComments.size()));
            } else {
                contentStream.drawString(labelComments);
            }
            contentStream.endText();
        }

// Make sure that the content stream is closed:
        contentStream.close();

// Save the newly created document
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
//        document.save("BlankPage.pdf");
// finally make sure that the document is properly
// closed.
        document.close();

        String pdfFileName = "Stock${individual.individualIDLabel}Label.pdf"
        response.addHeader("Content-Type", "application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=$pdfFileName")
        response.getOutputStream() << outputStream.toByteArray()
    }

//    def getFormattedID(String individualID){
//        render individualID
//    }
}
