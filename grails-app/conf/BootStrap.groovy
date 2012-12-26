import edu.uoregon.StubData

class BootStrap {

    def init = { servletContext ->

        def stubData = new StubData()
        stubData.stubLine()


    }
    def destroy = {
    }
}
