package net.programistka.shoppingadvisor;

import android.content.Context;
import android.test.AndroidTestCase;

import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemInteractor;
import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemPresenter;
import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemView;
import net.programistka.shoppingadvisor.dbhandlers.EmptyItemsDbHandler;
import net.programistka.shoppingadvisor.models.EmptyItem;
import net.programistka.shoppingadvisor.models.Prediction;
import net.programistka.shoppingadvisor.models.PredictionsHandler;
import net.programistka.shoppingadvisor.predictions.ShowPredictionsInteractor;
import net.programistka.shoppingadvisor.predictions.ShowPredictionsPresenter;
import net.programistka.shoppingadvisor.presenters.DbConfig;
import net.programistka.shoppingadvisor.selectallItems.SelectAllItemsInteractor;
import net.programistka.shoppingadvisor.selectallItems.SelectAllItemsPresenter;

import java.util.ArrayList;
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

    public void testWhenAddedEmptyItemForTheFirstTimeThenVisibleInHistoryOnce() {

        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        Calendar c = CalendarProvider.setCalendar(21, 3, 2016);
        SelectAllItemsPresenter selectAllItemsPresenter = new SelectAllItemsPresenter(new SelectAllItemsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));

        //When
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());

        //Then
        List<EmptyItem> emptyItems = selectAllItemsPresenter.selectAllItemsFromItemsTable();
        assertEquals(1, emptyItems.size());
        assertEquals("kasza", emptyItems.get(0).getName());
    }

    public void testWhenAddedEmptyItemForTheSecondTimeThenVisibleInHistoryTwice() {

        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        Calendar c = CalendarProvider.setCalendar(21, 3, 2016);
        Calendar c2 = CalendarProvider.setCalendar(23, 3, 2016);
        SelectAllItemsPresenter selectAllItemsPresenter = new SelectAllItemsPresenter(new SelectAllItemsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));

        //When
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());
        addEmptyItemPresenter.insertExistingEmptyItem(1, c2.getTimeInMillis());

        //Then
        List<EmptyItem> emptyItems = selectAllItemsPresenter.selectAllItemsFromEmptyItemsHistoryTable();
        assertEquals(2, emptyItems.size());
    }

    public void testWhenAddedEmptyItemForTheSecondTimeAndTheSameDayThenNotVisibleInPredictions() {

        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        Calendar c = CalendarProvider.setCalendar(21, 3, 2016);
        Calendar c2 = CalendarProvider.setCalendar(21, 3, 2016);
        SelectAllItemsPresenter selectAllItemsPresenter = new SelectAllItemsPresenter(new SelectAllItemsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));

        //When
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());
        addEmptyItemPresenter.insertExistingEmptyItem(1, c2.getTimeInMillis());

        //Then
        List<EmptyItem> shoppingHistory = selectAllItemsPresenter.selectShoppingHistoryForItemFromItemsHistoryTable(1);
        List<Long> shoppingTimesHistory = new ArrayList<>();
        for (EmptyItem item:shoppingHistory) {
            shoppingTimesHistory.add(item.getCreationDate());
        }
        Prediction prediction = PredictionsHandler.generatePrediction(shoppingTimesHistory);
        assertNull(prediction);
    }

    public void testWhenAddedEmptyItemForTheSecondTimeAndNotTheSameDayThenVisibleInPredictions() {

        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        Calendar c = CalendarProvider.setCalendar(21, 3, 2016);
        Calendar c2 = CalendarProvider.setCalendar(23, 3, 2016);
        SelectAllItemsPresenter selectAllItemsPresenter = new SelectAllItemsPresenter(new SelectAllItemsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));

        //When
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());
        addEmptyItemPresenter.insertExistingEmptyItem(1, c2.getTimeInMillis());

        //Then
        List<EmptyItem> shoppingHistory = selectAllItemsPresenter.selectShoppingHistoryForItemFromItemsHistoryTable(1);
        List<Long> shoppingTimesHistory = new ArrayList<>();
        for (EmptyItem item:shoppingHistory) {
            shoppingTimesHistory.add(item.getCreationDate());
        }
        Prediction prediction = PredictionsHandler.generatePrediction(shoppingTimesHistory);
        Calendar c3 = Calendar.getInstance();
        c3.setTimeInMillis(prediction.getTime());
        assertEquals(2, prediction.getDaysNumber());
        assertEquals(25, c3.get(Calendar.DAY_OF_MONTH));
        assertEquals(3, c3.get(Calendar.MONTH));
        assertEquals(2016, c3.get(Calendar.YEAR));
    }

    public void testWhenAddedEmptyItemForTheSecondTimeAndNotTheSameDayAndLeapYearThenVisibleInPredictions() {

        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        Calendar c = CalendarProvider.setCalendar(24, 1, 2016);
        Calendar c1 = CalendarProvider.setCalendar(27, 1, 2016);
        SelectAllItemsPresenter selectAllItemsPresenter = new SelectAllItemsPresenter(new SelectAllItemsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));

        //When
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());
        addEmptyItemPresenter.insertExistingEmptyItem(1, c1.getTimeInMillis());

        //Then
        List<EmptyItem> shoppingHistory = selectAllItemsPresenter.selectShoppingHistoryForItemFromItemsHistoryTable(1);
        List<Long> shoppingTimesHistory = new ArrayList<>();
        for (EmptyItem item:shoppingHistory) {
            shoppingTimesHistory.add(item.getCreationDate());
        }
        Prediction prediction = PredictionsHandler.generatePrediction(shoppingTimesHistory);
        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(prediction.getTime());
        assertEquals(3, prediction.getDaysNumber());
        assertEquals(1, c2.get(Calendar.DAY_OF_MONTH));
        assertEquals(2, c2.get(Calendar.MONTH));
        assertEquals(2016, c2.get(Calendar.YEAR));
    }

    public void testWhenAddedEmptyItemForTheSecondTimeAndNotTheSameDayAndNotLeapYearThenVisibleInPredictions() {

        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        Calendar c = CalendarProvider.setCalendar(24, 1, 2015);
        Calendar c1 = CalendarProvider.setCalendar(27, 1, 2015);
        SelectAllItemsPresenter selectAllItemsPresenter = new SelectAllItemsPresenter(new SelectAllItemsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));

        //When
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());
        addEmptyItemPresenter.insertExistingEmptyItem(1, c1.getTimeInMillis());

        //Then
        List<EmptyItem> shoppingHistory = selectAllItemsPresenter.selectShoppingHistoryForItemFromItemsHistoryTable(1);
        List<Long> shoppingTimesHistory = new ArrayList<>();
        for (EmptyItem item:shoppingHistory) {
            shoppingTimesHistory.add(item.getCreationDate());
        }
        Prediction prediction = PredictionsHandler.generatePrediction(shoppingTimesHistory);
        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(prediction.getTime());
        assertEquals(3, prediction.getDaysNumber());
        assertEquals(2, c2.get(Calendar.DAY_OF_MONTH));
        assertEquals(2, c2.get(Calendar.MONTH));
        assertEquals(2015, c2.get(Calendar.YEAR));
    }

    public void testWhenAddedExistingEmptyItemThreeItemsThenPredictionUpdated() {

        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        Calendar c = CalendarProvider.setCalendar(21, 3, 2016);
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());
        Calendar c2 = CalendarProvider.setCalendar(22, 3, 2016);
        addEmptyItemPresenter.insertExistingEmptyItem(1, c2.getTimeInMillis());

        SelectAllItemsPresenter selectAllItemsPresenter = new SelectAllItemsPresenter(new SelectAllItemsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));

        //When
        Calendar c3 = CalendarProvider.setCalendar(24, 3, 2016);
        addEmptyItemPresenter.insertExistingEmptyItem(1, c3.getTimeInMillis());

        //Then
        List<EmptyItem> shoppingHistory = selectAllItemsPresenter.selectShoppingHistoryForItemFromItemsHistoryTable(1);
        List<Long> shoppingTimesHistory = new ArrayList<>();
        for (EmptyItem item:shoppingHistory) {
            shoppingTimesHistory.add(item.getCreationDate());
        }
        Prediction prediction = PredictionsHandler.generatePrediction(shoppingTimesHistory);
        Calendar c4 = Calendar.getInstance();
        c4.setTimeInMillis(prediction.getTime());
        assertEquals(2, prediction.getDaysNumber());
        assertEquals(26, c4.get(Calendar.DAY_OF_MONTH));
        assertEquals(3, c4.get(Calendar.MONTH));
        assertEquals(2016, c4.get(Calendar.YEAR));
    }

    public void testWhenNewEmptyItemWithTheSameNameAsTheExistingOneThenNoNewItemIsCreatedAndNewHistoryEntryIsCreatedInHistoryTableAndPredictionIsUpdated() {

        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        Calendar c = CalendarProvider.setCalendar(21, 3, 2016);
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());
        Calendar c2 = CalendarProvider.setCalendar(22, 3, 2016);
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c2.getTimeInMillis());

        SelectAllItemsPresenter selectAllItemsPresenter = new SelectAllItemsPresenter(new SelectAllItemsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
        ShowPredictionsPresenter showPredictionsPresenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));

        //When
        List<EmptyItem> allItems = selectAllItemsPresenter.selectAllItemsFromItemsTable();
        List<EmptyItem> historyItems = selectAllItemsPresenter.selectShoppingHistoryForItemFromItemsHistoryTable(1);
        Calendar c3 = Calendar.getInstance();
        c3.setTimeInMillis(historyItems.get(historyItems.size()-1).getCreationDate());
        Prediction prediction = showPredictionsPresenter.getPredictionForItem(1);
        Calendar c4 = Calendar.getInstance();
        c4.setTimeInMillis(prediction.getTime());

        //Then
        assertEquals(1, allItems.size());
        assertEquals(2, historyItems.size());
        assertEquals(22, c3.get(Calendar.DAY_OF_MONTH));
        assertEquals(3, c3.get(Calendar.MONTH));
        assertEquals(2016, c3.get(Calendar.YEAR));
        assertEquals(1, prediction.getDaysNumber());
        assertEquals(23, c4.get(Calendar.DAY_OF_MONTH));
        assertEquals(3, c4.get(Calendar.MONTH));
        assertEquals(2016, c4.get(Calendar.YEAR));
    }
}
