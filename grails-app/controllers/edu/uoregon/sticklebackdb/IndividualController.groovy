package edu.uoregon.sticklebackdb

import org.springframework.dao.DataIntegrityViolationException

class IndividualController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [individualInstanceList: Individual.list(params), individualInstanceTotal: Individual.count()]
    }

    def create() {
        [individualInstance: new Individual(params)]
    }

    def save() {
        def individualInstance = new Individual(params)
        if (!individualInstance.save(flush: true)) {
            render(view: "create", model: [individualInstance: individualInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'individual.label', default: 'Individual'), individualInstance.id])
        redirect(action: "show", id: individualInstance.id)
    }

    def show(Long id) {
        def individualInstance = Individual.get(id)
        if (!individualInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'individual.label', default: 'Individual'), id])
            redirect(action: "list")
            return
        }

        [individualInstance: individualInstance]
    }

    def edit(Long id) {
        def individualInstance = Individual.get(id)
        if (!individualInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'individual.label', default: 'Individual'), id])
            redirect(action: "list")
            return
        }

        [individualInstance: individualInstance]
    }

    def update(Long id, Long version) {
        def individualInstance = Individual.get(id)
        if (!individualInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'individual.label', default: 'Individual'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (individualInstance.version > version) {
                individualInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'individual.label', default: 'Individual')] as Object[],
                          "Another user has updated this Individual while you were editing")
                render(view: "edit", model: [individualInstance: individualInstance])
                return
            }
        }

        individualInstance.properties = params

        if (!individualInstance.save(flush: true)) {
            render(view: "edit", model: [individualInstance: individualInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'individual.label', default: 'Individual'), individualInstance.id])
        redirect(action: "show", id: individualInstance.id)
    }

    def delete(Long id) {
        def individualInstance = Individual.get(id)
        if (!individualInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'individual.label', default: 'Individual'), id])
            redirect(action: "list")
            return
        }

        try {
            individualInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'individual.label', default: 'Individual'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'individual.label', default: 'Individual'), id])
            redirect(action: "show", id: id)
        }
    }
}
