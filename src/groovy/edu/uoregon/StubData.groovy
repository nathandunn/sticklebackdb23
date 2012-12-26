package edu.uoregon

import au.com.bytecode.opencsv.CSVReader
import edu.uoregon.sticklebackdb.Line
import org.apache.log4j.Logger

/**
 */
class StubData {

    private static final log = Logger.getLogger(this)

    def stubLine() {

        if (Line.count() > 0) return

        File f2 = new File(".")
        log.error "current file location: " + f2.absolutePath

        def servletContext = org.codehaus.groovy.grails.web.context.ServletContextHolder.servletContext
        def file = servletContext.getResourceAsStream("/WEB-INF/import/lines.csv")
        if (!file) {
            throw new RuntimeException("File does not exist: " + file)
        }

//        file.eachCsvLine([skipLines:1,'charset':'UTF-8']) { tokens ->
        CSVReader csvReader = file.toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        csvReader.eachLine { tokens ->
            Line line = new Line()
            line.captured = (tokens[0] == "Yes")
            line.captureKey = tokens[2]
            line.comment = tokens[3]
            line.name = tokens[4]
            line.abbreviation = tokens[5]
            line.species = tokens[6]
            try {
                line.status = tokens[7]?.length()>0 ? tokens[7]?.toInteger() : null
            } catch (e) {
                println "error: " + e
            }
            if(tokens.size()>12){
                line.storedLineName = tokens[12]
                line.storedLineNameAbbreviation = tokens[13]
            }
            line.save(insert: true, flush: true)
        }
    }

}
