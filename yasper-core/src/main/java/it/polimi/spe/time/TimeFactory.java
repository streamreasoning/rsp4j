package it.polimi.spe.time;

public class TimeFactory {

    private static Time time = null;
    private static ET et = null;

    public static Time getInstance() {
        if (time == null) {
            time = new TimeImpl();
        }
        return time;
    }

    public static ET getEvaluationTimeInstants() {
        if (et == null) {
            et = new ET();
        }
        return et;
    }
}
