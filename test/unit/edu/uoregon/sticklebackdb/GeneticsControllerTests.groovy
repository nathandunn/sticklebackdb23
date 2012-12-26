package edu.uoregon.sticklebackdb



import org.junit.*
import grails.test.mixin.*

@TestFor(GeneticsController)
@Mock(Genetics)
class GeneticsControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/genetics/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.geneticsInstanceList.size() == 0
        assert model.geneticsInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.geneticsInstance != null
    }

    void testSave() {
        controller.save()

        assert model.geneticsInstance != null
        assert view == '/genetics/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/genetics/show/1'
        assert controller.flash.message != null
        assert Genetics.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/genetics/list'

        populateValidParams(params)
        def genetics = new Genetics(params)

        assert genetics.save() != null

        params.id = genetics.id

        def model = controller.show()

        assert model.geneticsInstance == genetics
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/genetics/list'

        populateValidParams(params)
        def genetics = new Genetics(params)

        assert genetics.save() != null

        params.id = genetics.id

        def model = controller.edit()

        assert model.geneticsInstance == genetics
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/genetics/list'

        response.reset()

        populateValidParams(params)
        def genetics = new Genetics(params)

        assert genetics.save() != null

        // test invalid parameters in update
        params.id = genetics.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/genetics/edit"
        assert model.geneticsInstance != null

        genetics.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/genetics/show/$genetics.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        genetics.clearErrors()

        populateValidParams(params)
        params.id = genetics.id
        params.version = -1
        controller.update()

        assert view == "/genetics/edit"
        assert model.geneticsInstance != null
        assert model.geneticsInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/genetics/list'

        response.reset()

        populateValidParams(params)
        def genetics = new Genetics(params)

        assert genetics.save() != null
        assert Genetics.count() == 1

        params.id = genetics.id

        controller.delete()

        assert Genetics.count() == 0
        assert Genetics.get(genetics.id) == null
        assert response.redirectedUrl == '/genetics/list'
    }
}
