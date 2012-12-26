package edu.uoregon.sticklebackdb



import org.junit.*
import grails.test.mixin.*

@TestFor(BeakerController)
@Mock(Beaker)
class BeakerControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/beaker/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.beakerInstanceList.size() == 0
        assert model.beakerInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.beakerInstance != null
    }

    void testSave() {
        controller.save()

        assert model.beakerInstance != null
        assert view == '/beaker/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/beaker/show/1'
        assert controller.flash.message != null
        assert Beaker.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/beaker/list'

        populateValidParams(params)
        def beaker = new Beaker(params)

        assert beaker.save() != null

        params.id = beaker.id

        def model = controller.show()

        assert model.beakerInstance == beaker
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/beaker/list'

        populateValidParams(params)
        def beaker = new Beaker(params)

        assert beaker.save() != null

        params.id = beaker.id

        def model = controller.edit()

        assert model.beakerInstance == beaker
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/beaker/list'

        response.reset()

        populateValidParams(params)
        def beaker = new Beaker(params)

        assert beaker.save() != null

        // test invalid parameters in update
        params.id = beaker.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/beaker/edit"
        assert model.beakerInstance != null

        beaker.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/beaker/show/$beaker.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        beaker.clearErrors()

        populateValidParams(params)
        params.id = beaker.id
        params.version = -1
        controller.update()

        assert view == "/beaker/edit"
        assert model.beakerInstance != null
        assert model.beakerInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/beaker/list'

        response.reset()

        populateValidParams(params)
        def beaker = new Beaker(params)

        assert beaker.save() != null
        assert Beaker.count() == 1

        params.id = beaker.id

        controller.delete()

        assert Beaker.count() == 0
        assert Beaker.get(beaker.id) == null
        assert response.redirectedUrl == '/beaker/list'
    }
}
