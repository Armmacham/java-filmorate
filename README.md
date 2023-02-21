# База данных filmorate

## Код базы данных:
```
Table film {
id int [pk, increment]
name varchar [not null]
description varchar
releaseDate timestamp
duration int
mpa varchar [ref: - mpa.id]
}

Table user {
id int [pk, increment]
email varchar [unique, not null]
login varchar [not null]
name varchar
birthday timestamp
}

Table likes {
film_id int
user_id int
}

Table genre {
id int [pk, increment]
name varchar [not null]
}

Table film_genres {
film_id int
genre_id int
}

Table mpa {
id int [pk, increment]
name varchar [not null]
}

Table user_friends {
user_id int
friend_id int
status boolean
}

Ref: film_genres.film_id > film.id
Ref: film_genres.genre_id > genre.id
Ref: likes.film_id > film.id
Ref: likes.user_id > user.id
Ref: user_friends.user_id > user.id
Ref: user_friends.friend_id > user.id
```

## ![Схема базы данных](https://github.com/Armmacham/java-filmorate/blob/main/filmorate_scheme.png)
