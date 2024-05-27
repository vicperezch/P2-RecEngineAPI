package com.games4u.engine.controller;

import org.junit.jupiter.api.Test;

import com.games4u.engine.controller.PlatformController;
import com.games4u.engine.model.Platform;
import com.games4u.engine.service.PlatformService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(PlatformController.class)
public class PlatformControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlatformService platformService;

    private Platform platform1;
    private Platform platform2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        platform1 = new Platform("1", null);
        platform1.setName("PC");

        platform2 = new Platform("2", null);
        platform2.setName("Xbox");
    }

    @Test
    void testGetAllPlatforms() throws Exception {
        List<Platform> platforms = Arrays.asList(platform1, platform2);
        when(platformService.findAllPlatforms()).thenReturn(platforms);

        mockMvc.perform(get("/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("PC")))
                .andExpect(jsonPath("$[1].name", is("Xbox")));
    }

    @Test
    void testGetPlatformSuccess() throws Exception {
        when(platformService.findById("1")).thenReturn(Optional.of(platform1));

        mockMvc.perform(get("/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("PC")));
    }

    @Test
    void testGetPlatformNotFound() throws Exception {
        when(platformService.findById("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/{id}", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSavePlatform() throws Exception {
        when(platformService.save(any(Platform.class))).thenReturn(platform1);

        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"PC\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("PC")));
    }

    @Test
    void testDeletePlatformByIdSuccess() throws Exception {
        when(platformService.findById("1")).thenReturn(Optional.of(platform1));

        mockMvc.perform(delete("/{id}", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePlatformByIdNotFound() throws Exception {
        when(platformService.findById("1")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/{id}", "1"))
                .andExpect(status().isNotFound());
    }
}
