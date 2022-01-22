package crypto.assignment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import crypto.assignment.transport.CryptoHttpClient;

@RestController
public class MainController {

	private int requestCount = 0;

	@Autowired
	private CryptoHttpClient client;

	@GetMapping("/")
	public String index() {
		client.getAllTrades();
		client.getCandleStickChart();
		requestCount++;
		return "Greetings from Spring Boot!, RequestCount="+requestCount  ;
	}

}
