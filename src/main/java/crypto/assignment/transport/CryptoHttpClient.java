package crypto.assignment.transport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import crypto.assignment.configuration.CryptoHttpClientConfiguration;
import crypto.assignment.dto.CandleStickChart;
import crypto.assignment.dto.Trade;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CryptoHttpClient implements DataTransport {

    private static final Logger log = LoggerFactory.getLogger(CryptoHttpClient.class);
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CryptoHttpClientConfiguration cryptoHttpClientConfiguration;

    public List<Trade> getAllTrades(String instrumentName) {
        final String url = "http://api.crypto.com/v2/public/get-trades?instrument_name=" + instrumentName;
        log.info(String.format("Fetch trades data from url=%s.", url));

        Request request = new Request.Builder()
                .url(url)
                .build();

        String responseAsString;
        try (Response response = client.newCall(request).execute()) {
            responseAsString = response.body().string();
            JsonNode jsonObject = objectMapper.readTree(responseAsString).get("result").get("data");
            Trade[] trades = objectMapper.readValue(jsonObject.toString(), Trade[].class);
            List<Trade> tradeList = new ArrayList(Arrays.asList(trades));
            log.info(String.format("Fetched trades completed, TradesCount=%s.", tradeList.size()));
            return tradeList;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Failed to fetch trades");
            log.error(e.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public CandleStickChart getCandleStickChart(String instrumentName, String timeframe) {
        final String url = "http://api.crypto.com/v2/public/get-candlestick?instrument_name=" + instrumentName + "&timeframe=" + timeframe;
        log.info(String.format("Fetch candlestick data from url=%s.", url));

        Request request = new Request.Builder()
                .url(url)
                .build();

        String responseAsString;
        try (Response response = client.newCall(request).execute()) {
            responseAsString = response.body().string();
            JsonNode jsonObject = objectMapper.readTree(responseAsString).get("result");
            CandleStickChart chartData = objectMapper.readValue(jsonObject.toString(), CandleStickChart.class);
            log.info("Fetched candlestick data completed");
            return chartData;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to fetch candlestick data");
            log.error(e.getLocalizedMessage());
        }

        return new CandleStickChart();
    }

}
