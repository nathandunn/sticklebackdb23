package edu.uoregon.sticklebackdb

import org.apache.shiro.crypto.hash.Sha256Hash

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ResearcherController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static navigation = [
            title:'Researchers',action: 'index',order:10,
    ]

    def researcherService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Researcher.list(params), model:[researcherInstanceCount: Researcher.count()]
    }

    def show(Researcher researcherInstance) {
        respond researcherInstance
    }

    def create() {
        respond new Researcher(params)
    }

    String isValidPassword(String password) {
        if(!password || password.length()<6){
            return "Password length must be greater than 6"
        }
        return null;
    }

    @Transactional
    def save(Researcher researcherInstance) {
        if (researcherInstance == null) {
            notFound()
            return
        }

        String passwordErrorString = isValidPassword(params.password1)
        if (passwordErrorString==null) {
            if (params.password1.equals(params.password2)) {
                params.passwordHash = new Sha256Hash(params.password1).toHex()
                researcherInstance.passwordHash = params.passwordHash
            } else {
                researcherInstance.errors.rejectValue("passwordHash", "default.password.doesnotmatch", "Passwords do not match")
//                render(view: "create", model: [researcherInstance: researcherInstance])
//                respond researcherInstance.errors, view:'create'
                return
            }
        }
        else{
            researcherInstance.errors.rejectValue("passwordHash", "", passwordErrorString)
//            render(view: "create", model: [researcherInstance: researcherInstance])
//            respond researcherInstance.errors, view:'create'
            return
        }


        if (researcherInstance.hasErrors()) {
            respond researcherInstance.errors, view:'create'
            return
        }

        researcherInstance.save flush:true

        ResearchRole userRole = ResearchRole.findByName(ResearcherService.ROLE_USER)
        if(researcherInstance){
            researcherInstance.addToRoles( userRole )
        }

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'researcherInstance.label', default: 'Researcher'), researcherInstance.name])
                redirect researcherInstance
            }
            '*' { respond researcherInstance, [status: CREATED] }
        }
    }

    def edit(Researcher researcherInstance) {

        if(researcherService.isAdmin() || researcherService.currentUser.id == researcherInstance.id){
            respond researcherInstance
        }
        else{
            render(view:"/unauthorized")
        }

    }

    @Transactional
    def update(Researcher researcherInstance) {
        if (researcherInstance == null) {
            notFound()
            return
        }

        if(!researcherService.isAdmin() &&  researcherService.currentUser.id != researcherInstance.id){
            render(view:"/unauthorized")
            return
        }

        if (researcherInstance.hasErrors()) {
            respond researcherInstance.errors, view:'edit'
            return
        }
        if (params.password1) {

            String passwordErrorString = isValidPassword(params.password1)
            println "pasword Error string ? ${passwordErrorString}"
            if (passwordErrorString==null) {
                if (params.password1.equals(params.password2)) {
                    researcherInstance.passwordHash = new Sha256Hash(params.password1).toHex()
                } else {
                    researcherInstance.errors.rejectValue("passwordHash", "default.password.doesnotmatch", "Passwords do not match")
//                    render(view: "edit", model: [researcherInstance: researcherInstance])
                    respond researcherInstance.errors, view:'edit'
                    return
                }
            }
            else{
                researcherInstance.errors.rejectValue("passwordHash", "", passwordErrorString)
//                flash.message = passwordErrorString
//                render(view: "edit", model: [researcherInstance: researcherInstance])
                respond researcherInstance.errors, view:'edit'
                return
            }
        } else {
            params.passwordHash = researcherInstance.passwordHash
        }

        researcherInstance.properties = params


        researcherInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Researcher.label', default: 'Researcher'), researcherInstance.name])
                redirect researcherInstance
            }
            '*'{ respond researcherInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Researcher researcherInstance) {

        if (researcherInstance == null) {
            notFound()
            return
        }

        researcherInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Researcher.label', default: 'Researcher'), researcherInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'researcherInstance.label', default: 'Researcher'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
