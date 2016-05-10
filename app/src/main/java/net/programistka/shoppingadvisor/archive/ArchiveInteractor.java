package net.programistka.shoppingadvisor.archive;

import android.content.Context;

import net.programistka.shoppingadvisor.CalendarProvider;
import net.programistka.shoppingadvisor.dbhandlers.ArchiveDbHandler;
import net.programistka.shoppingadvisor.dbhandlers.EmptyItemsDbHandler;
import net.programistka.shoppingadvisor.presenters.DbConfig;

import java.util.Calendar;
import java.util.List;

public class ArchiveInteractor {
    private ArchiveDbHandler dbHandler;
    EmptyItemsDbHandler emptyItemsDbHandler;

    public ArchiveInteractor(DbConfig dbConfig, Context context) {
        dbHandler = new ArchiveDbHandler(dbConfig, context);
        emptyItemsDbHandler = new EmptyItemsDbHandler(dbConfig, context);
    }

    public void markAsArchived(List<Long> selectedItems) {
        for (Long itemId:selectedItems) {
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
        for (Long itemId:selectedItems) {
            emptyItemsDbHandler.deleteExistingEmptyItem(itemId);
        }
    }

    public void markAsEmpty(List<Long> selectedItems) {
        for (Long itemId:selectedItems) {
            Calendar c = CalendarProvider.setNowCalendar();
            emptyItemsDbHandler.insertExistingEmptyItem(itemId, c.getTimeInMillis());
        }
    }
}
