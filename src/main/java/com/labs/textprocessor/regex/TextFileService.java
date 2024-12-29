package com.labs.textprocessor.regex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextFileService {
    private static final Logger LOGGER = Logger.getLogger(TextFileService.class.getName());
    private final TextValidator validator;

    public TextFileService() {
        this.validator = new TextValidator();
    }

    // Reads the content from the specified file
    public String readFile(Path path) throws IOException {
        // Validate file path before reading
        if (!validator.isValidFilePath(path.toString())) {
            throw new IllegalArgumentException("Invalid file path.");
        }

        LOGGER.info("Reading file from: " + path);
        try {
            return Files.readString(path);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading file from: " + path, e);
            throw new IOException("Failed to read file: " + e.getMessage(), e);
        }
    }

    // Writes the specified content to the file
    public void writeFile(Path path, String content) throws IOException {
        // Validate content before writing
        if (!validator.isValidText(content)) {
            throw new IllegalArgumentException("Invalid content.");
        }

        LOGGER.info("Writing to file: " + path);
        try {
            Files.writeString(path, content);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing to file: " + path, e);
            throw new IOException("Failed to write file: " + e.getMessage(), e);
        }
    }
}
