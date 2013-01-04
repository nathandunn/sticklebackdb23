import edu.uoregon.StubData
import edu.uoregon.sticklebackdb.Stock

class BootStrap {

    def init = { servletContext ->

        String bootstrapValue = System.properties["bootstrap"]
        log.debug "bootstrapValue: " + bootstrapValue
        if (bootstrapValue) {
            new StubData().stubAll()
        }
        else
        if(Stock.count()==0)
        {
            new StubData().stubAll()
        }


    }
    def destroy = {
    }
}
