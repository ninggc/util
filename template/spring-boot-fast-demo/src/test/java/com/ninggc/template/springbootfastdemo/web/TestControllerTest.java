package com.ninggc.template.springbootfastdemo.web;

import com.ninggc.template.springbootfastdemo.AbstractBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

@PropertySource("classpath:morphia.properties")
public class TestControllerTest extends AbstractControllerTest {
    @Test
    public void test() throws Exception {
        String url = "/test";
        mockMvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void again() throws Exception {
        String url = "/test/again";
        mockMvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void sleep() throws Exception {
        String url = "/test/sleep";
        mockMvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}