package edu.uoregon.sticklebackdb

import org.springframework.dao.DataIntegrityViolationException

class GeneticsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [geneticsInstanceList: Genetics.list(params), geneticsInstanceTotal: Genetics.count()]
    }

    def create() {
        [geneticsInstance: new Genetics(params)]
    }

    def save() {
        def geneticsInstance = new Genetics(params)
        if (!geneticsInstance.save(flush: true)) {
            render(view: "create", model: [geneticsInstance: geneticsInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'genetics.label', default: 'Genetics'), geneticsInstance.id])
        redirect(action: "show", id: geneticsInstance.id)
    }

    def show(Long id) {
        def geneticsInstance = Genetics.get(id)
        if (!geneticsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'genetics.label', default: 'Genetics'), id])
            redirect(action: "list")
            return
        }

        [geneticsInstance: geneticsInstance]
    }

    def edit(Long id) {
        def geneticsInstance = Genetics.get(id)
        if (!geneticsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'genetics.label', default: 'Genetics'), id])
            redirect(action: "list")
            return
        }

        [geneticsInstance: geneticsInstance]
    }

    def update(Long id, Long version) {
        def geneticsInstance = Genetics.get(id)
        if (!geneticsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'genetics.label', default: 'Genetics'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (geneticsInstance.version > version) {
                geneticsInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'genetics.label', default: 'Genetics')] as Object[],
                          "Another user has updated this Genetics while you were editing")
                render(view: "edit", model: [geneticsInstance: geneticsInstance])
                return
            }
        }

        geneticsInstance.properties = params

        if (!geneticsInstance.save(flush: true)) {
            render(view: "edit", model: [geneticsInstance: geneticsInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'genetics.label', default: 'Genetics'), geneticsInstance.id])
        redirect(action: "show", id: geneticsInstance.id)
    }

    def delete(Long id) {
        def geneticsInstance = Genetics.get(id)
        if (!geneticsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'genetics.label', default: 'Genetics'), id])
            redirect(action: "list")
            return
        }

        try {
            geneticsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'genetics.label', default: 'Genetics'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'genetics.label', default: 'Genetics'), id])
            redirect(action: "show", id: id)
        }
    }
}
