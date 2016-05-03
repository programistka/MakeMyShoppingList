package net.programistka.shoppingadvisor.selectallItems;

import net.programistka.shoppingadvisor.models.EmptyItem;

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
