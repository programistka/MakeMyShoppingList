package com.programistka.makemyshoppinglist.archive;

import android.content.Context;

import com.programistka.makemyshoppinglist.dbhandlers.ArchiveDbHandler;
import com.programistka.makemyshoppinglist.dbhandlers.EmptyItemsDbHandler;
import com.programistka.makemyshoppinglist.dbhandlers.FirebaseDbHandler;
import com.programistka.makemyshoppinglist.presenters.DbConfig;

import java.util.List;

public class ArchiveInteractor {
    private ArchiveDbHandler dbHandler;
    EmptyItemsDbHandler emptyItemsDbHandler;
    FirebaseDbHandler firebaseDbHandler = new FirebaseDbHandler();

    public ArchiveInteractor(DbConfig dbConfig, Context context) {
        dbHandler = new ArchiveDbHandler(dbConfig, context);
        emptyItemsDbHandler = new EmptyItemsDbHandler(dbConfig, context);
    }

    public void markAsArchived(List<String> selectedItems) {
        for (String itemId : selectedItems) {
            firebaseDbHandler.moveItemToArchive(itemId);
        }
    }

    public void undoMarkAsArchived(List<Long> selectedItems) {
        // dbHandler.undoMarkAsArchived(selectedItems);
    }

    public Boolean checkIfArchivedElement(long itemId) {
        return dbHandler.checkIfArchivedElement(itemId);
    }

    public void undoMarkAsEmpty(List<String> selectedItems) {
        for (String itemId : selectedItems) {
            // emptyItemsDbHandler.deleteExistingEmptyItem(itemId);
        }
    }

    public void markAsEmpty(List<String> selectedItems, long time) {
        for (String itemId : selectedItems) {
            firebaseDbHandler.addEmptyExistingItem(itemId, time);
        }
    }
}
