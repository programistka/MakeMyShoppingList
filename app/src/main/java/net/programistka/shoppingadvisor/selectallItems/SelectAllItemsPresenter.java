package net.programistka.shoppingadvisor.selectallItems;

import android.content.Context;

import net.programistka.shoppingadvisor.models.EmptyItem;
import net.programistka.shoppingadvisor.presenters.DbConfig;

import java.util.List;

public class SelectAllItemsPresenter {

    private SelectAllItemsInteractor interactor;

    public SelectAllItemsPresenter(DbConfig dbConfig, Context context) {
        interactor = new SelectAllItemsInteractor(dbConfig, context);
    }

    public List<EmptyItem> selectAllItemsFromEmptyItemsHistoryTable() {
        return interactor.selectAllItemsFromEmptyItemsHistoryTable();
    }

    public List<EmptyItem> selectAllItemsFromItemsTable() {
        return interactor.selectAllItemsFromItemsTable();
    }
}
