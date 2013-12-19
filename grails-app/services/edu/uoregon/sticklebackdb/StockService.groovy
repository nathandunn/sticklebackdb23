package edu.uoregon.sticklebackdb

import edu.uoregon.sticklebackdb.Stock
import grails.transaction.Transactional

@Transactional
class StockService {


    Double getNextStockID(){
        String stock = Stock.executeQuery("select s.stockID from Stock s order by s.stockID desc",[max:1]).get(0)
        return stock.toDouble() + 1.0
    }
}
