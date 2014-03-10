package edu.uoregon.sticklebackdb

import grails.converters.JSON
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject
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
//        params.max = Math.min(max ?: 10, 100)
        params.max = 1000
        String query = params.q

//        if (query.startsWith("[a-zA-Z]")) {
        if (query ==~ "[a-zA-Z].*") {
            println "starts with text ${query}"
            List<Stock> stockList = Stock.findAllByStockNameIlike("%" + query + "%", params)
            Integer stockCount = Stock.countByStockNameIlike("%" + query + "%")
            def model = [stockInstanceList: stockList, stockInstanceTotal: stockCount]
            flash.message = "${stockCount} found for query: [${query}]"
            switch (stockCount) {
                case 1:
                    render(view: "show", model: [stockInstance: stockList.get(0)])
                    break
                case 0:
                    flash.message = "No stocks found for stock name [${query}]"
//                    break
                default:
                    render(view: "list", model: model)
            }
        } else if (query.contains(".")) {
            // search for individual
            redirect(action: "search", controller: "individual", params: params)
        } else {
            List<Stock> stockList = Stock.findAllByStockID(query, params)
            Integer stockCount = Stock.countByStockID(query)
            def model = [stockInstanceList: stockList, stockInstanceTotal: stockCount]
            switch (stockCount) {
                case 1:
                    render(view: "show", model: [stockInstance: stockList.get(0)])
                    break
                case 0:
                    flash.message = "No stocks found for stock ID [${query}]"
//                    break
                default:
                    render(view: "list", model: model)
            }
        }

    }

    def createFromCapture() {
        params.stockID = stockService.getNextStockID()
        Stock stock = new Stock(params)

        List<Line> linesWithCapture = new ArrayList<>()
        Line.listOrderByName().captures.each { it ->
            for (Line line in it.line) {
                linesWithCapture.add(line)
            }
        }

        linesWithCapture.unique(true)

//        println "lines with capture ${linesWithCapture}"
        List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")
        def model = [stockInstance: stock, maxStock: Stock.list(max: 1, sort: "stockID", order: "desc")[0], stockNames: stockNames, lines: linesWithCapture]
        render(view: "createFromCapture", model: model)
    }

    def createFromBreeding() {
        params.stockID = stockService.getNextStockID()
        Stock stock = new Stock(params)

        List<Line> linesWithCapture = new ArrayList<>()
        Line.all.captures.each { it ->
            for (Line line in it.line) {
                linesWithCapture.add(line)
            }
        }

        linesWithCapture.unique(true)

        List<Line> linesWithoutCapture = Line.all.minus(linesWithCapture)

        List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")
        def model = [stockInstance: stock, maxStock: Stock.list(max: 1, sort: "stockID", order: "desc")[0], stockNames: stockNames, lines: linesWithoutCapture]
        render(view: "createFromBreeding", model: model)
    }

    def create() {
        redirect(action: "list")
//        params.stockID = stockService.getNextStockID()
//        Stock stock = new Stock(params)
//
//        List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")
//        def model = [stockInstance: stock, maxStock: Stock.list(max: 1, sort: "stockID", order: "desc")[0], stockNames: stockNames]
//        render(view: "create", model: model)

    }


    def saveCapture() {
        def stockInstance = new Stock(params)


        println "params ${params}"

        List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")

        if (stockInstance.line == null) {
            flash.message = "Must associate a line with a stock"
            render(view: "createFromCapture", model: [stockInstance: stockInstance, stockNames: stockNames])
            return
        }

        Population population = Population.findById(params.population)
        Date captureDate = params.newLineDate
        String captureComment = params.newLinecomment
        Capture capture = new Capture(
                population: population
                ,captureDate: captureDate
                ,comment: captureComment
                ,line: stockInstance.line
        ).save(flush:true,failOnError: true)

        stockInstance.line.addToCaptures(capture)

        if (!stockInstance.save(flush: true)) {
            render(view: "createFromCapture", model: [stockInstance: stockInstance, stockNames: stockNames])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'stock.label', default: 'Stock'), stockInstance.stockIDLabel])
        redirect(action: "show", id: stockInstance.id)
    }

    def saveFromBreeding() {
        def stockInstance = new Stock(params)

        List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")

        if (stockInstance.line == null) {
            flash.message = "Must associate a line with a stock"
            render(view: "createFromBreeding", model: [stockInstance: stockInstance, stockNames: stockNames])
            return
        }

//        if(!stockInstance.fertilizationDate){
//            flash.message = "Must have a fertilization date or a line with a capture"
//            render(view: "createFromBreeding", model: [stockInstance: stockInstance, stockNames: stockNames])
//            return
//        }

        if (false == researcherService.isAdmin()) {
            Stock previousStock = Stock.findByStockName(stockInstance.stockName)
            if (previousStock == null) {
                stockInstance.errors.rejectValue("stockName", "stock.name.must.exist", "Use a previous stock name or ask Administrator to add it for you.")
                render(view: "createFromBreeding", model: [stockInstance: stockInstance, stockNames: stockNames])
                return
            }
        }

        if (stockInstance.maternalIndividual == null) {
            flash.message = "Must supply a maternal individual"
            render(view: "createFromBreeding", model: [stockInstance: stockInstance, stockNames: stockNames])
            return
        }

        if (stockInstance.paternalIndividual == null) {
            flash.message = "Must supply a paternal individual"
            render(view: "createFromBreeding", model: [stockInstance: stockInstance, stockNames: stockNames])
            return
        }

        if (!stockInstance.save(flush: true)) {
            render(view: "createFromBreeding", model: [stockInstance: stockInstance, stockNames: stockNames])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'stock.label', default: 'Stock'), stockInstance.stockIDLabel])
        redirect(action: "show", id: stockInstance.id)
    }

