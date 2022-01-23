package crypto.assignment.utils;

public class ChartIntervalParser {

    private final static double ONE_MIN_IN_MILLIS = 60000;
    private final static double ONE_HOUR_IN_MILLIS = 3600000;
    private final static double ONE_DAY_IN_MILLIS = 86400000;

    public static double parseIntervalStringToMillis(String interval) {
        double durationInMillis;
        switch (interval) {
            case ("1m"):
                durationInMillis = ONE_MIN_IN_MILLIS;
                break;
            case ("5m"):
                durationInMillis = 5 * ONE_MIN_IN_MILLIS;
                break;
            case ("15m"):
                durationInMillis = 15 * ONE_MIN_IN_MILLIS;
                break;
            case ("30m"):
                durationInMillis = 30 * ONE_MIN_IN_MILLIS;
                break;
            case ("1h"):
                durationInMillis = ONE_HOUR_IN_MILLIS;
                break;
            case ("4h"):
                durationInMillis = 4 * ONE_HOUR_IN_MILLIS;
                break;
            case ("6h"):
                durationInMillis = 6 * ONE_HOUR_IN_MILLIS;
                break;
            case ("12h"):
                durationInMillis = 12 * ONE_HOUR_IN_MILLIS;
                break;
            case ("1D"):
                durationInMillis = ONE_DAY_IN_MILLIS;
                break;
            case ("7D"):
                durationInMillis = 7 * ONE_DAY_IN_MILLIS;
                break;
            case ("14D"):
                durationInMillis = 14 * ONE_DAY_IN_MILLIS;
                break;
            case ("1M"):
                durationInMillis = 30 * ONE_DAY_IN_MILLIS;
                break;
            default:
                throw new IllegalArgumentException("Invalid Interval provided");
        }
        return durationInMillis;
    }

}
