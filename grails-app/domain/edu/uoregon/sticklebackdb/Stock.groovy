package edu.uoregon.sticklebackdb

class Stock {

    static constraints = {
    }



    Line line

    // should be S.NNNNN
    Integer barcode
    // TODO: group / index
    Individual maternalIndividual
    String maternalIndividualLabel
    Individual paternalIndividual
    String paternalIndividualLabel
    Genetics stockType

    Date crossDate

    // TODO: make enum
    Integer crossType

    Date fertilizationDateTime

    Integer fishAgeDays
    Integer fishStock

    String comment

    String breedingType

    String name
    String abbreviation


    String containerStatus
    String experimentStatus
    String groupStatus
    String individualStatus

    String getFullStatus(){
        return containerStatus + " " + experimentStatus + " " + groupStatus + " " + individualStatus
    }
}
