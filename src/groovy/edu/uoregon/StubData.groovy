package edu.uoregon
import au.com.bytecode.opencsv.CSVReader
import edu.uoregon.sticklebackdb.*
import org.apache.log4j.Logger
/**
 */
class StubData {

    private static final log = Logger.getLogger(this)

    def getImportFile(String name) {
        def servletContext = org.codehaus.groovy.grails.web.context.ServletContextHolder.servletContext
//        def file = servletContext.getResourceAsStream("/WEB-INF/import/lines.csv")
        def file = servletContext.getResourceAsStream("/WEB-INF/import/" + name)
        if (!file) {
            throw new RuntimeException("File does not exist: " + file)
        }
        return file
    }

    def stubAll() {
        stubLine()
        stubContainer()
        stubGenetics()
        stubStock()
        stubAquaria()
        stubIndividuals()
        processIndividualLineage()
        processStockLineage()
    }

    def stubLine() {

//        if (Line.count() > 0) return
//        Line.deleteAll(Line.all)

//        file.eachCsvLine([skipLines:1,'charset':'UTF-8']) { tokens ->
        CSVReader csvReader = getImportFile("lines.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        csvReader.eachLine { tokens ->
            Line line = new Line()

            line.captured = (tokens[0] == "Yes")
            line.captureKey = tokens[2]
            line.comment = tokens[3]
            line.name = tokens[4]
            line.abbreviation = tokens[5]
            line.species = tokens[6]
            try {
                line.status = tokens[7]?.length() > 0 ? tokens[7]?.toInteger() : null
            } catch (e) {
                println "error: " + e
            }
            if (tokens.size() > 12) {
                line.storedLineName = tokens[12]
                line.storedLineNameAbbreviation = tokens[13]
            }

            line.save(flush: true, insert: true)
        }
        println "FINISHED stub line " + Line.count()
    }

    def stubContainer() {
//        Container.deleteAll(Container.all)
        CSVReader csvReader = getImportFile("containerData.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        csvReader.eachLine { tokens ->
            Container container = new Container()
            container.barcode = tokens[1]
            container.location = tokens[2]
            container.size = tokens[4]?.size() > 0 ? tokens[4]?.toInteger() : null
            container.total = tokens[5]?.size() > 0 ? tokens[5]?.toInteger() : null

            container.save(flush: true, insert: true)
        }
        println "FINISHED stub container" + Container.count()
    }

    def stubGenetics() {
//        Genetics.deleteAll(Genetics.all)
        println "start stub genetics"
        CSVReader csvReader = getImportFile("genetics.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        csvReader.eachLine { tokens ->
            if (tokens.size() > 2) {

                Genetics genetics = new Genetics()

                try {
                    genetics.captureDate = tokens[0]?.size() > 0 ? Date.parse("mm/dd/yy", tokens[0]) : null
                    genetics.identification = tokens[1]
                    genetics.comments = tokens[5]
                    genetics.source = tokens[7]
                    genetics.species = tokens[8]
                } catch (e) {
                    println "error parsing identifier " + e
                }

                genetics.save(flush: true, insert: true)
            }
//            else {
//                println "nothing to parse"
//            }
        }
        println "FINISHED stub genetics " + Genetics.count()
    }

    def stubAquaria() {
//        Aquaria.deleteAll(Aquaria.all)
        println "start stub aquaria"
        CSVReader csvReader = getImportFile("sbAquaria.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        csvReader.eachLine { tokens ->

            Aquaria aquaria = new Aquaria()

            aquaria.container = Container.findByBarcode(tokens[0])

            aquaria.fishTotal = tokens[3]?.size() > 0 ? tokens[3].toInteger() : null

            aquaria.fishUnsexed = tokens[5]?.size() > 0 ? tokens[5].toInteger() : null

            aquaria.statusContainer = tokens[7]?.size() > 0 ? tokens[7].toInteger() : null
            aquaria.statusFishQuantity = tokens[8]?.size() > 0 ? tokens[8].toInteger() : null
            aquaria.statusStock = tokens[9]?.size() > 0 ? tokens[9].toInteger() : null

            aquaria.stock = tokens[4]?.size() > 0 ? Stock.findByBarcode(tokens[4].split("\\.")[0]) : null


            aquaria.save(flush: true, insert: true, failOnError: true)
        }
        println "finished stub aquaria: " + Aquaria.count()
    }

