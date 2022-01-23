package crypto.assignment.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CandleStickChartTest {
    private CandleStickChart candleStickChartUnderTest;

    @BeforeEach
    void setUp() {
        candleStickChartUnderTest = new CandleStickChart();
    }


    @Test
    void setIntervalInMillis_can_set_valid_interval() {
        candleStickChartUnderTest.setIntervalInMillis("5m");

        assertEquals(300000.0, candleStickChartUnderTest.getIntervalInMillis());
    }

    @Test
    void setIntervalInMillis_throws_error_if_invalid_interval() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> candleStickChartUnderTest.setIntervalInMillis("5X"),
                "Expected setIntervalInMillis() to throw an exception for invalid interval"
        );

        assertTrue(thrown.getMessage().contains("Invalid Interval provided"));
    }

    @Test
    void getStartTimeInMillis_returns_correct_start_time_if_candleSticks_nonempty() {
        ArrayList<CandleStick> candleSticks = new ArrayList<>();
        candleSticks.add(new CandleStick(80000, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.TEN));
        candleSticks.add(new CandleStick(90000, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.TEN));

        candleStickChartUnderTest = new CandleStickChart("BTC_USD", candleSticks, 1);
        assertEquals(80000, candleStickChartUnderTest.getEndTimeOfFirstCandle());
    }

    @Test
    void getStartTimeInMillis_returns_zero_if_candleSticks_is_empty() {
        ArrayList<CandleStick> candleSticks = new ArrayList<>();

        candleStickChartUnderTest = new CandleStickChart("BTC_USD", candleSticks, 1);
        assertEquals(0, candleStickChartUnderTest.getEndTimeOfFirstCandle());
    }
}