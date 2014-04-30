package edu.uoregon.sticklebackdb

class MeasuredValue implements Serializable{

    static constraints = {
        value nullable: false
        category nullable: false
        individual nullable: false
        experiment nullable: false
    }

    static mapping = {
        note type: "text"
    }

    String value

//    Experiment experiment
    Category category
    Individual individual
    Experiment experiment
    String type = MeasuredValueTypeEnum.TEXT
    String note

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
