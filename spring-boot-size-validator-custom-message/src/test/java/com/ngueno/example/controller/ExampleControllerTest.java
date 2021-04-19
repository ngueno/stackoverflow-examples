package com.ngueno.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ExampleControllerTest {

    @Test
    void validateExampleHavingLessCharsThanMin() throws Exception {
        ExampleRequest request = new ExampleRequest();
        request.setExample("12");

        MethodArgumentNotValidException exception = (MethodArgumentNotValidException) performExampleRequest(request)
                .andExpect(status().is(400)).andReturn().getResolvedException();
        assertEquals("The current message is an example, it must have between 3 and 10 characters",
                exception.getFieldError().getDefaultMessage());
    }

    @Test
    void validateExampleHavingMoreCharsThanMax() throws Exception {
        ExampleRequest request = new ExampleRequest();
        request.setExample("01234567891011");

        MethodArgumentNotValidException exception = (MethodArgumentNotValidException) performExampleRequest(request)
                .andExpect(status().is(400)).andReturn().getResolvedException();
        assertEquals("The current message is an example, it must have between 3 and 10 characters",
                exception.getFieldError().getDefaultMessage());
    }

    @Test
    void validateExampleHavingValidChars() throws Exception {
        ExampleRequest request = new ExampleRequest();
        request.setExample("0123456");

        performExampleRequest(request).andExpect(status().isOk());
    }

    private ResultActions performExampleRequest(ExampleRequest request) throws Exception {
        return mockMvc.perform(post("/example").contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
                .andDo(print());
    }

    private String toJson(ExampleRequest request) {
        try {
            return mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            fail("Failed to convert to json", e);
        }
        return null;
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
}