//    def save() {
//        def stockInstance = new Stock(params)
//
//        List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")
//
//        if (stockInstance.line == null) {
//            flash.message = "Must associate a line with a stock"
//            render(view: "create", model: [stockInstance: stockInstance, stockNames: stockNames])
//            return
//        }
//
//        if (stockInstance.fertilizationDate && stockInstance?.line?.captures) {
//            flash.message = "Can not have both a fertilization date and a Line with a capture"
//            render(view: "create", model: [stockInstance: stockInstance, stockNames: stockNames])
//            return
//        }
//        if (!stockInstance?.line?.captures && !stockInstance.fertilizationDate) {
//            flash.message = "Must have a fertilization date or a line with a capture"
//            render(view: "create", model: [stockInstance: stockInstance, stockNames: stockNames])
//            return
//        }
//
//        if (false == researcherService.isAdmin()) {
//            Stock previousStock = Stock.findByStockName(stockInstance.stockName)
//            if (previousStock == null) {
//                stockInstance.errors.rejectValue("stockName", "stock.name.must.exist", "Use a previous stock name or ask Administrator to add it for you.")
//                render(view: "create", model: [stockInstance: stockInstance, stockNames: stockNames])
//                return
//            }
//        }
//
//        if (stockInstance.maternalIndividual == null) {
//            flash.message = "Must supply a maternal individual"
//            render(view: "create", model: [stockInstance: stockInstance, stockNames: stockNames])
//            return
//        }
//
//        if (stockInstance.paternalIndividual == null) {
//            flash.message = "Must supply a paternal individual"
//            render(view: "create", model: [stockInstance: stockInstance, stockNames: stockNames])
//            return
//        }
//
//        if (!stockInstance.save(flush: true)) {
//            render(view: "create", model: [stockInstance: stockInstance, stockNames: stockNames])
//            return
//        }
//
//        flash.message = message(code: 'default.created.message', args: [message(code: 'stock.label', default: 'Stock'), stockInstance.stockIDLabel])
//        redirect(action: "show", id: stockInstance.id)
//    }

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

        List<Line> lineList = new ArrayList<>()
        Line.all.captures.each { it ->
            for (Line line in it.line) {
                lineList.add(line)
            }
        }

        lineList.unique(true)

        if (stockInstance.isBred()) {
            lineList = Line.all.minus(lineList)
        }

        List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")

        [stockInstance: stockInstance, stockNames: stockNames, lines: lineList]
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
                stockInstance.errors.rejectValue("stockName", "stock.name.must.exist", "Use a previous stock name or ask Administrator to add it for you.")

