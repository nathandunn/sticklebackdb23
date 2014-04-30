package edu.uoregon.sticklebackdb

/**
 */
class CategoryView {

    Category category
    LinkedHashMap<String, GroupedMeasuredValues> groupedMeasuredValuesList = new LinkedHashMap<>()

    def addMeasuredValue(MeasuredValue measuredValue) {
        GroupedMeasuredValues groupedMeasuredValues = groupedMeasuredValuesList.get(measuredValue.value)
        if(!groupedMeasuredValues){
            groupedMeasuredValues = new GroupedMeasuredValues(measuredValue: measuredValue.value)
        }
        groupedMeasuredValues.addMeasuredValue(measuredValue)
        groupedMeasuredValuesList.put(measuredValue.value,groupedMeasuredValues)
    }
}
