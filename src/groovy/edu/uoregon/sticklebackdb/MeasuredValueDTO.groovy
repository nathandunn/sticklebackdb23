package edu.uoregon.sticklebackdb

/**
 */
class MeasuredValueDTO implements Serializable{

    String stock
    String value
    String category
    Long id
//    private String experiment ;

    String getStock() {
        return stock
    }

    void setStock(String strain) {
        this.stock = strain
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
