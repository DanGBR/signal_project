package main.java.com.alerts.factory;
import main.java.com.alerts.Alert;

public class BloodOxygenAlertFactory extends AlertFactory{

    @Override
    public Alert createAlert(String patientID, String condition, long timestamp) {
        return new Alert(patientID, condition, timestamp);
        
    }
    
}