package com.programistka.makemyshoppinglist;

import android.content.Context;
import android.test.AndroidTestCase;

import com.programistka.makemyshoppinglist.addemptyitem.AddEmptyItemInteractor;
import com.programistka.makemyshoppinglist.addemptyitem.AddEmptyItemPresenter;
import com.programistka.makemyshoppinglist.addemptyitem.AddEmptyItemView;
import com.programistka.makemyshoppinglist.archive.ArchiveInteractor;
import com.programistka.makemyshoppinglist.archive.ArchivePresenter;
import com.programistka.makemyshoppinglist.dbhandlers.EmptyItemsDbHandler;
import com.programistka.makemyshoppinglist.models.EmptyItem;
import com.programistka.makemyshoppinglist.models.Prediction;
import com.programistka.makemyshoppinglist.models.PredictionsHandler;
import com.programistka.makemyshoppinglist.predictions.ShowPredictionsInteractor;
import com.programistka.makemyshoppinglist.predictions.ShowPredictionsPresenter;
import com.programistka.makemyshoppinglist.presenters.DbConfig;
import com.programistka.makemyshoppinglist.selectallItems.SelectAllItemsInteractor;
import com.programistka.makemyshoppinglist.selectallItems.SelectAllItemsPresenter;

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
        for (EmptyItem item : shoppingHistory) {
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
        for (EmptyItem item : shoppingHistory) {
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
        for (EmptyItem item : shoppingHistory) {
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
        for (EmptyItem item : shoppingHistory) {
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
        for (EmptyItem item : shoppingHistory) {
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
        c3.setTimeInMillis(historyItems.get(historyItems.size() - 1).getCreationDate());
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

    public void testWhenMarkAsBoughtAndPredictionExpiredThenPredictionIsMadeRelatedToCurrentDate() {
        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        ShowPredictionsPresenter showPredictionsPresenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
        Calendar c = CalendarProvider.setCalendar(21, 3, 2016);
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());
        Calendar c2 = CalendarProvider.setCalendar(23, 3, 2016);
        addEmptyItemPresenter.insertExistingEmptyItem(1, c2.getTimeInMillis());
        List<Long> selectedItems = new ArrayList<>();
        selectedItems.add((long) 1);

        //When
        showPredictionsPresenter.markAsBought(selectedItems);

        //Then
        Prediction prediction = showPredictionsPresenter.getPredictionForItem(1);
        Calendar now = CalendarProvider.setNowCalendar();
        assertEquals(now.getTimeInMillis() + 2 * 1000 * 24 * 3600, prediction.getTime());
    }

//    public void testWhenMarkAsBoughtAndPredictionNotExpiredThenNewPredictionInPredictionsTable() {
//        //Given
//        AddEmptyItemView view = mock(AddEmptyItemView.class);
//        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
//        ShowPredictionsPresenter showPredictionsPresenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
//        Calendar c = CalendarProvider.setCalendar(15, 4, 2016);
//        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());
//        Calendar c2 = CalendarProvider.setCalendar(20, 4, 2016);
//        addEmptyItemPresenter.insertExistingEmptyItem(1, c2.getTimeInMillis());
//        List<Long> selectedItems = new ArrayList<>();
//        selectedItems.add((long)1);
//
//        //When
//        showPredictionsPresenter.markAsBought(selectedItems);
//
//        //Then
//        Prediction prediction = showPredictionsPresenter.getPredictionForItem(1);
//        Calendar now = CalendarProvider.setNowCalendar();
//        Calendar c3 = Calendar.getInstance();
//        c3.setTimeInMillis(prediction.getTime());
//        assertEquals(30, c3.get(Calendar.DAY_OF_MONTH));
//        assertEquals(4, c3.get(Calendar.MONTH));
//        assertEquals(2016, c3.get(Calendar.YEAR));
//    }

    public void testWhenMarkAsBoughtAndThenUndoThenThePreviousPredictionIsRestored() {
        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        ShowPredictionsPresenter showPredictionsPresenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
        Calendar c = CalendarProvider.setCalendar(15, 4, 2016);
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());
        Calendar c2 = CalendarProvider.setCalendar(20, 4, 2016);
        addEmptyItemPresenter.insertExistingEmptyItem(1, c2.getTimeInMillis());
        List<Long> selectedItems = new ArrayList<>();
        selectedItems.add((long) 1);
        Prediction predictionBeforeMarkAsArchived = showPredictionsPresenter.getPredictionForItem(1);

        //When
        showPredictionsPresenter.markAsBought(selectedItems);
        showPredictionsPresenter.undoMarkAsBought(selectedItems);

        //Then
        Prediction predictionAfterMarkAsArchivedAndUndo = showPredictionsPresenter.getPredictionForItem(1);
        assertEquals(predictionBeforeMarkAsArchived.getDaysNumber(), predictionAfterMarkAsArchivedAndUndo.getDaysNumber());
        assertEquals(predictionBeforeMarkAsArchived.getTime(), predictionAfterMarkAsArchivedAndUndo.getTime());

    }

    public void testWhenMarkAsArchivedThenTheItemIsAddedToArchiveTable() {
        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        ArchivePresenter archivePresenter = new ArchivePresenter(new ArchiveInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
        Calendar c = CalendarProvider.setCalendar(15, 4, 2016);
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());
        Calendar c2 = CalendarProvider.setCalendar(20, 4, 2016);
        addEmptyItemPresenter.insertExistingEmptyItem(1, c2.getTimeInMillis());
        List<Long> selectedItems = new ArrayList<>();
        selectedItems.add((long) 1);

        //When
        archivePresenter.markAsArchived(selectedItems);

        //Then
        assertTrue(archivePresenter.checkIfArchivedElement(1));
    }

    public void testWhenMarkAsArchivedAndUndoThenTheItemIsRemovedFromArchiveTable() {
        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        ArchivePresenter archivePresenter = new ArchivePresenter(new ArchiveInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
        Calendar c = CalendarProvider.setCalendar(15, 4, 2016);
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());
        Calendar c2 = CalendarProvider.setCalendar(20, 4, 2016);
        addEmptyItemPresenter.insertExistingEmptyItem(1, c2.getTimeInMillis());
        List<Long> selectedItems = new ArrayList<>();
        selectedItems.add((long) 1);

        //When
        archivePresenter.markAsArchived(selectedItems);
        archivePresenter.undoMarkAsArchived(selectedItems);

        //Then
        assertFalse(archivePresenter.checkIfArchivedElement(1));
    }

    public void testWhenMarkAsEmptyThenCurrentDateAddedToTheHistoryTable() {
        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        ArchivePresenter archivePresenter = new ArchivePresenter(new ArchiveInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
        SelectAllItemsPresenter selectAllItemsPresenter = new SelectAllItemsPresenter(new SelectAllItemsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
        Calendar c = CalendarProvider.setCalendar(1, 4, 2016);
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());
        Calendar c2 = CalendarProvider.setCalendar(8, 4, 2016);
        addEmptyItemPresenter.insertExistingEmptyItem(1, c2.getTimeInMillis());
        List<Long> selectedItems = new ArrayList<>();
        selectedItems.add((long) 1);

        //When
        archivePresenter.markAsEmpty(selectedItems, CalendarProvider.setNowCalendar().getTimeInMillis());
        List<EmptyItem> items;
        items = selectAllItemsPresenter.selectAllItemsFromEmptyItemsHistoryTable();
        assertEquals(3, items.size());

        Calendar c3 = CalendarProvider.setNowCalendar();
        EmptyItem lastItem = items.get(2);
        assertEquals(lastItem.getCreationDate(), c3.getTimeInMillis());
    }

    public void testWhenMarkAsEmptyThenNewPredictionMade() {
        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        ArchivePresenter archivePresenter = new ArchivePresenter(new ArchiveInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
        ShowPredictionsPresenter showPredictionsPresenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
        Calendar c = CalendarProvider.setCalendar(1, 4, 2016);
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());
        Calendar c2 = CalendarProvider.setCalendar(8, 4, 2016);
        addEmptyItemPresenter.insertExistingEmptyItem(1, c2.getTimeInMillis());
        List<Long> selectedItems = new ArrayList<>();
        selectedItems.add((long) 1);

        //When
        Calendar c3 = CalendarProvider.setCalendar(11, 4, 2016);
        archivePresenter.markAsEmpty(selectedItems, c3.getTimeInMillis());

        //Then
        Prediction prediction = showPredictionsPresenter.getPredictionForItem(1);
        Calendar c4 = Calendar.getInstance();
        c4.setTimeInMillis(prediction.getTime());
        assertEquals(5, prediction.getDaysNumber());
        assertEquals(16, c4.get(Calendar.DAY_OF_MONTH));
        assertEquals(4, c4.get(Calendar.MONTH));
        assertEquals(2016, c4.get(Calendar.YEAR));
    }

    public void testWhenMarkAsEmptyAndUndoThenHistoryStateIsRecovered() {
        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        ArchivePresenter archivePresenter = new ArchivePresenter(new ArchiveInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
        SelectAllItemsPresenter selectAllItemsPresenter = new SelectAllItemsPresenter(new SelectAllItemsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
        Calendar c = CalendarProvider.setCalendar(1, 4, 2016);
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());
        Calendar c2 = CalendarProvider.setCalendar(8, 4, 2016);
        addEmptyItemPresenter.insertExistingEmptyItem(1, c2.getTimeInMillis());
        List<Long> selectedItems = new ArrayList<>();
        selectedItems.add((long) 1);

        //When
        archivePresenter.markAsEmpty(selectedItems, CalendarProvider.setNowCalendar().getTimeInMillis());
        archivePresenter.undoMarkAsEmpty(selectedItems);

        //Then
        List<EmptyItem> items;
        items = selectAllItemsPresenter.selectAllItemsFromEmptyItemsHistoryTable();
        assertEquals(2, items.size());
    }

    public void testWhenMarkAsEmptyAndUndoThenPredictionIsRecovered() {
        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        ArchivePresenter archivePresenter = new ArchivePresenter(new ArchiveInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
        ShowPredictionsPresenter showPredictionsPresenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
        Calendar c = CalendarProvider.setCalendar(1, 4, 2016);
        addEmptyItemPresenter.insertNewEmptyItem("Kasza", c.getTimeInMillis());
        Calendar c2 = CalendarProvider.setCalendar(8, 4, 2016);
        addEmptyItemPresenter.insertExistingEmptyItem(1, c2.getTimeInMillis());
        List<Long> selectedItems = new ArrayList<>();
        selectedItems.add((long) 1);

        //When
        archivePresenter.markAsEmpty(selectedItems, CalendarProvider.setNowCalendar().getTimeInMillis());
        archivePresenter.undoMarkAsEmpty(selectedItems);

        //Then
        Prediction prediction = showPredictionsPresenter.getPredictionForItem(1);
        Calendar c3 = Calendar.getInstance();
        c3.setTimeInMillis(prediction.getTime());
        assertEquals(7, prediction.getDaysNumber());
        assertEquals(15, c3.get(Calendar.DAY_OF_MONTH));
        assertEquals(4, c3.get(Calendar.MONTH));
        assertEquals(2016, c3.get(Calendar.YEAR));
    }

    public void testWhenOpenTabWithAllPredictionThenAllPredictionsAreShown() {
        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        ShowPredictionsPresenter showPredictionsPresenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
        Calendar c = CalendarProvider.setNowCalendar();
        c.add(Calendar.DATE, -1);
        Calendar c1 = CalendarProvider.setNowCalendar();
        c1.add(Calendar.DATE, -3);
        addEmptyItemPresenter.insertNewEmptyItemWithHistoryAndPrediction("Kasza", c.getTimeInMillis(), c1.getTimeInMillis(), 2);
        Calendar c2 = CalendarProvider.setNowCalendar();
        c2.add(Calendar.DATE, -15);
        Calendar c3 = CalendarProvider.setNowCalendar();
        c3.add(Calendar.DATE, -19);
        addEmptyItemPresenter.insertNewEmptyItemWithHistoryAndPrediction("Makaron", c2.getTimeInMillis(), c3.getTimeInMillis(), 15);

        //When
        List<EmptyItem> allPredictions = showPredictionsPresenter.getPredictions();

        //Then
        assertEquals(2, allPredictions.size());
    }

    public void testWhenOpenTabWithWeeklyPredictionThenWeeklyPredictionsAreShown() {
        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        ShowPredictionsPresenter showPredictionsPresenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
        Calendar c = CalendarProvider.setNowCalendar();
        c.add(Calendar.DATE, -1);
        Calendar c1 = CalendarProvider.setNowCalendar();
        c1.add(Calendar.DATE, -3);
        addEmptyItemPresenter.insertNewEmptyItemWithHistoryAndPrediction("Kasza", c.getTimeInMillis(), c1.getTimeInMillis(), 2);
        Calendar c2 = CalendarProvider.setNowCalendar();
        c2.add(Calendar.DATE, -15);
        Calendar c3 = CalendarProvider.setNowCalendar();
        c3.add(Calendar.DATE, -19);
        addEmptyItemPresenter.insertNewEmptyItemWithHistoryAndPrediction("Makaron", c2.getTimeInMillis(), c3.getTimeInMillis(), 15);

        //When
        List<EmptyItem> allPredictions = showPredictionsPresenter.getPredictionsForWeek();

        //Then
        assertEquals(1, allPredictions.size());
        assertEquals("Kasza", allPredictions.get(0).getName());
    }

    public void testWhenOpenTabWithMonthlyPredictionThenMonthlyPredictionsAreShown() {
        //Given
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        AddEmptyItemPresenter addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig("shopping_advisor_test.db"), mContext), view);
        ShowPredictionsPresenter showPredictionsPresenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DbConfig("shopping_advisor_test.db"), mContext));
        Calendar c = CalendarProvider.setNowCalendar();
        c.add(Calendar.DATE, -1);
        Calendar c1 = CalendarProvider.setNowCalendar();
        c1.add(Calendar.DATE, -3);
        addEmptyItemPresenter.insertNewEmptyItemWithHistoryAndPrediction("Kasza", c.getTimeInMillis(), c1.getTimeInMillis(), 2);
        Calendar c2 = CalendarProvider.setNowCalendar();
        c2.add(Calendar.DATE, -15);
        Calendar c3 = CalendarProvider.setNowCalendar();
        c3.add(Calendar.DATE, -19);
        addEmptyItemPresenter.insertNewEmptyItemWithHistoryAndPrediction("Makaron", c2.getTimeInMillis(), c3.getTimeInMillis(), 15);

        //When
        List<EmptyItem> allPredictions = showPredictionsPresenter.getPredictionsForMonth();

        //Then
        assertEquals(1, allPredictions.size());
        assertEquals("Makaron", allPredictions.get(0).getName());
    }
}
