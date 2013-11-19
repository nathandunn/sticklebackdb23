package edu.uoregon.sticklebackdb

class Individual {

    static constraints = {
         individualID nullable: false
    }
    
    // Individual ID (column U)
    String individualID
      
    // Stock ID (column S)
    String stockID
   
    // Stock (derived from column S)
    Stock stock    
    
    // Fertilization date (column H)
    Date fertilizationDate
      
    // Maternal Stock ID (column W)
    String maternalStockID
    
    // Maternal Stock
    Stock maternalStock
    
    // Maternal Individual ID (column W)
    String maternalIndividualID
    
    // Maternal Individual
    Individual maternalIndividual
    
    // Paternal ID (column X)
    String paternalStockID
    
    // PaternalStock
    Stock paternalStock
    
    // Paternal Individual ID (column X)
    String paternalIndividualID
    
    // Paternal individual
    Individual paternalIndividual
    
    // Researcher comments (column AA)
    String comments
    
    // Fish location (column Q)
    String fishLocation
}
