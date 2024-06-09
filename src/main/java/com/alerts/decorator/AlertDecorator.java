package main.java.com.alerts.decorator;
import main.java.com.alerts.Alert;

public abstract class AlertDecorator extends Alert {
    protected Alert decoratedAlert;

    public AlertDecorator(Alert alert) {
        super(alert.getPatientId(), alert.getCondition(), alert.getTimestamp());
        this.decoratedAlert = decoratedAlert;
    }
}
