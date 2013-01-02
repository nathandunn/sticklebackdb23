package edu.uoregon.sticklebackdb

import org.springframework.dao.DataIntegrityViolationException

class ContainerController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static navigation = [
            title:'Container',action: 'list',order:3
    ]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [containerInstanceList: Container.list(params), containerInstanceTotal: Container.count()]
    }

    def create() {
        [containerInstance: new Container(params)]
    }

    def save() {
        def containerInstance = new Container(params)
        if (!containerInstance.save(flush: true)) {
            render(view: "create", model: [containerInstance: containerInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'container.label', default: 'Container'), containerInstance.id])
        redirect(action: "show", id: containerInstance.id)
    }

    def show(Long id) {
        def containerInstance = Container.get(id)
        if (!containerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'container.label', default: 'Container'), id])
            redirect(action: "list")
            return
        }

        [containerInstance: containerInstance]
    }

    def edit(Long id) {
        def containerInstance = Container.get(id)
        if (!containerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'container.label', default: 'Container'), id])
            redirect(action: "list")
            return
        }

        [containerInstance: containerInstance]
    }

    def update(Long id, Long version) {
        def containerInstance = Container.get(id)
        if (!containerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'container.label', default: 'Container'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (containerInstance.version > version) {
                containerInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'container.label', default: 'Container')] as Object[],
                          "Another user has updated this Container while you were editing")
                render(view: "edit", model: [containerInstance: containerInstance])
                return
            }
        }

        containerInstance.properties = params

        if (!containerInstance.save(flush: true)) {
            render(view: "edit", model: [containerInstance: containerInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'container.label', default: 'Container'), containerInstance.id])
        redirect(action: "show", id: containerInstance.id)
    }

    def delete(Long id) {
        def containerInstance = Container.get(id)
        if (!containerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'container.label', default: 'Container'), id])
            redirect(action: "list")
            return
        }

        try {
            containerInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'container.label', default: 'Container'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'container.label', default: 'Container'), id])
            redirect(action: "show", id: id)
        }
    }
}
