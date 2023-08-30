CREATE TABLE car
(
    id    BIGSERIAL PRIMARY KEY,
    brand VARCHAR(32)                      NOT NULL,
    model VARCHAR(32)                      NOT NULL,
    price DECIMAL(10, 2) CHECK (price > 0) NOT NULL
);

CREATE TABLE person
(
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(128)        NOT NULL,
    age            INT CHECK (age > 0) NOT NULL,
    driver_license BOOLEAN DEFAULT false,
    car_id         BIGINT REFERENCES car (id)
);


SELECT s.name AS student_name,
       s.age  AS student_age,
       f.name AS faculty_name
FROM student s
         JOIN faculty f
              ON f.id = s.faculty_id;


SELECT s.name AS name,
       s.age  AS age,
       f.name AS faculty,
       a.id   AS avatar_id
FROM student s
         JOIN faculty f
              ON f.id = s.faculty_id
         JOIN avatar a
              ON s.id = a.student_id;