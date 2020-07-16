package com.thinhnguyen.nab.service.prepaiddata.controller;

import com.thinhnguyen.nab.service.prepaiddata.dto.VoucherDto;
import com.thinhnguyen.nab.service.prepaiddata.rest.TelecomRestClient;
import com.thinhnguyen.nab.service.prepaiddata.service.PrePaidDataService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import retrofit2.Call;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrepaidDataController.class)
public class PrepaidDataControllerTests {

    @MockBean
    private PrePaidDataService dataService;

    @MockBean
    TelecomRestClient telecomRestClient;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    Call callMock;

    @Test
    public void evaluates_retrieve_voucher_3rd_service_down() throws Exception {
        Mockito.when(telecomRestClient.purchaseData(Mockito.anyString(), Mockito.any())).thenReturn(callMock);
        Mockito.when(callMock.execute()).thenThrow(new IOException("Service is down"));

        mockMvc.perform(post("/v1/data/{type}/purchase", "D10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    public void evaluates_retrieve_voucher_from_3rd_error() throws Exception {
        mockMvc.perform(post("/v1/data/{type}/purchase", "D10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    public void evaluates_retrieve_voucher_from_3rd_wrong_package_error() throws Exception {
        mockMvc.perform(post("/v1/data/{type}/purchase", "DA0")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    public void evaluates_retrieve_voucher_from_3rd() throws Exception {
        Mockito.when(dataService.payData(Mockito.anyString())).thenReturn(new VoucherDto());
        mockMvc.perform(post("/v1/data/{type}/purchase", "D10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk());
    }

    //Only use this test on local env
//    @Test
    public void evaluates_retrieve_voucher_from_3rd_timeout() throws Exception {
        VoucherDto dto = new VoucherDto();
        Mockito.when(dataService.payData(Mockito.anyString())).then((m) -> {
            try {
                //Wait for 30 seconds
                Thread.sleep(35 * 1000);
            } catch (InterruptedException e) {

            }
            return dto;
        });
        mockMvc.perform(post("/v1/data/{type}/purchase", "D10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().is(202));
//        Mockito.verify(dataService, Mockito.times(1)).updateVoucher(Mockito.any());
    }
}
