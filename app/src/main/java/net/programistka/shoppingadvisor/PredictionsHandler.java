package net.programistka.shoppingadvisor;

import java.util.ArrayList;

/**
 * Created by maga on 28.03.16.
 */
public class PredictionsHandler {
    public static long GetPrediction(ArrayList<Long> shoppingTimes) {
        long current = shoppingTimes.get(0);
        long next = shoppingTimes.get(1);
        long prediction = next - current;
        if(shoppingTimes.size() == 2) {
            return next + next - current;
        } else {
            for (int i = 2; i < shoppingTimes.size(); i++) {
                current = next;
                next = shoppingTimes.get(i);
                prediction += next - current;
            }
        }
        return next + (prediction / (shoppingTimes.size() - 1)) ;
    }
}