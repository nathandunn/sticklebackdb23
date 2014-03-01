package edu.uoregon.sticklebackdb

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class IndividualController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static navigation = [
            title: 'Individuals', action: 'list', order: 2
    ]

    def stockService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [individualInstanceList: Individual.list(params), individualInstanceTotal: Individual.count()]
    }

    def query(Long pStockID) {
        render(view: "list", model: [individualInstanceList: Individual.findAllByStockID(pStockID), individualInstanceTotal: Individual.findAllByStockID(pStockID).size()])
    }

    def create() {
        [individualInstance: new Individual(params)]
    }

    def search(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        println "${params}"
        String query = params.q

        if(query.contains(".")){
            Integer stockID = query.split("\\.")[0] as Integer
            Integer individualID = query.split("\\.")[1] as Integer
            List<Individual> individuals = Individual.findAllByIndividualIDAndStockID(individualID,stockID,params)
            Integer individualCount = Individual.countByIndividualIDAndStockID(individualID,stockID)
            def model = [individualInstanceList: individuals, individualInstanceTotal: individualCount]

            switch (individualCount) {
                case 1:
                    render(view: "show", model:[individualInstance: individuals.get(0)])
                    return
                    break
                case 0:
                    flash.message = "No stocks found for individual ID [${query}]"
//                    break
                default:
                    render(view: "list", model: model)
                    return
            }
        }
        else{
            redirect(view:"list")
        }
    }

    def save() {
        def individualInstance = new Individual(params)

//        Stock stock = params.stock
        println "stock to evalute 2 ${params.stock}"
        Stock stock = Stock.findById(params.stock.id)

        println "stock to evalute ${stock}"

        Double individualID = stockService.getNextIndividualID(stock)
        println "individualID ${individualID}"
        individualInstance.individualID = individualID


        if (!individualInstance.save(flush: true)) {
            render(view: "create", model: [individualInstance: individualInstance])
            return
        }

//        stock.addToIndividuals(individualInstance)
//        individualInstance.stock = stock
//
//        stock.save(flush: true)
//        individualInstance.save(flush: true)

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

        String fishSexString
        if(individualInstance.fishSex == "Male" ||individualInstance.fishSex == "Female" ){
            fishSexString = individualInstance.fishSex
        }
        else{
            fishSexString = 'N/A'
        }

        [individualInstance: individualInstance, Individual: Individual, Stock: Stock,fishSexString:fishSexString]
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

        params.fishSex = params.fishSexString

        individualInstance.properties = params

        println "fishSex: ${params.fishSex}"
        println "fishSexString: ${params.fishSexString}"

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

    def addIndividualToStock(Long stockId,String location,String comment,String fishSex) {
        Stock stock = Stock.findById(stockId)
        Integer individualID = stockService.getNextIndividualID(stock)
        Individual individual = new Individual(
                fishLocation: location
                ,comments: comment
                ,fishSex: fishSex
                ,individualID: individualID
                ,stock: stock
        )
        individual.validate()
        if(individual.hasErrors()){
            return "failed to add individual ${params}"
        }

        println "adding "
        individual.save flush: true, insert: true

        println "added? ${individual}"

        String excludeGender = fishSex=='male' ? 'female' : 'male'
        return findIndividualsForStock(stockId,excludeGender)
    }

    def findIndividualsForStock(Long stockId,String excludeGender) {
        Stock stock = Stock.findById(stockId)
        println "foudn stock ${stock} for ${stockId}"
        List<Individual> individuals = Individual.findAllByStockAndFishSexNotEqual(stock, excludeGender,[order: "desc", sort: "individualID"])
        println "# of individuals ${individuals.size()}"

        Map<Long, String> strings = new HashMap<>()
        individuals.each { Individual it ->
            strings.put(it.id, it.individualIDLabel)
        }
        println "# of strings ${strings.size()}"

        render strings as JSON
    }

    def getNextIndividualID(Integer stockID) {

        List<Individual> ids = Individual.findAllByStockID(stockID)
        Integer max = stockID
        ids.each() { it ->
            Integer tmp = it.individualID
            if (tmp > max) {
                max = tmp
            }
        }
        Integer nextID = ++max
        render String.format("%.4f", nextID)
    }

    def print(Integer id){
        def individualInstance = Individual.get(id)
        if (!individualInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'individual.label', default: 'Individual'), id])
            redirect(action: "list")
            return
        }

        render (view:"label",model:[individualInstance:individualInstance])
    }

//    def getFormattedID(String individualID){
//        render individualID
//    }
}
