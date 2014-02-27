package edu.uoregon.sticklebackdb

class Line {

//    static transients = ['stockCount']
    String sticklebackSpecies = "Gasterosteus aculeatus"

    static constraints = {
        name unique: true, nullable: false, blank: false
//        capture nullable: true
    }


    static hasMany = [
            stocks    : Stock
            , captures: Capture
    ]

    static mapping = {
        comment type: "text"
        geneticNote type: "text"
    }

    Capture capture

    String name
    String species = sticklebackSpecies
    String comment
    String geneticNote

//    Integer getStockCount(){
//        if(stocks){
//            return stocks.size()
//        }
//        return 0
//    }

    // Boolean captured
    // String captureKey
    // String abbreviation
    //  Integer status
    //  String storedPopulationName
    //  String storedPopulationNameAbbreviation
}
