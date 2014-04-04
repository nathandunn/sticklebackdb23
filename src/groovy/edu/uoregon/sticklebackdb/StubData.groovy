package edu.uoregon.sticklebackdb

import au.com.bytecode.opencsv.CSVReader
import edu.uoregon.sticklebackdb.*
import org.apache.log4j.Logger
import org.apache.shiro.crypto.hash.Sha256Hash

/**
 */
//@CompileStatic
class StubData {

    private static final log = Logger.getLogger(this)

    /* 
     * Return the file to import    
     */

    def getImportFile(String name) {
        def servletContext = org.codehaus.groovy.grails.web.context.ServletContextHolder.servletContext
        def file = servletContext.getResourceAsStream("/WEB-INF/import/" + name)
        if (!file) {
            throw new RuntimeException("File does not exist: " + file)
        }
        return file
    }

    /*
     * Stub all data
     */

    def stubAll() {
        stubUsers()
        stubLine()
        // stubGenetics()
        stubStock()
        stubIndividuals()
//        processIndividualLineage()
        processStockLineage()
    }

    def stubUsers() {
        println "evaluating researchers: ${Researcher.count}"
        if (Researcher.count > 0) return
//
        def adminRole = new ResearchRole(name: ResearcherService.ROLE_ADMINISTRATOR)
        adminRole.addToPermissions("*:*")
        adminRole.save(failOnError: true, insert: true, flush: true)
        println "saved admin role ${ResearchRole.count}"

        def userRole = new ResearchRole(name: ResearcherService.ROLE_USER)
//        userRole.addToPermissions("*:*")
        userRole.addToPermissions("*:list")
        userRole.addToPermissions("*:show")
        userRole.addToPermissions("*:index")
        userRole.addToPermissions("line:addLineToStock")
        userRole.addToPermissions("line:addLineToStockWithCapture")
        userRole.addToPermissions("stock:*")
        userRole.addToPermissions("individual:*")
        userRole.addToPermissions("researcher:edit")
        userRole.addToPermissions("researcher:update")

//        userRole.addToPermissions("*:list")
//        userRole.addToPermissions("*:show")

//        userRole.addToPermissions("experiment:edit")
//        userRole.addToPermissions("experiment:update")
//        userRole.addToPermissions("researcher:edit")
//        userRole.addToPermissions("researcher:update")
//        userRole.addToPermissions("strain:addFilter")
//        userRole.addToPermissions("strain:showFilter")
        userRole.save(failOnError: true, insert: true, flush: true)
        println "saved user role ${ResearchRole.count}"

//        "William A. Cresko <wcresko@uoregon.edu>\n" +
//                "Catchen Julian <jcatchen@uoregon.edu>\n" +
//                "Susan Bassham <sbassham@uoregon.edu>\n" +
//                "Mark Currey <mcurrey@uoregon.edu>\n" +
//                "Clay Small <csmall@uoregon.edu>\n" +
//                "Ann Petersen <annp@uoregon.edu>\n" +
//                "Kristin Sikkink <ksikkink@uoregon.edu>\n" +
//                "Kristin Alligood <kristin.alligood@gmail.com>\n" +
//                "Thom Nelson <tcn@uoregon.edu>\n" +
//                "Kate Ituarte <cituarte@uoregon.edu>\n" +
//                "Kat Milligan-Myhre <kmilliga@uoregon.edu>\n" +
//                "Erik S Parker <eparker@uoregon.edu>"
        addAdminResearcher("Mark Currey", "mcurrey@uoregon.edu")
        addAdminResearcher("Nathan Dunn", "ndunn@cas.uoregon.edu")
        addUserResearcher("William A. Cresko", "wcresko@uoregon.edu")
        addUserResearcher("Julian Catchen", "jcatchen@uoregon.edu")
        addUserResearcher("Susan Bassham", "sbassham@uoregon.edu")
        addUserResearcher("Clay Small", "csmall@uoregon.edu")
        addUserResearcher("Ann Petersen", "annp@uoregon.edu")
        addUserResearcher("Kristin Sikkink", "ksikkink@uoregon.edu")
        addUserResearcher("Kristin Alligood", "kristin.alligood@gmail.com")
        addUserResearcher("Thom Nelson", "tcn@uoregon.edu")
        addUserResearcher("Kate Ituarte", "cituarte@uoregon.edu")
        addUserResearcher("Kat Milligan-Myhre", "kmilliga@uoregon.edu")
        addUserResearcher("Erik S Parker", "eparker@uoregon.edu")

//
//        new Researcher(
//                firstName: "Adam"
//                , lastName: "Burns"
//                , username: "aburns2@uoregon.edu"
//                , passwordHash: new Sha256Hash("ilikesr16").toHex()
//        ).addToRoles(adminRole).save()
//
    }

