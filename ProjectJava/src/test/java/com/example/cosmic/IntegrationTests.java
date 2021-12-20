package com.example.cosmic;

import com.example.cosmic.config.JwtProvider;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class IntegrationTests {
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    JwtProvider jwtTokenProvider;
    private MockMvc mockMvc;

    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
     public void testUnauthorizedJWTAccess() throws Exception {
        setUp();
        String token = "";
        mockMvc.perform(MockMvcRequestBuilders.get("/signin"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/admin").header("Authorization", "Bearer " + token))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testAuthorizedJWTAccess() throws Exception {
        setUp();
        String token = "VALID TOKEN WITH ADMIN ACCESS";

        mockMvc.perform(MockMvcRequestBuilders.get("/admin").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/admin").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
