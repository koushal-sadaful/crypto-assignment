package crypto.assignment.controllers;

import crypto.assignment.dto.CandleStickChartReconciliationResult;
import crypto.assignment.service.TradeReconciliationRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReconciliationController {

    private static final Logger log = LoggerFactory.getLogger(ReconciliationController.class);

    @Autowired
    private TradeReconciliationRequestProcessor tradeReconciliationRequestProcessor;

    @GetMapping("/reconcile")
    public @ResponseBody
    CandleStickChartReconciliationResult reconcileTrades(@RequestParam("instrument_name") String instrumentName,
                                                         @RequestParam("interval") String timeInterval) {

        log.info("Reconciliation request received for instrument=" + instrumentName + ", timeInterval=" + timeInterval);
        log.info(String.format("Reconciliation request received for instrument, instrument=%s, timeInterval=%s", instrumentName, timeInterval));
        return tradeReconciliationRequestProcessor.processRequest(instrumentName, timeInterval);
    }

}
