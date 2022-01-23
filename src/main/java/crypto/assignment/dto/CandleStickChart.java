package crypto.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import crypto.assignment.utils.ChartIntervalUtils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CandleStickChart {

    public CandleStickChart() {
    }

    public CandleStickChart(String instrumentName, List<CandleStick> candleSticks, double intervalInMillis) {
        this.instrumentName = instrumentName;
        this.candleSticks = candleSticks;
        this.intervalInMillis = intervalInMillis;
    }

    @JsonProperty("instrument_name")
    private String instrumentName;

    @JsonProperty("data")
    private List<CandleStick> candleSticks;

    @JsonProperty("interval")
    private double intervalInMillis;

    @JsonSetter("interval")
    public void setIntervalInMillis(String interval) {
        intervalInMillis = ChartIntervalUtils.parseIntervalStringToMillis(interval);
    }

    public double getIntervalInMillis() {
        return intervalInMillis;
    }

    public List<CandleStick> getCandleSticks() {
        return candleSticks;
    }

    public double getEndTimeOfFirstCandle() {
        if (candleSticks.size() <= 0) {
            return 0;
        }
        CandleStick firstCandleStick = candleSticks.get(0);
        return firstCandleStick.getEndTime();
    }

    public double getEndTimeOfLastCandle() {
        if (candleSticks.size() <= 0) {
            return 0;
        }
        CandleStick firstCandleStick = candleSticks.get(candleSticks.size() - 1);
        return firstCandleStick.getEndTime();
    }

    public String getInstrumentName() {
        return instrumentName;
    }
}
