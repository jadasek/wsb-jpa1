INSERT INTO ADDRESS (id, address_line1, address_line2, city, postal_code)
VALUES 
    (1, '123 Main St', '123 Main St', 'Springfield', '62701'),
    (2, '456 Elm St', '456 Elm St', 'Metropolis', '10001'),
    (3, '789 Oak St','789 Oak St',  'Gotham', '07030'),
    (4, '012 Pine St', '012 Pine St', 'Detroit', '67819');

-- Relacja jest dwustronna: Doctor jest rodzicem, Visit dzieckiem.
-- Zmiany w DoctorEntity (np. usunięcie lekarza) kaskadowo usuwają wizyty (CascadeType.ALL).

INSERT INTO DOCTOR (id, FIRST_NAME, last_Name, telephone_Number, email, doctor_Number, specialization,address_id)
VALUES (1, 'John', 'Doe', '123456789', 'john.doe@example.com', 'D123', 'CARDIOLOGY',3);

INSERT INTO DOCTOR (id, FIRST_NAME, last_Name, telephone_Number, email, doctor_Number, specialization,address_id)
VALUES (2, 'Anna', 'Smith', '987654321', 'anna.smith@example.com', 'D456', 'ORTHOPEDICS',4);


--Relacja jest jednokierunkowa od strony dziecka (Visit wskazuje na Patient).


INSERT INTO PATIENT (id, first_Name, last_Name, telephone_Number, email, patient_Number, date_Of_Birth,address_id, insurance_number)
VALUES 
    (1, 'Alice', 'Johnson', '555123456', 'alice.johnson@example.com', 'PAT001', '1990-04-15',1,4125162),
    (2, 'Bob', 'Brown', '555654321', 'bob.brown@example.com', 'PAT002', '1985-08-22',2,91732617);


INSERT INTO VISIT (id, description, time, doctor_id,patient_id)
VALUES (1, 'Regular Checkup', '2024-12-01 10:00:00', 1, 1);

INSERT INTO VISIT (id, description, time, doctor_id, patient_id)
VALUES (2, 'Cardiac Surgery', '2024-12-03 14:00:00', 1, 1);

INSERT INTO VISIT (id, description, time, doctor_id, patient_id)
VALUES (3, 'Fracture Treatment', '2024-12-02 11:30:00', 2, 2);

INSERT INTO VISIT (id, description, time, doctor_id, patient_id)
VALUES (4, 'Follow-up Appointment', '2024-12-05 09:00:00', 2, 2);

INSERT INTO VISIT (id, description, time, doctor_id, patient_id)
VALUES 
    (5, 'Routine Checkup', '2024-12-06 10:30:00', 1, 1),
    (6, 'Cardiac Consultation', '2024-12-07 15:00:00', 1, 2),
    (7, 'Orthopedic Follow-up', '2024-12-08 12:00:00', 2, 1),
    (8, 'Physical Therapy', '2024-12-09 09:30:00', 2, 2),
    (9, 'Heart Ultrasound', '2024-12-10 14:00:00', 1, 1),
    (10, 'Bone Density Test', '2024-12-11 11:00:00', 2, 2),
    (11, 'Emergency Cardiology Visit', '2024-12-12 16:00:00', 1, 2),
    (12, 'Post-Surgery Evaluation', '2024-12-13 10:00:00', 2, 1),
    (13, 'Annual Checkup', '2024-12-14 08:30:00', 1, 1),
    (14, 'Rehabilitation Session', '2024-12-15 13:00:00', 2, 2);


INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (1, 'Blood Test', 'USG', 1);
INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (2, 'X-ray', 'RTG', 2);
INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (3, 'Fracture Cast', NULL, 3);
INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (4, 'Pain Relief', NULL, 4);
INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (5, 'Physical Therapy Session', NULL, 5);
INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (6, 'Cardiac Stress Test', 'EKG', 6);
INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (7, 'Orthopedic Brace Fitting', NULL, 7);
INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (8, 'Rehabilitation Exercise', NULL, 8);
INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (9, 'Heart Ultrasound Scan', 'USG', 9);
INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (10, 'Bone Density Scan', 'RTG', 10);
INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (11, 'Emergency Pain Management', NULL, 11);
INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (12, 'Post-Surgery Wound Care', NULL, 12);
INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (13, 'Comprehensive Health Check', NULL, 13);
INSERT INTO MEDICAL_TREATMENT (id, description, Type, visit_id) VALUES (14, 'Rehabilitation Therapy', NULL, 14);





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




-- Przykład dla MEDICAL_TREATMENT do VISIT


/*
SELECT 
    V.id AS visit_id,
    V.description AS visit_description,
    V.time AS visit_time,
    D.first_name AS doctor_first_name,
    D.last_name AS doctor_last_name,
    P.first_name AS patient_first_name,
    P.last_name AS patient_last_name,
    P.email AS patient_email,
    M.description AS treatment_describtion,
M.type AS treatment_type
FROM 
    VISIT V
JOIN 
    DOCTOR D ON V.doctor_id = D.id
JOIN 
    PATIENT P ON V.patient_id = P.id
JOIN 
    MEDICAL_TREATMENT M ON V.id = M.visit_id;


*/

--Przykład dla adresów

/*
SELECT 
    P.id AS patient_id,
    P.first_name AS patient_first_name,
    P.last_name AS patient_last_name,
    A.address_line1 AS patient_address_line1,
    A.city AS patient_city,
    A.postal_code AS patient_postal_code
FROM 
    PATIENT P
JOIN 
    ADDRESS A ON P.address_id = A.id;

    */