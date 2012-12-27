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

    def stubLine() {

//        if (Line.count() > 0) return
        Line.deleteAll(Line.all)

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
    }

    def stubGenetics() {
//        Genetics.deleteAll(Genetics.all)
        println "start stub genetics"
        CSVReader csvReader = getImportFile("genetics.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        csvReader.eachLine { tokens ->
            Genetics genetics = new Genetics()
            println "garsing genetics: " + tokens

            try {
                genetics.captureDate = tokens[0]?.size() > 0 ? Date.parse("mm/dd/yy", tokens[0]) : null
                genetics.identification = tokens[1]
                genetics.comments = tokens[5]
                genetics.source = tokens[7]
                genetics.species = tokens[8]
            } catch (e) {
                println "error parsing identifier " + e
            }


            println "finishing stub genetics"
            genetics.save(flush: true, insert: true)
        }
        println "FINISHED stub genetics"
    }

    def stubAquaria() {
//        Aquaria.deleteAll(Aquaria.all)
        println "start stub aquaria"
        CSVReader csvReader = getImportFile("sbAquaria.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        csvReader.eachLine { tokens ->
            Aquaria aquaria = new Aquaria()

            aquaria.fishTotal = tokens[9]?.size()>0 ? tokens[9].size().toInteger() : null
            aquaria.fishUnsexed = tokens[10]?.size()>0 ? tokens[10].size().toInteger() : null
            aquaria.statusContainer = tokens[12]?.size()>0 ? tokens[12].size().toInteger() : null
            aquaria.statusFishQuantity =tokens[13]?.size()>0 ? tokens[13].size().toInteger() : null
            aquaria.statusStock = tokens[14]?.size()>0 ? tokens[14].size().toInteger() : null


            aquaria.container = Container.findByBarcode(tokens[2])


            aquaria.save(flush: true, insert: true)
        }
    }

    def stubStock() {
//        Stock.deleteAll(Stock.all)
        CSVReader csvReader = getImportFile("stocks.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        csvReader.eachLine { tokens ->
            Stock stock = new Stock()

            stock.save(flush: true, insert: true)
        }
    }
}
