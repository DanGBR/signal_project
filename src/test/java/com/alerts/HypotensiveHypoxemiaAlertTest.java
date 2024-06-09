import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.com.cardio_generator.generators.AlertGenerator;
import main.java.com.data_management.DataStorage;
import main.java.com.data_management.Patient;

public class HypotensiveHypoxemiaAlertTest {

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
    public void testHypotensiveHypoxemiaAlertTrigger() {
        patient.addRecord(new PatientRecord("2024-06-01T10:00:00", 88, 60, 90));
        alertGenerator.evaluateData();
        assertTrue(alertGenerator.isAlertTriggered("HypotensiveHypoxemiaAlert"));
    }

    @Test
    public void testHypotensiveHypoxemiaAlertNoTrigger() {
        patient.addRecord(new PatientRecord("2024-06-01T10:00:00", 120, 80, 93));
        alertGenerator.evaluateData();
        assertFalse(alertGenerator.isAlertTriggered("HypotensiveHypoxemiaAlert"));
    }
}