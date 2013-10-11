package edu.uoregon.sticklebackdb

import org.springframework.dao.DataIntegrityViolationException

class PopulationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static navigation = [
            title:'Population',action: 'list',order:5
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

        flash.message = message(code: 'default.created.message', args: [message(code: 'population.label', default: 'Population'), populationInstance.id])
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

        flash.message = message(code: 'default.updated.message', args: [message(code: 'populaton.label', default: 'Popultaion'), populationInstance.id])
        redirect(action: "show", id: populationInstance.id)
    }

    def delete(Long id) {
        def populationInstance = Population.get(id)
        if (!populationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'population.label', default: 'Population'), id])
            redirect(action: "list")
            return
        }

        try {
            populationInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'population.label', default: 'Population'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'population.label', default: 'Population'), id])
            redirect(action: "show", id: id)
        }
    }
}
