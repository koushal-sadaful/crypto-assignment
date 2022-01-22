package crypto.assignment.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CandleStick {

    @JsonProperty("t")
    private float endTime;

    @JsonProperty("h")
    private float high;

    @JsonProperty("l")
    private float low;

    @JsonProperty("o")
    private float open;

    @JsonProperty("c")
    private float close;

    @JsonProperty("v")
    private float volume;

}
