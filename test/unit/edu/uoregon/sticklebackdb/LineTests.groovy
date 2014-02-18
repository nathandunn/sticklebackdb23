package edu.uoregon.sticklebackdb

import grails.test.mixin.TestFor
/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Line)
class LineTests {

    void testSomething() {
        Line line = new Line(
                name: "asdf"
                ,species: "Stickleback"
                ,comment: "asdf"
                ,geneticNote: "asdf"
        )
        .save(failOnError: true)
        assert line!=null
    }
}
