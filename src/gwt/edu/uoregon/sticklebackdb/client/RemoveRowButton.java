package edu.uoregon.sticklebackdb.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

/**
 */
public class RemoveRowButton extends Button {

    private int rowId;
    private int measuredValueId;
    private final ExperimentTable parentTable ;

    private static QuickEntryServiceAsync quickEntryServiceAsync = GWT.create(QuickEntryService.class);

    public RemoveRowButton(int row, final int measuredValueId,ExperimentTable parentTable){
        super("Remove") ;
        this.rowId = row ;
        this.parentTable = parentTable ;
        this.measuredValueId = measuredValueId ;

        addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                quickEntryServiceAsync.removeMeasuredValue(measuredValueId,new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        Window.alert("failed to remove: "+caught);
                    }

                    public void onSuccess(String result) {
                        int rowIndex = getParentTable().findRowId(getRowId());
                        if(rowIndex>0){
                            getParentTable().removeRow(rowIndex);
                        }
                    }
                });
            }
        });
    }

    public int getRowId() {
        return rowId;
    }

    public final ExperimentTable getParentTable() {
        return parentTable;
    }
}
