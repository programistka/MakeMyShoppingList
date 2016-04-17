package net.programistka.shoppingadvisor;

import net.programistka.shoppingadvisor.models.Prediction;

import java.util.ArrayList;

/**
 * Created by maga on 28.03.16.
 */
public class PredictionsHandler {
    public static Prediction getPrediction(ArrayList<Long> shoppingTimes) {
        long current = shoppingTimes.get(0);
        System.out.println("get0" + shoppingTimes.get(0));
        long next = shoppingTimes.get(1);
        System.out.println("get1" + shoppingTimes.get(1));
        long predictionTime = next - current;
        System.out.println(predictionTime);
        Prediction prediction = new Prediction();
        if(shoppingTimes.size() == 2) {
            prediction.setDays_number((int)(next - current)/ (1000*3600*24));
            prediction.setTime(next + next - current);
        } else {
            for (int i = 2; i < shoppingTimes.size(); i++) {
                current = next;
                next = shoppingTimes.get(i);
                predictionTime += next - current;
                System.out.println("item i=" + i + shoppingTimes.get(i));
            }
            long time = predictionTime / (shoppingTimes.size() - 1);
            prediction.setTime(next + time);
            prediction.setDays_number((int)time/ (1000*3600*24));
        }
        return prediction;
    }
}