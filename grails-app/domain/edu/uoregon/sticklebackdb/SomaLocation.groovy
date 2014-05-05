package edu.uoregon.sticklebackdb

class SomaLocation {

    static constraints = {
        name nullable: false, unique: true
    }

    String name
}
