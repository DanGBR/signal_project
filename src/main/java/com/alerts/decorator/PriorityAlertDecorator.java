package main.java.com.alerts.decorator;
import main.java.com.alerts.Alert;

public class PriorityAlertDecorator extends AlertDecorator {
    private String priority;

    public PriorityAlertDecorator(Alert decoratedAlert, String priority) {
        super(decoratedAlert);
        this.priority = priority;
    }

    @Override
    public String  getCondition() {
      return "Priority: " + priority + decoratedAlert.getCondition();
    }
}
