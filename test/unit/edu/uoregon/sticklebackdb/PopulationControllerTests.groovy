package edu.uoregon.sticklebackdb

import grails.test.mixin.*

@TestFor(PopulationController)
@Mock(Population)
class PopulationControllerTests {

    def populateValidParams(params) {
        assert params != null
        params["comment"] = 'someValidName'
        params["sourceLat"] = 13.3
        params["sourceLong"] = 12.2
        params["identification"] = 'somewhere'
    }

    void testIndex() {
        controller.index()
        assert "/population/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.populationInstanceList.size() == 0
        assert model.populationInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.populationInstance != null
    }

    void testSave() {
        controller.save()

        assert model.populationInstance != null
        assert view == '/population/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/population/show/1'
        assert controller.flash.message != null
        assert Population.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/population/list'

        populateValidParams(params)
        def population = new Population(params)

        assert population.save() != null

        params.id = population.id

        def model = controller.show()

        assert model.populationInstance == population
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/population/list'

        populateValidParams(params)
        def population = new Population(params)

        assert population.save() != null

        params.id = population.id

        def model = controller.edit()

        assert model.populationInstance == population
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/population/list'

        response.reset()

        populateValidParams(params)
        def population = new Population(params)

        assert population.save() != null

        // test invalid parameters in update
        params.id = population.id
        params.identification = null

        controller.update()

        assert view == "/population/edit"
        assert model.populationInstance != null

        population.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/population/show/$population.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        population.clearErrors()

        populateValidParams(params)
        params.id = population.id
        params.version = -1
        controller.update()

        assert view == "/population/edit"
        assert model.populationInstance != null
        assert model.populationInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/population/list'

        response.reset()

        populateValidParams(params)
        def population = new Population(params)

        assert population.save() != null
        assert Population.count() == 1

        params.id = population.id

        controller.delete()

        assert Population.count() == 0
        assert Population.get(population.id) == null
        assert response.redirectedUrl == '/population/list'
    }
}
