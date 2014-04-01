package edu.uoregon.sticklebackdb
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(LineController)
@Mock([Line,Stock])
class LineControllerTests {

    def populateValidParams(params) {
        assert params != null
        params["name"] = 'someValidName'
        params["species"] = 'Stickleback'
        params["comment"] = 'what?'
        params["geneticNote"] = 'xn?'
    }

    void testIndex() {
        controller.index()
        assert "/line/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.lineInstanceList.size() == 0
        assert model.lineInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.lineInstance != null
    }

    void testSave() {
        controller.save()

        assert model.lineInstance != null
        assert view == '/line/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/line/show/1'
        assert controller.flash.message != null
        assert Line.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/line/list'

        populateValidParams(params)
        def line = new Line(params)

        assert line.save() != null

        params.id = line.id

        def model = controller.show()

        assert model.lineInstance == line
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/line/list'

        populateValidParams(params)
        def line = new Line(params)

        assert line.save() != null

        params.id = line.id

        def model = controller.edit()

        assert model.lineInstance == line
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/line/list'

        response.reset()

        populateValidParams(params)
        def line = new Line(params)

        assert line.save() != null

        // test invalid parameters in update
        params.id = line.id
        params.name = null

        controller.update()

        assert view == "/line/edit"
        assert model.lineInstance != null

        line.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/line/show/$line.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        line.clearErrors()

        populateValidParams(params)
        params.id = line.id
        params.version = -1
        controller.update()

        assert view == "/line/edit"
        assert model.lineInstance != null
        assert model.lineInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/line/list'

        response.reset()

        populateValidParams(params)
        def line = new Line(params)

        assert line.save() != null
        assert Line.count() == 1

        params.id = line.id

        controller.delete()

        assert Line.count() == 0
        assert Line.get(line.id) == null
        assert response.redirectedUrl == '/line/list'
    }
}
