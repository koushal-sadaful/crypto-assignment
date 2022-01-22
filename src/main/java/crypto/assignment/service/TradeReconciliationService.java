package crypto.assignment.service;

import crypto.assignment.dto.CandleStickChart;
import crypto.assignment.dto.Trade;
import crypto.assignment.transport.CryptoHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TradeReconciliationService implements ReconciliationService {

    @Autowired
    private CryptoHttpClient client;

    @Autowired
    private CandleStickChartGenerator chartGenerator;

    @Override
    public void process() {
        List<Trade> allTrades = client.getAllTrades();
        CandleStickChart chartFetched = client.getCandleStickChart();
        CandleStickChart chartGenerated = chartGenerator.generateChartFromTrades(allTrades, chartFetched.getIntervalInMillis());

    }

    @Override
    public void reconcile(CandleStickChart first, CandleStickChart second) {

    }




}
