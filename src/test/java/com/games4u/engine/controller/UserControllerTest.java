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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

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
    void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(user1, user2);
        when(userService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("User1")))
                .andExpect(jsonPath("$[1].name", is("User2")));
    }

    @Test
    void testGetUserSuccess() throws Exception {
        when(userService.findById(anyString())).thenReturn(Optional.of(user1));

        mockMvc.perform(get("/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("User1")));
    }

    @Test
    void testGetUserNotFound() throws Exception {
        when(userService.findById(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/{id}", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSaveUser() throws Exception {
        when(userService.save(any(User.class))).thenReturn(user1);

        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"User1\",\"email\":\"user1@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("User1")))
                .andExpect(jsonPath("$.email", is("user1@example.com")));
    }

    @Test
    void testUpdateUserByIdSuccess() throws Exception {
        when(userService.findById(anyString())).thenReturn(Optional.of(user1));
        when(userService.save(any(User.class))).thenReturn(user1);

        mockMvc.perform(put("/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated User1\",\"email\":\"updated_user1@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated User1")))
                .andExpect(jsonPath("$.email", is("updated_user1@example.com")));
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
    void testDeleteUserByIdSuccess() throws Exception {
        when(userService.findById(anyString())).thenReturn(Optional.of(user1));

        mockMvc.perform(delete("/{id}", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUserByIdNotFound() throws Exception {
        when(userService.findById(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/{id}", "1"))
                .andExpect(status().isNotFound());
    }
}
