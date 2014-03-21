package edu.uoregon.sticklebackdb.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class ExperimentTable extends FlexTable {

    int numberRows = 0;

    private final int STOCK_COLUMN = 0;
    private final int CATEGORY_COLUMN = 1;
    private final int VALUE_COLUMN = 2;
    private final int ACTION_COLUMN = 3;

//    private final String STRAIN_KEY = "strain";
    private final String VALUE_KEY = "value";
    private final String CATEGORY_KEY = "category";
    private final String EXPERIMENTS_KEY = "experiments";
    private final String CATEGORIES_KEY = "categories";
    private final String STOCKS_KEY = "stocks";
    private final String STOCK_KEY = "stock";
    private final String EXPERIMENT_KEY = "experimentId";
    private final String MEASURED_VALUE_KEY = "id";

    private String lastCategory = null;

    private MultiWordSuggestOracle stockOracle = new MultiWordSuggestOracle();

    private Button addButton = new Button("Add");
    //    private ListBox newStrainList = new ListBox();
    private SuggestBox newStockSuggestBox = new SuggestBox(stockOracle);
    private TextBox newValueBox = new TextBox();
    private ListBox newCategoryListBox = new ListBox();
    List<String> categoryList = new ArrayList<String>();

    private Integer experimentId = 0;

    public final static String ROW_HEIGHT = "20px";
//    MultiWordSuggestOracle categoryOracle = new MultiWordSuggestOracle();

    private static QuickEntryServiceAsync quickEntryServiceAsync = GWT.create(QuickEntryService.class);

    public ExperimentTable() {
        setCellPadding(0);
        setCellSpacing(0);
//        addStyleName("quick-entry-table");
        setStylePrimaryName("quick-entry-table");
        setWidth("70%");


    }

    public void udpateTable(JSONValue value) {
        clear();

        createHeaders();

        JSONObject measuredValueDto = value.isObject();

        experimentId = (int) measuredValueDto.get(EXPERIMENT_KEY).isNumber().doubleValue();

        GWT.log(measuredValueDto.toString());

        JSONArray stocks = measuredValueDto.get(STOCKS_KEY).isArray();
        for (int i = 0; i < stocks.size(); i++) {
//            newStrainList.addItem(strains.get(i).isString().stringValue());
            stockOracle.add(stocks.get(i).isString().stringValue());
        }


        JSONArray categories = measuredValueDto.get(CATEGORIES_KEY).isArray();
        for (int i = 0; i < categories.size(); i++) {
            String category = categories.get(i).isString().stringValue();
            newCategoryListBox.addItem(category);
//            categoryOracle.add(category);
            categoryList.add(category);
        }

        JSONArray experiments = measuredValueDto.get(EXPERIMENTS_KEY).isArray();

        GWT.log("number of experiments: " + experiments.size());

        for (int i = 0; i < experiments.size(); i++) {
            JSONObject measuredValue = experiments.get(i).isObject();

            lastCategory = measuredValue.get(CATEGORY_KEY).isString().stringValue();

            GWT.log("measured value: "+measuredValue.toString());
            GWT.log("measured value category: "+measuredValue.get(CATEGORY_KEY));
            GWT.log("measured value category value : "+measuredValue.get(CATEGORY_KEY).isString().stringValue());

            createRow(numberRows
                    , measuredValue.get(STOCK_KEY).isString().stringValue()
                    , lastCategory
                    , measuredValue.get(VALUE_KEY).isString().stringValue()
                    , String.valueOf(measuredValue.get(MEASURED_VALUE_KEY).isNumber().doubleValue())
            );

            ++numberRows;
        }


        createFooters();

        // for all cells set style name
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < getRowCount(); ++row) {
                getCellFormatter().setStyleName(row, col, "flexTable");
            }
        }

    }


//        ListBox strainList = new ListBox();
//        int selectedStrainIndex = 0 ;
//        for(int i = 0 ; i < this.strainList.getItemCount() ; i++){
//            strainList.addItem(this.strainList.getItemText(i));
//            if(this.strainList.getItemText(i).equals(strain)){
//                selectedStrainIndex=i ;
//            }
//        }
//        strainList.setSelectedIndex(selectedStrainIndex);

