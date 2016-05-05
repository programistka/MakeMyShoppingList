package net.programistka.shoppingadvisor;

import android.content.Context;
import android.test.AndroidTestCase;

import net.programistka.shoppingadvisor.dbhandlers.ArchiveDbHandler;
import net.programistka.shoppingadvisor.dbhandlers.EmptyItemsDbHandler;
import net.programistka.shoppingadvisor.dbhandlers.PredictionsDbHandler;
import net.programistka.shoppingadvisor.models.EmptyItem;
import net.programistka.shoppingadvisor.models.Prediction;
import net.programistka.shoppingadvisor.models.PredictionsHandler;
import net.programistka.shoppingadvisor.presenters.DbConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EmptyItemsDbHandlerTests extends AndroidTestCase {

    private EmptyItemsDbHandler dbHandler;

    public void setUp() throws Exception {
        dbHandler = new EmptyItemsDbHandler(new DbConfig("shopping_advisor_test.db"), mContext);
        mContext.deleteDatabase(dbHandler.getDatabaseName());
        mContext.openOrCreateDatabase(dbHandler.getDatabaseName(), Context.MODE_PRIVATE, null);
    }

    public void tearDown() throws Exception {
        mContext.deleteDatabase(dbHandler.getDatabaseName());
    }

    public void testWhenAddNewEmptyItemThenNewItemAddedToEmptyItemsTable() {
        Calendar c = CalendarProvider.setCalendar(21, 3, 2016);
        dbHandler.insertNewEmptyItem("Proszek do prania", c.getTimeInMillis());
        List<EmptyItem> emptyItemsList = dbHandler.selectAllItemsFromItemsTable();
        assertEquals(1, emptyItemsList.size());
        assertEquals("proszek do prania", emptyItemsList.get(0).getName());
    }

    public void testWhenAddNewEmptyItemThenNewOnceItemNotAddedToPredictionsTable() {
        Calendar c = CalendarProvider.setCalendar(21, 3, 2016);
        dbHandler.insertNewEmptyItem("Proszek do prania", c.getTimeInMillis());
        PredictionsDbHandler predictionsDbHandler = new PredictionsDbHandler(new DbConfig("shopping_advisor_test.db"), mContext);
        Prediction prediction = predictionsDbHandler.getPredictionForItem(1);
        assertNull(prediction);
    }

    public void testWhenAddNewEmptyItemThenNewItemAddedToEmptyItemsHistoryTable() {
        Calendar c = CalendarProvider.setCalendar(21, 3, 2016);
        dbHandler.insertNewEmptyItem("Proszek do prania", c.getTimeInMillis());
        List<EmptyItem> emptyItemsHistoryList = dbHandler.selectAllItemsFromEmptyItemsHistoryTable();
        assertEquals(1, emptyItemsHistoryList.size());
        assertEquals("proszek do prania", emptyItemsHistoryList.get(0).getName());
    }

    public void testWhenAddTwoEmptyItemsThenValidPredictionCreatedInPredictionsTable() {
        Calendar c = CalendarProvider.setCalendar(21, 3, 2016);
        dbHandler.insertNewEmptyItem("Proszek do prania", c.getTimeInMillis());
        Calendar c2 = CalendarProvider.setCalendar(23, 3, 2016);
        dbHandler.insertExistingEmptyItem(1, c2.getTimeInMillis());

        List<EmptyItem> emptyItems = dbHandler.selectAllItemsFromEmptyItemsHistoryTableByItemId(1);
        List<Long> emptyItemsTimes = new ArrayList<>();
        for (EmptyItem emptyItem:emptyItems){
            emptyItemsTimes.add(emptyItem.getCreationDate());
        }
         
        Prediction prediction = PredictionsHandler.generatePrediction(emptyItemsTimes);
        assertEquals(2, prediction.getDaysNumber());
        Calendar c3 = Calendar.getInstance();
        c3.setTimeInMillis(prediction.getTime());

        assertEquals(25, c3.get(Calendar.DAY_OF_MONTH));
        assertEquals(3, c3.get(Calendar.MONTH));
        assertEquals(2016, c3.get(Calendar.YEAR));
    }

    public void testWhenMoreThanTwoEmptyItemsThenValidPredictionCreatedInPredictionsTable() {
        Calendar c = CalendarProvider.setCalendar(21, 3, 2016);
        dbHandler.insertNewEmptyItem("Proszek do prania", c.getTimeInMillis());
        Calendar c2 = CalendarProvider.setCalendar(23, 3, 2016);
        dbHandler.insertExistingEmptyItem(1, c2.getTimeInMillis());
        Calendar c3 = CalendarProvider.setCalendar(28, 3, 2016);
        dbHandler.insertExistingEmptyItem(1, c3.getTimeInMillis());

        List<EmptyItem> emptyItems = dbHandler.selectAllItemsFromEmptyItemsHistoryTableByItemId(1);
        List<Long> emptyItemsTimes = new ArrayList<>();
        for (EmptyItem emptyItem:emptyItems){
            emptyItemsTimes.add(emptyItem.getCreationDate());
        }

        Prediction prediction = PredictionsHandler.generatePrediction(emptyItemsTimes);
        assertEquals(4, prediction.getDaysNumber());
        Calendar c4 = Calendar.getInstance();
        c4.setTimeInMillis(prediction.getTime());

        assertEquals(2, c4.get(Calendar.DAY_OF_MONTH));
        assertEquals(4, c4.get(Calendar.MONTH));
        assertEquals(2016, c4.get(Calendar.YEAR));
    }

    public void testWhenMoveElementToArchiveThenElementAddedToArchiveTable() {
        Calendar c = CalendarProvider.setCalendar(21, 3, 2016);
        dbHandler.insertNewEmptyItem("Proszek do prania", c.getTimeInMillis());
        Calendar c2 = CalendarProvider.setCalendar(23, 3, 2016);
        dbHandler.insertExistingEmptyItem(1, c2.getTimeInMillis());
        Calendar c3 = CalendarProvider.setCalendar(28, 3, 2016);
        dbHandler.insertExistingEmptyItem(1, c3.getTimeInMillis());
        ArchiveDbHandler archiveDbHandler = new ArchiveDbHandler(new DbConfig("shopping_advisor_test.db"), mContext);
        archiveDbHandler.insertItemToArchiveTable(1);
        assertTrue(archiveDbHandler.checkIfArchivedElement(1));
    }

    public void testWhenAddExistingEmptyItemWhichIsArchivedThenDeletedFromArchiveTable() {
        Calendar c = CalendarProvider.setCalendar(21, 3, 2016);
        dbHandler.insertNewEmptyItem("Proszek do prania", c.getTimeInMillis());
        Calendar c2 = CalendarProvider.setCalendar(23, 3, 2016);
        dbHandler.insertExistingEmptyItem(1, c2.getTimeInMillis());
        Calendar c3 = CalendarProvider.setCalendar(28, 3, 2016);
        dbHandler.insertExistingEmptyItem(1, c3.getTimeInMillis());
        ArchiveDbHandler archiveDbHandler = new ArchiveDbHandler(new DbConfig("shopping_advisor_test.db"), mContext);
        archiveDbHandler.insertItemToArchiveTable(1);
        Calendar c4 = CalendarProvider.setCalendar(1, 4, 2016);
        dbHandler.insertExistingEmptyItem(1, c4.getTimeInMillis());
        assertFalse(archiveDbHandler.checkIfArchivedElement(1));
    }

    //TODO: Test, ze gdy dodamy empty item, ktory byl zarchiwizowany to powstaje dodatakowa predykcja w bazie danych
    //TODO: Test, ze gdy dodamy empty item, ktory byl zarchiwizowany i powstala dodatkowa predykcja to po kolejnym wstawieniu predykcja znika
}
