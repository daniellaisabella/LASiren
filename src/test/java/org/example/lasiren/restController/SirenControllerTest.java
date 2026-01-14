package org.example.lasiren.restController;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.lasiren.dto.CreateSirenDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase
class SirenControllerTest {
    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createSiren_shouldReturn201() throws Exception {
        CreateSirenDTO dto = new CreateSirenDTO(55.0, 12.0, false);

        mockMvc.perform(post("/api/sirens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.latitude").value(55.0))
                .andExpect(jsonPath("$.disabled").value(false));
    }

    @Test
    void getAllSirens_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/sirens"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSiren_shouldReturn204() throws Exception {
        // 1. Create siren
        CreateSirenDTO dto = new CreateSirenDTO(55.0, 12.0, false);

        String response = mockMvc.perform(post("/api/sirens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // 2. Extract ID
        long id = objectMapper.readTree(response).get("id").asLong();

        // 3. Delete it
        mockMvc.perform(delete("/api/sirens/" + id))
                .andExpect(status().isNoContent());
    }
}