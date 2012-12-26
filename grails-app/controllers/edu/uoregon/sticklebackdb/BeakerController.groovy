package edu.uoregon.sticklebackdb

import org.springframework.dao.DataIntegrityViolationException

class BeakerController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [beakerInstanceList: Beaker.list(params), beakerInstanceTotal: Beaker.count()]
    }

    def create() {
        [beakerInstance: new Beaker(params)]
    }

    def save() {
        def beakerInstance = new Beaker(params)
        if (!beakerInstance.save(flush: true)) {
            render(view: "create", model: [beakerInstance: beakerInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'beaker.label', default: 'Beaker'), beakerInstance.id])
        redirect(action: "show", id: beakerInstance.id)
    }

    def show(Long id) {
        def beakerInstance = Beaker.get(id)
        if (!beakerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'beaker.label', default: 'Beaker'), id])
            redirect(action: "list")
            return
        }

        [beakerInstance: beakerInstance]
    }

    def edit(Long id) {
        def beakerInstance = Beaker.get(id)
        if (!beakerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'beaker.label', default: 'Beaker'), id])
            redirect(action: "list")
            return
        }

        [beakerInstance: beakerInstance]
    }

    def update(Long id, Long version) {
        def beakerInstance = Beaker.get(id)
        if (!beakerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'beaker.label', default: 'Beaker'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (beakerInstance.version > version) {
                beakerInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'beaker.label', default: 'Beaker')] as Object[],
                          "Another user has updated this Beaker while you were editing")
                render(view: "edit", model: [beakerInstance: beakerInstance])
                return
            }
        }

        beakerInstance.properties = params

        if (!beakerInstance.save(flush: true)) {
            render(view: "edit", model: [beakerInstance: beakerInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'beaker.label', default: 'Beaker'), beakerInstance.id])
        redirect(action: "show", id: beakerInstance.id)
    }

    def delete(Long id) {
        def beakerInstance = Beaker.get(id)
        if (!beakerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'beaker.label', default: 'Beaker'), id])
            redirect(action: "list")
            return
        }

        try {
            beakerInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'beaker.label', default: 'Beaker'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'beaker.label', default: 'Beaker'), id])
            redirect(action: "show", id: id)
        }
    }
}
