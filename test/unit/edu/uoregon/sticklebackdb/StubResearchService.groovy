package edu.uoregon.sticklebackdb

/**
 * Created by ndunn on 4/1/14.
 */
class StubResearchService extends ResearcherService{
    @Override
    boolean isAdmin() {
        return true
    }

    @Override
    Researcher getCurrentUser() {
        return null
    }
}
