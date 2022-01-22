package crypto.assignment.transport;
import crypto.assignment.models.CandleStickChart;
import crypto.assignment.models.Trade;

import java.util.List;

public interface DataTransport {
    List<Trade> getAllTrades();
    CandleStickChart getCandleStickChart();
}
