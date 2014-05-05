package edu.uoregon.sticklebackdb



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DnaLocationController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static navigation = [
            title: 'Dna', action: 'index', order: 145,
    ]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond DnaLocation.list(params), model:[dnaLocationInstanceCount: DnaLocation.count()]
    }

    def show(DnaLocation dnaLocationInstance) {
        respond dnaLocationInstance
    }

    def create() {
        respond new DnaLocation(params)
    }

    @Transactional
    def save(DnaLocation dnaLocationInstance) {
        if (dnaLocationInstance == null) {
            notFound()
            return
        }

        if (dnaLocationInstance.hasErrors()) {
            respond dnaLocationInstance.errors, view:'create'
            return
        }

        dnaLocationInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'dnaLocationInstance.label', default: 'DnaLocation'), dnaLocationInstance.id])
                redirect dnaLocationInstance
            }
            '*' { respond dnaLocationInstance, [status: CREATED] }
        }
    }

    def edit(DnaLocation dnaLocationInstance) {
        respond dnaLocationInstance
    }

    @Transactional
    def update(DnaLocation dnaLocationInstance) {
        if (dnaLocationInstance == null) {
            notFound()
            return
        }

        if (dnaLocationInstance.hasErrors()) {
            respond dnaLocationInstance.errors, view:'edit'
            return
        }

        dnaLocationInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'DnaLocation.label', default: 'DnaLocation'), dnaLocationInstance.id])
                redirect dnaLocationInstance
            }
            '*'{ respond dnaLocationInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(DnaLocation dnaLocationInstance) {

        if (dnaLocationInstance == null) {
            notFound()
            return
        }

        dnaLocationInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'DnaLocation.label', default: 'DnaLocation'), dnaLocationInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'dnaLocationInstance.label', default: 'DnaLocation'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
