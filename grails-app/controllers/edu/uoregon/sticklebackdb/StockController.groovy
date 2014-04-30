package edu.uoregon.sticklebackdb

import grails.converters.JSON
import grails.transaction.Transactional
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDFont
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject
import org.springframework.dao.DataIntegrityViolationException

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class StockController {

    def stockService
    def researcherService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static navigation = [
            title: 'Stocks', action: 'list', order: 1,
    ]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [stockInstanceList: Stock.list(params), stockInstanceTotal: Stock.count()]
    }

    def search(Integer max) {
//        params.max = Math.min(max ?: 10, 100)
        params.max = 1000
        String query = params.q

//        if (query.startsWith("[a-zA-Z]")) {
        if (query ==~ "[a-zA-Z].*") {
            log.debug  "starts with text ${query}"
            List<Stock> stockList = Stock.findAllByStockNameIlike("%" + query + "%", params)
            Integer stockCount = Stock.countByStockNameIlike("%" + query + "%")
            def model = [stockInstanceList: stockList, stockInstanceTotal: stockCount]
            flash.message = "${stockCount} found for query: [${query}]"
            switch (stockCount) {
                case 1:
                    render(view: "show", model: [stockInstance: stockList.get(0)])
                    break
                case 0:
                    flash.message = "No stocks found for stock name [${query}]"
//                    break
                default:
                    render(view: "list", model: model)
            }
        } else if (query.contains(".")) {
            // search for individual
            redirect(action: "search", controller: "individual", params: params)
        } else {
            List<Stock> stockList = Stock.findAllByStockID(query, params)
            Integer stockCount = Stock.countByStockID(query)
            def model = [stockInstanceList: stockList, stockInstanceTotal: stockCount]
            switch (stockCount) {
                case 1:
                    render(view: "show", model: [stockInstance: stockList.get(0)])
                    break
                case 0:
                    flash.message = "No stocks found for stock ID [${query}]"
//                    break
                default:
                    render(view: "list", model: model)
            }
        }

    }

    def createFromCapture() {
        params.stockID = stockService.getNextStockID()
        Stock stock = new Stock(params)

//        List<Line> lineList = new ArrayList<>()
//        Line.all.captures.each { it ->
//            for(Capture capture in it){
//                if(capture.captureDate){
//                    lineList.add(capture.line)
//                }
//            }
//        }
//        lineList.unique(true)

//        log.debug  "lines with capture ${linesWithCapture}"
        List<String> stockNames = getAllStockNames()



        def model = [stockInstance: stock, maxStock: Stock.list(max: 1, sort: "stockID", order: "desc")[0], stockNames: stockNames, lines: Line.listOrderByName(), isNewOrAdmin: true]
        render(view: "createFromCapture", model: model)
    }

    def createFromBreeding() {
        params.stockID = stockService.getNextStockID()
        Stock stock = new Stock(params)

//        List<Line> linesWithCapture = new ArrayList<>()
//        Line.all.captures.each { it ->
//            for(Capture capture in it){
//                if(capture.captureDate){
//                    linesWithCapture.add(capture.line)
//                }
//            }
//        }
//
//        linesWithCapture.unique(true)
//
//        List<Line> linesWithoutCapture = Line.all.minus(linesWithCapture)


        List<String> stockNames = getAllStockNames()
        def model = [stockInstance: stock, maxStock: Stock.list(max: 1, sort: "stockID", order: "desc")[0], stockNames: stockNames, lines: Line.listOrderByName(),isNewOrAdmin: false]
        render(view: "createFromBreeding", model: model)
    }

