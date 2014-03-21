package edu.uoregon.sticklebackdb

import org.springframework.dao.DataIntegrityViolationException

class MeasuredValueController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static navigation = [
            title: 'Measurements', action: 'list', order: 115
    ]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [measuredValueInstanceList: MeasuredValue.list(params), measuredValueInstanceTotal: MeasuredValue.count()]
    }

    def create() {
        [measuredValueInstance: new MeasuredValue(params)]
    }

    def save() {
        def measuredValueInstance = new MeasuredValue(params)
        if (!measuredValueInstance.save(flush: true)) {
            render(view: "create", model: [measuredValueInstance: measuredValueInstance])
            return
        }

        if(measuredValueInstance.checkValid()){
            flash.error = measuredValueInstance.checkValid()
            render(view: "create", model: [measuredValueInstance: measuredValueInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'measuredValue.label', default: 'MeasuredValue'), measuredValueInstance.id])
        redirect(action: "show", id: measuredValueInstance.id)
    }

    def show(Long id) {
        def measuredValueInstance = MeasuredValue.get(id)
        if (!measuredValueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'measuredValue.label', default: 'MeasuredValue'), id])
            redirect(action: "list")
            return
        }

        [measuredValueInstance: measuredValueInstance]
    }

    def edit(Long id) {
        def measuredValueInstance = MeasuredValue.get(id)
        if (!measuredValueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'measuredValue.label', default: 'MeasuredValue'), id])
            redirect(action: "list")
            return
        }

        [measuredValueInstance: measuredValueInstance]
    }

    def update(Long id, Long version) {
        def measuredValueInstance = MeasuredValue.get(id)
        if (!measuredValueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'measuredValue.label', default: 'MeasuredValue'), id])
            redirect(action: "list")
            return
        }
        if(measuredValueInstance.checkValid()){
            flash.error = measuredValueInstance.checkValid()
            render(view: "edit",id:measuredValueInstance.id, model: [measuredValueInstance: measuredValueInstance])
            return
        }

        if (version != null) {
            if (measuredValueInstance.version > version) {
                measuredValueInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'measuredValue.label', default: 'MeasuredValue')] as Object[],
                          "Another user has updated this MeasuredValue while you were editing")
                render(view: "edit", model: [measuredValueInstance: measuredValueInstance])
                return
            }
        }

        measuredValueInstance.properties = params

        if (!measuredValueInstance.save(flush: true)) {
            render(view: "edit", model: [measuredValueInstance: measuredValueInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'measuredValue.label', default: 'MeasuredValue'), measuredValueInstance.id])
        redirect(action: "show", id: measuredValueInstance.id)
    }

    def delete(Long id) {
        def measuredValueInstance = MeasuredValue.get(id)
        if (!measuredValueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'measuredValue.label', default: 'MeasuredValue'), id])
            redirect(action: "list")
            return
        }

        try {
            measuredValueInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'measuredValue.label', default: 'MeasuredValue'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'measuredValue.label', default: 'MeasuredValue'), id])
            redirect(action: "show", id: id)
        }
    }
}
