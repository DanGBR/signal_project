package main.java.com.alerts.decorator;
import main.java.com.alerts.Alert;

public class RepeatedAlertDecorator extends AlertDecorator {
    
    private final int repeatCount;
    
    public RepeatedAlertDecorator(Alert decratedAlert, int repeatCount) {
        super(decratedAlert);
        this.repeatCount = repeatCount;
    }
    
    @Override
    public String getCondition() {
        return repeatCount + " - " + decoratedAlert.getCondition();
    }
    
}
