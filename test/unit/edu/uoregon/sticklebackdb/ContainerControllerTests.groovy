package edu.uoregon.sticklebackdb



import org.junit.*
import grails.test.mixin.*

@TestFor(ContainerController)
@Mock(Container)
class ContainerControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/container/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.containerInstanceList.size() == 0
        assert model.containerInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.containerInstance != null
    }

    void testSave() {
        controller.save()

        assert model.containerInstance != null
        assert view == '/container/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/container/show/1'
        assert controller.flash.message != null
        assert Container.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/container/list'

        populateValidParams(params)
        def container = new Container(params)

        assert container.save() != null

        params.id = container.id

        def model = controller.show()

        assert model.containerInstance == container
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/container/list'

        populateValidParams(params)
        def container = new Container(params)

        assert container.save() != null

        params.id = container.id

        def model = controller.edit()

        assert model.containerInstance == container
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/container/list'

        response.reset()

        populateValidParams(params)
        def container = new Container(params)

        assert container.save() != null

        // test invalid parameters in update
        params.id = container.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/container/edit"
        assert model.containerInstance != null

        container.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/container/show/$container.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        container.clearErrors()

        populateValidParams(params)
        params.id = container.id
        params.version = -1
        controller.update()

        assert view == "/container/edit"
        assert model.containerInstance != null
        assert model.containerInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/container/list'

        response.reset()

        populateValidParams(params)
        def container = new Container(params)

        assert container.save() != null
        assert Container.count() == 1

        params.id = container.id

        controller.delete()

        assert Container.count() == 0
        assert Container.get(container.id) == null
        assert response.redirectedUrl == '/container/list'
    }
}