//        setWidget(numberRows, STRAIN_COLUMN, strainList);


    private void createRow(int numberRows, String stock, String category , String value, String measuredValueId) {
//        TextBox strainBox = new TextBox();
        StockEditBox stockEditBox = new StockEditBox(stockOracle, Double.valueOf(measuredValueId).intValue(), stock);
        setWidget(numberRows, STOCK_COLUMN, stockEditBox);

        ValueEditBox valueEditBox = new ValueEditBox(Double.valueOf(measuredValueId).intValue(), value);
        setWidget(numberRows, VALUE_COLUMN, valueEditBox);

        CategoryListBox categoryListBox = new CategoryListBox(Double.valueOf(measuredValueId).intValue(), category, this.categoryList);
        setWidget(numberRows, CATEGORY_COLUMN, categoryListBox);

        RemoveRowButton removeButton = new RemoveRowButton(numberRows, Double.valueOf(measuredValueId).intValue(), this);
        removeButton.setHeight(ROW_HEIGHT);
        removeButton.setStylePrimaryName("quick-entry-table");

        setWidget(numberRows, ACTION_COLUMN, removeButton);
        getRowFormatter().setStylePrimaryName(numberRows, "quick-entry-table");


    }

    private void addNewRow(String measuredValueId) {
//        GWT.log("row count : " + getRowCount()) ;
        insertRow(getRowCount() - 1);
//        GWT.log("2 row count : " + getRowCount()) ;
        int insertRow = getRowCount() - 2;

//
        createRow(insertRow,
//                newStrainList.getItemText(newStrainList.getSelectedIndex())
                newStockSuggestBox.getText()
                , newCategoryListBox.getItemText(newCategoryListBox.getSelectedIndex())
                , newValueBox.getText()
                , measuredValueId
        );

        for (int col = 0; col < ACTION_COLUMN; col++) {
//            for(int row = 0 ; row < getRowCount() ; ++row){
//                getCellFormatter().setStyleName(row,col,"flexTable");
            getCellFormatter().setStyleName(insertRow, col, "flexTable");
//            }
        }

        ++numberRows;
    }

    private void clearNewEntry() {
//        newStrainList.setSelectedIndex(0);

        newStockSuggestBox.setText("");
        selectLastCategory();
        newValueBox.setText("");
    }

    private void createFooters() {
//        setWidget(numberRows, STRAIN_COLUMN, newStrainList);
        setWidget(numberRows, STOCK_COLUMN, newStockSuggestBox);
        setWidget(numberRows, VALUE_COLUMN, newValueBox);
        setWidget(numberRows, CATEGORY_COLUMN, newCategoryListBox);
        setWidget(numberRows, ACTION_COLUMN, addButton);

//        newStrainList.insertItem("- Choose Strain - ", 0);
//        newStrainList.setSelectedIndex(0);
        newCategoryListBox.insertItem("- Choose Category - ", 0);

        selectLastCategory();
//        categoryList.setSelectedIndex(0);

        newValueBox.addKeyDownHandler(new KeyDownHandler() {
            public void onKeyDown(KeyDownEvent event) {
                if(event.getNativeKeyCode()== KeyCodes.KEY_ENTER){
                    addNewMeasuredValue();
                }
            }
        });

        addButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                addNewMeasuredValue();
            }
        });

        ++numberRows;
    }

    private void addNewMeasuredValue(){
        final String strain = newStockSuggestBox.getText();
        final String value = newValueBox.getText();
        final String category = newCategoryListBox.getItemText(newCategoryListBox.getSelectedIndex());
        lastCategory = category;
        quickEntryServiceAsync.createMeasuredValue(experimentId, strain, value, category, new AsyncCallback() {
            public void onFailure(Throwable caught) {
                Window.alert("failed to create record  " + strain + " " + value + " " + category);
            }

            public void onSuccess(Object result) {
                addNewRow(result.toString());
                clearNewEntry();
            }
        });
    }

    private void selectLastCategory() {
        if (lastCategory == null) {
            newCategoryListBox.setSelectedIndex(0);
        }
        if (lastCategory != null) {
            for (int i = 0; i < newCategoryListBox.getItemCount(); i++) {
                if (lastCategory.equals(newCategoryListBox.getItemText(i))) {
                    newCategoryListBox.setSelectedIndex(i);
                }
            }
        }
    }

    private void createHeaders() {
        setHTML(numberRows, STOCK_COLUMN, "<b>Stock</b>");
        setHTML(numberRows, VALUE_COLUMN, "<b>Value</b>");
        setHTML(numberRows, CATEGORY_COLUMN, "<b>Category</b>");
        setHTML(numberRows, ACTION_COLUMN, "<b>Action</b>");
        ++numberRows;
    }


    public int findRowId(int rowId) {
        for (int i = 1; i < getRowCount() - 1; i++) {
            if (rowId == ((RemoveRowButton) getWidget(i, ACTION_COLUMN)).getRowId()) {
                return i;
            }
        }
        return -1;
    }
}
