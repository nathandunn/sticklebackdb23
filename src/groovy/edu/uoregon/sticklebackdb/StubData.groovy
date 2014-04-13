package edu.uoregon.sticklebackdb

import au.com.bytecode.opencsv.CSVReader
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

                        /**
                         * Should be of the form sbCrsXXX
                         */
                        // Maternal Individual ID (column Q)
                        String maternalIndividualID = tokens[117].size() > 0 ? tokens[117] : null
                        if (maternalIndividualID != null) {
                            stock.maternalIndividualID = maternalIndividualID
                        } else {
                            stock.maternalIndividualID = null
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
     * Process the lineage of stock
     */
    def processStockLineage() {
        Map<String, String> femaleMap = new HashMap<>()
        Map<String, String> maleMap = new HashMap<>()
        CSVReader csvReader = getImportFile("crosses.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        println "start processing stocks"
        csvReader.eachLine { tokens ->
            try {

                if (tokens.size() > 5 && tokens[1]?.size() > 0) {
                    String key = tokens[17]
                    String individualID = tokens[1]
                    if (tokens[5] == "male") {
                        maleMap.put(key, individualID)
                    } else {
                        femaleMap.put(key, individualID)
                    }
                }


            }
            catch (e) {
                println "error processing line ${tokens} \n ${e}"
            }
        }

        Stock.all.each { stock ->

            if (stock.maternalIndividualID != null) {
                String individualIDValue
                if (femaleMap.containsKey(stock.maternalIndividualID)) {
                    individualIDValue = femaleMap.get(stock.maternalIndividualID)
                    println "maternalID: ${individualIDValue}"
                    String[] ids = individualIDValue.split("\\.")

                    Integer stockID = ids[0] as Integer
                    Stock maternalStock = Stock.findByStockID(stockID)
                    Integer individualID = ids[2] as Integer

                    Individual individual = Individual.findByStockAndIndividualID(maternalStock, individualID)
                    if (individual) {
                        println "individual found ${individual.individualIDLabel} mom of stock ${stock.stockIDLabel}"
                        if (individual.fishSex == "male") {
                            println "fixing sex from ${individual.fishSex} to female"
                            individual.fishSex = "female"
                            individual.save()
                        }
                        stock.maternalIndividual = individual
                        stock.maternalStock = individual.stock
                        stock.save(flush: true)
                    } else {
                        println "no maternal individual found for ${individualIDValue}"
                    }
                }
                else
                if (maleMap.containsKey(stock.maternalIndividualID)) {
                    individualIDValue = maleMap.get(stock.maternalIndividualID)
                    println "paternalID: ${individualIDValue}"
                    String[] ids = individualIDValue.split("\\.")

                    Integer stockID = ids[0] as Integer
                    Stock paternalStock = Stock.findByStockID(stockID)
                    Integer individualID = ids[2] as Integer

                    Individual individual = Individual.findByStockAndIndividualID(paternalStock, individualID)
                    if (individual) {
                        println "individual found ${individual.individualIDLabel} mom of stock ${stock.stockIDLabel}"
                        if (individual.fishSex == "female") {
                            println "fixing sex from ${individual.fishSex} to female"
                            individual.fishSex = "male"
                            individual.save()
                        }
                        stock.maternalIndividual = individual
                        stock.maternalStock = individual.stock
                        stock.save(flush: true)
                    } else {
                        println "no paternal individual found for ${individualIDValue}"
                    }
                }
                else {
                    println "no cross ${stock.maternalIndividualID} found for stock ${stock.stockIDLabel}"
                }
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