package crypto.assignment.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReconciliationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void reconcileRequest_rejects_invalid_interval_parameter() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/reconcile?instrument_name=BTC_USDT&interval=10m").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Invalid time interval")));
    }

    @Test
    public void reconcileRequest_processes_reconciliation_and_shows_result() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/reconcile?instrument_name=ETH_CRO&interval=5m").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Invalid time interval")));
    }

}