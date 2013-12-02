package edu.uoregon.sticklebackdb

class Individual {

    static constraints = {
         individualID nullable: false
    }
    
    // Individual ID (column U)
    Double individualID
    String individualIDLabel
      
    // Stock ID (column S)
    Double stockID
    String stockIDLabel
   
    // Stock (derived from column S)
    Stock stock    
    
    // Fertilization date (column H)
    Date fertilizationDate
      
    // Maternal Stock ID (column W)
    Double maternalStockID
    String maternalStockIDLabel
    
    // Maternal Stock
    Stock maternalStock
    
    // Maternal Individual ID (column W)
    Double maternalIndividualID
    String maternalIndividualIDLabel
    
    // Maternal Individual
    Individual maternalIndividual
    
    // Paternal ID (column X)
    Double paternalStockID
    String paternalStockIDLabel
    
    // PaternalStock
    Stock paternalStock
    
    // Paternal Individual ID (column X)
    Double paternalIndividualID
    String paternalIndividualIDLabel
    
    // Paternal individual
    Individual paternalIndividual
    
    // Researcher comments (column AA)
    String comments
    
    // Fish location (column Q)
    String fishLocation
    
    // Fish sex (column R)
    String fishSex
}
