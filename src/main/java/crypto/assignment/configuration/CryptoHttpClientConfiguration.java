package crypto.assignment.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "crypto-http-client")
public class CryptoHttpClientConfiguration {
    private String tradesUrl;
    private String candleStickChartUrl;

    public String getCandleStickChartUrl() {
        return candleStickChartUrl;
    }

    public String getAllTradesUrl() {
        return tradesUrl;
    }

}

