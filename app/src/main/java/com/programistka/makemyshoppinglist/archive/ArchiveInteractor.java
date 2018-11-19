package com.programistka.makemyshoppinglist.archive;

import android.content.Context;

import com.programistka.makemyshoppinglist.dbhandlers.ArchiveDbHandler;
import com.programistka.makemyshoppinglist.dbhandlers.EmptyItemsDbHandler;
import com.programistka.makemyshoppinglist.presenters.DbConfig;

import java.util.List;

public class ArchiveInteractor {
    private ArchiveDbHandler dbHandler;
    EmptyItemsDbHandler emptyItemsDbHandler;

    public ArchiveInteractor(DbConfig dbConfig, Context context) {
        dbHandler = new ArchiveDbHandler(dbConfig, context);
        emptyItemsDbHandler = new EmptyItemsDbHandler(dbConfig, context);
    }

    public void markAsArchived(List<Long> selectedItems) {
        for (Long itemId : selectedItems) {
            dbHandler.insertItemToArchiveTable(itemId);
        }
    }

    public void undoMarkAsArchived(List<Long> selectedItems) {
        dbHandler.undoMarkAsArchived(selectedItems);
    }

    public Boolean checkIfArchivedElement(long itemId) {
        return dbHandler.checkIfArchivedElement(itemId);
    }

    public void undoMarkAsEmpty(List<Long> selectedItems) {
        for (Long itemId : selectedItems) {
            emptyItemsDbHandler.deleteExistingEmptyItem(itemId);
        }
    }

    public void markAsEmpty(List<Long> selectedItems, long time) {
        for (Long itemId : selectedItems) {
            emptyItemsDbHandler.insertExistingEmptyItem(itemId, time);
        }
    }
}
