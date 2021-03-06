package com.programistka.makemyshoppinglist.models;

import com.programistka.makemyshoppinglist.CalendarProvider;

import java.util.Calendar;
import java.util.List;

public class PredictionsHandler {
    private static final long MILLIS_IN_DAY = 1000 * 3600 * 24;

    public static Prediction generatePrediction(List<Long> shoppingTimes) {
        if (shoppingTimes.size() < 2) {
            return null;
        }
        long current = shoppingTimes.get(0);
        long next = shoppingTimes.get(1);
        if (shoppingTimes.size() == 2) {
            return generatePredictionForTwoItems(current, next);
        } else {
            return generatePredictionForMoreThanTwoItems(shoppingTimes, current, next);
        }
    }

    public static Prediction generateExpiredBoughtPrediction(int daysToRunOut) {
        Prediction prediction = new Prediction();
        Calendar now = CalendarProvider.setNowCalendar();
        prediction.setTime(now.getTimeInMillis() + daysToRunOut * MILLIS_IN_DAY);
        prediction.setDaysNumber(daysToRunOut);
        return prediction;
    }

    public static Prediction generateBoughtPrediction(long nextEmptyItemDate, int daysToRunOut) {
        Prediction prediction = new Prediction();
        prediction.setTime(nextEmptyItemDate + daysToRunOut * MILLIS_IN_DAY);
        prediction.setDaysNumber(daysToRunOut);
        return prediction;
    }

    private static Prediction generatePredictionForMoreThanTwoItems(List<Long> shoppingTimes, long current, long next) {
        long predictionDays = (next - current) / MILLIS_IN_DAY;
        for (int i = 2; i < shoppingTimes.size(); i++) {
            current = next;
            next = shoppingTimes.get(i);
            predictionDays += (next - current) / MILLIS_IN_DAY;
        }
        long daysAverage = Math.round(((double) predictionDays / (shoppingTimes.size() - 1)));
        Prediction prediction = new Prediction();
        prediction.setTime(next + daysAverage * MILLIS_IN_DAY);
        prediction.setDaysNumber(daysAverage);
        return prediction;
    }

    private static Prediction generatePredictionForTwoItems(long current, long next) {
        Prediction prediction = new Prediction();
        long daysAverage = (next - current) / MILLIS_IN_DAY;
        prediction.setDaysNumber(daysAverage);
        prediction.setTime(next + daysAverage * MILLIS_IN_DAY);
        return prediction;
    }
}