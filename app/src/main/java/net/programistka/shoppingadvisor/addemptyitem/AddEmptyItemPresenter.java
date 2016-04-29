package net.programistka.shoppingadvisor.addemptyitem;

import net.programistka.shoppingadvisor.models.EmptyItem;

import java.util.List;

import javax.inject.Inject;

public class AddEmptyItemPresenter {

    @Inject AddEmptyItemInteractor interactor;

    private AddEmptyItemView view;

    public AddEmptyItemPresenter(AddEmptyItemView view) {
        this.view = view;
    }

    public List<EmptyItem> selectAllItemsFromItemsTable() {
        return interactor.selectAllItemsFromItemsTable();
    }

    public void insertNewEmptyItem(String name) {
        interactor.insertNewEmptyItem(name);
    }

    public void insertExistingEmptyItem(long id) {
        interactor.insertExistingEmptyItem(id);
    }

    public List<EmptyItem> selectAllItemsFromEmptyItemsHistoryTable() {
        return interactor.selectAllItemsFromEmptyItemsHistoryTable();
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
