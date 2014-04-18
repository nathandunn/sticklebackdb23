package edu.uoregon.sticklebackdb



import grails.test.mixin.*
import spock.lang.*

@TestFor(CaptureController)
@Mock([Capture,Line,Population,Stock])
class CaptureControllerSpec extends Specification {

    String populationName = "RabitSlough"

    def populateValidParams(params) {
        assert params != null
        params["comment"] = 'some type of catprue comment'
        params.captureDate = new Date()


        Line line = new Line(
                name: populationName
        ).save(failOnError: true)

        Population population = new Population(
                identification: populationName
        ).save(failOnError: true)

        params.line = line
        params.population = population

    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.captureInstanceList
            model.captureInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.captureInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def capture = new Capture()
            capture.validate()
            controller.save(capture)

        then:"The create view is rendered again with the correct model"
            model.captureInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            capture = new Capture(params)
            controller.save(capture)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/capture/show/1'
            controller.flash.message != null
            Capture.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def capture = new Capture(params)
            controller.show(capture)

        then:"A model is populated containing the domain instance"
            model.captureInstance == capture
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def capture = new Capture(params)
            controller.edit(capture)

        then:"A model is populated containing the domain instance"
            model.captureInstance == capture
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/capture/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def capture = new Capture()
            capture.validate()
            controller.update(capture)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.captureInstance == capture

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            capture = new Capture(params).save(flush: true)
            controller.update(capture)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/capture/show/$capture.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/capture/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def capture = new Capture(params).save(flush: true)

        then:"It exists"
            Capture.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(capture)

        then:"The instance is deleted"
            Capture.count() == 0
            response.redirectedUrl == '/capture/index'
            flash.message != null
    }
}
