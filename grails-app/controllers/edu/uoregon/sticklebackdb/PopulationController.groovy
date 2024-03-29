package edu.uoregon.sticklebackdb

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class PopulationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static navigation = [
            title: 'Population', action: 'list', order: 6
    ]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [populationInstanceList: Population.list(params), populationInstanceTotal: Population.count()]
    }

    def create() {
        [populationInstance: new Population(params)]
    }

    def save() {
        def populationInstance = new Population(params)
        if (!populationInstance.save(flush: true)) {
            render(view: "create", model: [populationInstance: populationInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'population.label', default: 'Population'), populationInstance.identification])
        redirect(action: "show", id: populationInstance.id)
    }

    def show(Long id) {
        def populationInstance = Population.get(id)
        if (!populationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'population.label', default: 'Population'), id])
            redirect(action: "list")
            return
        }

        [populationInstance: populationInstance]
    }

    def edit(Long id) {
        def populationInstance = Population.get(id)
        if (!populationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'population.label', default: 'Population'), id])
            redirect(action: "list")
            return
        }

        [populationInstance: populationInstance]
    }

    def update(Long id, Long version) {
        def populationInstance = Population.get(id)
        if (!populationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'population.label', default: 'Population'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (populationInstance.version > version) {
                populationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'population.label', default: 'Population')] as Object[],
                        "Another user has updated this Population while you were editing")
                render(view: "edit", model: [populationInstance: populationInstance])
                return
            }
        }

        populationInstance.properties = params

        if (!populationInstance.save(flush: true)) {
            render(view: "edit", model: [populationInstance: populationInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'population.label', default: 'Population'), populationInstance.identification])
        redirect(action: "show", id: populationInstance.id)
    }

    def delete(Long id) {
        def populationInstance = Population.get(id)
        if (!populationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'population.label', default: 'Population'), id])
            redirect(action: "list")
            return
        }

        def label = populationInstance.identification
        try {
            populationInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'population.label', default: 'Population'), label])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'population.label', default: 'Population'), label])
            redirect(action: "show", id: id)
        }
    }

    def addPopulation(String name, String comment, Boolean common, String latitude, String longitude) {

        println "adding a population ${params}"

        Population population = new Population(
                identification: name
                , comment: comment
                , common: common
                , sourceLatString: latitude
                , sourceLongString: longitude
        ).save()

        Line line = new Line(
                name: name
        ).save flush: true , failOnError: true



        println "ADDED Population ${population}"

        Map<String,Object> returnObject = new HashMap<>()
        returnObject.put("population",population)
        returnObject.put("line",line)

//        render population as JSON
        render returnObject as JSON
    }
}
