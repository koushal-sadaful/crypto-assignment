package crypto.assignment.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import crypto.assignment.models.CandleStick;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CandleStickChart {

    @JsonProperty("instrument_name")
    private String instrumentName;

    @JsonProperty("depth")
    private int depth;

    @JsonProperty("interval")
    private String interval;

    @JsonProperty("data")
    private Iterable<CandleStick> candleSticks;

}
