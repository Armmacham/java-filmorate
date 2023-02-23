
DELETE FROM LIKES;
DELETE FROM GENRELINE;
DELETE FROM FRIENDSHIP;
DELETE FROM USERS;
DELETE FROM FILM;

ALTER TABLE USERS ALTER COLUMN USER_ID RESTART WITH 1;
ALTER TABLE FILM ALTER COLUMN FILM_ID RESTART WITH 1;
ALTER TABLE FRIENDSHIP ALTER COLUMN FRIENDSHIP_ID RESTART WITH 1;
ALTER TABLE GENRELINE ALTER COLUMN GENRE_LINE_ID RESTART WITH 1;
ALTER TABLE LIKES ALTER COLUMN LIKE_ID RESTART WITH 1;

MERGE INTO RATINGMPA KEY(RATING_ID)
    VALUES (1, 'G', 'у фильма нет возрастных ограничений'),
           (2, 'PG', 'детям рекомендуется смотреть фильм с родителями'),
           (3, 'PG-13', 'детям до 13 лет просмотр не желателен'),
           (4, 'R', 'лицам до 17 лет просматривать фильм можно только в присутствии взрослого'),
           (5, 'NC-17', 'лицам до 18 лет просмотр запрещён');

MERGE INTO GENRE KEY(GENRE_ID)
    VALUES (1, 'Комедия'),
    (2, 'Драма'),
    (3, 'Мультфильм'),
    (4, 'Триллер'),
    (5, 'Документальный'),
    (6, 'Боевик');
DELETE FROM LIKES;
DELETE FROM GENRELINE;
DELETE FROM FRIENDSHIP;
DELETE FROM USERS;
DELETE FROM FILM;

ALTER TABLE USERS ALTER COLUMN USER_ID RESTART WITH 1;
ALTER TABLE FILM ALTER COLUMN FILM_ID RESTART WITH 1;
ALTER TABLE FRIENDSHIP ALTER COLUMN FRIENDSHIP_ID RESTART WITH 1;
ALTER TABLE GENRELINE ALTER COLUMN GENRE_LINE_ID RESTART WITH 1;
ALTER TABLE LIKES ALTER COLUMN LIKE_ID RESTART WITH 1;

MERGE INTO RATINGMPA KEY(RATING_ID)
    VALUES (1, 'G', 'у фильма нет возрастных ограничений'),
           (2, 'PG', 'детям рекомендуется смотреть фильм с родителями'),
           (3, 'PG-13', 'детям до 13 лет просмотр не желателен'),
           (4, 'R', 'лицам до 17 лет просматривать фильм можно только в присутствии взрослого'),
           (5, 'NC-17', 'лицам до 18 лет просмотр запрещён');

MERGE INTO GENRE KEY(GENRE_ID)
    VALUES (1, 'Комедия'),
    (2, 'Драма'),
    (3, 'Мультфильм'),
    (4, 'Триллер'),
    (5, 'Документальный'),
    (6, 'Боевик');