    String generatePassword() {
//        return RandomStringUtils.random(10)
        return "testpass123"
    }

    def addUserResearcher(String name, String email) {
        ResearchRole userRole = ResearchRole.findByName(ResearcherService.ROLE_USER)

        addResearcher(name, email, userRole)

        println "Added user researcher ${name}"

//        println "Added role ${name}"

    }

    def addAdminResearcher(String name, String email) {
        ResearchRole adminRole = ResearchRole.findByName(ResearcherService.ROLE_ADMINISTRATOR)

        addResearcher(name, email, adminRole)

        println "Added admin researcher ${name}"

//        println "Added role ${name}"

    }

    def addResearcher(String name, String email, ResearchRole role) {
        Researcher researcher = new Researcher(
                name: name
                , username: email
                , passwordHash: new Sha256Hash(generatePassword()).toHex()
        ).save(insert: true, flush: true)

        researcher.addToRoles(role)

        researcher.save(flush: true)

    }
/*
     * Grab the line data 
     */

    def stubLine() {

        CSVReader csvReader = getImportFile("lines.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        csvReader.eachLine { tokens ->
            Line line = new Line()

            // Read in the needed line information
            line.name = tokens[4]   // lineName
            line.comment = tokens[3]  // lineComments
            line.species = tokens[6]  // lineSpecies
            if (!line.species) {
                line.species = line.sticklebackSpecies
            }

            line.save(flush: true, insert: true, failOnError: true)
        }

        Line.all.each { it ->
            Population population = new Population(
                    identification: it.name
                    , comment: "Imported from line with comment ${it.comment}"
            ).save flush: true, insert: true, failOnError: true

            Capture capture = new Capture(
                    population: population
                    , comment: "created on import from line"
                    , line: it
            ).save flush: true, insert: true, failOnError: true
        }
    }

    /*
     * Grab the stock data
     */

