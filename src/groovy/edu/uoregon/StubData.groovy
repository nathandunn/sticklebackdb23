package edu.uoregon
import au.com.bytecode.opencsv.CSVReader
import edu.uoregon.sticklebackdb.*
import org.apache.log4j.Logger
/**
 */
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
        stubLine()
        // stubGenetics()
        stubStock()
        stubIndividuals()
        processIndividualLineage()
        processStockLineage()
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
            
            line.gps = "lat,lon"            
            
            line.save(flush: true, insert: true)
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
                    
                    if(tokens.size() < 38)
                    println "Error reading stock # " + tokens[0] + ", skipping, not enough information."
                    else{                    
                        // Population (find using column X)
                        stock.line = tokens[23]?.size() > 0 ? Line.findByName(tokens[23]) : null

                        // Stock ID (column O)    
                        stock.stockID = tokens[14]?.size() > 0 ? tokens[14].toDouble() : null
                        stock.stockIDLabel = String.format("%.4f", stock.stockID)
                             
                        // Fertilization date (column D)
                        try {
                            stock.fertilizationDate = tokens[3]?.size() > 0 ? Date.parse("mm/dd/yy", tokens[3]) : null  // fertilization Date
                        } catch (e) {
                            println "error parsing fertilization date: " + e
                        }
                    
                        // Researcher comments (column AK)
                        stock.comments = tokens[36]?.size() > 0 ? tokens[36] : null 
                    
                        // Stock name (column AM)
                        stock.stockName = tokens[38]?.size() > 0 ? tokens[38] : null
                                                            
                        // Maternal Individual ID (column Q)
                        def maternalIndividualID = tokens[16].size() > 0 ? tokens[16].toDouble() : null
                        if(maternalIndividualID != null){
                          stock.maternalIndividualID = maternalIndividualID
                          stock.maternalIndividualIDLabel = String.format("%.4f", maternalIndividualID)
                        }
                        else{
                            stock.maternalIndividualID = null
                            stock.maternalIndividualIDLabel = null
                        }
                        // Maternal stock ID (column R)
                        def maternalStockID = tokens[17].size() > 0 ? tokens[17].toDouble() : null
                        if(maternalStockID != null){
                            stock.maternalStockID = maternalStockID
                            stock.maternalStockIDLabel = String.format("%.4f", maternalStockID)
                        }
                        else{
                            stock.maternalStockID = null
                            stock.maternalStockIDLabel = null
                        }                   
                        // Paternal Individual ID (column T)
                        def paternalIndividualID = tokens[19].size() > 0? tokens[19].toDouble() : null
                        if(paternalIndividualID != null){
                            stock.paternalIndividualID = paternalIndividualID
                            stock.paternalIndividualIDLabel = String.format("%.4f",  paternalIndividualID)                    
                        }
                        else{
                            stock.paternalIndividualID = null
                            stock.paternalIndividualIDLabel = null
                        }
                        // Paternal stock ID (column U)
                        def paternalStockID = tokens[20].size() > 0 ? tokens[20].toDouble() : null
                        if(paternalStockID != null){
                            stock.paternalStockID = paternalStockID                            
                            stock.paternalStockIDLabel = String.format("%.4f", paternalStockID)
                        }
                        else{
                            stock.paternalStockID = null
                            stock.paternalStockIDLabel = null
                        }
                        
                        stock.save(flush: true, insert: true, failOnError: true)
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
        csvReader.eachLine { tokens ->
            
            if (tokens.size() > 15 && tokens[18]?.length() > 1 && tokens[19]?.length() > 1) {

                Individual individual = new Individual()
               
                // Individual ID (column U)
                if(tokens[20]?.length() > 1){
                    individual.individualID = tokens[20].toDouble()
                    individual.individualIDLabel = String.format("%.4f", individual.individualID)
                }
                
                // Stock ID (column S)
                individual.stockID = tokens[18].toDouble()
                individual.stockIDLabel =  String.format("%.4f", individual.stockID)
                 
                // Stock (from column S)
                individual.stock = Stock.findByStockID(individual.stockID) 
                
                // Fertlization date (column H)
                individual.fertilizationDate = tokens[7]?.size() > 0 ? Date.parse("mm/dd/yy", tokens[7]) : null

                // Maternal stock & individual lineage (column W)
                def maternalStockID =  tokens[22]?.size() > 0 ? tokens[22].toString() : null
                if(maternalStockID != null){
                                  
                    // get the stock ID
                    def sID = maternalStockID.split("\\.")[0] as Double
                    
                    // get the individual ID
                    def iID = maternalStockID as Double
                                                        
                   // set the values in the individual
                   individual.maternalStockID = sID
                   individual.maternalStockIDLabel = String.format("%.4f", sID)
                   individual.maternalIndividualID = iID
                   individual.maternalIndividualIDLabel = String.format("%.4f",  iID)                    
                }
                else{
                    individual.maternalStockID = null
                    individual.maternalStockIDLabel = null
                    individual.maternalIndividualID = null
                    individual.maternalIndividualIDLabel = null
                }
                           
                // Paternal stock lineage (column X)
                def paternalStockID = tokens[23]?.size() > 0 ? tokens[23].toString() : null
                if(paternalStockID != null){
                    
                    // get the stock ID
                    def sID = paternalStockID.split("\\.")[0] as Double
                    
                    // get the individual ID
                    def iID = paternalStockID as Double
                    
                    // set the values in the individual
                    individual.paternalStockID = sID
                    individual.paternalStockIDLabel = String.format("%.4f", sID)
                     individual.paternalIndividualID = iID
                    individual.paternalIndividualIDLabel = String.format("%.4f",  iID)                   
                }
                else{
                    individual.paternalStockID = null
                    individual.paternalStockIDLabel = null
                    individual.paternalIndividualID = null
                    individual.paternalIndividualIDLabel = null
                }
                
                // Fish location (column Q)
                individual.fishLocation = tokens[16].size() > 0 ? tokens[16] : null
                
                // Researcher comments
                individual.comments = tokens[26]?.size() > 0 ? tokens[26] : null
                
                // Fish sex (column R)
                individual.fishSex = tokens[17]?.size() > 0 ? tokens[17] : null
                
                individual.save(flush: true, insert: true)
            }
        }
        println "Individuals ${Individual.count()} added "
    }

    /*
     * Process the lineage of individuals
     */
    def processIndividualLineage() {
        CSVReader csvReader = getImportFile("individuals.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        
        println "start processing individuals"
        csvReader.eachLine { tokens ->
            if (tokens.size() > 15 && tokens[18]?.length() > 1 && tokens[19]?.length() > 1) {

                // Get the individual object, if one exists
                Individual individual
                
                // Get the individual
                if(tokens[20]?.length() > 1){     
                    def individualID = tokens[20].toDouble()
                    individual = Individual.findByIndividualID(individualID) 
                }
                
                // If we have an individual
                if (individual) {
                    
                    // Get the maternal stock                  
                    if (individual.maternalStockID != null)  {  
                        if(Stock.findByStockID(individual.maternalStockID) != null)
                            individual.maternalStock = Stock.findByStockID(individual.maternalStockID)
                        else
                        individual.maternalStock = null
                    }
                       
                    // Get the paternal stock
                    if (individual.paternalStockID != null)  {
                        if(Stock.findByStockID(individual.paternalStockID) != null)
                        individual.paternalStock = Stock.findByStockID(individual.paternalStockID)                    
                        else
                        individual.paternalStock = null
                    }
                    
                    // Get the maternal individual
                    if(individual.maternalIndividualID != null){
                        if(Individual.findByIndividualID(individual.maternalIndividualID) != null)
                        individual.maternalIndividual = Individual.findByIndividualID(individual.maternalIndividualID)
                        else
                        individual.maternalIndividual = null
                    }
                    
                    // Get the paternal individual
                    if(individual.paternalIndividualID != null){
                        if(Individual.findByIndividualID(individual.paternalIndividualID) != null)
                        individual.paternalIndividual = Individual.findByIndividualID(individual.paternalIndividualID) 
                        else
                        individual.paternalIndividual = null                            
                    }
                    
                    individual.save(flush: true, insert: false)
                }
            }
        }
        println "finished processing Individuals ${Individual.count()} individuals added "
    }
    
    /*
     * Process the lineage of stock
     */ 
    def processStockLineage() {
        CSVReader csvReader = getImportFile("stocks.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        println "start processing stocks"
        csvReader.eachLine { tokens ->
            try{

                if (tokens.size() > 5) {
                    
                    // Get the stock object
                    def stockID = tokens[14]?.size() > 0 ? tokens[14].toDouble() : null 
                    Stock stock = Stock.findByStockID(stockID)
                    
                    // If we have a stock
                    if (stock) {
                                            
                        // Get the maternal stock fron the saved ID                 
                        if (stock.maternalStockID != null)  {         
                            if(Stock.findByStockID(stock.maternalStockID) != null)
                            stock.maternalStock = Stock.findByStockID(stock.maternalStockID)
                        }
                        
                        // Get the paternal stock from the maternal stock ID
                        if(stock.paternalStockID != null){
                            if(Stock.findByStockID(stock.paternalStockID) != null)
                            stock.paternalStock = Stock.findByStockID(stock.paternalStockID)
                        }
                        
                        // Get the maternal individual
                        if(stock.maternalIndividualID != null){
                            if(Individual.findByIndividualID(stock.maternalIndividualID) != null)
                            stock.maternalIndividual = Individual.findByIndividualID(stock.maternalIndividualID)
                        }
                        
                        // Get the paternal individual
                        if(stock.paternalIndividualID != null){
                            if(Individual.findByIndividualID(stock.paternalIndividualID) != null)
                            stock.paternalIndividual = Individual.findByIndividualID(stock.paternalIndividualID)
                        }
                        stock.save(flush: true, insert: false)
                    }
                }
            }
            catch(e){
                println "error processing line ${tokens} \n ${e}"
            }
        }
        println "finished processing stocks: " + Stock.count()
    }

    // !! Not Used !!
    def stubGenetics() {
        //        Genetics.deleteAll(Genetics.all)
        println "start stub genetics"
        CSVReader csvReader = getImportFile("genetics.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        csvReader.eachLine { tokens ->
            if (tokens.size() > 2) {

                Genetics genetics = new Genetics()

                try {
                    // genetics.captureDate = tokens[0]?.size() > 0 ? Date.parse("mm/dd/yy", tokens[0]) : null // captureDate
                    genetics.identification = tokens[1]   // identification
                    genetics.comments = tokens[5]  // researcherCommentsGenetics
                    genetics.source = tokens[7]  // source
                    genetics.species = tokens[8]  // species
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
}