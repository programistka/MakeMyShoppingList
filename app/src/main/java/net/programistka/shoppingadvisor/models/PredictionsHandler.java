package net.programistka.shoppingadvisor.models;

import java.util.List;

public class PredictionsHandler {
    private static final int FACTOR = 1000*3600*24;

    public static Prediction generatePrediction(List<Long> shoppingTimes) {
        long current = shoppingTimes.get(0);
        long next = shoppingTimes.get(1);
        if(shoppingTimes.size() == 2) {
            return generatePredictionForTwoItems(current, next);
        } else {
            return generatePredictionForMoreThanTwoItems(shoppingTimes, current, next);
        }
    }

    public static Prediction generateBoughtPrediction(long nextEmptyItemDate, int daysToRunOut) {
        Prediction prediction = new Prediction();
        prediction.setTime(nextEmptyItemDate + daysToRunOut*FACTOR);
        prediction.setDaysNumber(daysToRunOut);
        return prediction;
    }

    private static Prediction generatePredictionForMoreThanTwoItems(List<Long> shoppingTimes, long current, long next) {
        long predictionTime = next - current;
        for (int i = 2; i < shoppingTimes.size(); i++) {
            current = next;
            next = shoppingTimes.get(i);
            predictionTime += next - current;
        }
        double time = predictionTime / (shoppingTimes.size() - 1);
        int days = (int) Math.round(time/(FACTOR));
        Prediction prediction = new Prediction();
        prediction.setTime(next + days*FACTOR);
        prediction.setDaysNumber(days);
        return prediction;
    }

    private static Prediction generatePredictionForTwoItems(long current, long next) {
        Prediction prediction = new Prediction();
        prediction.setDaysNumber((int)(next - current)/FACTOR);
        prediction.setTime(next + next - current);
        return prediction;
    }
}