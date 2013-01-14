package edu.uoregon.sticklebackdb

class Individual {

    static constraints = {
        index nullable: false
    }


    Stock stock       // the group
    Integer index   // the individual
    String fishLocation
    Individual maternal
    Individual paternal
    Date fertilization
    Date stockDate
    Date stockIndividualDate


    String getBarcode() {
        (stock?.barcode as String) + ".00." + (index as String)?.padLeft(4,"0")
    }
}
