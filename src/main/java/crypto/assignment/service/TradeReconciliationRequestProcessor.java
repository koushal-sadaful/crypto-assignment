package crypto.assignment.service;

import crypto.assignment.dto.CandleStickChart;
import crypto.assignment.dto.CandleStickChartReconciliationResult;
import crypto.assignment.dto.Trade;
import crypto.assignment.transport.CryptoHttpClient;
import crypto.assignment.utils.ChartIntervalUtils;
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
        if (!ChartIntervalUtils.isValidInterval(timeInterval))
            return new CandleStickChartReconciliationResult(instrumentName, "Invalid time interval:" + timeInterval);

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

            if (chartFetched.getCandleSticks() == null || chartFetched.getCandleSticks().isEmpty())
                return new CandleStickChartReconciliationResult(instrumentName, "No chart data to reconcile");

            HashMap<Double, ArrayList<Trade>> tradesInTimeBuckets = aggregator.groupByTimeBuckets(allTrades, chartFetched.getEndTimeOfFirstCandle(), chartFetched.getEndTimeOfLastCandle(), chartFetched.getIntervalInMillis());
            CandleStickChartReconciliationResult candleStickChartReconciliationResult = reconciliationEngine.reconcile(chartFetched, tradesInTimeBuckets);
            return candleStickChartReconciliationResult;
        } catch (Exception exception) {
            log.error(String.format("Failed to process request for instrument=%s, interval=%s.", instrumentName, timeInterval));
            exception.printStackTrace();
            log.error(exception.getLocalizedMessage());
            return new CandleStickChartReconciliationResult(instrumentName, "Failed to process request: " + exception.getMessage());
        }
    }
}
