package net.programistka.shoppingadvisor;

import android.test.mock.MockContext;

import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemPresenter;

import org.junit.Test;

public class EmptyItemsTests {
    @Test
    public void when_added_empty_item_for_the_first_time_then_visible_in_history_once() {
        AddEmptyItemPresenter presenter = new AddEmptyItemPresenter(new MockContext());
        presenter.insertNewEmptyItem("Kasza");
        presenter.selectAllItemsFromItemsTable();
    }

    @Test
    public void when_added_empty_item_for_the_second_time_then_visible_in_history_twice() {

    }

    @Test
    public void when_added_empty_item_for_the_second_time_and_not_the_same_day_then_visible_in_predictions() {

    }

    @Test
    public void when_added_existing_empty_item_then_prediction_updated() {

    }
}
