package net.programistka.shoppingadvisor;

import net.programistka.shoppingadvisor.models.Prediction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maga on 28.03.16.
 */
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
        prediction.setDays_number(days);
        return prediction;
    }

    private static Prediction generatePredictionForTwoItems(long current, long next) {
        Prediction prediction = new Prediction();
        prediction.setDays_number((int)(next - current)/FACTOR);
        prediction.setTime(next + next - current);
        return prediction;
    }
}