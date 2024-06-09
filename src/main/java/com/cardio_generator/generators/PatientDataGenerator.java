package main.java.com.cardio_generator.generators;

import main.java.com.cardio_generator.outputs.OutputStrategy;

/**
 * Interface representing a patient data generator
 * Implementations of this interface are responsible for generating simulated health data for a given patient and
 * outputting the data using a given output strategy
 */
public interface PatientDataGenerator {

    /**
     * Generates simulated health data for a given patient and outputs the data using the specified output strategy
     *
     * @param patientId The ID of the patient for whom the data is being generated
     * @param outputStrategy The output strategy used to output the generated data
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
