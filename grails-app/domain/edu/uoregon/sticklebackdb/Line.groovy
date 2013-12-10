package edu.uoregon.sticklebackdb

class Line {

    static constraints = {
        name unique: true, nullable: false,blank: false
        capture nullable: true
    }

    static hasMany = [
        stocks:Stock
    ]

    static mapping = {
        comment type: "text"
        geneticNote type: "text"
    }

    Capture capture

    String name
    String species
    String comment
    String geneticNote

    // Boolean captured
    // String captureKey
    // String abbreviation
    //  Integer status
    //  String storedPopulationName
    //  String storedPopulationNameAbbreviation
}
