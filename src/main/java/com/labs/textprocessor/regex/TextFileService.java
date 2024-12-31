package com.labs.textprocessor.regex;

import com.labs.textprocessor.utils.FileOperationResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * The type Text file service.
 */
public class TextFileService {
    private static final Logger LOGGER = Logger.getLogger(TextFileService.class.getName());
    private final TextValidator validator;

    /**
     * Instantiates a new Text file service.
     */
    public TextFileService() {
        this.validator = new TextValidator();
    }

    /**
     * Read file, file operation result.
     * Reads the content from the specified file
     * @param path the path
     * @return the file operation result
     */
    public FileOperationResult<String> readFile(Path path) {
        try {
            if (path == null) {
                LOGGER.warning("Null file path provided");
                return FileOperationResult.error("File path cannot be null");
            }

            if (validator.isValidFilePath(path.toString())) {
                LOGGER.warning("Invalid file path format: " + path + " (must be a .txt file)");
                return FileOperationResult.error("Invalid file path: Must be a .txt file");
            }

            if (!Files.exists(path)) {
                LOGGER.info("File does not exist: " + path);
                return FileOperationResult.error("File does not exist");
            }

            if (!Files.isReadable(path)) {
                LOGGER.warning("File is not readable: " + path);
                return FileOperationResult.error("File is not readable");
            }

            String content = Files.readString(path);
            LOGGER.info("Successfully read file: " + path);
            return FileOperationResult.success(content);

        } catch (IOException e) {
            LOGGER.warning("IO error reading file: " + path + " - " + e.getMessage());
            return FileOperationResult.error("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Write file, file operation result.
     * Writes the specified content to the file
     * @param path    the path
     * @param content the content
     * @return the file operation result
     */
    public FileOperationResult<Void> writeFile(Path path, String content) {
        try {
            if (path == null) {
                LOGGER.warning("Null file path provided");
                return FileOperationResult.error("File path cannot be null");
            }

            if (validator.isValidFilePath(path.toString())) {
                LOGGER.warning("Invalid file path format: " + path + " (must be a .txt file)");
                return FileOperationResult.error("Invalid file path: Must be a .txt file");
            }

            if (!validator.isValidText(content)) {
                LOGGER.warning("Invalid content provided: " +
                        (content == null ? "null" :
                                content.trim().isEmpty() ? "empty" :
                                        "exceeds maximum length of 1,000,000 characters"));
                return FileOperationResult.error("Invalid content: Text must not be empty and must be under 1,000,000 characters");
            }

            Path parent = path.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
                LOGGER.info("Created directory structure: " + parent);
            }

            Files.writeString(path, content);
            LOGGER.info("Successfully wrote to file: " + path);
            return FileOperationResult.success(null);

        } catch (IOException e) {
            LOGGER.warning("IO error writing file: " + path + " - " + e.getMessage());
            return FileOperationResult.error("Error writing file: " + e.getMessage());
        }
    }




}
