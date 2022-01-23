package crypto.assignment.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CandleStickReconciliationResultTest {

    @Test
    void createNonVerifiableResult() {
        CandleStickReconciliationResult nonVerifiableResult =  CandleStickReconciliationResult.createNonVerifiableResult(10000);
        assertTrue(nonVerifiableResult.isCannotBeVerified());
        assertFalse(nonVerifiableResult.isHighPriceMatch());
        assertFalse(nonVerifiableResult.isLowPriceMatch());
        assertFalse(nonVerifiableResult.isClosePriceMatch());
        assertFalse(nonVerifiableResult.isOpenPriceMatch());
    }
}