    def stubStock() {
//        Stock.deleteAll(Stock.all)
        CSVReader csvReader = getImportFile("stocks.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        println "start stub stocks"
        csvReader.eachLine { tokens ->
            if (tokens.size() > 5) {
                Stock stock = new Stock()
                try {
                    stock.barcode = tokens[14] as Integer

                    try {
                        stock.crossDate = tokens[1]?.size() > 0 ? Date.parse("mm/dd/yyyy", tokens[1]) : null
                    } catch (e) {
                        println "error parsing crossDAte: " + e
                    }
                    stock.crossType = tokens[2]?.size() > 0 ? tokens[2].toInteger() : null

                    try {
                        // TODO . . . somehow capture fertilization time as well
                        stock.fertilizationDateTime = tokens[3]?.size() > 0 ? Date.parse("mm/dd/yyyy", tokens[3]) : null
                    } catch (e) {
                        println "error parsing fertilization date/time: " + e
                    }

                    stock.fishAgeDays = tokens[7]?.size() > 0 ? tokens[7].toInteger() : null
                    stock.fishStock = tokens[14]?.size() > 0 ? tokens[14].toInteger() : null


                    if (tokens[15]?.size() > 2 && tokens[15]?.contains("\\.")) {
                        stock.maternalIndividualLabel = tokens[15]
                        String maternalStockID = tokens[15].substring(tokens[15].indexOf("\\."))
                        stock.maternalIndividual = Stock.findByFishStock(maternalStockID)
                    }

                    if (tokens[18]?.size() > 2 && tokens[18]?.contains("\\.")) {
                        stock.paternalIndividualLabel = tokens[15]
                        String paternalStockID = tokens[18].substring(tokens[18].indexOf("\\."))
                        stock.paternalIndividual = Stock.findByFishStock(paternalStockID)
                    }

                    stock.line = tokens[23]?.size() > 0 ? Line.findByName(tokens[23]) : null

                    stock.nursery1InitialNumber = tokens[25]?.size() > 0 ? tokens[25].toInteger() : null
                    stock.nursery2Fertiles = tokens[26]?.size() > 0 ? tokens[26].toInteger() : null
                    stock.nursery3Hatched = tokens[28]?.size() > 0 ? tokens[28].toInteger() : null
                    stock.nursery5SecondStageSurvivors = tokens[34]?.size() > 0 ? tokens[34].toInteger() : null
                    stock.nursery6Graduates = tokens[35]?.size() > 0 ? tokens[35].toInteger() : null

                    if (tokens.size() > 35) {
                        stock.comment = tokens[36]
                        stock.breedingType = tokens[37]?.size() > 0 ? tokens[37].toInteger() : null

                        stock.name = tokens[38]
                        stock.abbreviation = tokens[39]

                        stock.containerStatus = tokens[41]
                        stock.experimentStatus = tokens[42]
                        stock.groupStatus = tokens[43]
                        stock.individualStatus = tokens[44]

                        stock.stockType = tokens[45]?.size() > 0 ? Genetics.findByIdentification(tokens[45]) : null
                    }

                    stock.save(flush: true, insert: true, failOnError: true)
                }
                catch (e) {
                    println "error saving output: " + e
                }
            }
        }
        println "finished stubbing stocks: " + Stock.count()
    }


    def stubIndividuals() {
        CSVReader csvReader = getImportFile("individuals.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        println "start stub individuals"
        csvReader.eachLine { tokens ->
            if (tokens.size() > 15 && tokens[18]?.length() > 1 && tokens[19]?.length() > 1) {

                Individual individual = new Individual()
                individual.stock = Stock.findByBarcode(tokens[18] as Integer)
                individual.index = tokens[19] as Integer
                individual.fertilization = tokens[7]?.size() > 0 ? Date.parse("mm/dd/yyyy", tokens[7]) : null

                individual.stockDate = tokens[28]?.size() > 0 ? Date.parse("mm/dd/yyyy", tokens[28]) : null
                individual.stockIndividualDate = tokens[29]?.size() > 0 ? Date.parse("mm/dd/yyyy", tokens[29]) : null
                individual.save(flush: true, insert: true)
            }
        }
        println "Individuals ${Individual.count()} added "
    }

    def processIndividualLineage() {
        CSVReader csvReader = getImportFile("individuals.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        println "start processing individuals"
        csvReader.eachLine { tokens ->
            if (tokens.size() > 15 && tokens[18]?.length() > 1 && tokens[19]?.length() > 1) {

                Stock stock = Stock.findByBarcode(tokens[18] as Integer)
                Integer index = tokens[19] as Integer

                Individual individual = Individual.findByStockAndIndex(stock, index)
                if (individual) {


                    def maternalId = tokens[22]
                    if (maternalId) {
//                        println "maternalID ${maternalId}"
                        if (maternalId.contains(".")) {
                            def maternalStockId = maternalId.split("\\.")[0] as Integer
                            def maternalIndexId = maternalId.split("\\.")[1] as Integer
                            Stock maternalStock = Stock.findByBarcode(maternalStockId)
//                            println "found stock ${maternalStock} with ${maternalStockId} and ${maternalIndexId}"
                            if (maternalStock) {
                                Individual individual1 = Individual.findByIndexAndStock(maternalIndexId, maternalStock)
//                                println "found individual ${individual1} with stock ${maternalStock} and maternalIndexId ${maternalIndexId}"
                                individual.maternal = Individual.findByIndexAndStock(maternalIndexId, maternalStock)
                            }
                        } else {
                            Stock maternalStock = Stock.findByBarcode(maternalId as Integer)
//                            println "alternate found stock ${maternalStock} with ${maternalId} "
                            if (maternalStock) {
                                individual.maternal = Individual.findAllByStock(maternalStock)[0]
                            }
                        }
//                        println "added maternal ${individual.maternal}"
                    }

                    def paternalId = tokens[23]
                    if (paternalId) {
                        if (paternalId.contains(".")) {
                            def paternalStockId = paternalId.split("\\.")[0] as Integer
                            def paternalIndexId = paternalId.split("\\.")[1] as Integer
                            Stock paternalStock = Stock.findByBarcode(paternalStockId)
                            if (paternalStock) {
                                individual.paternal = Individual.findByIndexAndStock(paternalIndexId, paternalStock)
                            }
                        } else {
                            Stock paternalStock = Stock.findByBarcode(paternalId as Integer)
                            if (paternalStock) {
                                individual.paternal = Individual.findAllByStock(paternalStock)[0]
                            }
                        }
                    }
                }

                individual.save(flush: true, insert: false)
            }
        }
        println "finished processing Individuals ${Individual.count()} lineage added "
    }

    def processStockLineage() {
        CSVReader csvReader = getImportFile("stocks.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        println "start processing stocks"
        csvReader.eachLine { tokens ->
            try{

            if (tokens.size() > 5) {
//                println "trying to process ${tokens[14]}"
                Stock stock = Stock.findByBarcode(tokens[14] as Integer)
//                println "found stock ${stock} for ${tokens[14] as Integer}"
                if (stock && stock!=null && stock.id !=null) {
//                    println  "maternal token ${tokens[16]} and paternal token ${tokens[19]}"
                    if (tokens[16] && tokens[16].size() > 0 && tokens[16].contains("\\.")) {
//                        println "adding maternal for ${tokens[16]}"
                        def maternalStockID = tokens[16].substring(tokens[16].indexOf("\\.")) as Integer
                        stock.maternalIndividual = Individual.findByStockAndIndex(stock, maternalStockID)
//                        println "addED  maternal for ${stock.maternalIndividual} using ${maternalStockID}"
                    }

                    if (tokens[19] && tokens[19].size() > 0 && tokens[19].contains("\\.")) {
//                        println "adding paternal for ${tokens[19]}"
//                        stock.paternalIndividualLabel = tokens[15]
                        def paternalStockID = tokens[19].substring(tokens[19].indexOf("\\.")) as Integer
                        stock.paternalIndividual = Individual.findByStockAndIndex(stock, paternalStockID)
//                        println "addED  paternal for ${stock.paternalIndividual} using ${paternalStockID}"
                    }

                    stock.save(flush: true, insert: false)
                }
//                else{
//                    println "not prociessing ${tokens[14]} was null "
//                }
            }
            }
            catch(e){
                println "error processing line ${tokens} \n ${e}"
            }
        }

        println "finished processing stocks: " + Stock.count()

    }
}
