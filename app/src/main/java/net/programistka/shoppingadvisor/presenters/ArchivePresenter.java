package net.programistka.shoppingadvisor.presenters;

import android.content.Context;

import net.programistka.shoppingadvisor.dbhandlers.ArchiveDbHandler;

import java.util.List;

public class ArchivePresenter {

    private ArchiveDbHandler dbHandler;
    private DbConfig dbConfig;

    public ArchivePresenter(DbConfig dbConfig, Context context) {
        this.dbConfig = dbConfig;
        this.dbHandler = new ArchiveDbHandler(dbConfig, context);
    }
    public void markAsArchived(List<Long> selectedItems) {
        for (Long itemId:selectedItems) {
            dbHandler.insertItemToArchiveTable(itemId);
        }
    }
}
