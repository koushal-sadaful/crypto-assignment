package crypto.assignment.service;

import crypto.assignment.dto.CandleStick;
import crypto.assignment.dto.Trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface DataAggregator {
    HashMap<Double, ArrayList<Trade>> groupByTimeBuckets(List<Trade> tradeList, double startTimeInMillis, double intervalInMillis);
    CandleStick generateCandleStickData(double candleStickTimestamp, List<Trade> tradeList);
}
