package com.games4u.engine.controller;

import com.games4u.engine.controller.UserController;
import com.games4u.engine.model.User;
import com.games4u.engine.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user1 = new User("1", null, null, null, null, null, null, null, null);
        user1.setName("User1");
        user1.setEmail("user1@example.com");

        user2 = new User("2", null, null, null, null, null, null, null, null);
        user2.setName("User2");
        user2.setEmail("user2@example.com");
    }


    @Test
    void testGetUserNotFound() throws Exception {
        when(userService.findById(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/{id}", "1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testUpdateUserByIdNotFound() throws Exception {
        when(userService.findById(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(put("/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated User1\",\"email\":\"updated_user1@example.com\"}"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testDeleteUserByIdNotFound() throws Exception {
        when(userService.findById(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/{id}", "1"))
                .andExpect(status().isNotFound());
    }
}
