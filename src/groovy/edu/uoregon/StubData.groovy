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
        stubPopulation()
        // stubGenetics()
        stubStock()
        stubIndividuals()
        processIndividualLineage()
        processStockLineage()
    }

    /*
     * Grab the population data 
     */
    def stubPopulation() {

        CSVReader csvReader = getImportFile("lines.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        csvReader.eachLine { tokens ->
            Population population = new Population()
            
            // Read in the needed line information
            population.name = tokens[4]   // lineName
            population.comment = tokens[3]  // lineComments
            population.species = tokens[6]  // lineSpecies
            
            population.gps = "lat,lon"            
            
            population.save(flush: true, insert: true)
        }
        //  println "FINISHED stub population " + Population.count()
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

    /*
     * Grab the stock data
     */ 
    def stubStock() {
        //        Stock.deleteAll(Stock.all)
        CSVReader csvReader = getImportFile("stocks.csv").toCsvReader(skipLines: 1, 'charset': 'UTF-8')
        println "start stub stocks"
        csvReader.eachLine { tokens ->
            
            if (tokens.size() > 5) {
                //  println "Tokem size: " + tokens.size() 
                // println "token length " + tokens.length
                Stock stock = new Stock()
                try {
                     
                    if(tokens.size() < 38)
                    println "Error reading stock # " + tokens[0] + ", skipping, not enough information."
                    else{                    
                        // Population (find using column X)
                        stock.population = tokens[23]?.size() > 0 ? Population.findByName(tokens[23]) : null

                        // Stock ID (column O) (format to have x.0000)               
                        stock.stockID = tokens[14]?.size() > 0 ? tokens[14].toDouble() : null 
                    
                        // Fertilization date (column D)
                        try {
                            stock.fertilizationDate = tokens[3]?.size() > 0 ? Date.parse("mm/dd/yyyy", tokens[3]) : null  // fertilization Date
                        } catch (e) {
                            println "error parsing fertilization date: " + e
                        }
                    
                        // Researcher comments (column AK)
                        stock.comment = tokens[36]?.size() > 0 ? tokens[36] : null 
                    
                        // Stock name (column AM)
                        stock.stockName = tokens[38]?.size() > 0 ? tokens[38] : null
                                      
                        // Maternal lineage                     
                        if(tokens[16].size() > 0){
                        
                            // Maternal ID (column Q)
                            stock.maternalID = tokens[16].toDouble()
                        
                        }
                        else{ 
                            stock.maternalID = null
                            stock.maternalIndividual = null
                        }
                        if(tokens[17].size() > 0){
                            
                            // Maternal stock ID (column R)
                            stock.maternalStockID = tokens[17].toDouble()
                        }
                        else{
                            stock.maternalStockID = null
                            stock.maternalStock = null
                        }                 
                    
                        // Paternal lineage   
                        if(tokens[19].size() > 0){
                        
                            // Paternal ID (column T)
                            stock.paternalID = tokens[19].toDouble()
                                    
                        }
                        else{
                            stock.paternalID = null
                            stock.paternalIndividual = null
                        }
                        if(tokens[20].size() > 0){ 
                      
                            // Paternal stock ID (column U)
                            stock.paternalStockID = tokens[20].toDouble()
                           
                        }
                        else{
                            stock.paternalStockID = null
                            stock.paternalStock = null
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
                if(tokens[20]?.length() > 1)
                individual.individualID = tokens[20].toDouble()
                
                // Stock ID (column S)
                individual.stockID = tokens[18].toDouble()
                
                // Stock (from column S)
                individual.stock = Stock.findByStockID(tokens[18].toDouble()) 
                
                // Fertlization date (column H)
                individual.fertilizationDate = tokens[7]?.size() > 0 ? Date.parse("mm/dd/yyyy", tokens[7]) : null

                // Maternal lineage (column W)
                individual.maternalID =  tokens[22]?.size() > 0 ? tokens[22].toDouble() : null
                
                // Paternal lineage (column X)
                individual.paternalID = tokens[23]?.size() > 0 ? tokens[23].toDouble() : null
                
                // Fish location (column Q)
                individual.fishLocation = tokens[16].size() > 0 ? tokens[16] : null
                
                // Researcher comments
                individual.comments = tokens[26]?.size() > 0 ? tokens[26] : null
                
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
                if(tokens[20]?.length() > 1)          
                individual = Individual.findByIndividualID(tokens[20].toDouble()) 
                
                // If we have an individual
                if (individual) {
                    
                    // Get the maternal stock                  
                    if (individual.maternalID)  {
                        def matStockIDFull = individual.maternalID as String 
                        def matStockID = matStockIDFull.split("\\.")[0] as Double                        
                        individual.maternalStock = Stock.findByStockID(matStockID)    
                        individual.maternalIndividual = Individual.findByIndividualID(individual.maternalID)                        
                    }
                       
                    // Get the paternal stock
                    if (individual.paternalID)  {
                        def patStockIDFull = individual.paternalID as String 
                        def patStockID = patStockIDFull.split("\\.")[0] as Double                        
                        individual.paternalStock = Stock.findByStockID(patStockID)    
                        individual.paternalIndividual = Individual.findByIndividualID(individual.paternalID)                        
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
                    Stock stock = Stock.findByStockID(tokens[14] as Double)
                    
                    
                    
                     // If we have a stock
                if (stock) {
                    
                    // Get the maternal stock fron the saved ID                 
                    if (stock.maternalStockID != null)  {
                        println "materbal stick is" + stock.maternalStockID
                        stock.maternalStock = Stock.findByStockID(stock.maternalStockID)  
                        println "mater nale ame " + stock.maternalStock.stockName
                            
                    }
                    
                     /*   
                    
                    //                println "trying to process ${tokens[14]}"
                    //Stock stock = Stock.findByBarcode(tokens[14] as Integer)    // fishStock
                    //                println "found stock ${stock} for ${tokens[14] as Integer}"
                    if (stock && stock!=null && stock.id !=null) {
                                           println  "maternal token ${tokens[16]} and paternal token ${tokens[19]}"
                        if (tokens[16] && tokens[16].size() > 0 && tokens[16].contains("\\.")) {   //lineage maternal ID
                            //                        println "adding maternal for ${tokens[16]}"
                            def maternalStockID = tokens[16].substring(tokens[16].indexOf("\\.")) as Integer // lineage maternal ID
                            stock.maternalIndividual = Individual.findByStockAndIndex(stock, maternalStockID)
                            //                        println "addED  maternal for ${stock.maternalIndividual} using ${maternalStockID}"
                        }

                        if (tokens[19] && tokens[19].size() > 0 && tokens[19].contains("\\.")) {   // lineage Paternal ID
                            //                        println "adding paternal for ${tokens[19]}"
                            //                        stock.paternalIndividualLabel = tokens[15]
                            def paternalStockID = tokens[19].substring(tokens[19].indexOf("\\.")) as Integer   // lineage Paternal ID
                            stock.paternalIndividual = Individual.findByStockAndIndex(stock, paternalStockID)
                            //                        println "addED  paternal for ${stock.paternalIndividual} using ${paternalStockID}"
                        }
*/
                        stock.save(flush: true, insert: false)
                    }
                    //                else{
                    //                    println "not prociessing ${tokens[14]} was null "
                    //                }
                }
            }
            catch(e){
                println "error processing population ${tokens} \n ${e}"
            }
        }

        println "finished processing stocks: " + Stock.count()

    }
}
