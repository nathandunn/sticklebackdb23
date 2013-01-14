import edu.uoregon.StubData
import edu.uoregon.sticklebackdb.Individual
import edu.uoregon.sticklebackdb.Stock

class BootStrap {

    def init = { servletContext ->

        String bootstrapValue = System.properties["bootstrap"]
        println "bootstrapValue: " + bootstrapValue
        println "stock count: " + Stock.count()

        if (bootstrapValue) {
            new StubData().stubAll()
        }
        else
        if(Stock.count()==0)
        {
            new StubData().stubAll()
        }
        else{
            println "not stubbing data"
        }

    }
    def destroy = {
    }
}
