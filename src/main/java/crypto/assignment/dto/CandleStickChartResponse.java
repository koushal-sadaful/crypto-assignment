package crypto.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import crypto.assignment.models.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CandleStickChartResponse {
    private String instrumentName;
    private String intervalValue;
    private Iterable<CandleStick> candleSticks;

    public CandleStickChartResponse(String instrumentName, String intervalValue, Iterable<CandleStick> candleSticks) {
        this.instrumentName = instrumentName;
        this.intervalValue = intervalValue;
        this.candleSticks = candleSticks;
    }

    public CandleStickChartResponse() {
    }
}
