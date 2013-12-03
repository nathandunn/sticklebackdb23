package sticklebackdb

import edu.uoregon.sticklebackdb.Stock
import grails.transaction.Transactional

@Transactional
class StockService {

    def serviceMethod() {

    }

    String createLabelForId(Double stockId){
        return String.format("%.4f", stockId)
    }

    def setStockLabel(Stock stock){
        stock.stockIDLabel = createLabelForId(stock.stockID)
        return stock
    }

    Double getNextStockID(){
        String stock = Stock.executeQuery("select s.stockID from Stock s order by s.stockID desc",[max:1]).get(0)
//        double max = 0
//        stocks.each(){
//            double tmp = it.stockID.toDouble()
//
//            if(tmp > max)
//                max = tmp
//        }
        return stock.toDouble() + 1.0
    }
}
