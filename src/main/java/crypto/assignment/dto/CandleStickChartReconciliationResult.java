package crypto.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CandleStickChartReconciliationResult {

    @JsonProperty("instrument_name")
    private String instrumentName;

    @JsonProperty("data")
    private List<CandleStickReconciliationResult> candleStickReconciliationResults;

    public CandleStickChartReconciliationResult(String instrumentName) {
        this.instrumentName = instrumentName;
        candleStickReconciliationResults = new ArrayList<>();
    }

    public void addResult(CandleStickReconciliationResult result) {
        candleStickReconciliationResults.add(result);
    }
}
