package crypto.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CandleStick {
    public CandleStick(double endTime, BigDecimal high, BigDecimal low, BigDecimal open, BigDecimal close, BigDecimal volume) {
        this.endTime = endTime;
        this.high = high;
        this.low = low;
        this.open = open;
        this.close = close;
        this.volume = volume;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public BigDecimal getClose() {
        return close;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    @JsonProperty("t")
    private double endTime;

    @JsonProperty("h")
    private BigDecimal high;

    @JsonProperty("l")
    private BigDecimal low;

    @JsonProperty("o")
    private BigDecimal open;

    @JsonProperty("c")
    private BigDecimal close;

    @JsonProperty("v")
    private BigDecimal volume;

    public double getEndTime() {
        return endTime;
    }
}
