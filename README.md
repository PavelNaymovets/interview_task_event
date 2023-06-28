# Тестовое задание 

## Технические требования

Разработать приложение, которое позволит пользователям:
* Создавать аккаунты
* Создавать мероприятия после подписания договора на проведение мероприятий с
нашей компанией
* Записываться на посещение мероприятия

Подробный список требований представлен по _[ссылке](https://drive.google.com/file/d/10X1odEeqcLsy7sQEVn-y3xNaUdPafokJ/view?usp=sharing)_.

### Пояснение
* Запуск базы данных и системы миграции осуществляется через docker-compose _[docker-compose](https://github.com/PavelNaymovets/interview_task_event/tree/master/docker-compose)_ файл
* База данных PostgreSQL разворачивается в docker контейнере
* Миграция базы данных происходит через docker контэйнер liquibase. Скрипты _[тут](https://github.com/PavelNaymovets/interview_task_event/tree/master/docker-compose/liquibase/scripts)_.