package edu.uoregon.sticklebackdb

import edu.uoregon.sticklebackdb.MeasuredValueTypeEnum

class Category {

    static constraints = {
        name unique: true, nullable: false, blank: false
    }

    static hasMany = [
            measuredValues: MeasuredValue
//            ,experiments: Experiment
    ]
//
//    static belongsTo = [
//            Experiment
//    ]


    String name
    MeasuredValueTypeEnum type = MeasuredValueTypeEnum.TEXT// a possible string / float
    String units
    String note

    int compareTo(Object t) {
        return name.compareTo(t.name)
    }
}
