INSERT
INTO user (username, email, name)
VALUES ('studentin', 'stud0@gmail.com', 'Bernhard Zimmermann'),
       ('studentin1', 'stud1@gmail.com', 'Lisa Schmidt'),
       ('studentin2', 'stud2@gmail.com', 'Anna Müller'),
       ('studentin3', 'stud3@gmail.com', 'Martin Esel'),
       ('orga', 'orga0@gmail.com', 'Peter Organisatorus'),
       ('orga1', 'orga1@gmail.com', 'Meike Brummer'),
       ('orga2', 'orga2@gmail.com', 'Marianne Baum'),
       ('actuator', 'actuator@gmail.com', 'Christian Meter');

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
INTO `topic` (`id`, `description`, `moderated`, `title`, `forum_id`)
VALUES (1, 'Hier sind offizielle Ankündigungen und Informationen', 1, 'Ankündigungen', 1),
       (2, 'Hier kann über Aufgaben und Probleme diskutiert werden', 0, 'Aufgabenstellungen', 1),
       (3, 'Hier werden nur Memes gepostet', 0, 'Off-Topic', 1),
       (4, 'Hier sind offizielle Ankündigungen und Informationen', 1, 'Ankündigungen', 2),
       (5, 'Hier kann über Aufgaben und Probleme diskutiert werden', 0, 'Aufgabenstellungen', 2),
       (6, 'Hier werden nur Memes gepostet', 0, 'Off-Topic', 2),
       (7, 'Hier sind offizielle Ankündigungen und Informationen', 1, 'Ankündigungen', 3),
       (8, 'Hier kann über Aufgaben und Probleme diskutiert werden', 0, 'Aufgabenstellungen', 3),
       (9, 'Hier werden nur Memes gepostet', 0, 'Off-Topic', 3),
       (10, 'Hier sind offizielle Ankündigungen und Informationen', 1, 'Ankündigungen', 4),
       (11, 'Hier kann über Aufgaben und Probleme diskutiert werden', 0, 'Aufgabenstellungen', 4),
       (12, 'Hier werden nur Memes gepostet', 0, 'Off-Topic', 4);

INSERT
INTO thread (id, title, topic_id)
VALUES (1, 'Klausurtermine', 1),
       (2, 'Praktikum: Informationen', 1),
       (3, 'Organisation / Austausch', 1),
       (4, 'Tutorenjobs im WS 2020', 1),
       (5, 'Kommt zum Hackathon !', 1),
       (6, 'Ich brauche Hilfe bei Aufgabe 4a', 2),
       (7, 'Was ist eine IDE ?', 2),
       (8, 'Docker Compose funktioniert nicht :(', 2),
       (9, 'Ich will lieber Germanistik studieren', 2),
       (10, 'Was ist Hibernate ?', 2);


INSERT
INTO post (id, author, date_time, thread_id, user_username, text)
VALUES (1,
        'Peter Organisatorus',
        '2020-03-12 14:32:04',
        1,
        'orga',
        'Hallo Zusammen \nDie Klausur findet am 13.07. statt ! Um 13:00 in 5D.'),
       (2,
        'Lisa Schmidt',
        '2020-03-12 15:12:49',
        1,
        'studentin1',
        'Och nöö, da bin ich im Urlaub :('),
       (3,
        'Peter Organisatorus',
        '2020-03-12 16:05:50',
        1,
        'orga',
        'hahahaha, verkackt.');



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
       ('actuator', 3),
       ('actuator', 4),
       ('actuator', 5);
