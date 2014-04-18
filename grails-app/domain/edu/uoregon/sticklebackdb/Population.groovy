package edu.uoregon.sticklebackdb

/**
 * Same as capture
 */
class Population {

    static constraints = {
        identification nullable: false,unique: true
//        sourceLat nullable: true
//        sourceLong nullable: true
        sourceLatString nullable: true
        sourceLongString nullable: true
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

//    Double sourceLat
//    Double sourceLong
    String sourceLatString
    String sourceLongString

    Boolean common = false

    String getSourceDisplay(){
        if(sourceLatString && sourceLongString){
            return sourceLatString + "," + sourceLongString
        }
        return 'Lat/Long not specified'
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
