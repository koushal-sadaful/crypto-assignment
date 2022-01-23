package crypto.assignment.service;

import crypto.assignment.dto.*;
import crypto.assignment.transport.CryptoHttpClient;
import crypto.assignment.utils.CandleStickReconciliation;
import crypto.assignment.utils.TimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class TradeReconciliationRequestProcessor implements ReconciliationRequestProcessor {

    private static final Logger log = LoggerFactory.getLogger(TradeReconciliationRequestProcessor.class);

    @Autowired
    private CryptoHttpClient client;

    @Autowired
    private TradeDataAggregator aggregator;

    @Autowired
    private ReconciliationEngine reconciliationEngine;

    @Override
    public CandleStickChartReconciliationResult processRequest(String instrumentName, String timeInterval) {
        log.info(String.format("Start processing request for instrument=%s, interval=%s.", instrumentName, timeInterval));
        CandleStickChartReconciliationResult candleStickChartReconciliationResult = new CandleStickChartReconciliationResult(instrumentName);

        try {
            CandleStickChart chartFetched = client.getCandleStickChart(instrumentName, timeInterval);
            log.info(String.format("First CandleStick timestamp=%s, Last CandleStick timestamp=%s.",
                    TimeFormatter.convertUnixToHuman(chartFetched.getEndTimeOfFirstCandle()),
                    TimeFormatter.convertUnixToHuman(chartFetched.getEndTimeOfLastCandle()))
            );

            List<Trade> allTrades = client.getAllTrades(instrumentName);
            log.info(String.format("First Trade timestamp=%s, Last Trade timestamp=%s.",
                    TimeFormatter.convertUnixToHuman(allTrades.get(0).getTimestamp()),
                    TimeFormatter.convertUnixToHuman(allTrades.get(allTrades.size() - 1).getTimestamp()))
            );

            HashMap<Double, ArrayList<Trade>> tradesInTimeBuckets = aggregator.groupByTimeBuckets(allTrades, chartFetched.getEndTimeOfFirstCandle(), chartFetched.getEndTimeOfLastCandle(), chartFetched.getIntervalInMillis());

            chartFetched.getCandleSticks().forEach(candleStick -> {
                CandleStickReconciliationResult result;
                double endTime = candleStick.getEndTime();
                if (tradesInTimeBuckets.containsKey(endTime)) {
//                    log.error(String.format("Processing trade list for time bucket endTime=%s", TimeFormatter.convertUnixToHuman(endTime)));
                    ArrayList<Trade> allTradesInTimeBucket = tradesInTimeBuckets.get(endTime);
                    CandleStick computedCandleStickData = aggregator.generateCandleStickData(endTime, allTradesInTimeBucket);
                    result = CandleStickReconciliation.reconcile(candleStick, computedCandleStickData);
                } else {
                    log.error(String.format("No trades for time bucket endTime=%s", TimeFormatter.convertUnixToHuman(endTime)));
                    result = CandleStickReconciliationResult.createNonVerifiableResult(endTime);
                }
                candleStickChartReconciliationResult.addResult(result);
            });
        } catch (Exception exception) {
            log.error(String.format("Failed to process request for instrument=%s, interval=%s.", instrumentName, timeInterval));
            exception.printStackTrace();
            log.error(exception.getLocalizedMessage());
        }

        return candleStickChartReconciliationResult;
    }

}
