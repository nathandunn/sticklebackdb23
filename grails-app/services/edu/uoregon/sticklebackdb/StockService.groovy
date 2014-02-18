package edu.uoregon.sticklebackdb

import grails.transaction.Transactional

@Transactional
class StockService {


    Double getNextStockID() {
        String stock = Stock.executeQuery("select s.stockID from Stock s order by s.stockID desc", [max: 1]).get(0)
        return stock.toDouble() + 1.0
    }

    Double getNextIndividualID(Stock stock) {
        List<Individual> individualList = Individual.executeQuery("select ind from Individual ind where ind.stock = :stock order by ind.individualID desc"
                , [stock: stock]
                , [max: 1])
        if(individualList){
            return individualList.get(0).individualID+1.0d
        }
        else{
            return 1.0
        }
    }
}
