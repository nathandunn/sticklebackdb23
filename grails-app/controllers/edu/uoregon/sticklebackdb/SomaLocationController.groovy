package edu.uoregon.sticklebackdb



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SomaLocationController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static navigation = [
            title: 'Soma', action: 'list', order: 135,
    ]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond SomaLocation.list(params), model:[somaLocationInstanceCount: SomaLocation.count()]
    }

    def show(SomaLocation somaLocationInstance) {
        respond somaLocationInstance
    }

    def create() {
        respond new SomaLocation(params)
    }

    @Transactional
    def save(SomaLocation somaLocationInstance) {
        if (somaLocationInstance == null) {
            notFound()
            return
        }

        if (somaLocationInstance.hasErrors()) {
            respond somaLocationInstance.errors, view:'create'
            return
        }

        somaLocationInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'somaLocationInstance.label', default: 'SomaLocation'), somaLocationInstance.id])
                redirect somaLocationInstance
            }
            '*' { respond somaLocationInstance, [status: CREATED] }
        }
    }

    def edit(SomaLocation somaLocationInstance) {
        respond somaLocationInstance
    }

    @Transactional
    def update(SomaLocation somaLocationInstance) {
        if (somaLocationInstance == null) {
            notFound()
            return
        }

        if (somaLocationInstance.hasErrors()) {
            respond somaLocationInstance.errors, view:'edit'
            return
        }

        somaLocationInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'SomaLocation.label', default: 'SomaLocation'), somaLocationInstance.id])
                redirect somaLocationInstance
            }
            '*'{ respond somaLocationInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(SomaLocation somaLocationInstance) {

        if (somaLocationInstance == null) {
            notFound()
            return
        }

        somaLocationInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'SomaLocation.label', default: 'SomaLocation'), somaLocationInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'somaLocationInstance.label', default: 'SomaLocation'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
