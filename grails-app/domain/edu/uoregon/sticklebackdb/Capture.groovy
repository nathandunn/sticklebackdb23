package edu.uoregon.sticklebackdb

class Capture {

    static constraints = {
        line nullable: false
        population nullable: false
    }

    static mapping = {
        comment type: "text"
    }

    Line line
    Population population

    Date captureDate
    String comment

    String getDisplay(){
        line.name + " " + population.source + " "+captureDate.timeString
    }
}
