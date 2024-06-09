package test.java.com.alerts;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.com.cardio_generator.generators.AlertGenerator;
import main.java.com.data_management.DataStorage;
import main.java.com.data_management.Patient;

import main.java.com.cardio_generator.generators.AlertGenerator;

public class RapidDropAlertTest {

    private Patient patient;
    private AlertGenerator alertGenerator;

    @BeforeEach
    public void setUp() {
        patient = new Patient();
        DataStorage dataStorage = new DataStorage();
        dataStorage.addPatient(patient);
        alertGenerator = new AlertGenerator(dataStorage);
    }

    @Test
    public void testRapidDropAlertTrigger() {
        patient.addRecord(new PatientRecord("2024-06-01T10:00:00", 98));
        patient.addRecord(new PatientRecord("2024-06-01T10:10:00", 92));
        alertGenerator.evaluateData();
        assertTrue(alertGenerator.isAlertTriggered("RapidDropAlert"));
    }

    @Test
    public void testRapidDropAlertNoTrigger() {
        patient.addRecord(new PatientRecord("2024-06-01T10:00:00", 98));
        patient.addRecord(new PatientRecord("2024-06-01T10:10:00", 96));
        alertGenerator.evaluateData();
        assertFalse(alertGenerator.isAlertTriggered("RapidDropAlert"));
    }
}