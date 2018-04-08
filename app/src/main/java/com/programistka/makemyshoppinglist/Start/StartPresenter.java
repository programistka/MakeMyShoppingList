package com.programistka.makemyshoppinglist.start;

public class StartPresenter {
    private StartInteractor interactor;

    public StartPresenter(StartInteractor interactor) {
        this.interactor = interactor;
    }

    public Boolean ifAnyItemsExists() {
        return interactor.ifAnyItemsExists();
    }
}
