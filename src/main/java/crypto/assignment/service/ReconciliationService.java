package crypto.assignment.service;

import crypto.assignment.dto.CandleStickChart;

public interface ReconciliationService {
    void process(String instrumentName, String timeInterval);
    void reconcile(CandleStickChart first, CandleStickChart second);
}
