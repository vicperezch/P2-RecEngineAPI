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
import com.games4u.engine.repository.CategoryRepository;
import com.games4u.engine.repository.GameRepository;
import com.games4u.engine.repository.GenreRepository;

import static org.junit.jupiter.api.Assertions.*; 
public class GenreServiceTest {

     @Mock
    private GenreRepository genreRepository;

    private GenreService genreService;

    @BeforeEach
    void setUp() {
        genreRepository = mock(GenreRepository.class);
        genreService = new GenreService(genreRepository);
    }

    @Test
    void testDeleteById() {
        doNothing().when(genreRepository).deleteById("Genre1");

        genreService.deleteById("Genre1");

        verify(genreRepository, times(1)).deleteById("Genre1");
    }

    @Test
    void testFindAllGenres() {
 List<Genre> genres = Arrays.asList(new Genre("1", "RPG"), new Genre("2", "Action"));
        when(genreRepository.findAll()).thenReturn(genres);

        List<Genre> foundGenres = genreService.findAllGenres();

        assertEquals(2, foundGenres.size());
        assertEquals("1", foundGenres.get(0).getId());
        assertEquals("2", foundGenres.get(1).getId());
    }

    @Test
    void testFindById() {
        Genre genre = new Genre("1", "RPG");
        when(genreRepository.findById("1")).thenReturn(Optional.of(genre));

        Optional<Genre> foundGenre = genreService.findById("1");

        assertTrue(foundGenre.isPresent());
        assertEquals("1", foundGenre.get().getId());
    }

    @Test
    void testSave() {
        Genre genre = new Genre("X", "Y");
        when(genreRepository.save(genre)).thenReturn(genre);

        Genre savedGenre = genreService.save(genre);

        assertNotNull(savedGenre);
        assertEquals("X", savedGenre.getId());
    }
}
