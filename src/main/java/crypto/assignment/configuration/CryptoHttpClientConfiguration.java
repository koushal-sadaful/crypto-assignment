package crypto.assignment.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cryptoHttpClient")
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

