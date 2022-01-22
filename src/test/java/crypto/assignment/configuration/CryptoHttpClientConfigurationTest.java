package crypto.assignment.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { CryptoHttpClientConfiguration.class })
class CryptoHttpClientConfigurationTest {
    
    @Autowired
    private CryptoHttpClientConfiguration cryptoHttpClientConfiguration;

    @Test
    public void whenFactoryProvidedThenYamlPropertiesInjected() {
        assertEquals(cryptoHttpClientConfiguration.getCandleStickChartUrl(), "");
        assertEquals(cryptoHttpClientConfiguration.getAllTradesUrl(), "");

    }
}