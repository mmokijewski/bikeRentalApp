INSERT INTO cyclist(ID, FIRST_NAME, LAST_NAME, CREATE_DATE, UPDATE_DATE)
VALUES (1, 'Jan', 'Kowalski', '2023-08-17 17:55:26', '2023-08-17 18:55:26');
INSERT INTO cyclist(ID, FIRST_NAME, LAST_NAME, CREATE_DATE, UPDATE_DATE)
VALUES (2, 'Mateusz', 'Powolny', '2023-08-18 17:55:26', NULL);
INSERT INTO cyclist(ID, FIRST_NAME, LAST_NAME, CREATE_DATE, UPDATE_DATE)
VALUES (3, 'Zbigniew', 'Szybki', '2023-08-19 17:55:26', '2023-08-21 17:55:26');

INSERT INTO bike(ID, BRAND, CREATE_DATE, UPDATE_DATE)
VALUES (1, 'Specialized', '2023-08-18 17:55:26', '2023-08-17 17:55:26');
INSERT INTO bike(ID, BRAND, CREATE_DATE, UPDATE_DATE)
VALUES (2, 'Romet', '2023-08-19 17:55:26', '2023-08-17 17:55:26');
INSERT INTO bike(ID, BRAND, CREATE_DATE, UPDATE_DATE)
VALUES (3, 'Merida', '2023-08-21 17:55:26', '2023-08-17 17:55:26');

INSERT INTO reservation(ID, BIKE_ID, CYCLIST_ID, START_DATE, END_DATE, CREATE_DATE, UPDATE_DATE)
VALUES (1, 2, 2, '2023-11-13 11:49:00', '2023-11-13 12:20:48', '2023-11-13 11:49:00', NULL);
INSERT INTO reservation(ID, BIKE_ID, CYCLIST_ID, START_DATE, END_DATE, CREATE_DATE, UPDATE_DATE)
VALUES (2, 2, 2, '2023-11-13 12:54:00', '2023-11-13 13:52:00', '2023-11-13 12:54:00', NULL);
INSERT INTO reservation(ID, BIKE_ID, CYCLIST_ID, START_DATE, END_DATE, CREATE_DATE, UPDATE_DATE)
VALUES (3, 3, 1, '2023-11-16 14:00:00', '2023-11-16 14:30:00', '2023-11-16 14:00:00', NULL);