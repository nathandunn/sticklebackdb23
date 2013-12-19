package edu.uoregon.sticklebackdb

class ResearchRole {
    String name

    static hasMany = [ users: Researcher, permissions: String ]
    static belongsTo = Researcher

    static constraints = {
        name(nullable: false, blank: false, unique: true)
    }
}
