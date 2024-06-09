package main.java.com.alerts.strategy;

import main.java.com.alerts.Alert;
import main.java.com.alerts.AlertGenerator;
import main.java.com.data_management.DataStorage;
import main.java.com.data_management.Patient;
import main.java.com.data_management.PatientRecord;
import java.util.List;
import java.util.stream.Collectors;

public class OxygenSaturationStrategy extends AlertGenerator implements AlertStrategy {

    private final DataStorage dataStorage;
    private static final long TEN_MINUTES_INTERVAL_MS = 600000;

    public OxygenSaturationStrategy(DataStorage dataStorage) {
        super(dataStorage);
        this.dataStorage = dataStorage;
    }

    @Override
    public void checkAlert(Patient patient) {
        List<PatientRecord> records = fetchPatientRecords(patient);

        if (records.isEmpty()) {
            System.out.println("No records found for the specified time window.");
            return;
        }

        List<PatientRecord> saturationRecords = filterRecordsByType(records, "Saturation");

        evaluateSaturation(saturationRecords, patient);
    }

    private List<PatientRecord> fetchPatientRecords(Patient patient) {
        long startTime = System.currentTimeMillis() - TEN_MINUTES_INTERVAL_MS;
        long endTime = System.currentTimeMillis();
        return dataStorage.getRecords(patient.getPatientID(), startTime, endTime);
    }

    private List<PatientRecord> filterRecordsByType(List<PatientRecord> records, String type) {
        return records.stream()
                      .filter(record -> type.equals(record.getMeasurementValue()))
                      .collect(Collectors.toList());
    }

    private void evaluateSaturation(List<PatientRecord> records, Patient patient) {
        checkLowSaturation(records, patient);
        checkRapidDrop(records, patient);
    }

    private void checkLowSaturation(List<PatientRecord> records, Patient patient) {
        records.stream()
               .filter(record -> record.getMeasurementValue() < 92)
               .forEach(record -> triggerAlert(new Alert(
                       String.valueOf(patient.getPatientID()), 
                       "Low Saturation Alert", 
                       record.getTimestamp())));
    }

    private void checkRapidDrop(List<PatientRecord> records, Patient patient) {
        for (int i = 1; i < records.size(); i++) {
            double previousValue = records.get(i - 1).getMeasurementValue();
            double currentValue = records.get(i).getMeasurementValue();
            double dropPercentage = 100.0 * (previousValue - currentValue) / previousValue;

            if (dropPercentage >= 5) {
                triggerAlert(new Alert(
                        String.valueOf(patient.getPatientID()), 
                        "Saturation Rapid Drop Alert", 
                        records.get(i).getTimestamp()));
            }
        }
    }
}