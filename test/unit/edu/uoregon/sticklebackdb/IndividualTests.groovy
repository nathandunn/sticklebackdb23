package edu.uoregon.sticklebackdb

import grails.test.mixin.TestFor
/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Individual)
class IndividualTests {

    void testSomething() {
        Individual individual = new Individual(
           individualID: 12
                ,fishLocation: "ASDF"
                ,fishSex: "male"

        )
        .save(failOnError: true )
        assert individual!=null
    }
}
