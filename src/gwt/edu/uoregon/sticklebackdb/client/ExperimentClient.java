package edu.uoregon.sticklebackdb.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ExperimentClient implements EntryPoint {


    private static QuickEntryServiceAsync quickEntryServiceAsync = GWT.create(QuickEntryService.class);

    private Label label = new Label("no call made yet");
    private Label experimentLabel = new Label("call made yet");
    private VerticalPanel verticalPanel = new VerticalPanel();
    private Label messagePanel = new Label();
    private ExperimentTable experimentTable = new ExperimentTable();

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        Dictionary properties = Dictionary.getDictionary("properties");
        Integer experimentId = Integer.parseInt(properties.get("experimentId"));
//        Window.alert("experiment Id: "+experimentId);


//        debug() ;

        verticalPanel.add(messagePanel);
        verticalPanel.add(experimentTable);

        RootPanel.get("edit-table").add(verticalPanel);

        messagePanel.setText("Loading Measured Values . . . ");
        quickEntryServiceAsync.getMeasuredValuesForExperiment(experimentId, new AsyncCallback() {
            public void onFailure(Throwable caught) {
                experimentLabel.setText("failed - " + caught.getMessage());
            }

            public void onSuccess(Object strainResults) {
                experimentLabel.setText("succeed - " + strainResults.toString().length());
//                experimentLabel.setText("succeed - " + strainResults.toString());
                messagePanel.setText("Updating Table");
                JSONValue value = JSONParser.parseStrict((String) strainResults);
                experimentTable.udpateTable(value);
                GWT.log(value.toString());
                messagePanel.setText("");
            }
        });


//        RootPanel.get().add(button);
//        button.addClickHandler(new ClickHandler() {
//            public void onClick(ClickEvent event) {
////                myService.doit(new MyAsyncCallback(label));
////                Window.alert("clicked");
//                myService.doit(new AsyncCallback() {
//                    public void onFailure(Throwable caught) {
//                        label.setText("failed - "+caught.getMessage());
//                    }
//
//                    public void onSuccess(Object result) {
//                        label.setText("succeed - "+result);
//                    }
//                });
//            }
//        } );


    }

//    private void debug() {
////        START DEBUG
//        RootPanel.get().add(label);
//        RootPanel.get().add(experimentLabel);
//
//
//        quickEntryServiceAsync.doit(new AsyncCallback() {
//            public void onFailure(Throwable caught) {
//                label.setText("failed - " + caught.getMessage());
//            }
//
//            public void onSuccess(Object result) {
//                label.setText("succeed - " + result);
//            }
//        });
////        END DEBUG
//    }
}
