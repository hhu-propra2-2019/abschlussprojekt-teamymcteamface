INSERT INTO foren.forum (id, description, title) VALUES (1, 'orga1 ist hier Admin, studentin1 Moderator, das Topic "Informationen zur Klausur" ist moderiert', 'Algorithmen und Datenstrukturen');
INSERT INTO foren.forum (id, description, title) VALUES (2, 'orga1 ist hier Admin, studentin2 Moderator, das Topic "schlechte Witze aus dem Internet" ist anonym', 'Programmierung');
INSERT INTO foren.forum (id, description, title) VALUES (3, 'orga2 ist hier Admin', 'Datenbanksysteme');
INSERT INTO foren.forum (id, description, title) VALUES (4, 'Wir lehren sie in diesem Modul die Betriebssysteme.
         Bitte besuchen sie regelmäßig die Vorlesung, da es kein Skript geben wird!', 'Betriebssysteme');
INSERT INTO foren.forum (id, description, title) VALUES (5, 'Wir lehren sie in diesem Modul das Machine Learning.
         Bitte besuchen sie regelmäßig die Vorlesung, da es kein Skript geben wird!', 'Machine Learning');
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (1, false, '2020-03-27 10:23:51', 'Die Klausur findet am 05.05.2020 statt
Die Räume werden noch bekannt gegeben.', true, 'orga1', 1, 1);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (2, false, '2020-03-27 10:25:23', 'Die Räume werden wie üblich nach Nachnamen verteilt :
A-M : Hörsaal 5D
N-Z : Hörsaal 5C', true, 'studentin1', 1, 2);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (3, false, '2020-03-27 10:26:13', 'Aufgrund von Corona wird die Klausur auf den 08.08.2020 verschoben', true, 'orga1', 1, 3);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (4, false, '2020-03-27 10:30:52', 'War die erste Woche noch im Urlaub.', true, 'studentin2', 1, 4);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (5, false, '2020-03-27 10:31:42', 'Das findest du im Ilias.', true, 'studentin3', 1, 4);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (6, false, '2020-03-27 10:33:05', 'Bald geht auch die Materialsammlung online, dann muss man nicht mehr auf das langsame Ilias warten.', true, 'studentin1', 1, 4);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (7, false, '2020-03-27 10:34:03', 'Hauptsache besser als Ilias !', true, 'orga1', 1, 4);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (8, false, '2020-03-27 10:35:27', 'danke euch :)', true, 'studentin2', 1, 4);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (9, false, '2020-03-27 10:36:56', 'Kann mir da jemand helfen ?', true, 'studentin3', 1, 5);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (10, false, '2020-03-27 10:38:07', 'Hier ist nen gutes Video dazu :

https://www.youtube.com/watch?v=Hoixgm4-P4M', true, 'studentin2', 1, 5);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (11, false, '2020-03-27 10:40:23', 'Komme da einfach nicht auf den Baum ?', true, 'studentin2', 1, 6);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (12, false, '2020-03-27 10:41:21', 'Hallo ? Bitte irgendjemand ?', true, 'studentin2', 1, 6);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (13, false, '2020-03-27 10:42:25', 'Ganz ruhig ! Schau dir Seite 183 nochmal an, das Beispiel kannst du quasi direkt so anweden.', true, 'studentin1', 1, 6);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (14, true, '2020-03-27 10:51:41', 'Zum Glück ist das hier anonym !', true, 'studentin1', 2, 7);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (15, true, '2020-03-27 10:54:35', 'Did you hear about the monkeys who shared an Amazon account? They were Prime mates.', true, 'studentin3', 2, 8);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (16, true, '2020-03-27 10:55:11', 'We’ll we’ll we’ll…if it isn’t autocorrect.', true, 'orga1', 2, 9);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (17, true, '2020-03-27 10:56:54', 'The guy who invented auto-correct for smart phones passed away today.

Restaurant in peace.
', true, 'studentin2', 2, 9);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (18, false, '2020-03-27 11:00:04', 'nano ist beste !', true, 'studentin3', 2, 10);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (19, false, '2020-03-27 11:00:35', 'Dieser Beitrag wurde entfernt.', true, 'Unbekannt', 2, 10);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (20, false, '2020-03-27 11:01:08', 'Dieser Beitrag wurde entfernt.', true, 'Unbekannt', 2, 10);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (21, false, '2020-03-27 11:02:17', 'Nick hat mich überzeugt, IntelliJ kann einfach alles !', true, 'studentin1', 2, 11);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (22, false, '2020-03-27 11:02:45', 'Dieser Beitrag wurde entfernt.', true, 'Unbekannt', 2, 11);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (23, false, '2020-03-27 11:04:40', 'Wenn der Tonfall sich hier nicht ändert, wird moderiert ! ', true, 'orga1', 2, 12);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (24, false, '2020-03-27 11:05:24', 'Ja man ! Hauptsache kein nano !', true, 'studentin2', 2, 11);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (25, false, '2020-03-27 11:06:15', 'Aber Nano sieht doch so schön aus <3', true, 'studentin3', 2, 11);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (31, false, '2020-03-27 11:27:31', 'HAHA warum ist das falsch rum ? :D
', true, 'studentin2', 3, 14);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (30, false, '2020-03-27 11:26:07', 'nice mir auch !', true, 'studentin1', 3, 14);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (29, false, '2020-03-27 11:25:56', 'Mir ist langweilig', true, 'studentin2', 3, 14);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (32, false, '2020-03-27 11:28:36', 'Hä das ist ja total weird', true, 'studentin1', 3, 14);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (33, false, '2020-03-27 11:29:33', 'passiert das , wenn ich sehr schnell Beiträge schreibe ?', true, 'studentin1', 3, 14);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (34, false, '2020-03-27 11:29:33', 'man das probiere ich mal aus !', true, 'studentin2', 3, 14);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (35, false, '2020-03-27 11:30:47', 'Andere Idee : was ist wenn du nen neuen Post vor dem Reload schickst ?', true, 'studentin1', 3, 14);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (36, false, '2020-03-27 11:31:15', 'dann schicke ich jetzt mal ohne reload ab !', true, 'studentin2', 3, 14);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (37, false, '2020-03-27 11:31:35', 'also kann ich das nicht reproduzieren ?', true, 'studentin1', 3, 14);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (38, false, '2020-03-27 11:31:48', 'Es scheint nicht so zu sein', true, 'studentin2', 3, 14);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (39, false, '2020-03-27 11:32:13', 'komisch, aber immerhin schlagen wir so die Vorlesungszeit tot', true, 'studentin1', 3, 14);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (40, false, '2020-03-27 11:32:35', 'Und was passiert jetzt ?', true, 'studentin2', 3, 14);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (41, false, '2020-03-27 11:32:51', 'DAs formular sollte doch gar nicht mehr das sein ?', true, 'studentin1', 3, 14);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (42, false, '2020-03-27 11:34:45', 'Ihr sollt der Vorlesung folgen nicht Quatsch machen !', true, 'orga2', 3, 14);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (43, false, '2020-03-27 11:35:05', 'Jetzt schreiben SIe ja auch schon hier', true, 'studentin1', 3, 14);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (51, false, '2020-03-27 11:54:19', 'das', true, 'studentin2', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (50, false, '2020-03-27 11:54:14', 'sieht', true, 'studentin2', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (49, false, '2020-03-27 11:54:10', 'wie', true, 'studentin2', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (48, false, '2020-03-27 11:54:02', 'Also', true, 'studentin2', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (52, false, '2020-03-27 11:54:28', 'aus', true, 'studentin2', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (53, false, '2020-03-27 11:54:48', 'wenn', true, 'studentin2', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (54, false, '2020-03-27 11:54:58', 'ich', true, 'studentin2', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (61, false, '2020-03-27 11:56:13', 'auf', true, 'studentin2', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (62, false, '2020-03-27 11:56:18', 'einem', true, 'studentin2', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (63, false, '2020-03-27 11:56:25', 'Baum', true, 'studentin2', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (64, false, '2020-03-27 11:56:30', 'eine', true, 'studentin2', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (65, false, '2020-03-27 11:56:35', 'Suche', true, 'studentin2', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (66, false, '2020-03-27 11:56:49', 'durchführen', true, 'studentin2', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (67, false, '2020-03-27 11:56:55', 'möchte', true, 'studentin2', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (68, false, '2020-03-27 11:57:57', 'Also', true, 'studentin1', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (69, false, '2020-03-27 11:58:06', 'da kannst', true, 'studentin1', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (70, false, '2020-03-27 11:58:13', 'du am', true, 'studentin1', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (71, false, '2020-03-27 11:58:22', 'besten', true, 'studentin1', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (72, false, '2020-03-27 11:58:28', 'dir die', true, 'studentin1', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (73, false, '2020-03-27 11:58:35', 'Folie', true, 'studentin1', 1, 17);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (74, false, '2020-03-27 12:29:14', 'Es beginnt am Montag um 10:30 in 5C', true, 'orga2', 4, 18);
INSERT INTO foren.post (id, anonymous, date_time, text, visible, author_username, forum_id, thread_id) VALUES (75, false, '2020-03-27 12:30:41', 'sfdgsgsdfs', false, 'studentin1', 2, 19);
INSERT INTO foren.roles (username, role, forum_id) VALUES ('orga1', 'ADMIN', 1);
INSERT INTO foren.roles (username, role, forum_id) VALUES ('orga1', 'ADMIN', 2);
INSERT INTO foren.roles (username, role, forum_id) VALUES ('orga2', 'ADMIN', 3);
INSERT INTO foren.roles (username, role, forum_id) VALUES ('orga2', 'ADMIN', 4);
INSERT INTO foren.roles (username, role, forum_id) VALUES ('studentin1', 'MODERATOR', 1);
INSERT INTO foren.roles (username, role, forum_id) VALUES ('studentin2', 'MODERATOR', 2);
INSERT INTO foren.thread (id, anonymous, description, moderated, title, visible, author_username, forum_id, topic_id) VALUES (1, false, 'Die Klausur findet am 05.05.2020 statt
Die Räume werden noch bekannt gegeben.', true, 'Klausurtermin', true, 'orga1', 1, 1);
INSERT INTO foren.thread (id, anonymous, description, moderated, title, visible, author_username, forum_id, topic_id) VALUES (2, false, 'DIe Räume werden wie üblich nach Nachnamen verteilt :
A-M : Hörsaal 5D
N-Z : Hörsaal 5C', true, 'Raumverteilung', true, 'studentin1', 1, 1);
INSERT INTO foren.thread (id, anonymous, description, moderated, title, visible, author_username, forum_id, topic_id) VALUES (3, false, 'Aufgrund von Corona wird die Klausur auf den 08.08.2020 verschoben', true, 'Verschiebung Klausurtermin', true, 'orga1', 1, 1);
INSERT INTO foren.thread (id, anonymous, description, moderated, title, visible, author_username, forum_id, topic_id) VALUES (4, false, 'War die erste Woche noch im Urlaub.', false, 'Wo finde ich das Material ?', true, 'studentin2', 1, 2);
INSERT INTO foren.thread (id, anonymous, description, moderated, title, visible, author_username, forum_id, topic_id) VALUES (5, false, 'Kann mir da jemand helfen ?', false, 'Brauche Hilfe bei Quicksort', true, 'studentin3', 1, 2);
INSERT INTO foren.thread (id, anonymous, description, moderated, title, visible, author_username, forum_id, topic_id) VALUES (6, false, 'Komme da einfach nicht auf den Baum ?', false, 'Blatt 7 Nr. 4', true, 'studentin2', 1, 2);
INSERT INTO foren.thread (id, anonymous, description, moderated, title, visible, author_username, forum_id, topic_id) VALUES (7, true, 'Zum Glück ist das hier anonym !', false, 'Was sind acht Hobbits? Ein Hobbyte!', true, 'studentin1', 2, 3);
INSERT INTO foren.thread (id, anonymous, description, moderated, title, visible, author_username, forum_id, topic_id) VALUES (8, true, 'Did you hear about the monkeys who shared an Amazon account? They were Prime mates.', false, 'Amazon', true, 'studentin3', 2, 3);
INSERT INTO foren.thread (id, anonymous, description, moderated, title, visible, author_username, forum_id, topic_id) VALUES (9, true, 'We’ll we’ll we’ll…if it isn’t autocorrect.', false, 'Für alle die Englisch können', true, 'orga1', 2, 3);
INSERT INTO foren.thread (id, anonymous, description, moderated, title, visible, author_username, forum_id, topic_id) VALUES (10, false, 'nano ist beste !', false, 'NANO !!', true, 'studentin3', 2, 4);
INSERT INTO foren.thread (id, anonymous, description, moderated, title, visible, author_username, forum_id, topic_id) VALUES (11, false, 'Nick hat mich überzeugt, IntelliJ kann einfach alles !', false, 'IntelliJ ftw !', true, 'studentin1', 2, 4);
INSERT INTO foren.thread (id, anonymous, description, moderated, title, visible, author_username, forum_id, topic_id) VALUES (12, false, 'Wenn der Tonfall sich hier nicht ändert, wird moderiert ! ', false, 'Umgangsformen !', true, 'orga1', 2, 4);
INSERT INTO foren.thread (id, anonymous, description, moderated, title, visible, author_username, forum_id, topic_id) VALUES (14, false, 'Mir ist langweilig', false, 'Hallo wie gehts', true, 'studentin2', 3, 5);
INSERT INTO foren.thread (id, anonymous, description, moderated, title, visible, author_username, forum_id, topic_id) VALUES (17, false, 'Also', false, 'Hab hier mal ne längere Frage', true, 'studentin2', 1, 7);
INSERT INTO foren.thread (id, anonymous, description, moderated, title, visible, author_username, forum_id, topic_id) VALUES (18, false, 'Es beginnt am Montag um 10:30 in 5C', false, 'Seminarbeginn', true, 'orga2', 4, 8);
INSERT INTO foren.thread (id, anonymous, description, moderated, title, visible, author_username, forum_id, topic_id) VALUES (19, false, 'sfdgsgsdfs', true, 'fdafdsaf', false, 'studentin1', 2, 9);
INSERT INTO foren.topic (id, anonymous, description, moderated, title, forum_id) VALUES (1, false, 'Hier finden SIe alle organisatorischen Informationen zur Klausur.', true, 'Informationen zur Klausur', 1);
INSERT INTO foren.topic (id, anonymous, description, moderated, title, forum_id) VALUES (2, false, 'Hier könnt ihr alle Fragen bzgl. des Inhaltes der Vorlesung stellen.', false, 'Inhaltliche Fragen', 1);
INSERT INTO foren.topic (id, anonymous, description, moderated, title, forum_id) VALUES (3, true, 'Hier könnt ihr anonym Witze posten.', false, 'Schlechte Witze aus dem Internet', 2);
INSERT INTO foren.topic (id, anonymous, description, moderated, title, forum_id) VALUES (4, false, 'Mit welchem Editor/IDE sollen wir die Übungen vorführen ?', false, 'Editorwahl', 2);
INSERT INTO foren.topic (id, anonymous, description, moderated, title, forum_id) VALUES (5, false, 'hier könnt ihr alle Fragen zu den Abgaben stellen', false, 'Fragen zu den Blättern', 3);
INSERT INTO foren.topic (id, anonymous, description, moderated, title, forum_id) VALUES (7, false, 'hier wird das Paging demonstriert.', false, 'Fragen zum Paging', 1);
INSERT INTO foren.topic (id, anonymous, description, moderated, title, forum_id) VALUES (8, false, 'Hier steht wichtiger Orga Stuff', false, 'Ankündigungen', 4);
INSERT INTO foren.topic (id, anonymous, description, moderated, title, forum_id) VALUES (9, false, 'Hier steht alles wichtige drin', true, 'Ankündigungen', 2);
INSERT INTO foren.user (username, email, name) VALUES ('studentin', 'stud0@gmail.com', 'Bernhard Zimmermann');
INSERT INTO foren.user (username, email, name) VALUES ('studentin1', 'stud1@gmail.com', 'Lisa Schmidt');
INSERT INTO foren.user (username, email, name) VALUES ('studentin2', 'stud2@gmail.com', 'Anna Müller');
INSERT INTO foren.user (username, email, name) VALUES ('studentin3', 'stud3@gmail.com', 'Martin Esel');
INSERT INTO foren.user (username, email, name) VALUES ('orga', 'orga0@gmail.com', 'Peter Organisatorus');
INSERT INTO foren.user (username, email, name) VALUES ('orga1', 'orga1@gmail.com', 'Meike Brummer');
INSERT INTO foren.user (username, email, name) VALUES ('orga2', 'orga2@gmail.com', 'Marianne Baum');
INSERT INTO foren.user (username, email, name) VALUES ('actuator', 'actuator@gmail.com', 'Heinrich Heine');
INSERT INTO foren.user (username, email, name) VALUES ('Unbekannt', '', 'Default');
INSERT INTO foren.user_forum (username, id) VALUES ('actuator', 1);
INSERT INTO foren.user_forum (username, id) VALUES ('actuator', 2);
INSERT INTO foren.user_forum (username, id) VALUES ('actuator', 3);
INSERT INTO foren.user_forum (username, id) VALUES ('actuator', 4);
INSERT INTO foren.user_forum (username, id) VALUES ('actuator', 5);
INSERT INTO foren.user_forum (username, id) VALUES ('orga1', 1);
INSERT INTO foren.user_forum (username, id) VALUES ('orga1', 2);
INSERT INTO foren.user_forum (username, id) VALUES ('orga2', 3);
INSERT INTO foren.user_forum (username, id) VALUES ('orga2', 4);
INSERT INTO foren.user_forum (username, id) VALUES ('studentin1', 1);
INSERT INTO foren.user_forum (username, id) VALUES ('studentin1', 2);
INSERT INTO foren.user_forum (username, id) VALUES ('studentin1', 3);
INSERT INTO foren.user_forum (username, id) VALUES ('studentin1', 4);
INSERT INTO foren.user_forum (username, id) VALUES ('studentin2', 1);
INSERT INTO foren.user_forum (username, id) VALUES ('studentin2', 2);
INSERT INTO foren.user_forum (username, id) VALUES ('studentin2', 3);
INSERT INTO foren.user_forum (username, id) VALUES ('studentin2', 5);
INSERT INTO foren.user_forum (username, id) VALUES ('studentin3', 1);
INSERT INTO foren.user_forum (username, id) VALUES ('studentin3', 2);
INSERT INTO foren.user_forum (username, id) VALUES ('studentin3', 3);
INSERT INTO foren.user_forum (username, id) VALUES ('studentin3', 4);