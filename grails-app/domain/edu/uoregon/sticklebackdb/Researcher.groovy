package edu.uoregon.sticklebackdb

class Researcher {
    String username
    String passwordHash
    
    static hasMany = [ roles: ResearchRole, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false, unique: true)
    }
}
