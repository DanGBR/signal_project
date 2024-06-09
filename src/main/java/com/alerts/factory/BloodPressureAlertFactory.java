package main.java.com.alerts.factory;
import com.alerts.Alert;

public class BloodPressureAlertFactory extends AlertFactory{

    @Override
    public Alert createAlert(String patientID, String condition, long timestamp) {
        return new Alert(patientID, condition, timestamp);
    }
    
    
}