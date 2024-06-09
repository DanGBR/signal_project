package test.java.com.alerts;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.com.cardio_generator.generators.AlertGenerator;
import main.java.com.data_management.DataStorage;
import main.java.com.data_management.Patient;

public class CriticalThresholdAlertTest {

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
    public void testCriticalThresholdAlertTrigger() {
        patient.addRecord(new PatientRecord("2024-06-01T10:00:00", 185, 125));
        alertGenerator.evaluateData();
        assertTrue(alertGenerator.isAlertTriggered("CriticalThresholdAlert"));
    }

    @Test
    public void testCriticalThresholdAlertNoTrigger() {
        patient.addRecord(new PatientRecord("2024-06-01T10:00:00", 140, 85));
        alertGenerator.evaluateData();
        assertFalse(alertGenerator.isAlertTriggered("CriticalThresholdAlert"));
    }
}