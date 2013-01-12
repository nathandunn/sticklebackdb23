package edu.uoregon.sticklebackdb



import org.junit.*
import grails.test.mixin.*

@TestFor(IndividualController)
@Mock(Individual)
class IndividualControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/individual/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.individualInstanceList.size() == 0
        assert model.individualInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.individualInstance != null
    }

    void testSave() {
        controller.save()

        assert model.individualInstance != null
        assert view == '/individual/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/individual/show/1'
        assert controller.flash.message != null
        assert Individual.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/individual/list'

        populateValidParams(params)
        def individual = new Individual(params)

        assert individual.save() != null

        params.id = individual.id

        def model = controller.show()

        assert model.individualInstance == individual
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/individual/list'

        populateValidParams(params)
        def individual = new Individual(params)

        assert individual.save() != null

        params.id = individual.id

        def model = controller.edit()

        assert model.individualInstance == individual
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/individual/list'

        response.reset()

        populateValidParams(params)
        def individual = new Individual(params)

        assert individual.save() != null

        // test invalid parameters in update
        params.id = individual.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/individual/edit"
        assert model.individualInstance != null

        individual.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/individual/show/$individual.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        individual.clearErrors()

        populateValidParams(params)
        params.id = individual.id
        params.version = -1
        controller.update()

        assert view == "/individual/edit"
        assert model.individualInstance != null
        assert model.individualInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/individual/list'

        response.reset()

        populateValidParams(params)
        def individual = new Individual(params)

        assert individual.save() != null
        assert Individual.count() == 1

        params.id = individual.id

        controller.delete()

        assert Individual.count() == 0
        assert Individual.get(individual.id) == null
        assert response.redirectedUrl == '/individual/list'
    }
}
