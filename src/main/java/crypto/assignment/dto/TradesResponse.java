package crypto.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import crypto.assignment.models.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TradesResponse {
    private Iterable<Trade> trades;

    public TradesResponse(Iterable<Trade> trades) {
        this.trades = trades;
    }
}
