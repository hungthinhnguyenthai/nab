package com.thinhnguyen.provider.telecom.controller;

import com.thinhnguyen.provider.telecom.service.PrepaidDataService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrepaidDataController.class)
public class PrepaidDataControllerTest {

    @MockBean
    private PrepaidDataService dataService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void evaluates_retrieve_voucher() throws Exception {
        mockMvc.perform(post("/v1/data/{type}/purchase", "D10")
                .header("Authorization", "Bearer MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCS.A36dSwlaIkrWNntIlMQT58gXnjiPELX6SpYREFoOdwM")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{}")
                )
                .andExpect(status().isOk());
    }


    @Test
    public void evaluates_retrieve_voucher_bad_request() throws Exception {
        mockMvc.perform(post("/v1/data/{type}/purchase", "A10")
                .header("Authorization", "Bearer MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCS.A36dSwlaIkrWNntIlMQT58gXnjiPELX6SpYREFoOdwM")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{}")
        )
                .andExpect(status().isBadRequest());
    }


    @Test
    public void evaluates_retrieve_voucher_unauthorized() throws Exception {
        mockMvc.perform(post("/v1/data/{type}/purchase", "D10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{}")
        )
                .andExpect(status().isUnauthorized());
    }
}
