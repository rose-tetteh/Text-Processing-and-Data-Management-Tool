package com.labs.textprocessor.regex;

public class TextValidator {
    private static final int MAX_TEXT_LENGTH = 1_000_000;

    //Validates the text(not null, not empty,  and within max length)
    public boolean isValidText(String text){
        return text != null && !text.trim().isEmpty() && text.length() <= MAX_TEXT_LENGTH;

    }

    //Validates the file path(Should be a .txt  file and not contain invalid characters)
    public boolean isValidFilePath(String path){
        return path != null && path.endsWith(".txt") && !path.contains("...");
    }
}
