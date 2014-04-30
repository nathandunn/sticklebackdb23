package edu.uoregon.sticklebackdb

/**
 */
class GroupedMeasuredValues {

    String measuredValue
    List<MeasuredValue> measuredValues = new ArrayList<>()
    List<Individual> individuals = new ArrayList<>()

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        GroupedMeasuredValues that = (GroupedMeasuredValues) o

        if (measuredValue != that.measuredValue) return false

        return true
    }

    int hashCode() {
        return measuredValue.hashCode()
    }

    def addMeasuredValue(MeasuredValue measuredValue) {
        measuredValues.add(measuredValue)
        individuals.add(measuredValue.individual)
    }
}
