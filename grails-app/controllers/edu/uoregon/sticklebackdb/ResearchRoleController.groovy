package edu.uoregon.sticklebackdb



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ResearchRoleController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ResearchRole.list(params), model:[researchRoleInstanceCount: ResearchRole.count()]
    }

    def show(ResearchRole researchRoleInstance) {
        respond researchRoleInstance
    }

    def create() {
        respond new ResearchRole(params)
    }

    @Transactional
    def save(ResearchRole researchRoleInstance) {
        if (researchRoleInstance == null) {
            notFound()
            return
        }

        if (researchRoleInstance.hasErrors()) {
            respond researchRoleInstance.errors, view:'create'
            return
        }

        researchRoleInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'researchRoleInstance.label', default: 'ResearchRole'), researchRoleInstance.id])
                redirect researchRoleInstance
            }
            '*' { respond researchRoleInstance, [status: CREATED] }
        }
    }

    def edit(ResearchRole researchRoleInstance) {
        respond researchRoleInstance
    }

    @Transactional
    def update(ResearchRole researchRoleInstance) {
        if (researchRoleInstance == null) {
            notFound()
            return
        }

        if (researchRoleInstance.hasErrors()) {
            respond researchRoleInstance.errors, view:'edit'
            return
        }

        researchRoleInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ResearchRole.label', default: 'ResearchRole'), researchRoleInstance.id])
                redirect researchRoleInstance
            }
            '*'{ respond researchRoleInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ResearchRole researchRoleInstance) {

        if (researchRoleInstance == null) {
            notFound()
            return
        }

        researchRoleInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ResearchRole.label', default: 'ResearchRole'), researchRoleInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchRoleInstance.label', default: 'ResearchRole'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
