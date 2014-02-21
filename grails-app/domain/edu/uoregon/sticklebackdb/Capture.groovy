package edu.uoregon.sticklebackdb

class Capture {

    static constraints = {
        line nullable: false
        population nullable: false
        captureDate nullable: false
    }

    static mapping = {
        comment type: "text"
    }

    Line line
    Population population

    Date captureDate
    String comment

    String getDisplay(){
        line.name + " " + population.sourceDisplay + " "+captureDate.timeString
    }
}
