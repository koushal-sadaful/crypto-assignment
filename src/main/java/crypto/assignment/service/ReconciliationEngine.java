package crypto.assignment.service;

import crypto.assignment.dto.CandleStickChart;
import crypto.assignment.dto.CandleStickChartReconciliationResult;
import crypto.assignment.dto.Trade;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class ReconciliationEngine implements  ReconciliationService {

    @Override
    public CandleStickChartReconciliationResult reconcile(CandleStickChart candleStickChart, HashMap<Double, ArrayList<Trade>> tradesInTimeBuckets) {

        return null;
    }

}
