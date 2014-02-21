package edu.uoregon.sticklebackdb

import grails.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException

class StockController {

    def stockService
    def researcherService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static navigation = [
            title: 'Stocks', action: 'list', order: 1,
    ]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [stockInstanceList: Stock.list(params), stockInstanceTotal: Stock.count()]
    }

    def search(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        String query = params.q

        if (query.contains(".")) {
            // search for individual
            redirect(action: "search", controller: "individual", params: params)
        } else {
            List<Stock> stockList = Stock.findAllByStockID(query, params)
            Integer stockCount = Stock.countByStockID(query)
            def model = [stockInstanceList: stockList, stockInstanceTotal: stockCount]
            switch (stockCount) {
                case 1:
                    render(view: "show", model:[stockInstance: stockList.get(0)])
                    break
                case 0:
                    flash.message = "No stocks found for stock ID [${query}]"
//                    break
                default:
                    render(view: "list", model: model)
            }
        }

    }

    def create() {
        params.stockID = stockService.getNextStockID()
        Stock stock = new Stock(params)

        List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")
        def model = [stockInstance: stock, maxStock: Stock.list(max: 1, sort: "stockID", order: "desc")[0], stockNames: stockNames]
        render(view: "create", model: model)

    }

    def save() {
        def stockInstance = new Stock(params)

        if (false == researcherService.isAdmin()) {
            Stock previousStock = Stock.findByStockName(stockInstance.stockName)
            if (previousStock == null) {
                stockInstance.errors.rejectValue("stockName", "stock.name.must.exist", "Stock use previous stock name")
//                respond stockInstance.errors, view:'edit'
                List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")
                render(view: "create", model: [stockInstance: stockInstance, stockNames: stockNames])
                return
            }
        }

        if (!stockInstance.save(flush: true)) {
            List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")
            render(view: "create", model: [stockInstance: stockInstance, stockNames: stockNames])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'stock.label', default: 'Stock'), stockInstance.id])
        redirect(action: "show", id: stockInstance.id)
    }

    def show(Long id) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "list")
            return
        }


        [stockInstance: stockInstance]
    }

    def edit(Long id) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "list")
            return
        }

        List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")

        [stockInstance: stockInstance, stockNames: stockNames]
    }

    @Transactional
    def update(Long id, Long version) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (stockInstance.version > version) {
                stockInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'stock.label', default: 'Stock')] as Object[],
                        "Another user has updated this Stock while you were editing")
                respond stockInstance.errors, view: 'edit'
                return
            }
        }

        stockInstance.properties = params

        if (false == researcherService.isAdmin()) {
            Stock previousStock = Stock.findByStockName(stockInstance.stockName)
            if (previousStock == null) {
                stockInstance.errors.rejectValue("stockName", "stock.name.must.exist", "Stock use previous stock name")

//                List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")

                println "should be responding with some errors: ${stockInstance.errors}"
//                respond stockInstance.errors, view:'edit'
                List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")
                render(view: "edit", model: [stockInstance: stockInstance, stockNames: stockNames])
                return
            }
        }

        if (!stockInstance.save(flush: true)) {
//            render(view: "edit", model: [stockInstance: stockInstance])
            List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")
            render(view: "edit", model: [stockInstance: stockInstance, stockNames: stockNames])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'stock.label', default: 'Stock'), stockInstance.id])
        redirect(action: "show", id: stockInstance.id)
    }

    def delete(Long id) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "list")
            return
        }

        try {
            stockInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "show", id: id)
        }
    }

//    String getNextStockID(){
//        List stocks = Stock.listOrderByStockID()
//        double max = 0
//        stocks.each(){
//            double tmp = it.stockID.toDouble()
//
//           if(tmp > max)
//            max = tmp
//        }
//        def nextID = max + 1.0
//        return String.format("%.4f", nextID)
//    }
}
