package crypto.assignment.service;

import crypto.assignment.dto.CandleStick;
import crypto.assignment.dto.Trade;
import crypto.assignment.transport.CryptoHttpClient;
import crypto.assignment.utils.TimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Component
public class TradeDataAggregator implements DataAggregator {

    private static final Logger log = LoggerFactory.getLogger(TradeDataAggregator.class);

    @Override
    public HashMap<Double, ArrayList<Trade>> groupByTimeBuckets(List<Trade> tradeList, double startTime, double endTime, double interval) {
        log.info(String.format("Start grouping trades in time buckets, startTime=%s, endTime=%s, tradeListCount=%s, interval=%s",
                TimeFormatter.convertUnixToHuman(startTime),
                TimeFormatter.convertUnixToHuman(endTime),
                tradeList.size(), interval));

        ArrayList<Double> candleEndTimeList = new ArrayList<>();
        HashMap<Double, ArrayList<Trade>> tradeBucketsByEndTime = new HashMap<>();
        if (tradeList.size() <= 0) return tradeBucketsByEndTime;

        tradeList.sort(Comparator.comparingDouble(Trade::getTimestamp));
        log.info(String.format("First Trade timestamp=%s, Last Trade timestamp=%s.",
                TimeFormatter.convertUnixToHuman(tradeList.get(0).getTimestamp()),
                TimeFormatter.convertUnixToHuman(tradeList.get(tradeList.size() - 1).getTimestamp())
        ));

        double currentEndTime = startTime;

        while (currentEndTime <= endTime) {
            candleEndTimeList.add(currentEndTime);
            tradeBucketsByEndTime.put(currentEndTime, new ArrayList<>());
            currentEndTime = currentEndTime + interval;
        }

        candleEndTimeList.sort(Comparator.comparingDouble(Double::doubleValue));

        int currentEndTimeListIndex = 0;
        double currentEndTimeInList = candleEndTimeList.get(currentEndTimeListIndex);

        // go through each trade in order of timestamp
        // if trade is in current time bucket, add to list
        // if not, move on to the next bucket and repeat
        // ignore trades that do not fit in start-end window

        for (Trade trade : tradeList) {
            double timestamp = trade.getTimestamp();

            if (shouldIgnoreTrade(startTime, endTime, interval, timestamp)) continue;

            if (timestamp <= currentEndTimeInList) {
                addTradeToBucket(tradeBucketsByEndTime, currentEndTimeInList, trade);
            } else {
                while (timestamp > currentEndTimeInList && currentEndTimeListIndex < candleEndTimeList.size()) {
                    currentEndTimeListIndex++;
                    currentEndTimeInList = candleEndTimeList.get(currentEndTimeListIndex);
                }
                addTradeToBucket(tradeBucketsByEndTime, currentEndTimeInList, trade);
            }
        }
        log.info(String.format("Completed grouping trades in timebuckets, timeBucketCount=%s.", tradeBucketsByEndTime.size()));
        return tradeBucketsByEndTime;
    }

    private void addTradeToBucket(HashMap<Double, ArrayList<Trade>> tradeBucketsByEndTime, double currentEndTimeInList, Trade trade) {
        log.info(String.format("Adding Trade timestamp=%s to results.", trade.getTimestamp()));
        ArrayList<Trade> currentBucketTradeList = tradeBucketsByEndTime.get(currentEndTimeInList);
        currentBucketTradeList.add(trade);
        tradeBucketsByEndTime.put(currentEndTimeInList, currentBucketTradeList);
    }

    private boolean shouldIgnoreTrade(double startTime, double endTime, double interval, double timestamp) {
        return timestamp > endTime || timestamp < (startTime - interval);
    }

    @Override
    public CandleStick generateCandleStickData(double candleStickTimestamp, List<Trade> tradeList) {

        if (tradeList.size() <= 0) {
            return new CandleStick(0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        BigDecimal openTradePrice = tradeList.get(0).getPrice();
        BigDecimal closeTradePrice = tradeList.get(tradeList.size() - 1).getPrice();

        BigDecimal lowPrice = BigDecimal.valueOf(100000000);
        BigDecimal highPrice = BigDecimal.valueOf(0);
        BigDecimal totalQuantity = BigDecimal.valueOf(0);

        for (Trade trade : tradeList) {
            totalQuantity = totalQuantity.add(trade.getQuantity());
            int updateHighPrice = trade.getPrice().compareTo(highPrice);
            if (updateHighPrice > 0) {
                highPrice = trade.getPrice();
            }

            int updateLowPrice = trade.getPrice().compareTo(lowPrice);
            if (updateLowPrice < 0) {
                lowPrice = trade.getPrice();
            }
        }

        return new CandleStick(candleStickTimestamp, highPrice, lowPrice, openTradePrice, closeTradePrice, totalQuantity);
    }

}
