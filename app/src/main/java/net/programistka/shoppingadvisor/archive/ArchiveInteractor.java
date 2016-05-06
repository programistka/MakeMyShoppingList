package net.programistka.shoppingadvisor.archive;

import android.content.Context;

import net.programistka.shoppingadvisor.dbhandlers.ArchiveDbHandler;
import net.programistka.shoppingadvisor.presenters.DbConfig;

import java.util.List;

public class ArchiveInteractor {
    private ArchiveDbHandler dbHandler;

    public ArchiveInteractor(DbConfig dbConfig, Context context) {
        dbHandler = new ArchiveDbHandler(dbConfig, context);
    }

    public void markAsArchived(List<Long> selectedItems) {
        for (Long itemId:selectedItems) {
            dbHandler.insertItemToArchiveTable(itemId);
        }
    }

    public void undoMarkAsArchived(List<Long> selectedItems) {
        dbHandler.undoMarkAsArchived(selectedItems);
    }
}
