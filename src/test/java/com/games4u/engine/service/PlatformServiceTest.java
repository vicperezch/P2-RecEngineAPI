package com.games4u.engine.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*; 

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach; 
import com.games4u.engine.model.Category;
import com.games4u.engine.model.Game;
import com.games4u.engine.model.Genre;
import com.games4u.engine.model.Platform;
import com.games4u.engine.repository.CategoryRepository;
import com.games4u.engine.repository.GameRepository;
import com.games4u.engine.repository.GenreRepository;
import com.games4u.engine.repository.PlatformRepository;

import static org.junit.jupiter.api.Assertions.*; 

import org.junit.jupiter.api.Test;

public class PlatformServiceTest {

    @Mock
    private PlatformRepository platformRepository;

    private PlatformService platformService;

    @BeforeEach
    void setUp() {
        platformRepository = mock(PlatformRepository.class);
        platformService = new PlatformService(platformRepository);
    }

    @Test
    void testDeleteById() {
        doNothing().when(platformRepository).deleteById("Platform1");

        platformService.deleteById("Platform1");

        verify(platformRepository, times(1)).deleteById("Platform1");
    }

    @Test
    void testFindAllPlatforms() {
        List<Platform> platforms = Arrays.asList(new Platform("Platform1", "Platform1"), new Platform("Platform2", "Platform2"));
        when(platformRepository.findAll()).thenReturn(platforms);

        List<Platform> foundPlatforms = platformService.findAllPlatforms();

        assertEquals(2, foundPlatforms.size());
        assertEquals("Platform1", foundPlatforms.get(0).getId());
        assertEquals("Platform2", foundPlatforms.get(1).getId());
    }

    @Test
    void testFindById() {
        Platform platform = new Platform("Platform1", "Platform1");
        when(platformRepository.findById("Platform1")).thenReturn(Optional.of(platform));

        Optional<Platform> foundPlatform = platformService.findById("Platform1");

        assertTrue(foundPlatform.isPresent());
        assertEquals("Platform1", foundPlatform.get().getId());
    }

    @Test
    void testSave() {
        Platform platform = new Platform("NewPlatform", "Platform3");
        when(platformRepository.save(platform)).thenReturn(platform);

        Platform savedPlatform = platformService.save(platform);

        assertNotNull(savedPlatform);
        assertEquals("NewPlatform", savedPlatform.getId());
    }
}
