package org.example.lasiren.restController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.lasiren.dto.CreateFireDTO;
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
class FireControllerTest {
    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void createFire_shouldReturn201() throws Exception {
        CreateFireDTO dto = new CreateFireDTO(55.0, 12.0);

        mockMvc.perform(post("/api/fires")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.latitude").value(55.0));
    }

    @Test
    void getActiveFires_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/fires/active"))
                .andExpect(status().isOk());
    }
}