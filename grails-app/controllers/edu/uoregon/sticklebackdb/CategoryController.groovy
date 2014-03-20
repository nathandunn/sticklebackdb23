package edu.uoregon.sticklebackdb

import org.springframework.dao.DataIntegrityViolationException

class CategoryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

//    static navigation = [
//            title: 'Category', action: 'list', order: 110
//    ]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [categoryInstanceList: Category.list(params), categoryInstanceTotal: Category.count()]
    }

    def create() {
        [categoryInstance: new Category(params)]
    }

    def save() {
        def categoryInstance = new Category(params)
        if (!categoryInstance.save(flush: true)) {
            render(view: "create", model: [categoryInstance: categoryInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.name])
        redirect(action: "show", id: categoryInstance.id)
    }

    def show(Long id) {
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "list")
            return
        }

//        List<MeasuredValue> measuredValues = MeasuredValue.executeQuery("from MeasuredValue mv where mv.category = :category order by mv.category.name asc ",[category: categoryInstance])
        def criteria = MeasuredValue.createCriteria()
        List<MeasuredValue> measuredValues = criteria.list {
            eq("category", categoryInstance)
        }

        Map<String, CategoryView> map = new HashMap<String, CategoryView>()

        for (MeasuredValue measuredValue in measuredValues) {
            CategoryView categoryView = map.get(measuredValue.category.name)
            if (!categoryView) {
                categoryView = new CategoryView(category: measuredValue.category)
            }
            categoryView.addMeasuredValue(measuredValue)
            map.put(measuredValue.category.name, categoryView)
        }

        [categoryInstance: categoryInstance, measuredValues: measuredValues, categories: map.values()]
    }

    def edit(Long id) {
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "list")
            return
        }

        [categoryInstance: categoryInstance]
    }

    def update(Long id, Long version) {
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (categoryInstance.version > version) {
                categoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'category.label', default: 'Category')] as Object[],
                        "Another user has updated this Category while you were editing")
                render(view: "edit", model: [categoryInstance: categoryInstance])
                return
            }
        }

        categoryInstance.properties = params

        if (!categoryInstance.save(flush: true)) {
            render(view: "edit", model: [categoryInstance: categoryInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.name])
        redirect(action: "show", id: categoryInstance.id)
    }

    def delete(Long id) {
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "list")
            return
        }

        try {
            categoryInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "show", id: id)
        }
    }
}
