package crypto.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import net.minidev.json.annotate.JsonIgnore;
import utils.ChartIntervalParser;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CandleStickChart {

    @JsonProperty("instrument_name")
    private String instrumentName;

    @JsonProperty("depth")
    private int depth;

    @JsonProperty("data")
    private Iterable<CandleStick> candleSticks;

    @JsonProperty("interval")
    private double intervalInMillis;

    @JsonSetter("interval")
    public void setIntervalInMillis(String interval) {
        intervalInMillis = ChartIntervalParser.parseIntervalStringToMillis(interval);
    }
}
