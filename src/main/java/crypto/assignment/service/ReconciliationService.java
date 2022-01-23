package crypto.assignment.service;

import crypto.assignment.dto.CandleStickChart;
import crypto.assignment.dto.CandleStickChartReconciliationResult;
import crypto.assignment.dto.Trade;

import java.util.ArrayList;
import java.util.HashMap;

public interface ReconciliationService {
    CandleStickChartReconciliationResult reconcile(CandleStickChart candleStickChart, HashMap<Double, ArrayList<Trade>> tradesInTimeBuckets);
}
