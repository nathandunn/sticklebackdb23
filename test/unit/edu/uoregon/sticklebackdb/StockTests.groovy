package edu.uoregon.sticklebackdb

import grails.test.mixin.TestFor
/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Stock)
class StockTests {

    void testSomething() {
        Stock stock = new Stock(
                stockID: 123
                ,stockName: "bob"
        ).save failOnError: true
        assert stock!=null
    }
}
