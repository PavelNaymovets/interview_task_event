insert into users (last_name, first_name, middle_name, age, login, status, password)
values ('прохорова', 'инна', 'андреевна', 25, 'vil53', 'активный', '$2a$12$Pq7p4UGq08oKp.xCBGddd.CLE41cvfFgFjdWrW0hvr5ZFex4.LkoW'),
       ('селезнёва', 'ярослава', 'борисовна', 30, 'zlata.rybakov', 'активный', '$2a$12$R41MrhAZFaGQCXaK4PFPqOp.vkpISDQTTy..sOIm5sAAlmC.Lkaj6'),
       ('ермаков', 'назар', 'максимович', 28, 'popova.oleg', 'активный', '$2a$12$nC0mOhkf.VBNeoNL4icswOfQ0GFrKGqWAOizfKudunvPd/aOUe.cG'),
       ('виноградова', 'маргарита', 'сергеевна', 18, 'romanova.dina', 'активный', '$2a$12$qbKPpKlxZ8L0J0cOr3PpG.qQ/0wAEZRQqWBGXYtsWxszhfjB3ar46'),
       ('алексеев', 'аркадий', 'иванович', 27, 'akov.efremov', 'активный', '$2a$12$37aLWLHgzu1dt2Pa.Z8zLO4MrKpig4kMRrXlJyrI5pVydqMZTbiaG'),
       ('иванов', 'иван', 'иванович', 40, 'ivanov.ivan', 'удаленный', '$2a$12$9.0ouDrO6AdbVHF7NYZS0.1Kf/J34OMOSOAW71Iqc9FwC5MiavDx6');

insert into contracts (company_name, event_name, admin_id, status)
values ('им иванов и ко', 'день рождения', 1, 'подписан'),
       ('ооо кококола', 'корпоратив', 2, 'подписан'),
       ('ермаков н.м.', 'свадьба', 3, 'подписан'),
       ('мкоусош №2', 'выпускной', 4, 'подписан');

insert into events (contract_id, name, cost, status)
values (1, 'день рождения', 5000, 'не проведен'),
       (2, 'корпоратив', 10000, 'не проведен'),
       (3, 'свадьба', 15000, 'не проведен'),
       (4, 'выпускной', 20000, 'не проведен');

insert into event_members (event_id, user_id, pcr_test, status)
values (2, 1, 'отрицательный', 'участник'),
       (2, 2, 'отрицательный', 'участник'),
       (2, 3, 'отрицательный', 'участник'),
       (2, 4, 'положительный', 'не участник');

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('ROLE_MANAGER');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (1, 2),
       (1, 3);