package edu.uoregon.sticklebackdb.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface QuickEntryServiceAsync {

    void doit(AsyncCallback callback);

    void getMeasuredValuesForExperiment(Integer experimentId, AsyncCallback asyncCallback);

    void createMeasuredValue(Integer experimentId, String strain, String value, String category, AsyncCallback asyncCallback);

    void removeMeasuredValue(Integer measuredValueId, AsyncCallback<String> async);

    void saveMeasuredValue(Integer measuredValueId, String field, String oldValue, String newValue, AsyncCallback<String> async);
}
