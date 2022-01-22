package crypto.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Trade implements Comparable<Trade>  {

    public Trade() {
    }

    @JsonProperty("d")
    private double id;

    @JsonProperty("s")
    private String side;

    @JsonProperty("p")
    private BigDecimal price;

    public BigDecimal getQuantity() {
        return quantity;
    }

    @JsonProperty("q")
    private BigDecimal quantity;

    @JsonProperty("t")
    private double timestamp;

    public Trade(double id, String side, BigDecimal price, BigDecimal quantity, double timestamp) {
        this.id = id;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    public double getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(Trade o) {
       return Double.compare(this.getTimestamp(), o.getTimestamp());
    }

    public BigDecimal getPrice() {
        return price;
    }
}