//    def create() {
//        redirect(action: "list")
////        params.stockID = stockService.getNextStockID()
////        Stock stock = new Stock(params)
////
////        List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")
////        def model = [stockInstance: stock, maxStock: Stock.list(max: 1, sort: "stockID", order: "desc")[0], stockNames: stockNames]
////        render(view: "create", model: model)
//
//    }


    def saveCapture() {
        def stockInstance = new Stock(params)


        log.debug  "params ${params}"

        List<String> stockNames = getAllStockNames()

        Population population = Population.findById(params.population)

        if(!population){
            flash.message = "No population found for ID ${params.population}"
            render(view: "createFromCapture", model: [stockInstance: stockInstance, stockNames: stockNames])
            return
        }


        if (stockInstance.line == null) {

            Line line = Line.findByName(population.identification)
            if(!line){
                line = new Line(
                        name: population.identification
                ).save flush: true
            }

            stockInstance.line = line
            line.addToStocks(stockInstance)

//            flash.message = "Must associate a line with a stock"
//            render(view: "createFromCapture", model: [stockInstance: stockInstance, stockNames: stockNames])
//            return
        }

        Date captureDate = params.newLineDate
        String captureComment = params.newLinecomment
        Capture capture = new Capture(
                population: population
                , captureDate: captureDate
                , comment: captureComment
                , line: stockInstance.line
        ).save(flush: true, failOnError: true)

        stockInstance.line.addToCaptures(capture)

        if (!stockInstance.save(flush: true)) {
            render(view: "createFromCapture", model: [stockInstance: stockInstance, stockNames: stockNames])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'stock.label', default: 'Stock'), stockInstance.stockIDLabel])
        redirect(action: "show", id: stockInstance.id)
    }

    def saveFromBreeding() {
        def stockInstance = new Stock(params)

        List<String> stockNames = getAllStockNames()

        if (stockInstance.line == null) {
            flash.message = "Must associate a line with a stock"
            render(view: "createFromBreeding", model: [stockInstance: stockInstance, stockNames: stockNames])
            return
        }

//        if(!stockInstance.fertilizationDate){
//            flash.message = "Must have a fertilization date or a line with a capture"
//            render(view: "createFromBreeding", model: [stockInstance: stockInstance, stockNames: stockNames])
//            return
//        }

        if (false == researcherService.isAdmin()) {
            Stock previousStock = Stock.findByStockName(stockInstance.stockName)
            if (previousStock == null) {
                stockInstance.errors.rejectValue("stockName", "stock.name.must.exist", "Use a previous stock name or ask Administrator to add it for you.")
                render(view: "createFromBreeding", model: [stockInstance: stockInstance, stockNames: stockNames])
                return
            }
        }

        if (stockInstance.maternalIndividual == null) {
            flash.message = "Must supply a maternal individual"
            render(view: "createFromBreeding", model: [stockInstance: stockInstance, stockNames: stockNames])
            return
        }

        if (stockInstance.paternalIndividual == null) {
            flash.message = "Must supply a paternal individual"
            render(view: "createFromBreeding", model: [stockInstance: stockInstance, stockNames: stockNames])
            return
        }

        log.debug "paternal stock: ${stockInstance.paternalStock}"
        log.debug "paternal ind: ${stockInstance.paternalIndividual}"
        log.debug "maternal stock: ${stockInstance.maternalStock}"
        log.debug "maternal ind: ${stockInstance.maternalIndividual}"

        if (!stockInstance.save(flush: true)) {
            render(view: "createFromBreeding", model: [stockInstance: stockInstance, stockNames: stockNames])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'stock.label', default: 'Stock'), stockInstance.stockIDLabel])
        redirect(action: "show", id: stockInstance.id)
    }

//    def save() {
//        def stockInstance = new Stock(params)
//
//        List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")
//
//        if (stockInstance.line == null) {
//            flash.message = "Must associate a line with a stock"
//            render(view: "create", model: [stockInstance: stockInstance, stockNames: stockNames])
//            return
//        }
//
//        if (stockInstance.fertilizationDate && stockInstance?.line?.captures) {
//            flash.message = "Can not have both a fertilization date and a Line with a capture"
//            render(view: "create", model: [stockInstance: stockInstance, stockNames: stockNames])
//            return
//        }
//        if (!stockInstance?.line?.captures && !stockInstance.fertilizationDate) {
//            flash.message = "Must have a fertilization date or a line with a capture"
//            render(view: "create", model: [stockInstance: stockInstance, stockNames: stockNames])
//            return
//        }
//
//        if (false == researcherService.isAdmin()) {
//            Stock previousStock = Stock.findByStockName(stockInstance.stockName)
//            if (previousStock == null) {
//                stockInstance.errors.rejectValue("stockName", "stock.name.must.exist", "Use a previous stock name or ask Administrator to add it for you.")
//                render(view: "create", model: [stockInstance: stockInstance, stockNames: stockNames])
//                return
//            }
//        }
//
//        if (stockInstance.maternalIndividual == null) {
//            flash.message = "Must supply a maternal individual"
//            render(view: "create", model: [stockInstance: stockInstance, stockNames: stockNames])
//            return
//        }
//
//        if (stockInstance.paternalIndividual == null) {
//            flash.message = "Must supply a paternal individual"
//            render(view: "create", model: [stockInstance: stockInstance, stockNames: stockNames])
//            return
//        }
//
//        if (!stockInstance.save(flush: true)) {
//            render(view: "create", model: [stockInstance: stockInstance, stockNames: stockNames])
//            return
//        }
//
//        flash.message = message(code: 'default.created.message', args: [message(code: 'stock.label', default: 'Stock'), stockInstance.stockIDLabel])
//        redirect(action: "show", id: stockInstance.id)
//    }

    def show(Long id) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "list")
            return
        }


        [stockInstance: stockInstance]
    }

    def edit(Long id) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "list")
            return
        }

        // find all lines with captures without dates
