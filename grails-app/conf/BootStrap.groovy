import edu.uoregon.StubData

class BootStrap {

    def init = { servletContext ->

        new StubData().stubAll()


    }
    def destroy = {
    }
}
