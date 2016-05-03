package net.programistka.shoppingadvisor;

import android.content.Context;
import android.test.AndroidTestCase;

import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemInteractor;
import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemPresenter;
import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemView;
import net.programistka.shoppingadvisor.dbhandlers.EmptyItemsDbHandler;
import net.programistka.shoppingadvisor.models.EmptyItem;
import net.programistka.shoppingadvisor.presenters.DbConfig;
import net.programistka.shoppingadvisor.selectallItems.SelectAllItemsInteractor;
import net.programistka.shoppingadvisor.selectallItems.SelectAllItemsPresenter;

import java.util.Calendar;
import java.util.List;

import static org.mockito.Mockito.mock;

public class EmptyItemsTests extends AndroidTestCase {

    private EmptyItemsDbHandler dbHandler;

    public void setUp() throws Exception {
        dbHandler = new EmptyItemsDbHandler(new DbConfig("shopping_advisor_test.db"), mContext);
        mContext.deleteDatabase(dbHandler.getDatabaseName());
        mContext.openOrCreateDatabase(dbHandler.getDatabaseName(), Context.MODE_PRIVATE, null);
    }

    public void tearDown() throws Exception {
        mContext.deleteDatabase(dbHandler.getDatabaseName());
    }

    public void test_when_added_empty_item_for_the_first_time_then_visible_in_history_once() {
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        Calendar c = Calendar.getInstance();
        c.set(2016, 3, 21, 0, 0, 0);
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());
        SelectAllItemsPresenter selectAllItemsPresenter = new SelectAllItemsPresenter(new SelectAllItemsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
        List<EmptyItem> emptyItems = selectAllItemsPresenter.selectAllItemsFromItemsTable();
        assertEquals(1, emptyItems.size());
        assertEquals("kasza", emptyItems.get(0).getName());
    }

    public void test_when_added_empty_item_for_the_second_time_then_visible_in_history_twice() {

    }

    public void test_when_added_empty_item_for_the_second_time_and_not_the_same_day_then_visible_in_predictions() {

    }

    public void test_when_added_existing_empty_item_then_prediction_updated() {

    }
}
