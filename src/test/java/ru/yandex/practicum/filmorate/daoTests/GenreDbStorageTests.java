package ru.yandex.practicum.filmorate.daoTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenreDbStorage;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDbStorageTests {
        private final GenreDbStorage genreDbStorage;

        @Test
        public void shouldGetGenreById() {
            Genre dbGenre = genreDbStorage.getGenreById(1);

            assertThat(dbGenre).hasFieldOrPropertyWithValue("id", 1);
        }

        @Test
        void shouldGetAllGenres() {
            Collection<Genre> genres = genreDbStorage.getAllGenres();
            assertEquals(6, genres.size());
        }
    }

