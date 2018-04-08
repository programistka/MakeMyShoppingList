package com.programistka.makemyshoppinglist.selectallItems;

import com.programistka.makemyshoppinglist.models.EmptyItem;

import java.util.List;

public class SelectAllItemsPresenter {

    private SelectAllItemsInteractor interactor;

    public SelectAllItemsPresenter(SelectAllItemsInteractor interactor) {
        this.interactor = interactor;
    }

    public List<EmptyItem> selectAllItemsFromEmptyItemsHistoryTable() {
        return interactor.selectAllItemsFromEmptyItemsHistoryTable();
    }

    public List<EmptyItem> selectShoppingHistoryForItemFromItemsHistoryTable(long id) {
        return interactor.selectShoppingHistoryForItemFromItemsHistoryTable(id);
    }

    public List<EmptyItem> selectAllItemsFromItemsTable() {
        return interactor.selectAllItemsFromItemsTable();
    }
}
