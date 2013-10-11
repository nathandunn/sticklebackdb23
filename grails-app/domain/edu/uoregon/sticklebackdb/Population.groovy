package edu.uoregon.sticklebackdb

class Population {

    static constraints = {
    }

    static hasMany = [
        stocks:Stock
    ]

    String name
    String species
    String comment
    String gps
       
    // Boolean captured
    // String captureKey
    // String abbreviation
    //  Integer status
    //  String storedPopulationName
    //  String storedPopulationNameAbbreviation
}
