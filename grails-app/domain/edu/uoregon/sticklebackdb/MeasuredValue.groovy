package edu.uoregon.sticklebackdb

import edu.uoregon.MeasuredValueTypeEnum

class MeasuredValue implements Serializable{

    static constraints = {
        value nullable: false
        category nullable: false
        stock nullable: false
        experiment nullable: false
    }


    String value

//    Experiment experiment
    Category category
    Stock stock
    Experiment experiment
    String type = MeasuredValueTypeEnum.TEXT

    String checkValid() {
        switch (type){
            case MeasuredValueTypeEnum.INTEGER:
                try {
                    Integer.valueOf(value)
                    return null
                } catch (e) {
                    return "${value} is not a valid integer"
                }
                break
            case MeasuredValueTypeEnum.DECIMAL:
                try {
                    Float.valueOf(value)
                    return null
                } catch (e) {
                    return "${value} is not a valid float "
                }
                break
            default:
                return null
        }
    }
}
