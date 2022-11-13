package ru.yandex.practicum.filmorate.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Map;

@Getter
@Service
@Component
@Slf4j
@AllArgsConstructor
public class FilmService {

        private final Map<Integer, Film> films = new HashMap<>();

        public Film addFilm(Film film) {
            films.put(film.getId(), film);
            return film;
        }

        public Film updateFilm(Film film) {
            films.put(film.getId(), film);
            return film;
        }
    }
