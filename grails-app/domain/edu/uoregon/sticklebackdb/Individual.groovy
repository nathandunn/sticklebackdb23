package edu.uoregon.sticklebackdb

class Individual {

    static constraints = {
    }


    Stock stock       // the group
    Integer index   // the individual
    String fishLocation
    Individual maternal
    Individual paternal
    Date fertilization
    Date stockDate
    Date stockIndividualDate


    Integer getBarcode(){
        stock.getBarcode() + ".00."+(index as String).padLeft(4)
    }
}
