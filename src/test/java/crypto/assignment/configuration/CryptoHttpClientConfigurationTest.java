package crypto.assignment.configuration;

import crypto.assignment.controllers.CryptoApplication;
import crypto.assignment.service.ReconciliationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { CryptoApplication.class })
class CryptoHttpClientConfigurationTest {
    
    @Autowired
    private CryptoHttpClientConfiguration cryptoHttpClientConfiguration;

    @Test
    public void whenFactoryProvidedThenYamlPropertiesInjected() {
        assertEquals(cryptoHttpClientConfiguration.getCandleStickChartUrl(), "");
        assertEquals(cryptoHttpClientConfiguration.getAllTradesUrl(), "");

    }
}