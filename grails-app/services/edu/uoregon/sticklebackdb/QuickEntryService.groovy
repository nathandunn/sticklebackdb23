package edu.uoregon.sticklebackdb

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class QuickEntryService {

    static expose = ['gwt:edu.uoregon.sticklebackdb.client']

    String doit() {
        println "somethign back from doit "
        return "returning stuff from doit"
    }

    String getMeasuredValuesForExperiment(Integer experimentId) {
        Experiment experiment = Experiment.findById(experimentId)

        List<MeasuredValueDTO> measuredValueDTOList = new ArrayList<MeasuredValueDTO>();

        for (MeasuredValue measuredValue in MeasuredValue.findAllByExperiment(experiment, [order: "asc", sort: "stock.stockID"])) {
            MeasuredValueDTO measuredValueDTO = new MeasuredValueDTO()
            measuredValueDTO.category = measuredValue.category.name
            measuredValueDTO.stock = measuredValue.stock.stockIDLabel
            measuredValueDTO.value = measuredValue.value
            measuredValueDTO.id = measuredValue.id
            measuredValueDTOList.add(measuredValueDTO)
        }

        MeasuredValuesDTO measuredValuesDTO = new MeasuredValuesDTO()
        measuredValuesDTO.categories = Category.listOrderByName().collect { it.name }
        measuredValuesDTO.stocks = Stock.listOrderByStockIDLabel().collect { it.stockID.toString() }
        measuredValuesDTO.experiments = measuredValueDTOList

        measuredValuesDTO.experimentId = experimentId;

//        return experimentDTOList as JSON
        return measuredValuesDTO as JSON
//        return experiment.measuredValues as JSON
    }

    String createMeasuredValue(Integer experimentId, String strainString, String valueString, String categoryString) {
        Experiment experiment = Experiment.get(experimentId)
        Stock stock = Stock.findById(strainString as Integer)
        Category category = Category.findByName(categoryString)
        MeasuredValue measuredValue = new MeasuredValue(
                experiment: experiment
                , stock: stock
                , category: category
                , value: valueString
        ).save(insert: true, flush: true, failOnError: true)
        return measuredValue.id as String
    }

    String removeMeasuredValue(Integer measuredValueId) {
        MeasuredValue measuredValue = MeasuredValue.findById(measuredValueId)
        measuredValue.delete(flush: true)
        return measuredValue.id
    }

    String saveMeasuredValue(Integer measuredValueId, String field, String oldValue, String newValue) {
        println "saving [${measuredValueId}] field ${field} oldValue ${oldValue} newValue ${newValue} "
        MeasuredValue measuredValue = MeasuredValue.findById(measuredValueId)
        println "measuredValue ${measuredValue}"
        if (field == "strain") {
            Stock stock = Stock.findByStockID(newValue as Integer)
            println "stock [${stock}] found for [${newValue}]"
            if (stock == null) {
                return "error:Stock does not exist '${newValue}'"
            }
            measuredValue.stock = stock
        } else if (field == "value") {
            if (newValue == "bad") {
                return "error:badness"
            } else {
                measuredValue.value = newValue
            }
        } else if (field == "category") {
            Category category = Category.findByName(newValue)
            measuredValue.category = category
        } else {
            println "nothing saved "
        }
        measuredValue.save(insert: false, flush: true, failOnError: true)
        println "saved something "
        return measuredValue.id
    }
}
