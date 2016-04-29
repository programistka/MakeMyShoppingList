package net.programistka.shoppingadvisor.addemptyitem;

import net.programistka.shoppingadvisor.dbhandlers.EmptyItemsDbHandler;
import net.programistka.shoppingadvisor.models.EmptyItem;

import java.util.List;

public class AddEmptyItemPresenter {

    private EmptyItemsDbHandler dbHandler;

    private AddEmptyItemView view;

    public AddEmptyItemPresenter(AddEmptyItemView view) {
        //this.dbHandler = new EmptyItemsDbHandler(context);
        this.view = view;
    }

    public List<EmptyItem> selectAllItemsFromItemsTable() {
        return this.dbHandler.selectAllItemsFromItemsTable();
    }

    public void insertExistingEmptyItem(long id) {
        this.dbHandler.insertExistingEmptyItem(id);
    }

    public void insertNewEmptyItem(String name) {
        this.dbHandler.insertNewEmptyItem(name);
    }

    public List<EmptyItem> selectAllItemsFromEmptyItemsHistoryTable() {
        return this.dbHandler.selectAllItemsFromEmptyItemsHistoryTable();
    }

    public void addNewEmptyItem(String name) {
        String emptyItemNameText = name;
        if(emptyItemNameText.length() == 0) {
            view.showEmptyItemNameMessage();
        }
        else {
            insertNewEmptyItem(emptyItemNameText);
            view.redirectToEmptyItemsHistoryView();
        }
    }
}
