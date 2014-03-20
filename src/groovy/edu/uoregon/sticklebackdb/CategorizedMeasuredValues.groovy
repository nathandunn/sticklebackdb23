package edu.uoregon.sticklebackdb

/**
 */
class CategorizedMeasuredValues {

//    Map<Category,GroupedMeasuredValueMap> map = new LinkedHashMap<>()

    List<CategoryView> categoryViewList = new ArrayList<>()


    CategoryView getCategory(groovy.lang.Category category) {
        for(CategoryView categoryView in categoryViewList){
            if(categoryView.category==category){
                return categoryView
            }
        }
        return null
    }

    def addMeasuredValue(MeasuredValue measuredValue) {
        CategoryView categoryView = getCategory(measuredValue.category)
        Map<String, GroupedMeasuredValues> groupedMeasuredValuesMap = categoryView.groupedMeasuredValuesList

        GroupedMeasuredValues groupedMeasuredValues = groupedMeasuredValuesMap.get(measuredValue.value)
        if(!groupedMeasuredValues){
            groupedMeasuredValues = new GroupedMeasuredValues(
                    measuredValue: measuredValue.value
            )
        }
        groupedMeasuredValues.addMeasuredValue(measuredValue)
        groupedMeasuredValuesMap.put(measuredValue.value,groupedMeasuredValues)
    }

    def getCategories() {
        return categoryViewList
    }
}
