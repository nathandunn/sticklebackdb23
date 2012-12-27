import edu.uoregon.StubData

class BootStrap {

    def init = { servletContext ->

        def stubData = new StubData()
        stubData.stubLine()
        stubData.stubContainer()
        stubData.stubGenetics()
        stubData.stubAquaria()
        stubData.stubStock()

//        stubData.stubBeaker()


    }
    def destroy = {
    }
}
