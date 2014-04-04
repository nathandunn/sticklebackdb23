package edu.uoregon.sticklebackdb

class Individual {

    static constraints = {
         individualID nullable: false
    }

    static mapping = {
        comments type: "text"
    }

    // Individual ID (column U)
    Integer individualID
//    String individualIDLabel

    String getIndividualIDLabel(){
        if(individualID && stock){
            return String.valueOf(stock.stockID) +"."+ String.valueOf(individualID).padLeft(4,"0")
        }
        return ""
    }

    // Stock (derived from column S)
    Stock stock    

    // Fertilization date (column H)
//    Date fertilizationDate

    // Researcher comments (column AA)
    String comments

    // Fish location (column Q)
    String somaLocation
    String finclipLocation
    String dnaLocation
    String idStatus

    // Fish sex (column R)
    String fishSex

    // we only have the stock in the case where there is no individual
    // Maternal Individual
//    Individual maternalIndividual
    // Maternal Stock
//    Stock maternalStock

    // we only have the stock in the case where there is no individual
    // PaternalStock
//    Stock paternalStock
    // Paternal individual
//    Individual paternalIndividual


    // TODO remove when deployed
    // Stock ID (column S)
    Integer stockID

    // TODO remove when deployed
    // Maternal Stock ID (column W)
//    Integer maternalStockID

    // TODO remove when deployed
    // Maternal Individual ID (column W)
//    Integer maternalIndividualID

    // TODO remove when deployed
    // Paternal ID (column X)
//    Integer paternalStockID

    // TODO remove when deployed
    // Paternal Individual ID (column X)
//    Integer paternalIndividualID
}
