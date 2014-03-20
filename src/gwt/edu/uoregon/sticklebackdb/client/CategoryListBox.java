package edu.uoregon.sticklebackdb.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;

import java.util.List;

/**
 */
public class CategoryListBox extends ListBox{

    private Integer measuredValueId ;
    private String category;
    private List<String> categoryList ;

    private static QuickEntryServiceAsync quickEntryServiceAsync = GWT.create(QuickEntryService.class);

    public CategoryListBox(Integer measuredValueId, String category,List<String> categoryList) {

        this.measuredValueId = measuredValueId ;
        this.category = category;
        this.categoryList = categoryList ;

        setHeight(ExperimentTable.ROW_HEIGHT);
        setStyleName("quick-entry-table");


        int selectedCategoryIndex = 0 ;
        for(int i = 0 ; i < this.categoryList.size() ; i++){
            addItem(this.categoryList.get(i));
            if(this.categoryList.get(i).equals(category)){
                selectedCategoryIndex=i ;
            }
        }
        setSelectedIndex(selectedCategoryIndex);

        addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent event) {
                setEnabled(false);
                saveNewCategoryValue(getItemText(getSelectedIndex()));
            }
        });

//        strainBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
//            public void onSelection(SelectionEvent<SuggestOracle.Suggestion> event) {
//                Window.alert("selection changed: " + event.getSelectedItem().getDisplayString());
//            }
//        });
//        addValueChangeHandler(new ValueChangeHandler<String>() {
//            public void onValueChange(ValueChangeEvent<String> event) {
//                quickEntryServiceAsync.saveMeasuredValue(getMeasuredValueId(),"category", getCategory(),event.getValue(),new AsyncCallback(){
//                    public void onFailure(Throwable caught) {
//                        Window.alert("error saving string: "+caught);
//                    }
//
//                    public void onSuccess(Object result) {
//                        Window.alert("save successful : "+result);
//                    }
//                });
//                Window.alert("value changed: " + event.getValue());
//            }
//        });
    }

    private void saveNewCategoryValue(String replacementString) {
        quickEntryServiceAsync.saveMeasuredValue(getMeasuredValueId(), "category", getCategory(), replacementString, new AsyncCallback() {
            public void onFailure(Throwable caught) {
                Window.alert("Error saving category: " + caught);
                getElement().getStyle().setProperty("color", "red");
                // must be camel-cased
//                        getElement().getStyle().setProperty("backgroundColor", "red");
                setEnabled(true);
            }

            public void onSuccess(Object result) {
                if (result.toString().startsWith("error:")) {
                    Window.alert("Error saving category: " + result.toString().substring("error:".length()));
                    getElement().getStyle().setProperty("color", "red");
                } else {
//                            getElement().getStyle().setProperty("backgroundColor", "white");
                    getElement().getStyle().setProperty("color", "black");
                }
                setEnabled(true);
            }
        });
    }

    public String getCategory() {
        return category;
    }

    public Integer getMeasuredValueId() {
        return measuredValueId;
    }
}
