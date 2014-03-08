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
    Boolean common = false

    String getSourceDisplay(){
        if(sourceLat && sourceLong){
            return sourceLat + "," + sourceLong
        }
        return 'N/A'
    }

    String getShortIdentification(){
        int maxLength = 45
        if(identification.length()<maxLength){
            return identification
        }
        else{
            return identification.subSequence(0,maxLength) + "..."
        }
    }

}
