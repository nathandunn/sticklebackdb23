package edu.uoregon.sticklebackdb



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CaptureController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static navigation = [
            title:'Capture',action: 'index',order:7
    ]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Capture.list(params), model:[captureInstanceCount: Capture.count()]
    }

    def show(Capture captureInstance) {
        respond captureInstance
    }

    def create() {
        respond new Capture(params)
    }

    @Transactional
    def save(Capture captureInstance) {
        if (captureInstance == null) {
            notFound()
            return
        }

        if (captureInstance.hasErrors()) {
            respond captureInstance.errors, view:'create'
            return
        }

        captureInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'captureInstance.label', default: 'Capture'), captureInstance.id])
                redirect captureInstance
            }
            '*' { respond captureInstance, [status: CREATED] }
        }
    }

    def edit(Capture captureInstance) {
        respond captureInstance
    }

    @Transactional
    def update(Capture captureInstance) {
        if (captureInstance == null) {
            notFound()
            return
        }

        if (captureInstance.hasErrors()) {
            respond captureInstance.errors, view:'edit'
            return
        }

        captureInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Capture.label', default: 'Capture'), captureInstance.id])
                redirect captureInstance
            }
            '*'{ respond captureInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Capture captureInstance) {

        if (captureInstance == null) {
            notFound()
            return
        }

        captureInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Capture.label', default: 'Capture'), captureInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'captureInstance.label', default: 'Capture'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}