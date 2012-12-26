package edu.uoregon.sticklebackdb



import org.junit.*
import grails.test.mixin.*

@TestFor(AquariaController)
@Mock(Aquaria)
class AquariaControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/aquaria/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.aquariaInstanceList.size() == 0
        assert model.aquariaInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.aquariaInstance != null
    }

    void testSave() {
        controller.save()

        assert model.aquariaInstance != null
        assert view == '/aquaria/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/aquaria/show/1'
        assert controller.flash.message != null
        assert Aquaria.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/aquaria/list'

        populateValidParams(params)
        def aquaria = new Aquaria(params)

        assert aquaria.save() != null

        params.id = aquaria.id

        def model = controller.show()

        assert model.aquariaInstance == aquaria
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/aquaria/list'

        populateValidParams(params)
        def aquaria = new Aquaria(params)

        assert aquaria.save() != null

        params.id = aquaria.id

        def model = controller.edit()

        assert model.aquariaInstance == aquaria
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/aquaria/list'

        response.reset()

        populateValidParams(params)
        def aquaria = new Aquaria(params)

        assert aquaria.save() != null

        // test invalid parameters in update
        params.id = aquaria.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/aquaria/edit"
        assert model.aquariaInstance != null

        aquaria.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/aquaria/show/$aquaria.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        aquaria.clearErrors()

        populateValidParams(params)
        params.id = aquaria.id
        params.version = -1
        controller.update()

        assert view == "/aquaria/edit"
        assert model.aquariaInstance != null
        assert model.aquariaInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/aquaria/list'

        response.reset()

        populateValidParams(params)
        def aquaria = new Aquaria(params)

        assert aquaria.save() != null
        assert Aquaria.count() == 1

        params.id = aquaria.id

        controller.delete()

        assert Aquaria.count() == 0
        assert Aquaria.get(aquaria.id) == null
        assert response.redirectedUrl == '/aquaria/list'
    }
}
