package edu.uoregon.sticklebackdb

class FinclipLocation {

    static constraints = {
        name nullable: false, unique: true
    }

    String name
}
