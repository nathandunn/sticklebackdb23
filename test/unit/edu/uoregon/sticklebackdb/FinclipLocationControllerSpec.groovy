package edu.uoregon.sticklebackdb



import grails.test.mixin.*
import spock.lang.*

@TestFor(FinclipLocationController)
@Mock(FinclipLocation)
class FinclipLocationControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.finclipLocationInstanceList
            model.finclipLocationInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.finclipLocationInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def finclipLocation = new FinclipLocation()
            finclipLocation.validate()
            controller.save(finclipLocation)

        then:"The create view is rendered again with the correct model"
            model.finclipLocationInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            finclipLocation = new FinclipLocation(params)

            controller.save(finclipLocation)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/finclipLocation/show/1'
            controller.flash.message != null
            FinclipLocation.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def finclipLocation = new FinclipLocation(params)
            controller.show(finclipLocation)

        then:"A model is populated containing the domain instance"
            model.finclipLocationInstance == finclipLocation
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def finclipLocation = new FinclipLocation(params)
            controller.edit(finclipLocation)

        then:"A model is populated containing the domain instance"
            model.finclipLocationInstance == finclipLocation
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/finclipLocation/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def finclipLocation = new FinclipLocation()
            finclipLocation.validate()
            controller.update(finclipLocation)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.finclipLocationInstance == finclipLocation

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            finclipLocation = new FinclipLocation(params).save(flush: true)
            controller.update(finclipLocation)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/finclipLocation/show/$finclipLocation.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/finclipLocation/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def finclipLocation = new FinclipLocation(params).save(flush: true)

        then:"It exists"
            FinclipLocation.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(finclipLocation)

        then:"The instance is deleted"
            FinclipLocation.count() == 0
            response.redirectedUrl == '/finclipLocation/index'
            flash.message != null
    }
}
