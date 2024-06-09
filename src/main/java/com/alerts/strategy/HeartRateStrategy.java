package main.java.com.alerts.strategy;

import main.java.com.alerts.Alert;
import main.java.com.alerts.AlertGenerator;
import main.java.com.data_management.DataStorage;
import main.java.com.data_management.Patient;
import main.java.com.data_management.PatientRecord;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HeartRateStrategy extends AlertGenerator implements AlertStrategy {

    private final DataStorage dataStorage;

    public HeartRateStrategy(DataStorage dataStorage) {
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

        List<PatientRecord> ECGRecords = filterRecordsByType(records, "ECG");

        evaluateHeartRate(ECGRecords, patient);
    }

    private List<PatientRecord> fetchPatientRecords(Patient patient) {
        long startTime = System.currentTimeMillis() - HOUR_INTERVAL_MS;
        long endTime = System.currentTimeMillis();
        return dataStorage.getRecords(patient.getPatientID(), startTime, endTime);
    }

    private List<PatientRecord> filterRecordsByType(List<PatientRecord> records, String type) {
        return records.stream()
                      .filter(record -> type.equals(record.getMeasurementValue()))
                      .collect(Collectors.toList());
    }

    private void evaluateHeartRate(List<PatientRecord> records, Patient patient) {
        if (records.size() >= 5) {
            checkIrregularHeartRate(records, patient);
        }
        checkCriticalHeartRate(records, patient);
    }

    private void checkIrregularHeartRate(List<PatientRecord> records, Patient patient) {
        final int windowSize = 5;
        final double thresholdMultiplier = 1.5;

        IntStream.range(0, records.size() - windowSize + 1)
                 .forEach(i -> {
                     double windowAvg = calculateWindowAverage(records, i, windowSize);
                     double threshold = windowAvg * thresholdMultiplier;

                     if (i + windowSize < records.size()) {
                         double nextValue = records.get(i + windowSize).getMeasurementValue();
                         if (nextValue > threshold) {
                             triggerAlert(new Alert(String.valueOf(patient.getPatientID()), "Irregular Heart Rate Alert", records.get(i).getTimestamp()));
                         }
                     }
                 });
    }

    private double calculateWindowAverage(List<PatientRecord> records, int start, int windowSize) {
        return IntStream.range(start, start + windowSize)
                        .mapToDouble(i -> records.get(i).getMeasurementValue())
                        .average()
                        .orElse(0.0);
    }

    private void checkCriticalHeartRate(List<PatientRecord> records, Patient patient) {
        records.stream()
               .filter(record -> record.getMeasurementValue() < 50 || record.getMeasurementValue() > 100)
               .forEach(record -> triggerAlert(new Alert(String.valueOf(patient.getPatientID()), "Critical Heart Rate Alert", record.getTimestamp())));
    }
}
