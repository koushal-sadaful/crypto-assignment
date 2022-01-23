package crypto.assignment.utils;

import crypto.assignment.dto.CandleStick;
import crypto.assignment.dto.CandleStickChartReconciliationResult;
import crypto.assignment.dto.CandleStickReconciliationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CandleStickReconciliationTest {

    @Test
    void reconcile_can_compare_two_candle_stick_match_price_within_tolerance() {
        CandleStick first = new CandleStick(10000,
                BigDecimal.valueOf(99.00000018131),
                BigDecimal.valueOf(95.00000012313),
                BigDecimal.valueOf(98.01123412121),
                BigDecimal.valueOf(99.00000018131),
                BigDecimal.valueOf(100)
        );

        CandleStick second = new CandleStick(10000,
                BigDecimal.valueOf(99.00000018121),
                BigDecimal.valueOf(95.00000012313),
                BigDecimal.valueOf(98.01123412123),
                BigDecimal.valueOf(99.00000118131),
                BigDecimal.valueOf(100)
        );

        CandleStickReconciliationResult result = CandleStickReconciliation.reconcile(first, second);

        assertTrue(result.isHighPriceMatch());
        assertTrue(result.isLowPriceMatch());
        assertTrue(result.isOpenPriceMatch());
        assertFalse(result.isClosePriceMatch());
    }

    @Test
    void reconcile_returns_empty_data_point_if_invalid_comparison() {
        CandleStick first = new CandleStick(10000,
                BigDecimal.valueOf(99.00000018131),
                BigDecimal.valueOf(95.00000012313),
                BigDecimal.valueOf(98.01123412121),
                BigDecimal.valueOf(99.00000018131),
                BigDecimal.valueOf(100)
        );

        CandleStick second = new CandleStick(0,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.valueOf(99.00000118131),
                BigDecimal.valueOf(100)
        );

        CandleStickReconciliationResult result = CandleStickReconciliation.reconcile(first, second);

        assertTrue(result.isCannotBeVerified());
        assertFalse(result.isHighPriceMatch());
        assertFalse(result.isLowPriceMatch());
        assertFalse(result.isOpenPriceMatch());
        assertFalse(result.isClosePriceMatch());
    }

}