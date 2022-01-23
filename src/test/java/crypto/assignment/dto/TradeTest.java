package crypto.assignment.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TradeTest {

    @Test
    void testEquals_should_return_true_if_all_attributes_match() {
        Trade firstTrade = new Trade(100, "SELL", BigDecimal.valueOf(10.000001), BigDecimal.valueOf(90.00000001), 1000);
        Trade secondTrade = new Trade(100, "SELL", BigDecimal.valueOf(10.000001), BigDecimal.valueOf(90.00000001), 1000);

        assertEquals(secondTrade, firstTrade);
    }

    @Test
    void testEquals_should_return_false_if_price_mismatch() {
        Trade firstTrade = new Trade(100, "SELL", BigDecimal.valueOf(10.000001), BigDecimal.valueOf(90.00000001), 1000);
        Trade secondTrade = new Trade(100, "SELL", BigDecimal.valueOf(10.0000011), BigDecimal.valueOf(90.00000001), 1000);

        assertNotEquals(secondTrade, firstTrade);
    }

    @Test
    void testEquals_should_return_false_if_side_mismatch() {
        Trade firstTrade = new Trade(100, "SELL", BigDecimal.valueOf(10.000001), BigDecimal.valueOf(90.00000001), 1000);
        Trade secondTrade = new Trade(100, "BUY", BigDecimal.valueOf(10.000001), BigDecimal.valueOf(90.00000001), 1000);

        assertNotEquals(secondTrade, firstTrade);
    }

    @Test
    void testEquals_should_return_false_if_quantity_mismatch() {
        Trade firstTrade = new Trade(100, "SELL", BigDecimal.valueOf(10.000001), BigDecimal.valueOf(90.00000001), 1000);
        Trade secondTrade = new Trade(100, "SELL", BigDecimal.valueOf(10.000001), BigDecimal.valueOf(90.000000011), 1000);

        assertNotEquals(secondTrade, firstTrade);
    }

    @Test
    void testEquals_should_return_false_if_timestamp_mismatch() {
        Trade firstTrade = new Trade(100, "SELL", BigDecimal.valueOf(10.000001), BigDecimal.valueOf(90.00000001), 1000);
        Trade secondTrade = new Trade(100, "SELL", BigDecimal.valueOf(10.000001), BigDecimal.valueOf(90.00000001), 1001);

        assertNotEquals(secondTrade, firstTrade);
    }
}