//        List<Line> lineList = new ArrayList<>()
//        Line.all.captures.each { it ->
//            for(Capture capture in it){
//                if(capture.captureDate){
//                    lineList.add(capture.line)
//                }
//            }
//        }
//
//        lineList.unique(true)
//
//        if (stockInstance.isBred()) {
//            lineList = Line.all.minus(lineList)
//        }

        List<String> stockNames = getAllStockNames()

        [stockInstance: stockInstance, stockNames: stockNames, lines: Line.listOrderByName(),isNewOrAdmin: researcherService.admin || !stockInstance.id]
    }

    @Transactional
    def update(Long id, Long version) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (stockInstance.version > version) {
                stockInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'stock.label', default: 'Stock')] as Object[],
                        "Another user has updated this Stock while you were editing")
                respond stockInstance.errors, view: 'edit'
                return
            }
        }

        stockInstance.properties = params

        if (false == researcherService.isAdmin()) {
            Stock previousStock = Stock.findByStockName(stockInstance.stockName)
            if (previousStock == null) {
                stockInstance.errors.rejectValue("stockName", "stock.name.must.exist", "Use a previous stock name or ask Administrator to add it for you.")

//                List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")

//                respond stockInstance.errors, view:'edit'
                List<String> stockNames = getAllStockNames()
                render(view: "edit", model: [stockInstance: stockInstance, stockNames: stockNames])
                return
            }
        }

        if (!stockInstance.save(flush: true)) {
//            render(view: "edit", model: [stockInstance: stockInstance])
            List<String> stockNames = getAllStockNames()
            render(view: "edit", model: [stockInstance: stockInstance, stockNames: stockNames])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'stock.label', default: 'Stock'), stockInstance.stockIDLabel])
        redirect(action: "show", id: stockInstance.id)
    }

    @Transactional
    def updateCapture(Long id, Long version) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (stockInstance.version > version) {
                stockInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'stock.label', default: 'Stock')] as Object[],
                        "Another user has updated this Stock while you were editing")
                respond stockInstance.errors, view: 'edit'
                return
            }
        }

        stockInstance.properties = params

        if (false == researcherService.isAdmin()) {
            Stock previousStock = Stock.findByStockName(stockInstance.stockName)
            if (previousStock == null) {
                stockInstance.errors.rejectValue("stockName", "stock.name.must.exist", "Use a previous stock name or ask Administrator to add it for you.")
                List<String> stockNames = getAllStockNames()
                render(view: "edit", model: [stockInstance: stockInstance, stockNames: stockNames])
                return
            }
        }

        if (!stockInstance.save(flush: true)) {
            List<String> stockNames = getAllStockNames()
            render(view: "edit", model: [stockInstance: stockInstance, stockNames: stockNames])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'stock.label', default: 'Stock'), stockInstance.stockIDLabel])
        redirect(action: "show", id: stockInstance.id)
    }


    @Transactional
    def updateBreeding(Long id, Long version) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (stockInstance.version > version) {
                stockInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'stock.label', default: 'Stock')] as Object[],
                        "Another user has updated this Stock while you were editing")
                respond stockInstance.errors, view: 'edit'
                return
            }
        }

        stockInstance.properties = params

        if (false == researcherService.isAdmin()) {
            Stock previousStock = Stock.findByStockName(stockInstance.stockName)
            if (previousStock == null) {
                stockInstance.errors.rejectValue("stockName", "stock.name.must.exist", "Use a previous stock name or ask Administrator to add it for you.")

//                List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")

//                respond stockInstance.errors, view:'edit'
                List<String> stockNames = getAllStockNames()
                render(view: "edit", model: [stockInstance: stockInstance, stockNames: stockNames])
                return
            }
        }

        if (!stockInstance.save(flush: true)) {
//            render(view: "edit", model: [stockInstance: stockInstance])
            List<String> stockNames = getAllStockNames()
            render(view: "edit", model: [stockInstance: stockInstance, stockNames: stockNames])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'stock.label', default: 'Stock'), stockInstance.stockIDLabel])
        redirect(action: "show", id: stockInstance.id)
    }

    def delete(Long id) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "list")
            return
        }

        def label = stockInstance.stockIDLabel
        try {
            stockInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'stock.label', default: 'Stock'), label])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'stock.label', default: 'Stock'), label])
            redirect(action: "show", id: id)
        }
    }

    def print3(Integer id) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'individual.label', default: 'Individual'), id])
            redirect(action: "list")
            return
        }

        render(view: "label3", model: [stockInstance: stockInstance])
    }

    def printPdf(Integer id) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
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
        contentStream.drawString( "Name: ${stockInstance.labelStockName}" );
        contentStream.endText()

        contentStream.beginText()
        contentStream.setFont(fontBold,14)
        contentStream.moveTextPositionByAmount( leftMargin, 45 );
        contentStream.drawString( "${stockInstance.stockIDLabel}" );
        contentStream.endText();


        contentStream.beginText()
        contentStream.setFont(font,10)
        contentStream.moveTextPositionByAmount( 85, 45 );
        contentStream.drawString( "Fert: ${g:formatDate([date:stockInstance.fertilizationDate,type:"date",dateStyle: "short"])}" );
        contentStream.endText();

        if(stockInstance.comments) {
            contentStream.beginText()
            contentStream.setFont(font,8)
            contentStream.moveTextPositionByAmount( leftMargin, 30 );
            String labelComments = stockInstance.comments
            Integer maxWidth = 40
            if (labelComments.size() > maxWidth) {
                contentStream.drawString(labelComments.substring(0, maxWidth));
                contentStream.endText()

                contentStream.beginText()
                contentStream.moveTextPositionByAmount(leftMargin, 20);
                contentStream.drawString(labelComments.substring(maxWidth, labelComments.size()));
            } else {
                contentStream.drawString(labelComments);
            }
            contentStream.endText()
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

        String pdfFileName = "Stock${stockInstance.stockIDLabel}Label.pdf"
        response.addHeader("Content-Type", "application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=$pdfFileName")
        response.getOutputStream() << outputStream.toByteArray()
    }

    def print(Integer id) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'individual.label', default: 'Individual'), id])
            redirect(action: "list")
            return
        }

        render(view: "label", model: [stockInstance: stockInstance])
    }

    def findCommonLineForStocks() {
        log.debug  "params ${params}"
        def paternalStockId = params.paternalStockId
        def maternalStockId = params.maternalStockId
        log.debug  "finding common lines: ${paternalStockId} - ${maternalStockId}"
        Stock paternalStock = Stock.findById(paternalStockId as Long)
        Stock maternalStock = Stock.findById(maternalStockId as Long)

        if (paternalStock.line && paternalStock.line == maternalStock.line) {
            render paternalStock.line.id
            return
        }

        render ''
    }

    private getAllStockNames(){
//        List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")
        List<String> stockNames = new ArrayList<>()
        Stock.all.each{
            if(!stockNames.contains(it.stockName)){
                stockNames.add(it.stockName)
            }
        }
        return stockNames.sort(true)
    }

    private Map<Integer,List<Stock>> getAllParentStocks(Stock stock, Map<Integer,List<Stock>> parentStockMap,Integer currentLayer) {

        if (!stock.paternalStock && !stock.maternalStock) {
            return parentStockMap
        }

        List<Stock> parentStockList = parentStockMap.get(currentLayer)
        if(parentStockList==null){
            parentStockList = new ArrayList<>()
        }

        if (stock.paternalStock) {
            if(!parentStockList.contains(stock.paternalStock)){
                parentStockList.add(stock.paternalStock)
                parentStockMap.put(currentLayer,parentStockList)
//                parentStockMap.addAll(getAllParentStocks(stock.paternalStock, parentStockMap))
//                parentStockMap.put(currentLayer-1,getAllParentStocks(stock.paternalStock, parentStockMap,currentLayer-1))
                parentStockMap=getAllParentStocks(stock.paternalStock, parentStockMap,currentLayer-1)
            }
        }

        if (stock.maternalStock) {
            if(!parentStockList.contains(stock.maternalStock)) {
                parentStockList.add(stock.maternalStock)
                parentStockMap.put(currentLayer,parentStockList)
//                parentStockMap.addAll(getAllParentStocks(stock.maternalStock, parentStockMap))
//                parentStockMap.put(currentLayer-1,getAllParentStocks(stock.maternalStock, parentStockMap,currentLayer-1))
                parentStockMap=getAllParentStocks(stock.maternalStock, parentStockMap,currentLayer-1)
            }
        }

        parentStockList = parentStockList.unique(true)

        return parentStockMap
    }

    private Map<Integer,List<Stock>> getAllChildStocks(Stock stock, Map<Integer,List<Stock>> childStockMap,Integer currentLayer) {

//        return childStocks

        log.debug  "child stocks: ${stock.stockIDLabel}"
        List<Stock> broodStocks = Stock.findAllByPaternalStock(stock)
        log.debug  "broods: ${broodStocks.size()}"
        List<Stock> mareStocks = Stock.findAllByMaternalStock(stock)
        log.debug  "mare: ${broodStocks.size()}"

        List<Stock> childStockList = childStockMap.get(currentLayer)
        if(childStockList==null){
            childStockList = new ArrayList<>()
        }

        if (!broodStocks && !mareStocks) {
            childStockList.unique(true)
            childStockMap.put(currentLayer,childStockList)
            return childStockMap
        }

        log.debug  "evaluating: ${childStockMap.size()}"
        for (brood in broodStocks) {
            if(!childStockList.contains(brood)){
                childStockList.add(brood)
                childStockMap.put(currentLayer,childStockList)
//                childStockList.addAll(getAllChildStocks(brood, childStockMap,++currentLayer))
//                childStockMap.put(currentLayer+1,getAllChildStocks(brood, childStockMap,currentLayer+1))
                childStockMap = getAllChildStocks(brood, childStockMap,currentLayer+1)
            }
        }
        log.debug  "post-brood: ${childStockMap.size()}"

        for (mare in mareStocks) {
            if(!childStockList.contains(mare)) {
                childStockList.add(mare)
                childStockMap.put(currentLayer,childStockList)
//                childStockMap.addAll(getAllChildStocks(mare, childStockMap))
//                childStockMap.put(currentLayer+1,getAllChildStocks(mare, childStockMap,currentLayer+1))
                childStockMap = getAllChildStocks(mare, childStockMap,currentLayer+1)
            }
        }
        log.debug  "post-mare: ${childStockMap.size()}"

//        childStockMap = childStockList.unique(true)

        log.debug  "post-unique: ${childStockMap.size()}"

        return childStockMap
    }

    def findStock() {
        def stockID = params.id
        Stock stock = Stock.findById(stockID)
        render stock as JSON
    }

    def bracket() {

        def stockID = params.stockID
        Stock stock = Stock.findByStockID(stockID)

        TreeMap<Integer,List<Stock>> childStocks = getAllChildStocks(stock, new TreeMap<Integer,List<Stock>>(),1)
//        childStocks.values().sort(true, new Comparator<Stock>() {
//            @Override
//            int compare(Stock o1, Stock o2) {
//                return o1.stockID - o2.stockID
//            }
//        })
        TreeMap<Integer,List<Stock>> parentStocks = getAllParentStocks(stock, new TreeMap<Integer,List<Stock>>(),-1)
//        parentStocks.values().sort(true) { a, b ->
//            a.stockID - b.stockID
//        }

        [stockInstance: stock, childStocks: childStocks, parentStocks: parentStocks]

    }

    private def getParentArray(Stock stock) {

        if (!stock.paternalStock && !stock.maternalStock) return

        def paternalStock = stock.paternalStock
        def maternalStock = stock.maternalStock

        JSONArray winnerArray = new JSONArray()

        if (paternalStock && maternalStock) {
            def paternalObject = new JSONObject()
            paternalObject.put("name", paternalStock.stockIDLabel)
            paternalObject.put("winners", getParentArray(paternalStock))
            def maternalObject = new JSONObject()
            maternalObject.put("name", maternalStock.stockIDLabel)
            maternalObject.put("winners", getParentArray(maternalStock))

            winnerArray.add(paternalObject)
            winnerArray.add(maternalObject)
        }

        return winnerArray
    }

    private def getChildArray(Stock stock) {

        List<Stock> broods = Stock.findAllByPaternalStock(stock)
        List<Stock> mares = Stock.findAllByMaternalStock(stock)

        JSONArray winnerArray = new JSONArray()

        for (brood in broods) {
            def broodObject = new JSONObject()
            broodObject.put("name", brood.stockIDLabel)
            broodObject.put("challengers", getChildArray(brood))


            winnerArray.add(broodObject)
        }

        for (mare in mares) {
            def mareObject = new JSONObject()
            mareObject.put("name", mare.stockIDLabel)
            mareObject.put("challengers", getChildArray(mare))
            winnerArray.add(mareObject)
        }

        return winnerArray
    }

    def lineage() {

        def stockID = params.stockID ?: 108
        Stock stock = Stock.findByStockID(stockID)

        // root object!
        def jsonObject = new JSONObject()
        jsonObject.put("name", stock.stockIDLabel)
        jsonObject.put("winners", getParentArray(stock))
        jsonObject.put("challengers", getChildArray(stock))

        render jsonObject as JSON
    }


}
