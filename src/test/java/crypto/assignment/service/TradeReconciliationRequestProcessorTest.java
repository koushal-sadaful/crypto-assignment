package crypto.assignment.service;

import crypto.assignment.dto.CandleStick;
import crypto.assignment.dto.CandleStickChart;
import crypto.assignment.dto.Trade;
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

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TradeReconciliationRequestProcessorTest {

    @Mock
    private CryptoHttpClient mockClient;

    @Mock
    private TradeDataAggregator mockAggregator;

    @Mock
    private ReconciliationEngine mockReconciliationEngine;

    @InjectMocks
    private TradeReconciliationRequestProcessor processorUnderTest = new TradeReconciliationRequestProcessor();

    @BeforeEach
    void setUp() {
        when(mockClient.getAllTrades("")).thenReturn(new ArrayList<Trade>());
        when(mockClient.getCandleStickChart("", "")).thenReturn(new CandleStickChart());
        when(mockAggregator.groupByTimeBuckets(any(), any(),  any(), any())).thenReturn(new HashMap<>());
        when(mockAggregator.generateCandleStickData(any(), any())).thenReturn(new CandleStick());
    }

    @Test
    void processRequest() {
        processorUnderTest.processRequest("", "5m");

    }
}