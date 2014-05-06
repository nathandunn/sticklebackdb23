package edu.uoregon.sticklebackdb



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FinclipLocationController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static navigation = [
            title: 'Finclip', action: 'index', order: 155,
    ]


    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond FinclipLocation.list(params), model:[finclipLocationInstanceCount: FinclipLocation.count()]
    }

    def show(FinclipLocation finclipLocationInstance) {
        respond finclipLocationInstance
    }

    def create() {
        respond new FinclipLocation(params)
    }

    @Transactional
    def save(FinclipLocation finclipLocationInstance) {
        if (finclipLocationInstance == null) {
            notFound()
            return
        }

        if (finclipLocationInstance.hasErrors()) {
            respond finclipLocationInstance.errors, view:'create'
            return
        }

        finclipLocationInstance.save flush:true

//        request.withFormat {
//            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'finclipLocationInstance.label', default: 'FinclipLocation'), finclipLocationInstance.id])
                redirect finclipLocationInstance
//            }
//            '*' { respond finclipLocationInstance, [status: CREATED] }
//        }
    }

    def edit(FinclipLocation finclipLocationInstance) {
        respond finclipLocationInstance
    }

    @Transactional
    def update(FinclipLocation finclipLocationInstance) {
        if (finclipLocationInstance == null) {
            notFound()
            return
        }

        if (finclipLocationInstance.hasErrors()) {
            respond finclipLocationInstance.errors, view:'edit'
            return
        }

        finclipLocationInstance.save flush:true

//        request.withFormat {
//            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'FinclipLocation.label', default: 'FinclipLocation'), finclipLocationInstance.id])
                redirect finclipLocationInstance
//            }
//            '*'{ respond finclipLocationInstance, [status: OK] }
//        }
    }

    @Transactional
    def delete(FinclipLocation finclipLocationInstance) {

        if (finclipLocationInstance == null) {
            notFound()
            return
        }

        finclipLocationInstance.delete flush:true

//        request.withFormat {
//            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'FinclipLocation.label', default: 'FinclipLocation'), finclipLocationInstance.id])
                redirect action:"index", method:"GET"
//            }
//            '*'{ render status: NO_CONTENT }
//        }
    }

    protected void notFound() {
//        request.withFormat {
//            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'finclipLocationInstance.label', default: 'FinclipLocation'), params.id])
                redirect action: "index", method: "GET"
//            }
//            '*'{ render status: NOT_FOUND }
//        }
    }
}
