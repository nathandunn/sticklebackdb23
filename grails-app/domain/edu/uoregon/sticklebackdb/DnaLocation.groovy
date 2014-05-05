package edu.uoregon.sticklebackdb

class DnaLocation {

    static constraints = {
        name nullable: false, unique: true
    }

    String name
}
