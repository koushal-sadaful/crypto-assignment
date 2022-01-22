package crypto.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import crypto.assignment.utils.ChartIntervalParser;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CandleStickChart {

    @JsonProperty("instrument_name")
    private String instrumentName;

    @JsonProperty("depth")
    private int depth;

    @JsonProperty("data")
    private List<CandleStick> candleSticks;

    @JsonProperty("interval")
    private double intervalInMillis;

    @JsonSetter("interval")
    public void setIntervalInMillis(String interval) {
        intervalInMillis = ChartIntervalParser.parseIntervalStringToMillis(interval);
    }

    public double getIntervalInMillis() {
        return intervalInMillis;
    }

    public List<CandleStick> getCandleSticks() {
        return candleSticks;
    }

    public double getStartTimeInMillis() {
        if (candleSticks.size() <= 0) {
            return 0;
        }
        CandleStick firstCandleStick = candleSticks.get(0);
        return firstCandleStick.getEndTime() - intervalInMillis;
    }

    public double getStartTimeOfLastCandle() {
        if (candleSticks.size() <= 0) {
            return 0;
        }
        CandleStick firstCandleStick = candleSticks.get(candleSticks.size() - 1);
        return firstCandleStick.getEndTime() - intervalInMillis;
    }
}
