package crypto.assignment.service;

import crypto.assignment.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReconciliationEngineTest {

    @InjectMocks
    ReconciliationEngine reconciliationEngine;

    @Mock
    TradeDataAggregator aggregator;

    @Test
    void reconcile_given_an_empty_trade_list() {

        ArrayList<CandleStick> sticks = new ArrayList<>();
        sticks.add(new CandleStick(10, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE));
        sticks.add(new CandleStick(10, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN));

        CandleStickChart chart = new CandleStickChart("BTC_USD", sticks, 10);

        HashMap<Double, ArrayList<Trade>> emptyTradeBuckets = new HashMap<>();
        CandleStickChartReconciliationResult result = reconciliationEngine.reconcile(chart, emptyTradeBuckets);

        assertEquals("BTC_USD", result.getInstrumentName());
        assertEquals(2, result.getReconciliationResults().size());
        for (CandleStickReconciliationResult resultItem : result.getReconciliationResults()) {
            assertTrue(resultItem.isCannotBeVerified());
        }
    }

    @Test
    void reconcile_given_an_empty_candle_stick_chart() {

        ArrayList<CandleStick> sticks = new ArrayList<>();
        CandleStickChart chart = new CandleStickChart("BTC_USD", sticks, 10);

        HashMap<Double, ArrayList<Trade>> tradeBuckets = new HashMap<>();
        ArrayList<Trade> trades = new ArrayList<>();
        trades.add(new Trade(1, "BUY", BigDecimal.ONE, BigDecimal.ONE, 1));
        trades.add(new Trade(2, "BUY", BigDecimal.ONE, BigDecimal.ONE, 2));
        tradeBuckets.put(10.0, trades);

        CandleStickChartReconciliationResult result = reconciliationEngine.reconcile(chart, tradeBuckets);

        assertEquals("BTC_USD", result.getInstrumentName());
        assertEquals("No candle stick data to verify", result.getSummaryMessage());
    }

    @Test
    void reconcile_given_candlestick_data_and_trades_returns_reconciliation_results() {
        ArrayList<CandleStick> sticks = new ArrayList<>();
        sticks.add(new CandleStick(10, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE));
        sticks.add(new CandleStick(20, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN));

        HashMap<Double, ArrayList<Trade>> tradeBucket = new HashMap<>();
        ArrayList<Trade> tradeListOne = new ArrayList<>();
        tradeListOne.add(new Trade(1, "BUY", BigDecimal.ONE, BigDecimal.ONE, 1));
        tradeListOne.add(new Trade(2, "BUY", BigDecimal.ONE, BigDecimal.ONE, 2));
        tradeBucket.put(10.0, tradeListOne);

        ArrayList<Trade> tradeListTwo = new ArrayList<>();
        tradeListTwo.add(new Trade(3, "BUY", BigDecimal.TEN, BigDecimal.TEN, 11));
        tradeListTwo.add(new Trade(4, "BUY", BigDecimal.TEN, BigDecimal.TEN, 12));
        tradeBucket.put(20.0, tradeListTwo);


        CandleStickChart chart = new CandleStickChart("BTC_USD", sticks, 10);

        when(aggregator.generateCandleStickData(10, tradeListOne))
                .thenReturn(new CandleStick(10, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE));
        when(aggregator.generateCandleStickData(20, tradeListTwo))
                .thenReturn(new CandleStick(20, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE));

        CandleStickChartReconciliationResult result = reconciliationEngine.reconcile(chart, tradeBucket);

        CandleStickReconciliationResult expectedFirstResult = new CandleStickReconciliationResult(10, true, true, true, true);
        CandleStickReconciliationResult expectedSecondResult = new CandleStickReconciliationResult(20, false, false, false, false);
        ArrayList<CandleStickReconciliationResult> expectedResults = new ArrayList<>();
        expectedResults.add(expectedFirstResult);
        expectedResults.add(expectedSecondResult);


        assertEquals("BTC_USD", result.getInstrumentName());
        assertNull(result.getSummaryMessage());
        assertIterableEquals(expectedResults, result.getReconciliationResults());

    }
}