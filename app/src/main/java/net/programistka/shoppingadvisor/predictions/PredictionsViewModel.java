package net.programistka.shoppingadvisor.predictions;

import net.programistka.shoppingadvisor.models.EmptyItem;

import java.util.List;

public class PredictionsViewModel {
    private List<EmptyItem> thisWeek;
    private List<EmptyItem> thisMonth;

    public List<EmptyItem> getThisWeek() {
        return thisWeek;
    }

    public void setThisWeek(List<EmptyItem> thisWeek) {
        this.thisWeek = thisWeek;
    }

    public List<EmptyItem> getThisMonth() {
        return thisMonth;
    }

    public void setThisMonth(List<EmptyItem> thisMonth) {
        this.thisMonth = thisMonth;
    }
}
