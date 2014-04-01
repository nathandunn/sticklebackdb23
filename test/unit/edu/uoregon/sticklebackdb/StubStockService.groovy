package edu.uoregon.sticklebackdb

/**
 * Created by ndunn on 4/1/14.
 */
class StubStockService extends StockService{
    @Override
    Double getNextStockID() {
        return 32d
    }

    @Override
    Double getNextIndividualID(Stock stock) {
        return 33d
    }
}
