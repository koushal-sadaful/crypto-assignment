package crypto.assignment.service;

import crypto.assignment.dto.CandleStick;
import crypto.assignment.dto.Trade;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TradeDataAggregator implements DataAggregator {

    @Override
    public HashMap<Double, ArrayList<Trade>> groupByTimeBuckets(List<Trade> tradeList, double startTimeInMillis, double intervalInMillis) {
        HashMap<Double, ArrayList<Trade>> tradesInTimeBuckets = new HashMap<>() {
        };

        Collections.sort(tradeList);
        tradeList.sort(Comparator.comparingDouble(Trade::getTimestamp));

        List<Trade> sortedTrades = tradeList.stream()
                .sorted(Comparator.comparing(Trade::getTimestamp))
                .collect(Collectors.toList());

        double currentStartTime = startTimeInMillis;
        double currentEndTime = currentStartTime + intervalInMillis;
        tradesInTimeBuckets.put(currentEndTime, new ArrayList<>());


        // go through each trade in order of timestamp
        // if trade is in current time bucket, add to list
        // if not, create next bucket and add trade to it

        for (Trade trade : tradeList) {
            if (trade.getTimestamp() < currentEndTime && trade.getTimestamp() >= currentStartTime) {
                tradesInTimeBuckets.get(currentEndTime).add(trade);
            } else {
                currentStartTime = currentEndTime;
                currentEndTime = currentStartTime + intervalInMillis;
                tradesInTimeBuckets.put(currentEndTime, new ArrayList<>());
                tradesInTimeBuckets.get(currentEndTime).add(trade);
            }
        }
        return tradesInTimeBuckets;
    }

    @Override
    public CandleStick generateCandleStickData(double candleStickTimestamp, List<Trade> tradeList) {
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
