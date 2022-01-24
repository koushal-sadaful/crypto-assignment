package crypto.assignment.service;

import crypto.assignment.dto.*;
import crypto.assignment.transport.CryptoHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TradeReconciliationRequestProcessorTest {

    @Mock
    private CryptoHttpClient mockClient;

    @Mock
    private TradeDataAggregator mockAggregator;

    @Mock
    private ReconciliationEngine mockReconciliationEngine;

    @InjectMocks
    private TradeReconciliationRequestProcessor processorUnderTest;


    @Test
    void processRequest_given_no_chart_data_returns_result() {
        ArrayList<Trade> tradeList = new ArrayList<>();
        tradeList.add(new Trade(1, "BUY", BigDecimal.ONE, BigDecimal.ONE, 1));
        tradeList.add(new Trade(2, "BUY", BigDecimal.ONE, BigDecimal.ONE, 2));

        when(mockClient.getAllTrades("ETH_CRO")).thenReturn(tradeList);
//        when(mockAggregator.groupByTimeBuckets(any(), any(), any(), any())).thenReturn(new HashMap<>());
//        when(mockAggregator.generateCandleStickData(any(), any())).thenReturn(new CandleStick());

        when(mockClient.getCandleStickChart("ETH_CRO", "5m")).thenReturn(new CandleStickChart());

        CandleStickChartReconciliationResult result = processorUnderTest.processRequest("ETH_CRO", "5m");
        assertEquals("No chart data to reconcile", result.getSummaryMessage());
        assertEquals(null, result.getReconciliationResults());


    }

    @Test
    void processRequest_given_chart_and_trades_returns_result() {
        //Create the trades
        ArrayList<Trade> tradeList = new ArrayList<>();
        tradeList.add(new Trade(1, "BUY", BigDecimal.ONE, BigDecimal.ONE, 1));
        tradeList.add(new Trade(2, "BUY", BigDecimal.ONE, BigDecimal.ONE, 2));
        when(mockClient.getAllTrades("ETH_CRO")).thenReturn(tradeList);

        //create the candlestick chart
        ArrayList<CandleStick> sticks = new ArrayList<>();
        sticks.add(new CandleStick(10, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE));
        sticks.add(new CandleStick(20, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN));
        CandleStickChart chart = new CandleStickChart("ETH_CRO", sticks, 10);
        when(mockClient.getCandleStickChart("ETH_CRO", "5m")).thenReturn(chart);

        //create the result the trade aggregator will provide
        HashMap<Double, ArrayList<Trade>> tradesGroupedByTimeBucket = new HashMap<>();
        ArrayList<Trade> tradeListOne = new ArrayList<>();
        tradeListOne.add(new Trade(1, "BUY", BigDecimal.ONE, BigDecimal.ONE, 1));
        tradeListOne.add(new Trade(2, "BUY", BigDecimal.ONE, BigDecimal.ONE, 2));
        tradesGroupedByTimeBucket.put(10.0, tradeListOne);

        ArrayList<Trade> tradeListTwo = new ArrayList<>();
        tradeListTwo.add(new Trade(3, "BUY", BigDecimal.TEN, BigDecimal.TEN, 11));
        tradeListTwo.add(new Trade(4, "BUY", BigDecimal.TEN, BigDecimal.TEN, 12));
        tradesGroupedByTimeBucket.put(20.0, tradeListTwo);

        when(mockAggregator.groupByTimeBuckets(any(), anyDouble(), anyDouble(), anyDouble())).thenReturn(tradesGroupedByTimeBucket);

        //Provide results of reconciliation engine
        CandleStickReconciliationResult firstResult = CandleStickReconciliationResult.createNonVerifiableResult(10);
        CandleStickReconciliationResult secondResult = CandleStickReconciliationResult.createNonVerifiableResult(10);
        ArrayList<CandleStickReconciliationResult> results = new ArrayList<>();
        results.add(firstResult);
        results.add(secondResult);

        when(mockReconciliationEngine.reconcile(chart, tradesGroupedByTimeBucket)).thenReturn(
                new CandleStickChartReconciliationResult("ETH_CRO", results)
        );

        CandleStickChartReconciliationResult result = processorUnderTest.processRequest("ETH_CRO", "5m");
        assertNull(result.getSummaryMessage());
        assertEquals(2, result.getReconciliationResults().size());
        assertEquals(firstResult, result.getReconciliationResults().get(0));
        assertEquals(secondResult, result.getReconciliationResults().get(1));
    }
}