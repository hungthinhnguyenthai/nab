package com.thinhnguyen.nab.service.prepaiddata.controller;

import com.thinhnguyen.nab.service.prepaiddata.dto.VoucherDto;
import com.thinhnguyen.nab.service.prepaiddata.service.PrePaidDataService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrepaidDataController.class)
public class PrepaidDataControllerTests {

    @MockBean
    private PrePaidDataService dataService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void evaluates_retrieve_voucher_from_3rd() throws Exception {
        mockMvc.perform(post("/v1/data/{type}/purchase", "D10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    public void evaluates_retrieve_voucher_error_from_3rd() throws Exception {
        Mockito.when(dataService.payData(Mockito.anyString())).thenReturn(new VoucherDto());
        mockMvc.perform(post("/v1/data/{type}/purchase", "D10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk());
    }



    //Only use this test on local env
    @Test
    public void evaluates_retrieve_voucher_from_3rd_timeout() throws Exception {
        Mockito.when(dataService.payData(Mockito.anyString())).then((m) -> {
            try {
                Thread.sleep(31 * 1000);
            } catch (InterruptedException e) {

            }
            return new VoucherDto();
        });
        mockMvc.perform(post("/v1/data/{type}/purchase", "D10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().is(202));
    }
}
