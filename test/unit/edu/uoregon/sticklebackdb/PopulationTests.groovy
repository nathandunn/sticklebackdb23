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
                ,sourceLat: 12.2
                ,sourceLong: 13.3
                ,identification: 'asdfa'
        )
        .save(failOnError: true )
        assert population!=null
    }
}
