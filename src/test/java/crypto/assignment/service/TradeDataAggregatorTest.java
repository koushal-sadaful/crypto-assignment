package crypto.assignment.service;

import crypto.assignment.dto.CandleStick;
import crypto.assignment.dto.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

class TradeDataAggregatorTest {

    private TradeDataAggregator tradeDataAggregatorUnderTest;

    @BeforeEach
    void setUp() {
        tradeDataAggregatorUnderTest = new TradeDataAggregator();
    }

    @Test
    void generateChartFromTrades_can_generate_chart_data_from_trades() {
        ArrayList<Trade> trades = new ArrayList<>();
        double firstTradeTimestamp = Double.parseDouble("1642869300000");
        double secondTradeTimestamp = Double.parseDouble("1642869600000");
        double thirdTradeTimestamp = Double.parseDouble("1642869900000");
        double fourthTradeTimestamp = Double.parseDouble("1642869900100");
        double interval = Double.parseDouble("300000");

        Trade firstTrade = new Trade(1, "BUY", BigDecimal.valueOf(1.01), BigDecimal.valueOf(100), firstTradeTimestamp);
        Trade secondTrade = new Trade(2, "SELL", BigDecimal.valueOf(2.01), BigDecimal.valueOf(200), secondTradeTimestamp);
        Trade thirdTrade = new Trade(3, "SELL", BigDecimal.valueOf(4.01), BigDecimal.valueOf(300), thirdTradeTimestamp);
        Trade fourthTrade = new Trade(4, "BUY", BigDecimal.valueOf(5.01), BigDecimal.valueOf(500), fourthTradeTimestamp);

        trades.add(thirdTrade);
        trades.add(secondTrade);
        trades.add(firstTrade);
        trades.add(fourthTrade);

        HashMap<Double, ArrayList<Trade>> tradeGroup = tradeDataAggregatorUnderTest.groupByTimeBuckets(trades, firstTradeTimestamp, interval);

        ArrayList<Trade> firstBucket = new ArrayList<>();
        firstBucket.add(firstTrade);

        ArrayList<Trade> secondBucket = new ArrayList<>();
        secondBucket.add(secondTrade);

        ArrayList<Trade> thirdBucket = new ArrayList<>();
        thirdBucket.add(thirdTrade);
        thirdBucket.add(fourthTrade);


        assertEquals(3, tradeGroup.size());
        assertIterableEquals(firstBucket, tradeGroup.get(firstTradeTimestamp + interval));
        assertIterableEquals(secondBucket, tradeGroup.get(secondTradeTimestamp + interval));
        assertIterableEquals(thirdBucket, tradeGroup.get(thirdTradeTimestamp + interval));

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
}