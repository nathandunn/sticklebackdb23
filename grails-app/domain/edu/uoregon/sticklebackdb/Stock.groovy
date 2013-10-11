package edu.uoregon.sticklebackdb

class Stock {

    static constraints = {
    }


    // Population name or abbrv (column X)
    Population population
    
    // Fish stock (column O)
    Double stockID 

    // Stock name (column AM)
    String stockName
    
    // Researcher comments (column AK)
    String comment

    // Fertilization date (column D)
    Date fertilizationDate
    
    // Lineage, maternal ID (column Q)
    Double maternalID
    
    // Lineage, maternal individual (from column Q)
    Individual maternalIndividual
    
    // Lineage, maternal stock ID (column R)
    Double maternalStockID
    
    // Lineage, maternal stock (from column R)
    Stock maternalStock
    
    // Lineage, paternal ID (column T)
    Double paternalID
    
    // Lineage, paternal individual (from column T)
    Individual paternalIndividual
    
    // Lineage, paternal stock ID (column U)
    Double paternalStockID
    
     // Lineage, maternal stock (from column U)
    Stock paternalStock
    
    
    
    
    // Barcode (column A) TODO: should be S.NNNNN
   // Integer barcode
    
    // TODO: group / index
    //Individual maternalIndividual
    
    // Lineage, maternal ID label (column S)
   // String maternalIndividualLabel
    
    // Lineage, paternal ID (column T)
   // Individual paternalIndividual
    
    // Lineage, paternal ID label (column V)
    //String paternalIndividualLabel
    
    // Stock breeding type (column  AL) ---???
    //Genetics stockType

    // Cross date (column B)
   // Date crossDate

    // Cross type (column C) TODO: make enum - 0 or 1?
   // Integer crossType

    // Fertilization date & time (columns D & F - G?)
//    Date fertilizationDateTime

    // Fish age days (column I)
  //  Integer fishAgeDays
    
 
    // Stock breeding type (column AL)
   // String breedingType
    
    // Stock name abbreviation (column AN)
   // String abbreviation

    // Stock status containers (column AP) 
  //  String containerStatus
    
    // Stock status exp (column AQ)
   // String experimentStatus
    
    // Stock status grps (column AR)
   // String groupStatus
    
    // Stock status inds (column AS)
   // String individualStatus

   // String getFullStatus(){
  //      return containerStatus + " " + experimentStatus + " " + groupStatus + " " + individualStatus
   // }
}
