# java-filmorate
Template repository for Filmorate project.

https://drawsql.app/non-28/diagrams/filmorate

Две основные таблицы users & films хранят данные пользователей и соответственно фильмов
  - Для пользователей создана дополнительная таблица friends по соединению один ко многим в которой хранится база друзей и значение подтвержденён или нет

У таблицы films есть дополнительные таблицы genres & rating
  - films & geres связаны через общую таблицу film_genre по соединению многие ко многим
  - таблица rating по связи один к одному

Основные таблицы users & films связаны общей таблицей likes по соединению многие ко многим

![Ссылка на ER-диаграмму](https://github.com/IgorMartynkin1981/java-filmorate/blob/create-er-diagram/ER-diagrams.bmp)
