package crypto.assignment.service;

import crypto.assignment.dto.CandleStickChart;
import crypto.assignment.dto.Trade;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CandleStickChartGenerator implements ChartGenerator {
    @Override
    public CandleStickChart generateChartFromTrades(List<Trade> tradeList, double intervalInMillis) {
        return null;
    }
}
