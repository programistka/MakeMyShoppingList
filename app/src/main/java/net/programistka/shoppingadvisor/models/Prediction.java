package net.programistka.shoppingadvisor.models;

/**
 * Created by maga on 15.04.16.
 */
public class Prediction {
    private long time;
    private int days_number;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getDays_number() {
        return days_number;
    }

    public void setDays_number(int days_number) {
        this.days_number = days_number;
    }
}
