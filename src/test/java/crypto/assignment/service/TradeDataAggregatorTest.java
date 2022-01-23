package crypto.assignment.service;

import crypto.assignment.dto.CandleStick;
import crypto.assignment.dto.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

class TradeDataAggregatorTest {

    private TradeDataAggregator tradeDataAggregatorUnderTest;

    @BeforeEach
    void setUp() {
        tradeDataAggregatorUnderTest = new TradeDataAggregator();
    }

    @Test
    void generateChartFromTrades_can_generate_chart_data_from_trades() {
        ArrayList<Trade> trades = new ArrayList<>();
        double firstTradeTimestamp = Double.parseDouble("50");
        double secondTradeTimestamp = Double.parseDouble("100");
        double thirdTradeTimestamp = Double.parseDouble("200");
        double fourthTradeTimestamp = Double.parseDouble("300");
        double fifthTradeTimestamp = Double.parseDouble("1000");
        double interval = 100;
        double chartStartTime = 0;
        double chartEndTime = 1500;

        Trade firstTrade = new Trade(1, "BUY", BigDecimal.valueOf(1.01), BigDecimal.valueOf(100), firstTradeTimestamp);
        Trade secondTrade = new Trade(2, "SELL", BigDecimal.valueOf(2.01), BigDecimal.valueOf(200), secondTradeTimestamp);
        Trade thirdTrade = new Trade(3, "SELL", BigDecimal.valueOf(4.01), BigDecimal.valueOf(300), thirdTradeTimestamp);
        Trade fourthTrade = new Trade(4, "BUY", BigDecimal.valueOf(5.01), BigDecimal.valueOf(500), fourthTradeTimestamp);
        Trade fifthTrade = new Trade(5, "BUY", BigDecimal.valueOf(6.01), BigDecimal.valueOf(500), fifthTradeTimestamp);

        trades.add(thirdTrade);
        trades.add(secondTrade);
        trades.add(firstTrade);
        trades.add(fourthTrade);
        trades.add(fifthTrade);

        HashMap<Double, ArrayList<Trade>> tradesGroupedByTime = tradeDataAggregatorUnderTest.groupByTimeBuckets(trades, chartStartTime, chartEndTime, interval);

        ArrayList<Trade> tradesInTimeBucketKey100 = new ArrayList<>();
        tradesInTimeBucketKey100.add(firstTrade);
        tradesInTimeBucketKey100.add(secondTrade);

        ArrayList<Trade> tradesInTimeBucketKey200 = new ArrayList<>();
        tradesInTimeBucketKey200.add(thirdTrade);

        ArrayList<Trade> tradesInTimeBucketKey300 = new ArrayList<>();
        tradesInTimeBucketKey300.add(fourthTrade);

        ArrayList<Trade> tradesInTimeBucketKey1000 = new ArrayList<>();
        tradesInTimeBucketKey1000.add(fifthTrade);


        assertEquals(16, tradesGroupedByTime.size());
        double[] expectedTradeBucketToBeEmpty = new double[]{0, 400, 500, 600, 700, 800, 900, 1100, 1200, 1300, 1400, 1500};
        for (double key : expectedTradeBucketToBeEmpty) {
            assertTrue(tradesGroupedByTime.get(key).isEmpty());
        }

        assertIterableEquals(tradesInTimeBucketKey100, tradesGroupedByTime.get(100.0));
        assertIterableEquals(tradesInTimeBucketKey200, tradesGroupedByTime.get(200.0));
        assertIterableEquals(tradesInTimeBucketKey300, tradesGroupedByTime.get(300.0));
        assertIterableEquals(tradesInTimeBucketKey1000, tradesGroupedByTime.get(1000.0));

    }

    @Test
    void generateCandleStickData_given_trades_can_create_candlestick_statistics() {
        ArrayList<Trade> trades = new ArrayList<>();

        Trade firstTrade = new Trade(1, "BUY", BigDecimal.valueOf(5.00), BigDecimal.valueOf(100), 1);
        Trade secondTrade = new Trade(2, "SELL", BigDecimal.valueOf(13.01), BigDecimal.valueOf(200), 2);
        Trade thirdTrade = new Trade(3, "SELL", BigDecimal.valueOf(14.01), BigDecimal.valueOf(300), 3);
        Trade fourthTrade = new Trade(4, "BUY", BigDecimal.valueOf(6.00), BigDecimal.valueOf(500), 4);

        trades.add(firstTrade);
        trades.add(secondTrade);
        trades.add(thirdTrade);
        trades.add(fourthTrade);

        CandleStick candleStickData = tradeDataAggregatorUnderTest.generateCandleStickData(Double.parseDouble("1000"), trades);
        assertEquals(BigDecimal.valueOf(1100), candleStickData.getVolume());
        assertEquals(BigDecimal.valueOf(5.00), candleStickData.getOpen());
        assertEquals(BigDecimal.valueOf(6.00), candleStickData.getClose());
        assertEquals(BigDecimal.valueOf(14.01), candleStickData.getHigh());
        assertEquals(BigDecimal.valueOf(5.00), candleStickData.getLow());
    }

    @Test
    void generateCandleStickData_handles_empty_trade_list() {
        ArrayList<Trade> trades = new ArrayList<>();

        CandleStick candleStickData = tradeDataAggregatorUnderTest.generateCandleStickData(Double.parseDouble("1000"), trades);
        assertEquals(BigDecimal.ZERO, candleStickData.getVolume());
        assertEquals(BigDecimal.ZERO, candleStickData.getOpen());
        assertEquals(BigDecimal.ZERO, candleStickData.getClose());
        assertEquals(BigDecimal.ZERO, candleStickData.getHigh());
        assertEquals(BigDecimal.ZERO, candleStickData.getLow());
    }
}