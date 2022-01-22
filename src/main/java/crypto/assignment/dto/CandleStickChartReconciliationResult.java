package crypto.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CandleStickChartReconciliationResult {

    @JsonProperty("instrument_name")
    private String instrumentName;

    @JsonProperty("data")
    private List<CandleStickReconciliationResult> candleStickReconciliationResults;

}
