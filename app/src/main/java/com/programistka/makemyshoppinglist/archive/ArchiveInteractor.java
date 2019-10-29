package com.programistka.makemyshoppinglist.archive;

import com.programistka.makemyshoppinglist.dbhandlers.DatabaseConfigApp;
import com.programistka.makemyshoppinglist.dbhandlers.FirebaseDbHandler;

import java.util.List;

public class ArchiveInteractor {
    FirebaseDbHandler firebaseDbHandler;

    public ArchiveInteractor(DatabaseConfigApp databaseConfig) {
        firebaseDbHandler = new FirebaseDbHandler(databaseConfig);
    }

    public void markAsArchived(List<String> selectedItems) {
        for (String itemId : selectedItems) {
            firebaseDbHandler.markAsArchived(itemId);
        }
    }

    public void undoMarkAsArchived(List<Long> selectedItems) {
        // dbHandler.undoMarkAsArchived(selectedItems);
    }

    public Boolean checkIfArchivedElement(long itemId) {
        //return dbHandler.checkIfArchivedElement(itemId);
        return false;
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
