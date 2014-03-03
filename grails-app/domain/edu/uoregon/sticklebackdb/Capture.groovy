package edu.uoregon.sticklebackdb

class Capture {

    static constraints = {
        line nullable: false
        population nullable: false
        // this is a "for-now"
        captureDate nullable: true
    }

    static mapping = {
        comment type: "text"
    }

    Line line
    Population population

    Date captureDate
    String comment

    String getDisplay(){
        line.name + " " + population.sourceDisplay + " "+ (captureDate?.dateString && captureDate?.dateString!='null' ? captureDate.dateString : '')
    }
}
