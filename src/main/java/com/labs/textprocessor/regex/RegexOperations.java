package com.labs.textprocessor.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexOperations {

    /**
     * Searches for the first occurrence of the pattern in the input string.
     *
     * @param input   The input string.
     * @param pattern The regex pattern to search for.
     * @return The first match found, or null if no match is found.
     */
    public String search(String input, String pattern) {
        Matcher matcher = Pattern.compile(pattern).matcher(input);
        return matcher.find() ? matcher.group() : null;
    }

    /**
     * Checks if the entire input string matches the pattern.
     *
     * @param input   The input string.
     * @param pattern The regex pattern to match.
     * @return True if the input matches the pattern, false otherwise.
     */
    public boolean match(String input, String pattern) {
        return Pattern.matches(pattern, input);
    }

    /**
     * Finds all occurrences of the pattern in the input string.
     *
     * @param input   The input string.
     * @param pattern The regex pattern to search for.
     * @return An array of all matches found, or an empty array if no matches are found.
     */
    public String[] findAll(String input, String pattern) {
        Matcher matcher = Pattern.compile(pattern).matcher(input);
        List<String> matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches.toArray(new String[0]);
    }

    /**
     * Replaces the first occurrence of the pattern in the input string with the replacement string.
     *
     * @param input       The input string.
     * @param pattern     The regex pattern to replace.
     * @param replacement The replacement string.
     * @return The resulting string after replacement.
     */
    public String replace(String input, String pattern, String replacement) {
        return input.replaceFirst(pattern, replacement);
    }

    /**
     * Replaces the next occurrence of the pattern in the input string with the replacement string.
     *
     * @param input       The input string.
     * @param pattern     The regex pattern to replace.
     * @param replacement The replacement string.
     * @return The resulting string after replacing the next occurrence.
     */
    public String replaceNext(String input, String pattern, String replacement) {
        Matcher matcher = Pattern.compile(pattern).matcher(input);
        if (matcher.find()) {
            return matcher.replaceFirst(replacement);
        }
        return input;
    }

    /**
     * Replaces all occurrences of the pattern in the input string with the replacement string.
     *
     * @param input       The input string.
     * @param pattern     The regex pattern to replace.
     * @param replacement The replacement string.
     * @return The resulting string after replacing all occurrences.
     */
    public String replaceAll(String input, String pattern, String replacement) {
        return input.replaceAll(pattern, replacement);
    }

    /**
     * Splits the input string around matches of the pattern.
     *
     * @param input   The input string.
     * @param pattern The regex pattern to split by.
     * @return An array of strings computed by splitting the input string.
     */
    public String[] split(String input, String pattern) {
        return input.split(pattern);
    }

    /**
     * Checks if the pattern exists within the input string.
     *
     * @param input   The input string.
     * @param pattern The regex pattern to check.
     * @return True if the pattern exists, false otherwise.
     */
    public boolean contains(String input, String pattern) {
        Matcher matcher = Pattern.compile(pattern).matcher(input);
        return matcher.find();
    }
}