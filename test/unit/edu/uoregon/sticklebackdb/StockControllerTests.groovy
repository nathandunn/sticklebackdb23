package edu.uoregon.sticklebackdb



import org.junit.*
import grails.test.mixin.*

@TestFor(StockController)
@Mock([Stock,StockService,ResearcherService,Line,Individual,Population,Capture])
class StockControllerTests {

    String populationName = "Rabbit Slough"

    def populateValidParams(params) {
        assert params != null
        params["stockID"] = 11
        params["comments"] = "whatup"
        params["stockName"] = "some name"

        Population population  = Population.findByIdentification(populationName)
        if(!population){
            population = new Population(
                    identification: populationName
            ).save(failOnError: true,flush:true)
        }
        params.population = population.id
    }

    void testIndex() {
        controller.index()
        assert "/stock/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.stockInstanceList.size() == 0
        assert model.stockInstanceTotal == 0
    }

    void testCreateFromBreeding() {
        controller.stockService = new StubStockService()
        controller.createFromBreeding()

        assert model.stockInstance != null
    }

    void testCreateFromCapture() {
        controller.stockService = new StubStockService()
        controller.createFromCapture()

        assert model.stockInstance != null
    }


    void testSaveCapture() {
        controller.stockService = new StockService()
        controller.researcherService = new ResearcherService()
        controller.saveCapture()

        assert model.stockInstance != null
        assert view == '/stock/createFromCapture'

        response.reset()

        populateValidParams(params)
        controller.saveCapture()

        assert response.redirectedUrl == '/stock/show/1'
        assert controller.flash.message != null
        assert Stock.count() == 1
    }

    void testSaveFromBreeding() {
//        controller.stockService = new StockService()
        controller.researcherService = new StubResearchService()
        controller.saveFromBreeding()

        assert model.stockInstance != null
        assert view == '/stock/createFromBreeding'

        response.reset()

        populateValidParams(params)
        Line line = Line.findOrSaveByName(populationName)
        Individual maternalIndividual = new Individual(
             individualID: 1
        ).save(failOnError: true)
        Individual paternalIndividual = new Individual(
                individualID: 2
        ).save(failOnError: true)
        params.maternalIndividual  = maternalIndividual
        params.paternalIndividual  = paternalIndividual
        params.line = line


        controller.saveFromBreeding()

        println view

        assert response.redirectedUrl == '/stock/show/1'
        assert controller.flash.message != null
        assert Stock.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/stock/list'

        populateValidParams(params)
        def stock = new Stock(params)

        assert stock.save() != null

        params.id = stock.id

        def model = controller.show()

        assert model.stockInstance == stock
    }

    // does not support this type
    @Ignore
    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/stock/list'

        populateValidParams(params)
        def stock = new Stock(params)

        assert stock.save() != null

        params.id = stock.id

        def model = controller.edit()

        assert model.stockInstance == stock
    }

    void testUpdate() {
        controller.researcherService = new StubResearchService()
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/stock/list'

        response.reset()

        populateValidParams(params)
        def stock = new Stock(params)

        assert stock.save() != null

        // test invalid parameters in update
        params.id = stock.id
        params.stockID = "Asdfadsf"

        controller.update()

        assert view == "/stock/edit"
        assert model.stockInstance != null

        stock.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/stock/show/$stock.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        stock.clearErrors()

        populateValidParams(params)
        params.id = stock.id
        params.version = -1
        controller.update()

//        assert view == "/stock/edit"
        assert view == "edit"
        assert model.stockInstance != null
        assert model.stockInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/stock/list'

        response.reset()

        populateValidParams(params)
        def stock = new Stock(params)

        assert stock.save() != null
        assert Stock.count() == 1

        params.id = stock.id

        controller.delete()

        assert Stock.count() == 0
        assert Stock.get(stock.id) == null
        assert response.redirectedUrl == '/stock/list'
    }
}
