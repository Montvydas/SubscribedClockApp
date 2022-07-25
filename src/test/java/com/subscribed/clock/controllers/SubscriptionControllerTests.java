package com.subscribed.clock.controllers;

import com.subscribed.clock.exceptions.DuplicateEntryException;
import com.subscribed.clock.exceptions.InvalidUrlException;
import com.subscribed.clock.services.SubscriptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubscriptionController.class)
public class SubscriptionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriptionService subscriptionService;

    @Test
    public void whenRegisterUrl_correctData_success() throws Exception {
        doNothing().when(subscriptionService).register("localhost", 5);

        this.mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\":\"localhost\",\"interval\":5}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void whenRegisterUrl_invalidUrl_returnsBadRequest() throws Exception {
        doThrow(InvalidUrlException.class).when(subscriptionService).register("localhost", 5);

        this.mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\":\"localhost\",\"interval\":5}"))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void whenRegisterUrl_duplicateEntry_returnsConflict() throws Exception {
        doThrow(DuplicateEntryException.class).when(subscriptionService).register("localhost", 5);

        this.mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\":\"localhost\",\"interval\":5}"))
                .andDo(print())
                .andExpect(status().is(409));
    }
}
