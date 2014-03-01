package edu.uoregon.sticklebackdb

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class LineController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static navigation = [
            title:'Line',action: 'list',order:5
    ]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [lineInstanceList: Line.list(params), lineInstanceTotal: Line.count()]
    }

    def create() {
        [lineInstance: new Line(params)]
    }

    def addLineToStock(){

        def stockId = params.stockId
        def comment = params.comment
        def name = params.name
        Stock stock = Stock.findById(stockId)
        Line line = new Line(name: name , comment: comment,stock: stock).save flush: true
        render line as JSON
    }

    def addLineToStockWithCapture(){

        println "params: ${params}"

//        def stockId = params.stockId
//        def comment = params.comment
        def name = params.name
        def populationId = params.populationId
//        def captureName = params.captureName
        def captureYear = params.captureYear
        def captureMonth = params.captureMonth
        def captureDay = params.captureDay
        def captureComment = params.captureComment

//        Stock stock = Stock.findById(stockId)
        Population population = Population.findById(populationId)



        Line line = new Line(name: name ).save()

        Capture capture = new Capture(
                population: population
                ,captureDate: Date.parse("yyyy/mm/dd",captureYear+"/"+captureMonth+"/"+captureDay)
                ,line: line
                ,comment: captureComment
        ).save flush: true
        line.addToCaptures(capture)
        line.save flush: true

        render line as JSON
    }

    def save() {
        def lineInstance = new Line(params)
        if (!lineInstance.save(flush: true)) {
            render(view: "create", model: [lineInstance: lineInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'line.label', default: 'Line'), lineInstance.id])
        redirect(action: "show", id: lineInstance.id)
    }

    def show(Long id) {
        def lineInstance = Line.get(id)
        if (!lineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'line.label', default: 'Line'), id])
            redirect(action: "list")
            return
        }

        List<Stock> stocks = Stock.findAllByLine(lineInstance,[sort:"stockID",order: "asc"])

        [lineInstance: lineInstance,stocks:stocks]
    }

    def edit(Long id) {
        def lineInstance = Line.get(id)
        if (!lineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'line.label', default: 'Line'), id])
            redirect(action: "list")
            return
        }

        [lineInstance: lineInstance]
    }

    def update(Long id, Long version) {
        def lineInstance = Line.get(id)
        if (!lineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'line.label', default: 'Line'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (lineInstance.version > version) {
                lineInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'line.label', default: 'Line')] as Object[],
                          "Another user has updated this Line while you were editing")
                render(view: "edit", model: [lineInstance: lineInstance])
                return
            }
        }

        lineInstance.properties = params

//        for(capture in lineInstance.capture){
//            // TODO: if capture belongs to other line, fix there
//            assert false
////            capture.save()
//        }

        println "params: ${params}"
        println "lineInstance.properties: ${lineInstance.properties}"


        if (!lineInstance.save(flush: true)) {
            render(view: "edit", model: [lineInstance: lineInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'line.label', default: 'Line'), lineInstance.id])
        redirect(action: "show", id: lineInstance.id)
    }

    def delete(Long id) {
        def lineInstance = Line.get(id)
        if (!lineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'line.label', default: 'Line'), id])
            redirect(action: "list")
            return
        }

        try {
            lineInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'line.label', default: 'Line'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'line.label', default: 'Line'), id])
            redirect(action: "show", id: id)
        }
    }
}
