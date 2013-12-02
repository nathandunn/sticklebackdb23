package edu.uoregon.sticklebackdb

class Stock {

    // Initialize variables
    Stock(){
        comments = null
        fertilizationDate = null
        maternalIndividualID = null
        maternalStockID = null
        maternalStock = null
        paternalIndividualID = null
        paternalStockID = null
        paternalStock = null
    }
    
    static constraints = {
    }

    // Line name (column X)
    Line line
    
    // Fish stock (column O)
    Double stockID
    String stockIDLabel 
   
    // Stock name (column AM)
    String stockName
    
    // Researcher comments (column AK)
    String comments

    // Fertilization date (column D)
    Date fertilizationDate
    
    // Lineage, maternal Individual ID (column Q)
    Double maternalIndividualID
    String maternalIndividualIDLabel
    
    // Lineage, maternal Individual (from column Q)
    Individual maternalIndividual
    
    // Lineage, maternal stock ID (column R)
    Double maternalStockID
    String maternalStockIDLabel
    
    // Lineage, maternal stock (from column R)
    Stock maternalStock
    
    // Lineage, paternal Individual ID (column T)
    Double paternalIndividualID
    String paternalIndividualIDLabel
    
    // Lineage, paternal Individual (from column T)
    Individual paternalIndividual
      
    // Lineage, paternal stock ID (column U)
    Double paternalStockID
    String paternalStockIDLabel
    
    // Lineage, maternal stock (from column U)
    Stock paternalStock   
}
