package edu.uoregon.sticklebackdb

class Stock {

    static constraints = {
    }

    // TODO: group / index
    Container barcode

    Date crossDate

    // TODO: make enum
    Integer crossType
    Genetics stockType

    Date fertilizationDateTime
    String fertilizationDisplay

    Integer fishAgeDays

    Integer fishStock

    Stock maternalStock
    Line line

    Integer nursery1InitialNumber
    Integer nursery2Fertiles
    Integer nursery3Hatched
    Integer nursery5SecondStageSurvivors
    Integer nursery6Graduates

}
