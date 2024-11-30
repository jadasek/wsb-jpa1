insert into address (id, address_line1, address_line2, city, postal_code)
            values (1, 'xx', 'yy', 'city', '62-030');



-- Relacja jest dwustronna: Doctor jest rodzicem, Visit dzieckiem.
-- Zmiany w DoctorEntity (np. usunięcie lekarza) kaskadowo usuwają wizyty (CascadeType.ALL).

INSERT INTO DOCTOR (id, FIRST_NAME, last_Name, telephone_Number, email, doctor_Number, specialization)
VALUES (1, 'John', 'Doe', '123456789', 'john.doe@example.com', 'D123', 'CARDIOLOGY');

INSERT INTO DOCTOR (id, FIRST_NAME, last_Name, telephone_Number, email, doctor_Number, specialization)
VALUES (2, 'Anna', 'Smith', '987654321', 'anna.smith@example.com', 'D456', 'ORTHOPEDICS');


--Relacja jest jednokierunkowa od strony dziecka (Visit wskazuje na Patient).


INSERT INTO PATIENT (id, first_Name, last_Name, telephone_Number, email, patient_Number, date_Of_Birth)
VALUES 
    (1, 'Alice', 'Johnson', '555123456', 'alice.johnson@example.com', 'PAT001', '1990-04-15'),
    (2, 'Bob', 'Brown', '555654321', 'bob.brown@example.com', 'PAT002', '1985-08-22');


INSERT INTO VISIT (id, description, time, doctor_id,patient_id)
VALUES (1, 'Regular Checkup', '2024-12-01 10:00:00', 1, 1);

INSERT INTO VISIT (id, description, time, doctor_id, patient_id)
VALUES (2, 'Cardiac Surgery', '2024-12-03 14:00:00', 1, 1);

INSERT INTO VISIT (id, description, time, doctor_id, patient_id)
VALUES (3, 'Fracture Treatment', '2024-12-02 11:30:00', 2, 2);

INSERT INTO VISIT (id, description, time, doctor_id, patient_id)
VALUES (4, 'Follow-up Appointment', '2024-12-05 09:00:00', 2, 2);


INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (1, 'Blood Test', 'USG',1);
INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (2, 'X-ray', 'USG',1);
INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (3, 'Fracture Cast', 'USG',3);
INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (4, 'Pain Relief', 'USG',3);


-- Przykład 2 w 1, zastosowania dla relacji: PATIENT TO VISIT i DOCTOR TO VISIT
/*
SELECT 
    V.id AS visit_id,
    V.description AS visit_description,
    V.time AS visit_time,
    D.first_Name AS doctor_first_name,
    D.last_Name AS doctor_last_name,
    P.email AS patient_email
FROM 
    VISIT V
JOIN 
    DOCTOR D ON V.doctor_id = D.id
JOIN 
    PATIENT P ON V.patient_id = P.id;

*/





