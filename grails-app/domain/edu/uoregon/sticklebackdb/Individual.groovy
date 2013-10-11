package edu.uoregon.sticklebackdb

class Individual {

    static constraints = {
         individualID nullable: false
    }
    
    // Individual ID (column U)
    Double individualID
     
    // Stock ID (column S)
    Double stockID

    // Stock (derived from column S)
    Stock stock    
    
    // Fertilization date (column H)
    Date fertilizationDate
      
    // Maternal ID (column W)
    Double maternalID
       
    // Maternal Stock (derived from column W)
    Stock maternalStock
    
    // Maternal Individual (derived from column W)
    Individual maternalIndividual
    
    // Paternal ID (column X)
    Double paternalID
    
    // Paternal Stock (derived from column X)
    Stock paternalStock
    
    // Paternal Individual (derived from column X)
    Individual paternalIndividual 
    
    // Researcher comments (column AA)
    String comments
    
    // Fish location (column Q)
    String fishLocation
}
