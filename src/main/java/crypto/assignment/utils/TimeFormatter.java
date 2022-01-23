package crypto.assignment.utils;

import java.text.SimpleDateFormat;

public class TimeFormatter {
    public static String convertUnixToHuman(double unixTimestamp){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(unixTimestamp);
    }
}