    def stubStock() {
        //        Stock.deleteAll(Stock.all)
        CSVReader csvReader = getImportFile("stocks.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        println "start stub stocks"

        csvReader.eachLine { tokens ->

            if (tokens.size() > 5) {
                // Create a new stock
                Stock stock = new Stock()

                try {

                    if (tokens.size() < 38)
                        println "Error reading stock # " + tokens[0] + ", skipping, not enough information."
                    else {
                        // Population (find using column X)
                        stock.line = tokens[23]?.size() > 0 ? Line.findByName(tokens[23]) : null

                        // Stock ID (column O)    
                        stock.stockID = tokens[14]?.size() > 0 ? tokens[14].toDouble() : null

                        // Fertilization date (column D)
                        try {
                            stock.fertilizationDate = tokens[3]?.size() > 0 ? Date.parse("mm/dd/yy", tokens[3]) : null
                            // fertilization Date
                        } catch (e) {
                            println "error parsing fertilization date: " + e
                        }

                        // Researcher comments (column AK)
                        stock.comments = tokens[36]?.size() > 0 ? tokens[36] : null

                        // Stock name (column AM)
                        stock.stockName = tokens[38]?.size() > 0 ? tokens[38] : null

                        // Maternal Individual ID (column Q)
                        String maternalIndividualID = tokens[16].size() > 0 ? tokens[16] : null
                        if (maternalIndividualID != null) {
                            stock.maternalIndividualID = maternalIndividualID
                        } else {
                            stock.maternalIndividualID = null
                        }
                        // Maternal stock ID (column R)
                        def maternalStockID = tokens[17].size() > 0 ? Math.round(tokens[17].toDouble()) : null
                        if (maternalStockID != null) {
                            stock.maternalStockID = maternalStockID
                        } else {
                            stock.maternalStockID = null
                        }
                        // Paternal Individual ID (column T)
                        String paternalIndividualID = tokens[19].size() > 0 ? tokens[19] : null
                        if (paternalIndividualID != null) {
                            stock.paternalIndividualID = paternalIndividualID
                        } else {
                            stock.paternalIndividualID = null
                        }
                        // Paternal stock ID (column U)
                        def paternalStockID = tokens[20].size() > 0 ? Math.round(tokens[20].toDouble()) : null
                        if (paternalStockID != null) {
                            stock.paternalStockID = paternalStockID
                        } else {
                            stock.paternalStockID = null
                        }

                        stock.save(flush: true, insert: true, failOnError: true)
                        if (stock.stockID == 108) {
                            println "saved stock ${stock.stockID} label[${stock.stockIDLabel}] maternal ind id:[${stock.maternalIndividualID}] maternal ind:[${stock.maternalIndividual}]"
                        }
                    }
                }
                catch (e) {
                    println "error saving output: " + e
                }
            }
        }
        println "finished stubbing stocks: " + Stock.count()
    }

    /*
     *  Grab the individuals data
     */

    def stubIndividuals() {
        CSVReader csvReader = getImportFile("individuals.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        println "start stub individuals"
        Integer count = 0;
        csvReader.eachLine { tokens ->

//            if (tokens.size() > 15 && tokens[18]?.length() > 0 && tokens[19]?.length() > 0) {
            if (tokens.size() > 15 && tokens[18]?.length() > 0) {

                Individual individual = new Individual()

                // Individual ID (column U)
//                if (tokens[20]?.length() > 0 && tokens[20].contains(".")) {
                if (tokens[19]?.length() > 0) {
//                    individual.individualID = (tokens[20].split("\\.")[1]) as Integer
                    individual.individualID = tokens[19] as Integer
                } else {
                    return
                }

                // Stock ID (column S)
                individual.stockID = tokens[18].toDouble()

                // Stock (from column S)
                individual.stock = Stock.findByStockID(individual.stockID)

                // Fertlization date (column H)
//                individual.fertilizationDate = tokens[7]?.size() > 0 ? Date.parse("mm/dd/yy", tokens[7]) : null

                // Maternal stock & individual lineage (column W)
//                def maternalStockID = tokens[22]?.size() > 0 ? tokens[22].toString() : null
//                if (maternalStockID != null) {
//
//                    // get the stock ID
//                    def sID = maternalStockID.split("\\.")[0] as Double
//
//                    // get the individual ID
//                    def iID = maternalStockID as Double
//
//                    // set the values in the individual
//                    individual.maternalStockID = sID
//                    individual.maternalIndividualID = iID
//                } else {
//                    individual.maternalStockID = null
//                    individual.maternalIndividualID = null
//                }

                // Paternal stock lineage (column X)
//                def paternalStockID = tokens[23]?.size() > 0 ? tokens[23].toString() : null
//                if (paternalStockID != null) {
//
//                    // get the stock ID
//                    def sID = paternalStockID.split("\\.")[0] as Double
//
//                    // get the individual ID
//                    def iID = paternalStockID as Double
//
//                    // set the values in the individual
//                    individual.paternalStockID = sID
//                    individual.paternalIndividualID = iID
//                } else {
//                    individual.paternalStockID = null
//                    individual.paternalIndividualID = null
//                }

                // Fish location (column Q)
                individual.somaLocation = tokens[16].size() > 0 ? tokens[16] : null

                // Researcher comments
                individual.comments = tokens[26]?.size() > 0 ? tokens[26] : null

                // Fish sex (column R)
                individual.fishSex = tokens[17]?.size() > 0 ? tokens[17] : null

                individual.save(flush: true, insert: true, failOnError: true)
            } else {
                println "ignore line ${count} ${tokens?.size()} ,  ${tokens[0]} ${tokens}"
            }
            ++count
        }
        println "Individuals ${Individual.count()} added "
    }

    /*
     * Process the lineage of individuals
     */

//    def processIndividualLineage() {
//        CSVReader csvReader = getImportFile("individuals.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
//
//        println "start processing individuals"
//        csvReader.eachLine { tokens ->
////            if (tokens.size() > 15 && tokens[18]?.length() > 1 && tokens[19]?.length() > 1) {
//            if (tokens.size() > 15 && tokens[18]?.length() > 0) {
//
//                // Get the individual object, if one exists
//                Individual individual
//
//                // Get the individual
//                if (tokens[20]?.length() > 1 && tokens[20].contains(".")) {
//                    Integer individualID = tokens[20].split("\\.")[1] as Integer
//                    individual = Individual.findByIndividualID(individualID)
//                }
//
//                // If we have an individual
//                if (individual) {
//
//                    // Get the maternal stock
//                    if (individual.maternalStockID != null) {
//                        if (Stock.findByStockID(individual.maternalStockID) != null)
//                            individual.maternalStock = Stock.findByStockID(individual.maternalStockID)
//                        else
//                            individual.maternalStock = null
//                    }
//
//                    // Get the paternal stock
//                    if (individual.paternalStockID != null) {
//                        if (Stock.findByStockID(individual.paternalStockID) != null)
//                            individual.paternalStock = Stock.findByStockID(individual.paternalStockID)
//                        else
//                            individual.paternalStock = null
//                    }
//
//                    // Get the maternal individual
//                    if (individual.maternalIndividualID != null) {
//                        if (Individual.findByIndividualID(individual.maternalIndividualID) != null)
//                            individual.maternalIndividual = Individual.findByIndividualID(individual.maternalIndividualID)
//                        else
//                            individual.maternalIndividual = null
//                    }
//
//                    // Get the paternal individual
//                    if (individual.paternalIndividualID != null) {
//                        if (Individual.findByIndividualID(individual.paternalIndividualID) != null)
//                            individual.paternalIndividual = Individual.findByIndividualID(individual.paternalIndividualID)
//                        else
//                            individual.paternalIndividual = null
//                    }
//
//                    individual.save(flush: true, insert: false)
//                }
//            }
//        }
//        println "finished processing Individuals ${Individual.count()} individuals added "
//    }

    /*
     * Process the lineage of stock
     */

    def processStockLineage() {
        CSVReader csvReader = getImportFile("stocks.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        println "start processing stocks"
        csvReader.eachLine { tokens ->
            try {

                if (tokens.size() > 5) {

                    // Get the stock object
                    def stockID = tokens[14]?.size() > 0 ? tokens[14].toDouble() : null
                    Stock stock = Stock.findByStockID(stockID)

                    // If we have a stock
                    if (stock) {

                        // Get the maternal stock fron the saved ID
                        if (stock.maternalStockID != null) {
                            if (Stock.findByStockID(stock.maternalStockID) != null)
                                stock.maternalStock = Stock.findByStockID(stock.maternalStockID)
                        }

                        // Get the paternal stock from the maternal stock ID
                        if (stock.paternalStockID != null) {
                            if (Stock.findByStockID(stock.paternalStockID) != null) {
                                stock.paternalStock = Stock.findByStockID(stock.paternalStockID)
                            }
                        }

                        // Get the maternal individual
                        try {
                            if (stock.maternalIndividualID != null) {
//                            Integer maternalStockID = stock.maternalIndividualID.split("\\.")[0] as Integer

                                Integer maternalIndividualID
                                if (stock.maternalIndividualID.indexOf('.') > 0) {
                                    maternalIndividualID = stock.maternalIndividualID.split("\\.")[1] as Integer
                                } else {
                                    maternalIndividualID = stock.maternalIndividualID as Integer
                                }

//                                Integer maternalIndividualID = stock.maternalIndividualID.split("\\.")[1] as Integer
//
                                if (Individual.findByIndividualIDAndStock(maternalIndividualID, stock.maternalStock) != null) {
                                    stock.maternalIndividual = Individual.findByIndividualIDAndStock(maternalIndividualID, stock.maternalStock)
                                }
                            }
                        }
                        catch (Exception me) {
                            println "failed to process maternal individual ${stock.maternalIndividualID} for stock ${stock.stockIDLabel} for: ${me.fillInStackTrace()}"
                        }

                        // Get the paternal individual
                        try {
                            if (stock.paternalIndividualID != null) {
                                Integer paternalIndividualID
                                if (stock.paternalIndividualID.indexOf('.') > 0) {
                                    paternalIndividualID = stock.paternalIndividualID.split("\\.")[1] as Integer
                                } else {
                                    paternalIndividualID = stock.paternalIndividualID as Integer
                                }

                                if (Individual.findByIndividualIDAndStock(paternalIndividualID, stock.paternalStock) != null) {
                                    stock.paternalIndividual = Individual.findByIndividualIDAndStock(paternalIndividualID, stock.paternalStock)
                                }
                            }
                        }
                        catch (Exception oe) {
                            println "failed to process paternal individual ${stock.paternalIndividualID} for stock ${stock.stockIDLabel} for ${oe.fillInStackTrace()}"
                        }

                        stock.save(flush: true, insert: false)
                    }
                }
            }
            catch (e) {
                println "error processing line ${tokens} \n ${e}"
            }
        }
        println "finished processing stocks: " + Stock.count()
    }

    // !! Not Used !!
    def stubGenetics() {
        //        Genetics.deleteAll(Genetics.all)
        println "start stub population"
        CSVReader csvReader = getImportFile("genetics.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        csvReader.eachLine { tokens ->
            if (tokens.size() > 2) {

                Population population = new Population()

                try {
                    // genetics.captureDate = tokens[0]?.size() > 0 ? Date.parse("mm/dd/yy", tokens[0]) : null // captureDate
                    population.identification = tokens[1]   // identification
                    population.comment = tokens[5]  // researcherCommentsGenetics
//                    population.source = tokens[7]  // source
//                    population.species = tokens[8]  // species
                } catch (e) {
                    println "error parsing identifier " + e
                }

                population.save(flush: true, insert: true)
            }
            //            else {
            //                println "nothing to parse"
            //            }
        }
        println "FINISHED stub population " + Population.count()
    }
}