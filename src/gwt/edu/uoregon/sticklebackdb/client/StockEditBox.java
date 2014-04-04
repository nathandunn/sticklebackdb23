package edu.uoregon.sticklebackdb.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;

/**
 */
public class StockEditBox extends SuggestBox {

    private Integer measuredValueId ;
    private String stock ;

    private static QuickEntryServiceAsync quickEntryServiceAsync = GWT.create(QuickEntryService.class);

    public StockEditBox(MultiWordSuggestOracle stockOracle, Integer measuredValueId, String stock) {

        super(stockOracle);
        this.measuredValueId = measuredValueId ;
        this.stock = stock ;


        setLimit(10);
        setText(this.stock);
        setHeight(ExperimentTable.ROW_HEIGHT);
        setStyleName("quick-entry-table");
        addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
            public void onSelection(SelectionEvent<SuggestOracle.Suggestion> event) {
                saveNewStockValue(event.getSelectedItem().getReplacementString());
            }

        });

        addValueChangeHandler(new ValueChangeHandler<String>() {
            public void onValueChange(ValueChangeEvent<String> event) {
                setEnabled(false);
                saveNewStockValue(getText());
            }
        });
    }

    private void saveNewStockValue(String replacementString) {
        quickEntryServiceAsync.saveMeasuredValue(getMeasuredValueId(), "individual", getStock(), replacementString, new AsyncCallback() {
            public void onFailure(Throwable caught) {
                Window.alert("Error saving stock: " + caught);
                getElement().getStyle().setProperty("color", "red");
                // must be camel-cased
//                        getElement().getStyle().setProperty("backgroundColor", "red");
                setEnabled(true);
            }

            public void onSuccess(Object result) {
                if (result.toString().startsWith("error:")) {
                    Window.alert("Error saving stock: " + result.toString().substring("error:".length()));
                    getElement().getStyle().setProperty("color", "red");
                } else {
//                            getElement().getStyle().setProperty("backgroundColor", "white");
                    getElement().getStyle().setProperty("color", "black");
                }
                setEnabled(true);
            }
        });
    }

    public String getStock() {
        return stock;
    }

    public Integer getMeasuredValueId() {
        return measuredValueId;
    }
}
