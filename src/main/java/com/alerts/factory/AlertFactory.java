package main.java.com.alerts.factory;

import main.java.com.alerts.Alert;

public abstract class AlertFactory {
    public abstract Alert createAlert(String patientID, String condition, long timestamp);  
}