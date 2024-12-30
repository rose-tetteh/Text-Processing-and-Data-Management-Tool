package com.labs.textprocessor.utils;

public record FileOperationResult<T>(T data, String error, boolean success) {
    /**
     * Success file operation result.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the file operation result
     */
    public static <T> FileOperationResult<T> success(T data) {
        return new FileOperationResult<>(data, null, true);
    }

    /**
     * Error file operation result.
     *
     * @param <T>          the type parameter
     * @param errorMessage the error message
     * @return the file operation result
     */
    public static <T> FileOperationResult<T> error(String errorMessage) {
        return new FileOperationResult<>(null, errorMessage, false);
    }
}
