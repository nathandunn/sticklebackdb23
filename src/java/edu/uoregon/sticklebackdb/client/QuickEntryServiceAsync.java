package edu.uoregon.sticklebackdb.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface QuickEntryServiceAsync {
    void doit(AsyncCallback callback);
    void getMeasuredValuesForExperiment(java.lang.Integer arg0, AsyncCallback callback);
    void createMeasuredValue(java.lang.Integer arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3, AsyncCallback callback);
    void removeMeasuredValue(java.lang.Integer arg0, AsyncCallback callback);
    void saveMeasuredValue(java.lang.Integer arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3, AsyncCallback callback);
}
