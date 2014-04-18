package edu.uoregon.sticklebackdb



import grails.test.mixin.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Population)
class PopulationTests {

    void testSomething() {
        Population population =new Population(
                comment:'asdf'
                ,sourceLatString: "12.2"
                ,sourceLongString: "13.3"
                ,identification: 'asdfa'
        )
        .save(failOnError: true )
        assert population!=null
    }
}
