package test.java.com.alerts;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.com.cardio_generator.generators.AlertGenerator;
import main.java.com.data_management.DataStorage;
import main.java.com.data_management.Patient;

public class TrendAlertTest {

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
    public void testIncreasingBloodPressureTrigger() {
        patient.addRecord(new PatientRecord("2024-06-01T10:00:00", 120, 80));
        patient.addRecord(new PatientRecord("2024-06-01T10:10:00", 131, 85));
        patient.addRecord(new PatientRecord("2024-06-01T10:20:00", 142, 90));
        alertGenerator.evaluateData();
        assertTrue(alertGenerator.isAlertTriggered("TrendAlert"));
    }

    @Test
    public void testIncreasingBloodPressureNoTrigger() {
        patient.addRecord(new PatientRecord("2024-06-01T10:00:00", 120, 80));
        patient.addRecord(new PatientRecord("2024-06-01T10:10:00", 125, 85));
        patient.addRecord(new PatientRecord("2024-06-01T10:20:00", 130, 90));
        alertGenerator.evaluateData();
        assertFalse(alertGenerator.isAlertTriggered("TrendAlert"));
    }

    @Test
    public void testDecreasingBloodPressureTrigger() {
        patient.addRecord(new PatientRecord("2024-06-01T10:00:00", 142, 90));
        patient.addRecord(new PatientRecord("2024-06-01T10:10:00", 130, 85));
        patient.addRecord(new PatientRecord("2024-06-01T10:20:00", 118, 80));
        alertGenerator.evaluateData();
        assertTrue(alertGenerator.isAlertTriggered("TrendAlert"));
    }

    @Test
    public void testDecreasingBloodPressureNoTrigger() {
        patient.addRecord(new PatientRecord("2024-06-01T10:00:00", 142, 90));
        patient.addRecord(new PatientRecord("2024-06-01T10:10:00", 137, 85));
        patient.addRecord(new PatientRecord("2024-06-01T10:20:00", 132, 80));
        alertGenerator.evaluateData();
        assertFalse(alertGenerator.isAlertTriggered("TrendAlert"));
    }
}