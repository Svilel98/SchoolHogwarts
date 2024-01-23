CREATE TABLE man
(person_ID INT PRIMARY KEY, name VARCHAR, age INTEGER, license BOOLEAN);
CREATE TABLE car
(car_ID INT PRIMARY KEY, mark VARCHAR, model VARCHAR, price NUMERIC);
CREATE TABLE ownership
(person_ID INT,
 car_ID INT,
 FOREIGN KEY(person_ID) REFERENCES man(person_ID),
 FOREIGN KEY (car_ID) REFERENCES car(car_ID)
);