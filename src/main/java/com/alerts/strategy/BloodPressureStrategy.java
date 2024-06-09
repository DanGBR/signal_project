package main.java.com.alerts.strategy;

import main.java.com.alerts.Alert;
import main.java.com.alerts.AlertGenerator;
import main.java.com.data_management.DataStorage;
import main.java.com.data_management.Patient;
import main.java.com.data_management.PatientRecord;
import java.util.List;
import java.util.stream.Collectors;

public class BloodPressureStrategy extends AlertGenerator implements AlertStrategy {
    private final DataStorage dataStorage;

    public BloodPressureStrategy(DataStorage dataStorage) {
        super(dataStorage);
        this.dataStorage = dataStorage;
    }

    @Override
    public void checkAlert(Patient patient) {
        long startTime = System.currentTimeMillis() - DAY_INTERVAL_MS;
        long endTime = System.currentTimeMillis();
        List<PatientRecord> records = dataStorage.getRecords(patient.getPatientID(), startTime, endTime);

        if (records.isEmpty()) {
            System.out.println("No records found for the specified time window.");
            return;
        }

        List<PatientRecord> systolicRecords = filterRecordsByType(records, "SystolicPressure");
        List<PatientRecord> diastolicRecords = filterRecordsByType(records, "DiastolicPressure");

        evaluateTrendsAndThresholds(systolicRecords, patient, "Systolic Blood Pressure", 90, 180);
        evaluateTrendsAndThresholds(diastolicRecords, patient, "Diastolic Blood Pressure", 60, 120);
    }

    private List<PatientRecord> filterRecordsByType(List<PatientRecord> records, String type) {
        return records.stream()
                      .filter(record -> type.equals(record.getMeasurementValue()))
                      .collect(Collectors.toList());
    }

    private void evaluateTrendsAndThresholds(List<PatientRecord> records, Patient patient, String type, int lowerThreshold, int upperThreshold) {
        if (records.size() < 3) return;

        checkTrend(records, patient, type);
        checkCriticalThresholds(records, patient, type, lowerThreshold, upperThreshold);
    }

    private void checkTrend(List<PatientRecord> records, Patient patient, String type) {
        boolean decreasingTrend = true;
        boolean increasingTrend = true;

        for (int i = 0; i < records.size() - 1; i++) {
            double currentValue = records.get(i).getMeasurementValue();
            double nextValue = records.get(i + 1).getMeasurementValue();

            if (currentValue - nextValue <= 10) decreasingTrend = false;
            if (nextValue - currentValue <= 10) increasingTrend = false;
        }

        String patientID = String.valueOf(patient.getPatientID());
        long timestamp = records.get(records.size() - 1).getTimestamp();
        if (decreasingTrend) triggerAlert(new Alert(patientID, type + " Decreasing Trend Alert", timestamp));
        if (increasingTrend) triggerAlert(new Alert(patientID, type + " Increasing Trend Alert", timestamp));
    }

    private void checkCriticalThresholds(List<PatientRecord> records, Patient patient, String type, int lowerThreshold, int upperThreshold) {
        for (PatientRecord record : records) {
            double value = record.getMeasurementValue();
            long timestamp = record.getTimestamp();
            String patientID = String.valueOf(patient.getPatientID());

            if (value > upperThreshold) {
                triggerAlert(new Alert(patientID, type + " Critical High Alert: " + type + " has exceeded " + upperThreshold + " mmHg", timestamp));
            }
            if (value < lowerThreshold) {
                triggerAlert(new Alert(patientID, type + " Critical Low Alert: " + type + " has dropped below " + lowerThreshold + " mmHg", timestamp));
            }
        }
    }
}
