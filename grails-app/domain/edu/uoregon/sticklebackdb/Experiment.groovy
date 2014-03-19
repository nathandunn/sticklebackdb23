package edu.uoregon.sticklebackdb

class Experiment {

    static constraints = {
        name nullable: false,unique: true,blank: false
    }

    static hasMany = [
            measuredValues: MeasuredValue
    ]

    String name
    Researcher researcher
    Date whenPerformed
    String note

}