//                List<String> stockNames = Stock.executeQuery("select distinct s.stockName from Stock s order by s.stockName asc ")

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

        flash.message = message(code: 'default.updated.message', args: [message(code: 'stock.label', default: 'Stock'), stockInstance.stockIDLabel])
        redirect(action: "show", id: stockInstance.id)
    }

    def delete(Long id) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "list")
            return
        }

        def label = stockInstance.stockIDLabel
        try {
            stockInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'stock.label', default: 'Stock'), label])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'stock.label', default: 'Stock'), label])
            redirect(action: "show", id: id)
        }
    }

    def print(Integer id) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'individual.label', default: 'Individual'), id])
            redirect(action: "list")
            return
        }

        render(view: "label", model: [stockInstance: stockInstance])
    }

    def findCommonLineForStocks() {
        println "params ${params}"
        def paternalStockId = params.paternalStockId
        def maternalStockId = params.maternalStockId
        println "finding common lines: ${paternalStockId} - ${maternalStockId}"
        Stock paternalStock = Stock.findById(paternalStockId as Long)
        Stock maternalStock = Stock.findById(maternalStockId as Long)

        if (paternalStock.line && paternalStock.line == maternalStock.line) {
            render paternalStock.line.id
            return
        }

        render ''
    }

    private List<Stock> getAllParentStocks(Stock stock, List<Stock> parentStocks) {

        if (!stock.paternalStock && !stock.maternalStock) {
            return parentStocks
        }

        if (stock.paternalStock) {
            parentStocks.add(stock.paternalStock)
            parentStocks.addAll(getAllParentStocks(stock.paternalStock, parentStocks))
        }

        if (stock.maternalStock) {
            parentStocks.add(stock.maternalStock)
            parentStocks.addAll(getAllParentStocks(stock.maternalStock, parentStocks))
        }

        parentStocks.unique()

        return parentStocks
    }

    private List<Stock> getAllChildStocks(Stock stock, List<Stock> childStocks) {

//        return childStocks

        println "child stocks: ${stock.stockIDLabel}"
        List<Stock> broodStocks = Stock.findAllByPaternalStock(stock)
        println "broods: ${broodStocks.size()}"
        List<Stock> mareStocks = Stock.findAllByMaternalStock(stock)
        println "mare: ${broodStocks.size()}"

        if (!broodStocks && !mareStocks) {
            childStocks.unique(true)
            return childStocks
        }

        println "evaluating: ${childStocks.size()}"
        for (brood in broodStocks) {
            childStocks.add(brood)
            childStocks.addAll(getAllChildStocks(brood, childStocks))
        }
        println "post-brood: ${childStocks.size()}"

        for (mare in mareStocks) {
            childStocks.add(mare)
            childStocks.addAll(getAllChildStocks(mare, childStocks))
        }
        println "post-mare: ${childStocks.size()}"

        childStocks.unique(true)

        println "post-unique: ${childStocks.size()}"

        return childStocks
    }

    def findStock(){
        def stockID = params.id
        Stock stock = Stock.findById(stockID)
        render stock as JSON
    }

    def bracket() {

        def stockID = params.stockID
        Stock stock = Stock.findByStockID(stockID)

        List<Stock> childStocks = getAllChildStocks(stock, new ArrayList<Stock>())
        childStocks.sort(true, new Comparator<Stock>() {
            @Override
            int compare(Stock o1, Stock o2) {
                return o1.stockID - o2.stockID
            }
        })
        List<Stock> parentStocks = getAllParentStocks(stock, new ArrayList<Stock>())
        parentStocks.sort(true) { a, b ->
            a.stockID - b.stockID
        }

        [stockInstance: stock, childStocks: childStocks, parentStocks: parentStocks]

    }

    private def getParentArray(Stock stock) {

        if (!stock.paternalStock) return

        def paternalStock = stock.paternalStock
        def maternalStock = stock.maternalStock

        JSONArray winnerArray = new JSONArray()

        if (paternalStock && maternalStock) {
            def paternalObject = new JSONObject()
            paternalObject.put("name", paternalStock.stockIDLabel)
            paternalObject.put("winners", getParentArray(paternalStock))
            def maternalObject = new JSONObject()
            maternalObject.put("name", maternalStock.stockIDLabel)
            maternalObject.put("winners", getParentArray(maternalStock))

            winnerArray.add(paternalObject)
            winnerArray.add(maternalObject)
        }

        return winnerArray
    }

    private def getChildArray(Stock stock) {

        List<Stock> broods = Stock.findAllByPaternalStock(stock)
        List<Stock> mares = Stock.findAllByMaternalStock(stock)

        JSONArray winnerArray = new JSONArray()

        for (brood in broods) {
            def broodObject = new JSONObject()
            broodObject.put("name", brood.stockIDLabel)
            broodObject.put("challengers", getChildArray(brood))


            winnerArray.add(broodObject)
        }

        for (mare in mares) {
            def mareObject = new JSONObject()
            mareObject.put("name", mare.stockIDLabel)
            mareObject.put("challengers", getChildArray(mare))
            winnerArray.add(mareObject)
        }

        return winnerArray
    }

    def lineage() {

        def stockID = params.stockID ?: 108
        Stock stock = Stock.findByStockID(stockID)

        // root object!
        def jsonObject = new JSONObject()
        jsonObject.put("name", stock.stockIDLabel)
        jsonObject.put("winners", getParentArray(stock))
        jsonObject.put("challengers", getChildArray(stock))

        render jsonObject as JSON
    }


}
