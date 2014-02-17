package edu.uoregon.sticklebackdb

/**
 * Same as capture
 */
class Population {

    static constraints = {
    }

    static hasMany = [
            captures: Capture
    ]

    static mapping = {
        comment type: "text"
    }

    String identification

    // includes geographic data
    String comment

    // TODO: change into object
    String source
    Double sourceLat
    Double sourceLong

    // TODO: convert to object
    String species

    String gps

    String getSourceDisplay(){
        if(sourceLat && sourceLong){
            return sourceLat + "," + sourceLong
        }
        return 'N/A'
    }

}
