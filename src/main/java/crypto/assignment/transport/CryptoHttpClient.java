package crypto.assignment.transport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import crypto.assignment.models.CandleStickChart;
import crypto.assignment.models.Trade;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public List<Trade> getAllTrades() {
        log.info("getAllTrades - fetching trades");

        Request request = new Request.Builder()
                .url("http://api.crypto.com/v2/public/get-trades")
                .build();

        String responseAsString = "";

        try (Response response = client.newCall(request).execute()) {

            responseAsString = response.body().string();
            JsonNode jsonObject = objectMapper.readTree(responseAsString).get("result").get("data");

            Trade[] trades = objectMapper.readValue(jsonObject.toString(), Trade[].class);
            List<Trade> tradeList = new ArrayList(Arrays.asList(trades));
            log.info("getAllTrades - fetching trades");

            return tradeList;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("getAllTrades - failed to fetch trades");
            log.error(e.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public CandleStickChart getCandleStickChart() {
        final String uri = "http://api.crypto.com/v2/public/get-candlestick?instrument_name=BTC_USDT&timeframe=5m";

        Request request = new Request.Builder()
                .url(uri)
                .build();

        String responseAsString = "";

        try (Response response = client.newCall(request).execute()) {
            responseAsString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(responseAsString);
        return new CandleStickChart();
    }
}
