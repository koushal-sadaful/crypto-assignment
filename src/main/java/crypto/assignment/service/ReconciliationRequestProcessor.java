package crypto.assignment.service;

import crypto.assignment.dto.CandleStickChartReconciliationResult;

public interface ReconciliationRequestProcessor {
    CandleStickChartReconciliationResult processRequest(String instrumentName, String timeInterval);
}
