package edu.uoregon.sticklebackdb

import org.springframework.dao.DataIntegrityViolationException

class AquariaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static navigation = [
            title:'Aquaria',action: 'list',order:3
    ]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [aquariaInstanceList: Aquaria.list(params), aquariaInstanceTotal: Aquaria.count()]
    }

    def create() {
        [aquariaInstance: new Aquaria(params)]
    }

    def save() {
        def aquariaInstance = new Aquaria(params)
        if (!aquariaInstance.save(flush: true)) {
            render(view: "create", model: [aquariaInstance: aquariaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'aquaria.label', default: 'Aquaria'), aquariaInstance.id])
        redirect(action: "show", id: aquariaInstance.id)
    }

    def show(Long id) {
        def aquariaInstance = Aquaria.get(id)
        if (!aquariaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'aquaria.label', default: 'Aquaria'), id])
            redirect(action: "list")
            return
        }

        [aquariaInstance: aquariaInstance]
    }

    def edit(Long id) {
        def aquariaInstance = Aquaria.get(id)
        if (!aquariaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'aquaria.label', default: 'Aquaria'), id])
            redirect(action: "list")
            return
        }

        [aquariaInstance: aquariaInstance]
    }

    def update(Long id, Long version) {
        def aquariaInstance = Aquaria.get(id)
        if (!aquariaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'aquaria.label', default: 'Aquaria'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (aquariaInstance.version > version) {
                aquariaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'aquaria.label', default: 'Aquaria')] as Object[],
                          "Another user has updated this Aquaria while you were editing")
                render(view: "edit", model: [aquariaInstance: aquariaInstance])
                return
            }
        }

        aquariaInstance.properties = params

        if (!aquariaInstance.save(flush: true)) {
            render(view: "edit", model: [aquariaInstance: aquariaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'aquaria.label', default: 'Aquaria'), aquariaInstance.id])
        redirect(action: "show", id: aquariaInstance.id)
    }

    def delete(Long id) {
        def aquariaInstance = Aquaria.get(id)
        if (!aquariaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'aquaria.label', default: 'Aquaria'), id])
            redirect(action: "list")
            return
        }

        try {
            aquariaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'aquaria.label', default: 'Aquaria'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'aquaria.label', default: 'Aquaria'), id])
            redirect(action: "show", id: id)
        }
    }
}
