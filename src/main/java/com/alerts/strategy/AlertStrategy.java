package main.java.com.alerts.strategy;
import main.java.com.data_management.Patient;

public interface AlertStrategy {
    void checkAlert(Patient patient);
}
