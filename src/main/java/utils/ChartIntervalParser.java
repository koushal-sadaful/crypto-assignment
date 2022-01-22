package utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

public class ChartIntervalParser {

    public static double parseIntervalStringToMillis(String interval){
        int lastCharIndex = interval.length() - 1;
        String durationString = interval.substring(lastCharIndex);
        String multiplierString = interval.substring(0, lastCharIndex);
        double durationInMillis;
        switch (durationString){
            case ("m"):
                durationInMillis = 60 * 1000;
                break;
            case ("h") :
                durationInMillis = 60 * 60 * 1000;
                break;
            case ("D") :
                durationInMillis = 24 * 60 * 60 * 1000;
                break;
            case ("M"):
                durationInMillis = 30 * 14 * 60 * 60 * 1000;
                break;
            default:
                throw new IllegalArgumentException("Invalid Interval provided");
        }
        double multiplier = Double.parseDouble(multiplierString);
        return multiplier * durationInMillis;
    }

}
