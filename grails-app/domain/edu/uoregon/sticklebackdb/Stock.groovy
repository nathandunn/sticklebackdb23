package edu.uoregon.sticklebackdb

/**
 * TODO: implement may not have both a capture and a parent stock / individual
 */
class Stock {

    // Initialize variables
//    Stock(){
//        comments = null
//        fertilizationDate = null
//        maternalIndividualID = null
//        maternalStockID = null
//        maternalStock = null
//        paternalIndividualID = null
//        paternalStockID = null
//        paternalStock = null
//    }

    static hasMany = [
            individuals: Individual
    ]

    static mapping = {
        comments type: "text"
    }

    static constraints = {
//        capture nullable: true
//        line nullable: false
        line nullable: true
    }


    // Line name (column X)
    Line line
//    Capture capture

    // TODO remove one of these
    // TODO make stockID an Integer
    // Fish stock (column O)
    Integer stockID
//    String stockIDLabel

    String getStockIDLabel(){
        if(stockID){
            return String.format("%.4f", stockID as Float)
        }
        return ""
    }

    // Stock name (column AM)
    String stockName
    
    // Researcher comments (column AK)
    String comments

    // Fertilization date (column D)
    Date fertilizationDate


    // TODO: REMOVE AFTER DEPLOYMENT (just create new individual)
    // Lineage, maternal Individual (from column Q)
    Individual maternalIndividual
    // Lineage, maternal stock (from column R)
    // we keep the stock in the case we don't know the Individual
    Stock maternalStock

    // TODO: REMOVE AFTER DEPLOYMENT (just create new individual)
    // Lineage, paternal Individual (from column T)
    Individual paternalIndividual
    // Lineage, maternal stock (from column U)
    // we keep the stock in the case we don't know the Individual
    Stock paternalStock


    // TODO: REMOVE AFTER DEPLOYMENT
    // Lineage, maternal Individual ID (column Q)
    String maternalIndividualID

    // TODO: REMOVE AFTER DEPLOYMENT
    // Lineage, maternal stock ID (column R)
    Integer maternalStockID

    // TODO: REMOVE AFTER DEPLOYMENT
    // Lineage, paternal Individual ID (column T)
    String paternalIndividualID

    // TODO: REMOVE AFTER DEPLOYMENT
    // Lineage, paternal stock ID (column U)
    Integer paternalStockID

    @Override
    public String toString() {
        return "Stock{" +
                "line=" + line +
                ", stockID=" + stockID +
                ", stockIDLabel='" + stockIDLabel + '\'' +
                ", stockName='" + stockName + '\'' +
                ", comments='" + comments + '\'' +
                ", fertilizationDate=" + fertilizationDate +
                ", maternalIndividual=" + maternalIndividual +
                ", maternalStock=" + maternalStock +
                ", paternalIndividual=" + paternalIndividual +
                ", paternalStock=" + paternalStock +
                '}';
    }

    public isCapture(){
        int count = 0 ;
        for(capture in line?.captures){
            if(capture.captureDate) ++count
        }

//        return line?.captures?.size()>0
        return count>0
    }

    public isBred(){
        return fertilizationDate!=null
    }
}
