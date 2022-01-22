package crypto.assignment.service;

import crypto.assignment.dto.CandleStickChart;

public interface ReconciliationService {
    void process();
    void reconcile(CandleStickChart first, CandleStickChart second);
}
