package crypto.assignment.utils;

import crypto.assignment.dto.CandleStick;
import crypto.assignment.dto.CandleStickReconciliationResult;

import java.math.BigDecimal;

public class CandleStickReconciliation {

    private static final BigDecimal toleranceValue = BigDecimal.valueOf(0.000000001);

    public static CandleStickReconciliationResult reconcile(CandleStick actual, CandleStick computer) {

        if (computer.getEndTime() != actual.getEndTime()) {
            return CandleStickReconciliationResult.createNonVerifiableResult(actual.getEndTime());
        }

        boolean highPriceMatch = CandleStickReconciliation.isWithinTolerance(actual.getHigh(), computer.getHigh(), toleranceValue);
        boolean lowPriceMatch = CandleStickReconciliation.isWithinTolerance(actual.getLow(), computer.getLow(), toleranceValue);
        boolean openPriceMatch = CandleStickReconciliation.isWithinTolerance(actual.getOpen(), computer.getOpen(), toleranceValue);
        boolean closePriceMatch = CandleStickReconciliation.isWithinTolerance(actual.getClose(), computer.getClose(), toleranceValue);

        return new CandleStickReconciliationResult(actual.getEndTime(), highPriceMatch, lowPriceMatch, openPriceMatch, closePriceMatch);
    }


    private static boolean isWithinTolerance(BigDecimal first, BigDecimal second, BigDecimal tolerance) {
        BigDecimal difference = first.subtract(second);
        return (difference.compareTo(tolerance.negate()) > 0 && difference.compareTo(tolerance) < 0)
                || difference.abs().compareTo(tolerance) == 0;
    }
}
