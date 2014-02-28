package edu.uoregon.sticklebackdb

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class CaptureController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static navigation = [
            title: 'Capture', action: 'index', order: 7
    ]

    def stockService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Capture.list(params), model: [captureInstanceCount: Capture.count()]
    }

    def show(Capture captureInstance) {

        List<Stock> stockList = Stock.findAllByLine(captureInstance.line)
        respond captureInstance, model: [captureStocks: stockList]
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

//        captureInstance.line.addToCaptures(captureInstance)
//        Line line = new Line(
//                name: params.newLineName
//        )

//        line.captures= captureInstance
//        line.addToCaptures(captureInstance)
//        captureInstance.line = line

//        if (line.hasErrors()) {
//            respond line.errors, view: 'create'
//            return
//        }
        captureInstance.validate()

        if ( captureInstance.hasErrors()) {
            respond captureInstance.errors, view: 'create'
            return
        }


//        line.save flush: true
        captureInstance.save flush: true

        Stock stock = new Stock(
                capture: captureInstance
                , stockID: stockService.getNextStockID()
        ).save flush: false


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
            respond captureInstance.errors, view: 'edit'
            return
        }

        captureInstance.save flush: true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Capture.label', default: 'Capture'), captureInstance.id])
                redirect captureInstance
            }
            '*' { respond captureInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Capture captureInstance) {

        if (captureInstance == null) {
            notFound()
            return
        }

        captureInstance.delete flush: true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Capture.label', default: 'Capture'), captureInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'captureInstance.label', default: 'Capture'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
