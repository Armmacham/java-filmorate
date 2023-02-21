package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenreDbStorage;

import java.util.Collection;


@Service
public class GenreService {

    private final GenreDbStorage genreDbStorage;

    @Autowired
    public GenreService(GenreDbStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    public Collection<Genre> getAllGenres() {
        return genreDbStorage.getAllGenres();
    }

    public Collection<Genre> getFilmGenres(int filmId) {
        return genreDbStorage.getGenresByFilmId(filmId);
    }

    public Genre getGenre(String supposedId) {
        int genreId = intFromString(supposedId);
        return genreDbStorage.getGenreById(genreId);
    }

    public boolean deleteFilmGenres(int filmId) {
        return genreDbStorage.deleteFilmGenres(filmId);
    }

    public boolean addFilmGenres(int filmId, Collection<Genre> genres) {
        return genreDbStorage.addFilmGenres(filmId, genres);
    }

    private Integer intFromString(final String supposedInt) {
        try {
            return Integer.valueOf(supposedInt);
        } catch (NumberFormatException exception) {
            return Integer.MIN_VALUE;
        }
    }
}