INSERT INTO hospital.patient (first_name, last_name, mothers_name, gender, birth_date, death_date,
                              place_of_birth, phone_number, email)
VALUES ('Child1', 'Patient', null, 'MAN', now(), null, 'Pécs', '+123456', 'child@hospital.com');
INSERT INTO hospital.patient (first_name, last_name, mothers_name, gender, birth_date, death_date,
                              place_of_birth, phone_number, email)
VALUES ('Mom2', 'Patient', null, 'WOMAN', '2000-03-03', null, 'Pécs', '+123456',
        'child@hospital.com');

INSERT INTO hospital.connection (type, quality, distance, start_date, end_date, patient_id,
                                 connected_patient_id)
VALUES ('CHILD', 'positive', 0, '2000-03-03', null, 1, 2);
INSERT INTO hospital.connection (type, quality, distance, start_date, end_date, patient_id,
                                 connected_patient_id)
VALUES ('MOTHER', 'positive', 0, '2000-03-02', null, 2, 1);
