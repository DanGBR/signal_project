package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of the OutputStrategy interface for writing data to files
 * This class writes patient data to files in a specified directory
 * This class implements the OutputStrategy interface
 */
public class FileOutputStrategy implements OutputStrategy {

    //Camel case: BaseDirectory -> baseDirectory
    private String baseDirectory;

    //Camel case: file_map -> fileMap
    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();

    /**
     * Constructor to initialize the FileOutputStrategy with a base directory
     *
     * @param baseDirectory The base directory where files will be created
     */
    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }

    /**
     * Outputs health data for a patient to a file
     *
     * @param patientId The ID of the patient for which the data is being outputted
     * @param timestamp The timestamp shows when the data was generated
     * @param label The label of the health data being outputted
     * @param data The data being outputted
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }

        // Set the FilePath variable
        //Camel case: FilePath -> filePath
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        //Specify the Exception : Exception -> IOException
        } catch (IOException e) {
            System.err.println("Error writing to file " + fileMap + ": " + e.getMessage());
        }
    }
}