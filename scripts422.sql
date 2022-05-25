CREATE TABLE person
(
    id                  int          NOT NULL,
    name                varchar(255) NOT NULL,
    age                 int          NOT NULL,
    has_drivers_license bool         NOT NULL,
    PRIMARY KEY (id),
);

CREATE TABLE car
(
    id    int          NOT NULL,
    brand varchar(255) NOT NULL,
    model varchar(255) NOT NULL,
    price int          NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE person_car
(
    id        int NOT NULL,
    person_id int NOT NULL,
    car_id    int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (person_id) REFERENCES person (id),
    FOREIGN KEY (car_id) REFERENCES car (id)
);