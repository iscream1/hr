--if you need a new db, and run the script from psql:
-- CREATE DATABASE hospital;
-- \connect hospital;
CREATE SCHEMA hospital;

CREATE TYPE hospital.gender_enum AS ENUM ('MAN', 'WOMAN', 'OTHER');

CREATE TABLE hospital.patient
(
    id             bigserial            NOT NULL,
    first_name     varchar(255)         NOT NULL,
    last_name      varchar(255)         NOT NULL,
    mothers_name   varchar(255)         NULL DEFAULT NULL,
    gender         hospital.gender_enum NOT NULL,
    birth_date     date                 NOT NULL,
    death_date     date                 NULL DEFAULT NULL,
    place_of_birth varchar(255)         NOT NULL,
    phone_number   varchar(255)         NULL DEFAULT NULL,
    email          varchar(100)         NULL DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE hospital.address
(
    id         bigserial   NOT NULL,
    county     varchar(30) NOT NULL,
    postcode   varchar(20) NOT NULL,
    city       varchar(50) NOT NULL,
    street     varchar(50) NOT NULL,
    house_num  varchar(10) NOT NULL, --can contain letters
    patient_id bigint      NOT NULL, --a patient can have multiple addresses
    PRIMARY KEY (id)
);
ALTER TABLE hospital.address
    ADD CONSTRAINT address_patient_id FOREIGN KEY (patient_id) REFERENCES hospital.patient (id);
CREATE INDEX idx_address_patient_id ON hospital.address (patient_id);

CREATE TYPE hospital.connection_enum AS ENUM ('MOTHER', 'FATHER', 'CHILD', 'GRANDMOTHER',
    'GRANDFATHER', 'GRANDCHILD', 'SIBLING', 'NEIGHBOR', 'COLLEAGUE', 'BOSS');

CREATE TABLE hospital.connection
(
    id                   bigserial                NOT NULL,
    type                 hospital.connection_enum NOT NULL,
    quality              varchar(20)              NOT NULL,
    distance             numeric(2)               NOT NULL,
    start_date           date                     NOT NULL,
    end_date             date                     NULL DEFAULT NULL,
    patient_id           bigint                   NOT NULL,
    connected_patient_id bigint                   NOT NULL,
    PRIMARY KEY (id)
);
ALTER TABLE hospital.connection
    ADD CONSTRAINT check_distance CHECK (distance <= 10 AND distance >= 0);
ALTER TABLE hospital.connection
    ADD CONSTRAINT connection_patient_id FOREIGN KEY (patient_id) REFERENCES hospital.patient (id);
ALTER TABLE hospital.connection
    ADD CONSTRAINT connection_connected_patient_id FOREIGN KEY (connected_patient_id) REFERENCES hospital.patient (id);
CREATE INDEX idx_connection_patient_id ON hospital.connection (patient_id);
CREATE INDEX idx_connection_connected_patient_id ON hospital.connection (connected_patient_id);

CREATE OR REPLACE FUNCTION start_date_update() RETURNS TRIGGER AS
$$
DECLARE
    x date :=
        (SELECT p.birth_date
         FROM hospital.patient p
         WHERE p.id = new.patient_id);
BEGIN
    UPDATE hospital.connection
    SET start_date = x
    WHERE NEW.start_date <= x
      AND connection.id = NEW.id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER connection_update_on_insert
    AFTER INSERT
    ON hospital.connection
    FOR EACH ROW
EXECUTE FUNCTION start_date_update();

UPDATE hospital.patient child
SET mothers_name = (SELECT CONCAT(mom.first_name, ' ', mom.last_name)
                    FROM hospital.patient mom
                             JOIN hospital.connection c
                                  ON c.patient_id = mom.id
                                      AND c.connected_patient_id = child.id
                                      AND c.type = 'MOTHER')
WHERE child.mothers_name IS NULL;
