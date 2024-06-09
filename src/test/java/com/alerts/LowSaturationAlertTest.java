package test.java.com.alerts;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.com.cardio_generator.generators.AlertGenerator;
import main.java.com.data_management.DataStorage;
import main.java.com.data_management.Patient;

public class LowSaturationAlertTest {

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
    public void testLowSaturationAlertTrigger() {
        patient.addRecord(new PatientRecord("2024-06-01T10:00:00", 91));
        alertGenerator.evaluateData();
        assertTrue(alertGenerator.isAlertTriggered("LowSaturationAlert"));
    }

    @Test
    public void testLowSaturationAlertNoTrigger() {
        patient.addRecord(new PatientRecord("2024-06-01T10:00:00", 93));
        alertGenerator.evaluateData();
        assertFalse(alertGenerator.isAlertTriggered("LowSaturationAlert"));
    }
}
