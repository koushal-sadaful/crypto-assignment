package crypto.assignment.controllers;

import crypto.assignment.service.TradeReconciliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	private int requestCount = 0;

	@Autowired
	private TradeReconciliationService tradeReconciliationService;

	@GetMapping("/")
	public String index() {
		tradeReconciliationService.process();
		requestCount++;
		return "Greetings from Spring Boot!, RequestCount="+requestCount  ;
	}

}
