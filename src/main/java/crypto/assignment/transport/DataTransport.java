package crypto.assignment.transport;
import crypto.assignment.dto.CandleStickChart;
import crypto.assignment.dto.Trade;

import java.util.List;

public interface DataTransport {
    List<Trade> getAllTrades(String instrumentName);
    CandleStickChart getCandleStickChart(String instrumentName, String timeframe);
}
