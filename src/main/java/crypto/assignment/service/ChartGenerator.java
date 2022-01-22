package crypto.assignment.service;

import crypto.assignment.dto.CandleStickChart;
import crypto.assignment.dto.Trade;

import java.util.List;

public interface ChartGenerator {
    CandleStickChart generateChartFromTrades(List<Trade> tradeList);
}
