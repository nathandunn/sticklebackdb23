package edu.uoregon.sticklebackdb.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc")
public interface QuickEntryService extends RemoteService {
    String doit();

    String getMeasuredValuesForExperiment(Integer experimentId);

    String createMeasuredValue(Integer experimentId, String strain, String value, String category);

    String removeMeasuredValue(Integer measuredValueId);

    String saveMeasuredValue(Integer measuredValueId, String field, String oldValue, String newValue);
}
