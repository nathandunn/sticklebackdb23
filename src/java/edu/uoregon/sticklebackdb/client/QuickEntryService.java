package edu.uoregon.sticklebackdb.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc")
public interface QuickEntryService extends RemoteService {
    java.lang.String doit();
    java.lang.String getMeasuredValuesForExperiment(java.lang.Integer arg0);
    java.lang.String createMeasuredValue(java.lang.Integer arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3);
    java.lang.String removeMeasuredValue(java.lang.Integer arg0);
    java.lang.String saveMeasuredValue(java.lang.Integer arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3);
}
