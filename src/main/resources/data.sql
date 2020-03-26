INSERT
INTO user (username, email, name)
VALUES ('studentin', 'stud0@gmail.com', 'Bernhard Zimmermann'),
       ('studentin1', 'stud1@gmail.com', 'Lisa Schmidt'),
       ('studentin2', 'stud2@gmail.com', 'Anna Müller'),
       ('studentin3', 'stud3@gmail.com', 'Martin Esel'),
       ('orga', 'orga0@gmail.com', 'Peter Organisatorus'),
       ('orga1', 'orga1@gmail.com', 'Meike Brummer'),
       ('orga2', 'orga2@gmail.com', 'Marianne Baum'),
       ('actuator', 'actuator@gmail.com', 'Christian Meter'),
       ('Unbekannt', '', 'Default');

INSERT
INTO forum (id, description, title)
VALUES (1,
        'Wir lehren sie in diesem Modul die Algorithmen und Datenstrukturen.
         Bitte besuchen sie regelmäßig die Vorlesung, da es kein Skript geben wird!',
        'Algorithmen und Datenstrukturen'),
       (2,
        'Wir lehren sie in diesem Modul die Programmierung.
         Bitte besuchen sie regelmäßig die Vorlesung, da es kein Skript geben wird!',
        'Programmierung'),
       (3,
        'Wir lehren sie in diesem Modul die Datenbanksysteme.
         Bitte besuchen sie regelmäßig die Vorlesung, da es kein Skript geben wird!',
        'Datenbanksysteme'),
       (4,
        'Wir lehren sie in diesem Modul die Betriebssysteme.
         Bitte besuchen sie regelmäßig die Vorlesung, da es kein Skript geben wird!',
        'Betriebssysteme'),
       (5,
        'Wir lehren sie in diesem Modul das Machine Learning.
         Bitte besuchen sie regelmäßig die Vorlesung, da es kein Skript geben wird!',
        'Machine Learning');

INSERT
INTO `topic` (`id`, `description`, `moderated`, `title`, `forum_id`, `anonymous`)
VALUES (1, 'Hier sind offizielle Ankündigungen und Informationen', 1, 'Ankündigungen', 1, FALSE),
       (2, 'Hier kann über Aufgaben und Probleme diskutiert werden', 0, 'Aufgabenstellungen', 1, TRUE),
       (3, 'Hier werden nur Memes gepostet', 0, 'Off-Topic', 1, true),
       (4, 'Hier sind offizielle Ankündigungen und Informationen', 1, 'Ankündigungen', 2, false),
       (5, 'Hier kann über Aufgaben und Probleme diskutiert werden', 0, 'Aufgabenstellungen', 2, true),
       (6, 'Hier werden nur Memes gepostet', 0, 'Off-Topic', 2, false),
       (7, 'Hier sind offizielle Ankündigungen und Informationen', 1, 'Ankündigungen', 3, false),
       (8, 'Hier kann über Aufgaben und Probleme diskutiert werden', 0, 'Aufgabenstellungen', 3, true),
       (9, 'Hier werden nur Memes gepostet', 0, 'Off-Topic', 3, false),
       (10, 'Hier sind offizielle Ankündigungen und Informationen', 1, 'Ankündigungen', 4, true),
       (11, 'Hier kann über Aufgaben und Probleme diskutiert werden', 0, 'Aufgabenstellungen', 4, false),
       (12, 'Hier werden nur Memes gepostet', 0, 'Off-Topic', 4, true);

INSERT
INTO thread (id, author_username, title, topic_id, anonymous, forum_id, moderated, visible)
VALUES (1, 'orga1', 'Klausurtermine', 1, FALSE, 1,1,1),
       (2, 'studentin1', 'Praktikum: Informationen', 1, FALSE, 1,1,1),
       (3, 'orga2', 'Organisation / Austausch', 1, FALSE, 1,1,1),
       (4, 'studentin', 'Tutorenjobs im WS 2020', 1, FALSE, 1,1,1),
       (5, 'orga1', 'Kommt zum Hackathon !', 1, FALSE, 1,1,1),
       (6, 'studentin', 'Ich brauche Hilfe bei Aufgabe 4a', 2, TRUE, 1,0,1),
       (7, 'orga2', 'Was ist eine IDE ?', 2, TRUE, 1,0,1),
       (8, 'studentin3', 'Docker Compose funktioniert nicht :(', 2, TRUE, 1,0,1),
       (9, 'orga', 'Ich will lieber Germanistik studieren', 2, TRUE, 1,0,1),
       (10, 'orga', 'Was ist Hibernate ?', 2, TRUE, 1,0,1);

INSERT
INTO post (id, author_username, date_time, thread_id, text, anonymous, visible, forum_id)
VALUES (1,
        'studentin',
        '2020-03-12 14:32:04',
        1,
        'Hallo Zusammen \nDie Klausur findet am 13.07. statt ! Um 13:00 in 5D.'
           , FALSE, true, 1),
       (2,
        'studentin1',
        '2020-03-12 15:12:49',
        1,
        'Och nöö, da bin ich im Urlaub :(',
        FALSE, true, 1),
       (3,
        'orga2',
        '2020-03-12 16:05:50',
        1,
        'hahahaha, verkackt.',
        FALSE, true, 1);

INSERT
INTO user_forum (username, id)
VALUES ('studentin', 1),
       ('studentin', 2),
       ('studentin', 3),
       ('studentin1', 3),
       ('studentin1', 5),
       ('studentin2', 1),
       ('studentin2', 2),
       ('studentin2', 4),
       ('studentin2', 5),
       ('studentin3', 1),
       ('orga', 3),
       ('orga1', 5),
       ('orga2', 2),
       ('orga2', 3),
       ('actuator', 1),
       ('actuator', 2),
       ('actuator', 4),
       ('actuator', 5);

INSERT
INTO roles (username, role, forum_id)
VALUES ('studentin','STUDENT', 1),
       ('studentin','STUDENT', 2),
       ('studentin','STUDENT', 3),
       ('actuator', 'ADMIN', 1),
       ('actuator', 'ADMIN', 2),
       ('actuator', 'ADMIN',4),
       ('actuator', 'ADMIN', 5),
       ('studentin1','STUDENT', 3),
       ('studentin1','STUDENT', 5),
       ('studentin2','STUDENT', 1),
       ('studentin2','STUDENT', 2),
       ('studentin2','STUDENT', 4),
       ('studentin2','STUDENT', 5),
       ('studentin3','MODERATOR', 1),
       ('orga', 'MODERATOR',3),
       ('orga1','STUDENT', 5),
       ('orga2', 'STUDENT',2),
       ('orga2', 'ADMIN',3);