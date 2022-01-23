package crypto.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CandleStickChartReconciliationResult {

    @JsonProperty("instrument_name")
    private String instrumentName;

    @JsonProperty("reconciliation_summary")
    private String summaryMessage;

    public List<CandleStickReconciliationResult> getReconciliationResults() {
        return candleStickReconciliationResults;
    }

    @JsonProperty("data")
    private List<CandleStickReconciliationResult> candleStickReconciliationResults;

    public CandleStickChartReconciliationResult(String instrumentName, String summaryMessage) {
        this.instrumentName = instrumentName;
        this.summaryMessage = summaryMessage;
    }

    public CandleStickChartReconciliationResult(String instrumentName) {
        this.instrumentName = instrumentName;
        candleStickReconciliationResults = new ArrayList<>();
    }

    public void addResult(CandleStickReconciliationResult result) {
        candleStickReconciliationResults.add(result);
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public String getSummaryMessage() {
        return summaryMessage;
    }
}
