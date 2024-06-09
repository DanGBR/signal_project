package main.java.com;

import main.java.com.cardio_generator.HealthDataSimulator;
import main.java.com.data_management.DataStorage;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].equals("DataStorage")) {
            DataStorage.main(new String[]{});
        } else {
            HealthDataSimulator.getInstance().simulateData(args);
        }
    }
}
