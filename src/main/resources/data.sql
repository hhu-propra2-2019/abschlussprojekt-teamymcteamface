INSERT INTO user (username, email, name)
VALUES ('studentin', 'stud0@gmail.com', 'Bernhard Zimmermann');
INSERT INTO user (username, email, name)
VALUES ('studentin1', 'stud1@gmail.com', 'Lisa Schmidt');
INSERT INTO user (username, email, name)
VALUES ('studentin2', 'stud2@gmail.com', 'Anna Müller');
INSERT INTO user (username, email, name)
VALUES ('studentin3', 'stud3@gmail.com', 'Martin Esel');
INSERT INTO user (username, email, name)
VALUES ('orga', 'orga0@gmail.com', 'Peter Organisatorus');
INSERT INTO user (username, email, name)
VALUES ('orga1', 'orga1@gmail.com', 'Meike Brummer');
INSERT INTO user (username, email, name)
VALUES ('orga2', 'orga2@gmail.com', 'Marianne Baum');
INSERT INTO user (username, email, name)
VALUES ('actuator', 'actuator@gmail.com', 'Christian Meter');



INSERT INTO forum (id, description, title)
VALUES ('1',
        'Wir lehren sie in diesem Modul die Algorithmen und Datenstrukturen.
Bitte besuchen sie regelmäßig die Vorlesung, da es kein Skript geben wird!',
        'Algorithmen und Datenstrukturen');
INSERT INTO forum (id, description, title)
VALUES ('2',
        'Wir lehren sie in diesem Modul die Programmierung.
Bitte besuchen sie regelmäßig die Vorlesung, da es kein Skript geben wird!',
        'Programmierung');
INSERT INTO forum (id, description, title)
VALUES ('3',
        'Wir lehren sie in diesem Modul die Datenbanksysteme.
Bitte besuchen sie regelmäßig die Vorlesung, da es kein Skript geben wird!',
        'Datenbanksysteme');
INSERT INTO forum (id, description, title)
VALUES ('4',
        'Wir lehren sie in diesem Modul die Betriebssysteme.
Bitte besuchen sie regelmäßig die Vorlesung, da es kein Skript geben wird!',
        'Betriebssysteme');
INSERT INTO forum (id, description, title)
VALUES ('5',
        'Wir lehren sie in diesem Modul das Machine Learning.
Bitte besuchen sie regelmäßig die Vorlesung, da es kein Skript geben wird!',
        'Machine Learning');



INSERT INTO user_forum (username, id)
VALUES ('studentin', '1');
INSERT INTO user_forum (username, id)
VALUES ('studentin', '2');
INSERT INTO user_forum (username, id)
VALUES ('studentin', '3');
INSERT INTO user_forum (username, id)
VALUES ('studentin1', '3');
INSERT INTO user_forum (username, id)
VALUES ('studentin1', '5');
INSERT INTO user_forum (username, id)
VALUES ('studentin2', '1');
INSERT INTO user_forum (username, id)
VALUES ('studentin2', '2');
INSERT INTO user_forum (username, id)
VALUES ('studentin2', '4');
INSERT INTO user_forum (username, id)
VALUES ('studentin2', '5');
INSERT INTO user_forum (username, id)
VALUES ('studentin3', '1');
INSERT INTO user_forum (username, id)
VALUES ('orga', '3');
INSERT INTO user_forum (username, id)
VALUES ('orga1', '5');
INSERT INTO user_forum (username, id)
VALUES ('orga2', '2');
INSERT INTO user_forum (username, id)
VALUES ('orga2', '3');
