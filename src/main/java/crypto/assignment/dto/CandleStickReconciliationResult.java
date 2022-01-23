package crypto.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CandleStickReconciliationResult {

    @JsonProperty("time")
    private double timestamp;
    @JsonProperty("high_price_match")
    private boolean highPriceMatch;
    @JsonProperty("low_price_match")
    private boolean lowPriceMatch;
    @JsonProperty("open_price_match")
    private boolean openPriceMatch;
    @JsonProperty("close_price_match")
    private boolean closePriceMatch;
    @JsonProperty("not_enough_data_for_verification")
    private boolean cannotBeVerified;

    public double getTimestamp() {
        return timestamp;
    }

    public boolean isHighPriceMatch() {
        return highPriceMatch;
    }

    public boolean isLowPriceMatch() {
        return lowPriceMatch;
    }

    public boolean isOpenPriceMatch() {
        return openPriceMatch;
    }

    public boolean isClosePriceMatch() {
        return closePriceMatch;
    }

    public boolean isCannotBeVerified() {
        return cannotBeVerified;
    }

    public CandleStickReconciliationResult(double timestamp, boolean highPriceMatch, boolean lowPriceMatch, boolean openPriceMatch, boolean closePriceMatch) {
        this.timestamp = timestamp;
        this.highPriceMatch = highPriceMatch;
        this.lowPriceMatch = lowPriceMatch;
        this.openPriceMatch = openPriceMatch;
        this.closePriceMatch = closePriceMatch;
        this.cannotBeVerified = false;
    }

    public static CandleStickReconciliationResult createNonVerifiableResult(double timestamp) {
        return new CandleStickReconciliationResult(timestamp, true);
    }

    private CandleStickReconciliationResult(double timestamp, boolean cannotBeVerified) {
        this.timestamp = timestamp;
        this.cannotBeVerified = cannotBeVerified;
    }

    @Override
    public boolean equals(Object obj) {
        CandleStickReconciliationResult result = (CandleStickReconciliationResult) obj;
        return timestamp == result.timestamp &&
                highPriceMatch == result.isHighPriceMatch() &&
                lowPriceMatch == result.isLowPriceMatch() &&
                openPriceMatch == result.isOpenPriceMatch() &&
                closePriceMatch == result.isClosePriceMatch() &&
                cannotBeVerified == result.isCannotBeVerified();
    }
}
