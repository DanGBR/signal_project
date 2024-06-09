package main.java.com.cardio_generator.outputs;

/**
 * Interface representing an output strategy for the patient data
 * Implementations of this interface define how generated health data is outputted
 */
public interface OutputStrategy {

    /**
     * Outputs health data for a patient using the specified parameters
     *
     * @param patientId The ID of a given patient whose data is being outputted
     * @param timestamp The timestamp showing when the data was generated
     * @param label The label of the health data being outputted
     * @param data The data being outputted
     */
    void output(int patientId, long timestamp, String label, String data);
}
