package net.programistka.shoppingadvisor;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;


/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class PredictionsUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void prediction_isCorrect() {
        ArrayList<Long> shoppingHistory = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2016, 3, 19, 0, 0, 0);
        shoppingHistory.add(calendar.getTimeInMillis());
        calendar.set(2016, 3, 22, 0, 0, 0);
        shoppingHistory.add(calendar.getTimeInMillis());
        calendar.set(2016, 3, 25, 0, 0, 0);
        shoppingHistory.add(calendar.getTimeInMillis());

        Calendar calendarActual = Calendar.getInstance();
        Date dateActual = new Date();
        dateActual.setTime(PredictionsHandler.GetPrediction(shoppingHistory));
        calendarActual.setTime(dateActual);

        assertEquals(2016, calendarActual.get(Calendar.YEAR));
        assertEquals(3, calendarActual.get(Calendar.MONTH));
        assertEquals(28, calendarActual.get(Calendar.DATE));
    }
}