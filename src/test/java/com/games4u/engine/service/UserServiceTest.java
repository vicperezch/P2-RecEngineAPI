package com.games4u.engine.service;


import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*; 

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach; 
import com.games4u.engine.model.Category;
import com.games4u.engine.model.Game;
import com.games4u.engine.model.Genre;
import com.games4u.engine.model.Platform;
import com.games4u.engine.model.User;
import com.games4u.engine.repository.CategoryRepository;
import com.games4u.engine.repository.GameRepository;
import com.games4u.engine.repository.GenreRepository;
import com.games4u.engine.repository.PlatformRepository;
import com.games4u.engine.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*; 

public class UserServiceTest {

     @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testDeleteById() {
        doNothing().when(userRepository).deleteById("user1@example.com");

        userService.deleteById("user1@example.com");

        verify(userRepository, times(1)).deleteById("user1@example.com");
    }

    @Test
    void testFindAllUsers() {
        List<User> users = Arrays.asList(new User("1", "user1@example.com", "Diego", "1234", null, null, null, null, null), new User("2", "user2@example.com", "Juan", "5678", null, null, null, null, null));
        when(userRepository.findAll()).thenReturn(users);

        List<User> foundUsers = userService.findAllUsers();

        assertEquals(2, foundUsers.size());
        assertEquals("user1@example.com", foundUsers.get(0).getEmail());
        assertEquals("user2@example.com", foundUsers.get(1).getEmail());
    }

    @Test
    void testFindByEmail() {
        User user = new User("1", "user1@example.com", "Diego", "1234", null, null, null, null, null);
        when(userRepository.findByEmail("user1@example.com")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findByEmail("user1@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("user1@example.com", foundUser.get().getEmail());
    }

    @Test
    void testFindById() {
        User user = new User("1", "user1@example.com", "Diego", "1234", null, null, null, null, null);
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findById("1");

        assertTrue(foundUser.isPresent());
        assertEquals("1", foundUser.get().getId());
    }

    @Test
    void testSave() {
        User user = new User("3", "newuser@example.com", "Victor", "9876", null, null, null, null, null);
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.save(user);

        assertNotNull(savedUser);
        assertEquals("newuser@example.com", savedUser.getEmail());
    }
}
