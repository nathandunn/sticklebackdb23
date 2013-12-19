package edu.uoregon.sticklebackdb

import grails.transaction.Transactional
import org.apache.shiro.SecurityUtils

@Transactional
class ResearcherService {

    static String ROLE_ADMINISTRATOR = "ROLE_ADMINISTRATOR"
    static String ROLE_USER = "ROLE_USER"

    Researcher getCurrentUser(){
        String currentUserName = SecurityUtils?.subject?.principal
        if(currentUserName){
            return Researcher.findByUsername(currentUserName)
        }
        return null
    }

    boolean isAdmin() {
        String currentUserName = SecurityUtils?.subject?.principal
        if(currentUserName){
            Researcher researcher = Researcher.findByUsername(currentUserName)
            for(ResearchRole role in researcher.roles){
                if(role.name==ROLE_ADMINISTRATOR){
                    return true
                }
            }
        }
        return false
    }
}