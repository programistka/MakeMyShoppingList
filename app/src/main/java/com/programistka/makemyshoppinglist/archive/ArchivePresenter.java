package com.programistka.makemyshoppinglist.archive;

import java.util.List;

public class ArchivePresenter {

    private ArchiveInteractor interactor;

    public ArchivePresenter(ArchiveInteractor interactor) {
        this.interactor = interactor;
    }

    public void markAsArchived(List<String> selectedItems) {
        interactor.markAsArchived(selectedItems);
    }

    public void undoMarkAsArchived(List<String> selectedItems) {
        // interactor.undoMarkAsArchived(selectedItems);
    }

    public Boolean checkIfArchivedElement(long itemId) {
        return interactor.checkIfArchivedElement(itemId);
    }

    public void undoMarkAsEmpty(List<String> selectedItems) {
        interactor.undoMarkAsEmpty(selectedItems);
    }

    public void markAsEmpty(List<String> selectedItems, long time) {
        interactor.markAsEmpty(selectedItems, time);
    }
}
