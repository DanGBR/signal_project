package main.java.com.cardio_generator.generators;

import java.util.Random;

import main.java.com.cardio_generator.outputs.OutputStrategy;

/**
 * Generates simulated alert data for patients
 * This class implements the PatientDataGenerator interface
 */
public class AlertGenerator implements PatientDataGenerator {

    //Constant name (all caps): randomGenerator -> RANDOM_GENERATOR
    public static final Random RANDOM_GENERATOR = new Random();

    //Camel case: AlertStates -> alertStates
    private boolean[] alertStates; // false = resolved, true = pressed

    /**
     * Constructor to initialize the AlertGenerator
     *
     * @param patientCount The number of patients for whom alert data will be generated
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Generates simulated alert data for a patient and outputs it using the specified output strategy
     *
     * @param patientId The ID of the patient for whom data is being generated
     * @param outputStrategy The output strategy used to output the generated data
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                //Camel case: Lambda -> lambda
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = RANDOM_GENERATOR.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
