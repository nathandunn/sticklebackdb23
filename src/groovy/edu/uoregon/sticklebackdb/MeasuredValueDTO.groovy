package edu.uoregon.sticklebackdb

/**
 */
class MeasuredValueDTO implements Serializable{

    String individual
    String value
    String category
    Long id
//    private String experiment ;

    String getIndividual() {
        return individual
    }

    void setIndividual(String strain) {
        this.individual = strain
    }

    String getValue() {
        return value
    }

    void setValue(String value) {
        this.value = value
    }

    String getCategory() {
        return category
    }

    void setCategory(String category) {
        this.category = category
    }
}
