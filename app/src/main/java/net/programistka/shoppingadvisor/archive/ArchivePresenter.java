package net.programistka.shoppingadvisor.archive;

import java.util.List;

public class ArchivePresenter {

    private ArchiveInteractor interactor;

    public ArchivePresenter(ArchiveInteractor interactor) {
        this.interactor = interactor;
    }
    public void markAsArchived(List<Long> selectedItems) {
        interactor.markAsArchived(selectedItems);
    }

    public void undoMarkAsArchived(List<Long> selectedItems) {
        interactor.undoMarkAsArchived(selectedItems);
    }

    public Boolean checkIfArchivedElement(long itemId) {
        return interactor.checkIfArchivedElement(itemId);
    }

    public void undoMarkAsEmpty(List<Long> selectedItems) {
        interactor.undoMarkAsEmpty(selectedItems);
    }

    public void markAsEmpty(List<Long> selectedItems, long time) {
        interactor.markAsEmpty(selectedItems, time);
    }
}
