package net.programistka.shoppingadvisor.presenters;

import android.content.Context;

import net.programistka.shoppingadvisor.dbhandlers.ArchiveDbHandler;

import java.util.List;

public class ArchivePresenter {

    private ArchiveDbHandler dbHandler;

    public ArchivePresenter(Context context) {
        this.dbHandler = new ArchiveDbHandler(context);
    }
    public void markAsArchived(List<Long> selectedItems) {
        for (Long itemId:selectedItems) {
            dbHandler.insertItemToArchiveTable(itemId);
        }
    }
}
