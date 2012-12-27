package edu.uoregon.sticklebackdb

class Stock {

    static constraints = {
    }

    // TODO: group / index
    Container barcode
    Stock maternalStock
    String maternalStockLabel
    Stock paternalStock
    String paternalStockLabel
    Line line
    Genetics stockType

    Date crossDate

    // TODO: make enum
    Integer crossType

    Date fertilizationDateTime

    Integer fishAgeDays
    Integer fishStock

    // TODO: seems incomplete
    Integer nursery1InitialNumber
    Integer nursery2Fertiles
    Integer nursery3Hatched
    Integer nursery5SecondStageSurvivors
    Integer nursery6Graduates

    String comment

    String breedingType

    String name
    String abbreviation


    String containerStatus
    String experimentStatus
    String groupStatus
    String individualStatus
}
