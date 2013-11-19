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
    String stockID 

    // Displayable stock ID
    //String displayStockID
    
    // Stock name (column AM)
    String stockName
    
    // Researcher comments (column AK)
    String comments

    // Fertilization date (column D)
    Date fertilizationDate
    
    // Lineage, maternal Individual ID (column Q)
    String maternalIndividualID
    
    // Lineage, maternal Individual (from column Q)
    Individual maternalIndividual
    
    // Lineage, maternal stock ID (column R)
    String maternalStockID
    
    // Lineage, maternal stock (from column R)
    Stock maternalStock
    
    // Lineage, paternal Individual ID (column T)
    String paternalIndividualID
    
    // Lineage, paternal Individual (from column T)
    Individual paternalIndividual
      
    // Lineage, paternal stock ID (column U)
    String paternalStockID
    
    // Lineage, maternal stock (from column U)
    Stock paternalStock   
}
