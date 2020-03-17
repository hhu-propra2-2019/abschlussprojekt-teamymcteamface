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
