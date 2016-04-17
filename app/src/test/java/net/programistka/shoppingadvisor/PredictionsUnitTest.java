package net.programistka.shoppingadvisor;

import net.programistka.shoppingadvisor.models.Prediction;

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
    public void when_more_then_two_dates_and_not_in_the_same_month_then_prediction_is_correct() {
        //Given
        ArrayList<Long> shoppingHistory = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2016, 2, 19, 0, 0, 0);
        shoppingHistory.add(calendar.getTimeInMillis());
        calendar.set(2016, 2, 22, 0, 0, 0);
        shoppingHistory.add(calendar.getTimeInMillis());
        calendar.set(2016, 2, 25, 0, 0, 0);
        shoppingHistory.add(calendar.getTimeInMillis());
        calendar.set(2016, 3, 15, 0, 0, 0);
        shoppingHistory.add(calendar.getTimeInMillis());

        //When
        Calendar calendarActual = Calendar.getInstance();
        Date dateActual = new Date();
        Prediction prediction = PredictionsHandler.getPrediction(shoppingHistory);
        dateActual.setTime(prediction.getTime());
        calendarActual.setTime(dateActual);

        //Then
        assertEquals(2016, calendarActual.get(Calendar.YEAR));
        assertEquals(3, calendarActual.get(Calendar.MONTH));
        assertEquals(24, calendarActual.get(Calendar.DATE));
        assertEquals(9, prediction.getDays_number());
    }

    @Test
    public void when_more_then_two_dates_and_in_the_same_month_then_prediction_is_correct() {
        //Given
        ArrayList<Long> shoppingHistory = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2016, 2, 19, 0, 0, 0);
        shoppingHistory.add(calendar.getTimeInMillis());
        calendar.set(2016, 2, 22, 0, 0, 0);
        shoppingHistory.add(calendar.getTimeInMillis());
        calendar.set(2016, 2, 25, 0, 0, 0);
        shoppingHistory.add(calendar.getTimeInMillis());

        //When
        Calendar calendarActual = Calendar.getInstance();
        Date dateActual = new Date();
        Prediction prediction = PredictionsHandler.getPrediction(shoppingHistory);
        dateActual.setTime(prediction.getTime());
        calendarActual.setTime(dateActual);

        //Then
        assertEquals(2016, calendarActual.get(Calendar.YEAR));
        assertEquals(2, calendarActual.get(Calendar.MONTH));
        assertEquals(28, calendarActual.get(Calendar.DATE));
        assertEquals(3, prediction.getDays_number());
    }

    @Test
    public void when_two_dates_then_prediction_is_correct() {
        //Given
        ArrayList<Long> shoppingHistory = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2016, 2, 19, 0, 0, 0);
        shoppingHistory.add(calendar.getTimeInMillis());
        calendar.set(2016, 2, 22, 0, 0, 0);
        shoppingHistory.add(calendar.getTimeInMillis());

        //When
        Calendar calendarActual = Calendar.getInstance();
        Date dateActual = new Date();
        Prediction prediction = PredictionsHandler.getPrediction(shoppingHistory);
        dateActual.setTime(prediction.getTime());
        calendarActual.setTime(dateActual);

        //Then
        assertEquals(2016, calendarActual.get(Calendar.YEAR));
        assertEquals(2, calendarActual.get(Calendar.MONTH));
        assertEquals(25, calendarActual.get(Calendar.DATE));
        assertEquals(3, prediction.getDays_number());
    }
}
