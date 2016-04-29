package net.programistka.shoppingadvisor;

import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemPresenter;
import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemView;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EmptyItemsTests {

    @Mock
    private AddEmptyItemView view;

    private AddEmptyItemPresenter presenter;

    @Test
    public void when_added_empty_item_for_the_first_time_then_visible_in_history_once() {
        MockitoAnnotations.initMocks(this);
        presenter = new AddEmptyItemPresenter(view);
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
