package edu.uoregon.sticklebackdb.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TextBox;

/**
 */
public class ValueEditBox extends TextBox {

    private Integer measuredValueId;
    private String value;

    private static QuickEntryServiceAsync quickEntryServiceAsync = GWT.create(QuickEntryService.class);

    public ValueEditBox(Integer measuredValueId, String strain) {

        this.measuredValueId = measuredValueId;
        this.value = strain;

        setText(this.value);
        setHeight(ExperimentTable.ROW_HEIGHT);
        setStyleName("quick-entry-table");
        addValueChangeHandler(new ValueChangeHandler<String>() {
            public void onValueChange(ValueChangeEvent<String> event) {
                setEnabled(false);
                quickEntryServiceAsync.saveMeasuredValue(getMeasuredValueId(), "value", getValue(), getText(), new AsyncCallback() {
                    public void onFailure(Throwable caught) {
                        Window.alert("error saving value: " + caught);
                        getElement().getStyle().setProperty("color", "red");
                        // must be camel-cased
//                        getElement().getStyle().setProperty("backgroundColor", "red");
                        setEnabled(true);
                    }

                    public void onSuccess(Object result) {
                        if (result.toString().startsWith("error:")) {
                            Window.alert("error saving value: " + result.toString().substring("error:".length()));
                            getElement().getStyle().setProperty("color", "red");
                        }
                        else{
//                            getElement().getStyle().setProperty("backgroundColor", "white");
                            getElement().getStyle().setProperty("color", "black");
                        }
                        setEnabled(true);
                    }
                });
            }
        });
    }

    public String getValue() {
        return value;
    }

    public Integer getMeasuredValueId() {
        return measuredValueId;
    }
}
