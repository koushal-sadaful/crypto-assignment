package crypto.assignment.service;

import crypto.assignment.dto.CandleStick;
import crypto.assignment.dto.CandleStickChart;
import crypto.assignment.dto.Trade;
import crypto.assignment.transport.CryptoHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class TradeReconciliationService implements ReconciliationService {

    @Autowired
    private CryptoHttpClient client;

    @Autowired
    private TradeDataAggregator aggregator;

    @Override
    public void process() {
        List<Trade> allTrades = client.getAllTrades();

        CandleStickChart chartFetched = client.getCandleStickChart();
        HashMap<Double, ArrayList<Trade>> tradesInTimeBuckets = aggregator.groupByTimeBuckets(allTrades, chartFetched.getStartTimeInMillis(), chartFetched.getIntervalInMillis());




        chartFetched.getCandleSticks().forEach(candleStick -> {
            double endTime = candleStick.getEndTime();
            ArrayList<Trade> allTradesInTimeBucket  = tradesInTimeBuckets.get(endTime);
            CandleStick expectedCandleStickStatistics = aggregator.generateCandleStickData(endTime, allTradesInTimeBucket);





        });





    }

    @Override
    public void reconcile(CandleStickChart first, CandleStickChart second) {

    }

}
