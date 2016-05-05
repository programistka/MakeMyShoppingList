package net.programistka.shoppingadvisor.addemptyitem;

public class AddEmptyItemPresenter {

    private AddEmptyItemInteractor interactor;
    private AddEmptyItemView view;

    public AddEmptyItemPresenter(AddEmptyItemInteractor interactor, AddEmptyItemView view) {
        this.interactor = interactor;
        this.view = view;
    }

    public void insertNewEmptyItem(String name, long time) {
        interactor.insertNewEmptyItem(name, time);
    }

    public void insertExistingEmptyItem(long id, long time) {
        interactor.insertExistingEmptyItem(id, time);
    }

    public void addNewEmptyItem(String name, long time) {
        if(name.length() == 0) {
            view.showEmptyItemNameMessage();
        }
        else {
            insertNewEmptyItem(name, time);
            view.redirectToPredictionsView();
        }
    }
}
