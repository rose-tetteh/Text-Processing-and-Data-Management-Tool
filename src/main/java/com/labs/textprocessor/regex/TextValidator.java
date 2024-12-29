package com.labs.textprocessor.regex;

/**
 * The type Text validator.
 */
public class TextValidator {
    private static final int MAX_TEXT_LENGTH = 1_000_000;

    /**
     * Is valid text boolean.
     * Validates the text(not null, not empty,  and within max length)
     * @param text the text
     * @return the boolean
     */
    public boolean isValidText(String text){
        return text != null && !text.trim().isEmpty() && text.length() <= MAX_TEXT_LENGTH;

    }

    /**
     * Is valid file path boolean.
     * Validates the file path(Should be a .txt  file and not contain invalid characters)
     * @param path the path
     * @return the boolean
     */
    public boolean isValidFilePath(String path){
        return path == null || !path.endsWith(".txt") || path.contains("...");
    }
}
