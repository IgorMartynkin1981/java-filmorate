SELECT * FROM RATING_MPA;
SELECT * FROM GENRES;
MERGE INTO genres VALUES ( 1, 'Комедия');
MERGE INTO genres VALUES (2, 'Драма');
MERGE INTO genres VALUES (3, 'Мультфильм');
MERGE INTO genres VALUES (4, 'Триллер');
MERGE INTO genres VALUES (5, 'Документальный');
MERGE INTO genres VALUES (6, 'Боевик');

MERGE INTO rating_mpa VALUES (1, 'G', 'у фильма нет возрастных ограничений');
MERGE INTO rating_mpa VALUES (2, 'PG', 'детям рекомендуется смотреть фильм с родителями');
MERGE INTO rating_mpa VALUES (3, 'PG-13', 'детям до 13 лет просмотр не желателен');
MERGE INTO rating_mpa VALUES (4, 'R', 'лицам до 17 лет просматривать фильм можно только в присутствии взрослого');
MERGE INTO rating_mpa VALUES (5, 'G', 'у фильма нет возрастных ограничений');
MERGE INTO rating_mpa VALUES (6, 'NC-17', 'лицам до 18 лет просмотр запрещён');