package crypto.assignment.service;

import crypto.assignment.dto.*;
import crypto.assignment.utils.CandleStickReconciliation;
import crypto.assignment.utils.TimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class ReconciliationEngine implements ReconciliationService {

    private static final Logger log = LoggerFactory.getLogger(ReconciliationEngine.class);

    @Autowired
    private TradeDataAggregator aggregator;

    @Override
    public CandleStickChartReconciliationResult reconcile(CandleStickChart candleStickChart, HashMap<Double, ArrayList<Trade>> tradesInTimeBuckets) {

        if (candleStickChart.getCandleSticks().isEmpty())
            return new CandleStickChartReconciliationResult(candleStickChart.getInstrumentName(), "No candle stick data to verify");

        CandleStickChartReconciliationResult results = new CandleStickChartReconciliationResult(candleStickChart.getInstrumentName());
        candleStickChart.getCandleSticks().forEach(candleStick -> {
            CandleStickReconciliationResult result;
            double endTime = candleStick.getEndTime();
            if (tradesInTimeBuckets.containsKey(endTime)) {
                log.debug(String.format("Processing trade list for time bucket endTime=%s", TimeFormatter.convertUnixToHuman(endTime)));
                ArrayList<Trade> allTradesInTimeBucket = tradesInTimeBuckets.get(endTime);

                log.debug(String.format("Processing trade list for time bucket endTime=%s, trades=%s", TimeFormatter.convertUnixToHuman(endTime), allTradesInTimeBucket.size()));
                if (allTradesInTimeBucket.isEmpty()) {
                    result = CandleStickReconciliationResult.createNonVerifiableResult(endTime);
                } else {
                    CandleStick computedCandleStickData = aggregator.generateCandleStickData(endTime, allTradesInTimeBucket);
                    result = CandleStickReconciliation.reconcile(candleStick, computedCandleStickData);
                }

            } else {
                log.debug(String.format("No trades for time bucket endTime=%s", TimeFormatter.convertUnixToHuman(endTime)));
                result = CandleStickReconciliationResult.createNonVerifiableResult(endTime);
            }
            results.addResult(result);
        });
        return results;
    }
}
