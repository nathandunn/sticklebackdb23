package edu.uoregon.sticklebackdb

import grails.transaction.Transactional

@Transactional
class StockService {


    Double getNextStockID() {
        String stock = Stock.executeQuery("select s.stockID from Stock s order by s.stockID desc", [max: 1]).get(0)
        return stock.toDouble() + 1.0
    }

    Double getNextIndividualID(Stock stock) {
        println "evaluating stcok ${stock}"
        Individual lastIndividual = Individual.executeQuery("select ind from Individual ind where ind.stock = :stock order by ind.individualID desc"
                ,[stock:stock]
                ,[max:1])?.get(0)
        println "last Individual ${lastIndividual}"
        if(stock.individuals){
            List<Individual> individualList = stock.individuals.sort() { a, b ->
                a.individualID <=> b.individualID
            }
            println individualList.first().individualID
            println individualList.last().individualID
            return individualList.last().individualID.toDouble() + 1.0
        }
        else{
            return 1.0
        }
    }
}
