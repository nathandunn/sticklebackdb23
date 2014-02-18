package edu.uoregon.sticklebackdb

/**
 * Same as capture
 */
class Population {

    static constraints = {
        identification nullable: false,unique: true
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

    Double sourceLat
    Double sourceLong

    // TODO: convert to object
    String species

    String getSourceDisplay(){
        if(sourceLat && sourceLong){
            return sourceLat + "," + sourceLong
        }
        return 'N/A'
    }

}
