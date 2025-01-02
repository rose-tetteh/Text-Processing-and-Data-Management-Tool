package com.labs.textprocessor.controller;

import com.labs.textprocessor.regex.RegexOperations;
import com.labs.textprocessor.regex.TextFileService;

import com.labs.textprocessor.utils.FileOperationResult;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class UIController implements Initializable {

    public TextArea mainTextArea;
    public CheckBox regexCheckBox;
    public CheckBox matchCaseCheckBox;
    public Label wordCountLabel;
    public Label lineColumnLabel;
    public Label characterCountLabel;
    public TextField replaceField;
    @FXML
    private TextArea matchedTextArea;
    @FXML
    private TextArea replacedTextArea;

    @FXML
    private HBox searchBar;

    @FXML
    private ToggleButton boldButton;

    @FXML
    private ToggleButton italicButton;

    @FXML
    private ColorPicker textColorPicker;

    @FXML
    private ToggleButton underlineButton;

    @FXML
    private ToggleButton alignLeftButton;

    @FXML
    private ToggleButton alignCenterButton;

    @FXML
    private ToggleButton alignRightButton;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> fontBox;

    @FXML
    private ComboBox<Integer> fontSizeBox;

    @FXML
    private BooleanProperty searchBarVisible = new SimpleBooleanProperty(true);
  


    @FXML
    public BooleanProperty searchBarVisibleProperty() {
        return searchBarVisible;
    }

    private final RegexOperations regexOperations = new RegexOperations();
    private final ArrayDeque<String> replacedMatches = new ArrayDeque<>(); // Store replaced matches for history tracking

    private final TextFileService textFileService;

    private ArrayList<String> matchedTexts = new ArrayList<>();

    private int currentIndex = 0;

    public UIController() {
        this.textFileService = new TextFileService();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpFontCombo();
        setUpSizeComboBox();
        searchBar.visibleProperty().bind(searchBarVisible);
        searchBar.managedProperty().bind(searchBarVisible);

    }


    /**
     * Sets up the font selection combo box with all available system fonts.
     * This method initializes the font dropdown with alphabetically sorted system fonts
     * and sets a default font value.
     * <p>
     * Time Complexity: O(n log n) where n is the number of available fonts
     * Space Complexity: O(n) where n is the number of available fonts
     *
     * @throws IllegalStateException if the FontBox is not properly initialized
     */
    @FXML
    public void setUpFontCombo() {
        if (fontBox == null) {
            throw new IllegalStateException("FontBox not initialized");
        }

        // Direct retrieval and addition to ComboBox items
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        List<String> fontList = Arrays.stream(ge.getAvailableFontFamilyNames())
                .sorted()
                .collect(Collectors.toList());

        fontBox.getItems().setAll(fontList);
        fontBox.setValue(fontList.contains("Arial") ? "Arial" : fontList.get(0));
    }

    /**
     * Sets up the font size combo box with predefined size options.
     * This method initializes the font size dropdown with common font sizes
     * and sets a default size value.
     *
     * @throws IllegalStateException if the FontSizeBox is not properly initialized
     */
    @FXML
    private void setUpSizeComboBox() {
        if (fontSizeBox == null) {
            throw new IllegalStateException("FontSizeBox not initialized");
        }

        // Using immutable list for predefined sizes
        final List<Integer> FONT_SIZES = List.of(8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72);

        fontSizeBox.getItems().setAll(FONT_SIZES);
        fontSizeBox.setValue(12);
    }


    /**
     * Handle the open option clicked (open a file and display content in TextArea).
     */
    @FXML
    public void handleOpenOptionClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        // Show open file dialog
        Stage stage = (Stage) mainTextArea.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            // Call textFileService to read the file
            FileOperationResult<String> result = textFileService.readFile(file.toPath());

            if (result.isSuccess()) { // Use the isSuccess() method to check success
                // If successful, set the content in the TextArea
                mainTextArea.setText(result.getData());
            } else {
                // If error, show an alert or log the error message
                System.out.println("Error reading file: " + result.getError());
            }
        }
    }

    /**
     * Handle the save option clicked (save the content of TextArea to a file).
     */
    @FXML
    public void handleSaveOptionClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        // Show save file dialog
        Stage stage = (Stage) mainTextArea.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            // Get content from TextArea
            String content = mainTextArea.getText();

            // Call textFileService to write the content to the selected file
            FileOperationResult<Void> result = textFileService.writeFile(file.toPath(), content);

            if (result.isSuccess()) {
                // If successful, show a confirmation or log
                System.out.println("File saved successfully!");
            } else {
                // If error, show an alert or log the error message
                System.out.println("Error saving file: " + result.getError());
            }
        }
    }


    public void applyFormatting(ActionEvent actionEvent) {
        IndexRange selection = mainTextArea.getSelection();
        if (selection.getLength() > 0) {
            // Store the current style of the TextArea
            String currentStyle = mainTextArea.getStyle();
            StringBuilder styleBuilder = new StringBuilder(currentStyle);

            // Create style for selected portion
            String fontFamily = fontBox.getValue();
            styleBuilder.append("-fx-font-family: '").append(fontFamily).append("';");

            String fontSize = fontSizeBox.getValue().toString();
            styleBuilder.append("-fx-font-size: ").append(fontSize).append("px; ");

            // Handle bold logic
            if (boldButton.isSelected()) {
                if (!currentStyle.contains("-fx-font-weight: bold;")) {
                    styleBuilder.append("-fx-font-weight: bold;");
                }
            } else {
                // Remove bold if it exists in the style
                int boldIndex = currentStyle.indexOf("-fx-font-weight: bold;");
                if (boldIndex != -1) {
                    styleBuilder = new StringBuilder(
                            currentStyle.replace("-fx-font-weight: bold;", "")
                    );
                }
            }

            // Handle italic logic
            if (italicButton.isSelected()) { // Assume italicButton is your ToggleButton for italics
                if (!currentStyle.contains("-fx-font-style: italic;")) {
                    styleBuilder.append("-fx-font-style: italic;");
                }
            } else {
                // Remove italic if it exists in the style
                int italicIndex = currentStyle.indexOf("-fx-font-style: italic;");
                if (italicIndex != -1) {
                    styleBuilder = new StringBuilder(
                            currentStyle.replace("-fx-font-style: italic;", "")
                    );
                }
            }

            Color textColor = textColorPicker.getValue(); // Assume textColorPicker is your ColorPicker for text color
            if (textColor != null) {
                String colorString = String.format("#%02X%02X%02X", (int) (textColor.getRed() * 255), (int) (textColor.getGreen() * 255), (int) (textColor.getBlue() * 255));
                if (!currentStyle.contains("-fx-text-fill: " + colorString + ";")) {
                    styleBuilder.append("-fx-text-fill: ").append(colorString).append(";");
                }
            }


            // Handle underline logic
            if (underlineButton.isSelected()) { // Assume underlineButton is your ToggleButton for underline
                if (!currentStyle.contains("-fx-underline: true;")) {
                    styleBuilder.append("-fx-underline: true;");
                }
            } else {
                // Remove underline if it exists in the style
                int underlineIndex = currentStyle.indexOf("-fx-underline: true;");
                if (underlineIndex != -1) {
                    styleBuilder = new StringBuilder(
                            currentStyle.replace("-fx-underline: true;", "")
                    );
                }
            }

            // Check which button is selected and apply the corresponding alignment
            if (alignLeftButton.isSelected()) {
                styleBuilder.append("-fx-text-alignment: left;");
                alignCenterButton.setSelected(false);  // Deselect other buttons
                alignRightButton.setSelected(false);
            } else if (alignCenterButton.isSelected()) {
                styleBuilder.append("-fx-text-alignment: center;");
                alignLeftButton.setSelected(false);  // Deselect other buttons
                alignRightButton.setSelected(false);
            } else if (alignRightButton.isSelected()) {
                styleBuilder.append("-fx-text-alignment: right;");
                alignLeftButton.setSelected(false);  // Deselect other buttons
                alignCenterButton.setSelected(false);
            }


            // Apply the updated style
            mainTextArea.setStyle(styleBuilder.toString());

            // Reselect the text
            int start = selection.getStart();
            int end = selection.getEnd();
            mainTextArea.selectRange(start, end);
        }
    }

    /**
     * Displays an error alert dialog with the specified title and message.
     *
     * @param title   The title of the error dialog
     * @param message The error message to display
     * @throws IllegalArgumentException if title or message is null
     */
    private void showErrorAlert(String title, String message) {
        if (title == null || message == null) {
            throw new IllegalArgumentException("Title and message cannot be null");
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void searchAndDisplayMatches(KeyEvent keyEvent) {

        matchedTextArea.clear();


        String searchTerm = searchField.getText();
        String inputText = mainTextArea.getText();

        try {
            Pattern pattern;

            if (regexCheckBox.isSelected()) {

                // Use the search term as regex

                pattern = matchCaseCheckBox.isSelected()
                        ? Pattern.compile(searchTerm)       // Case-sensitive regex
                        : Pattern.compile(searchTerm, Pattern.CASE_INSENSITIVE); // Case-insensitive regex;


            } else {

                    // Escape the search term for literal matching
                    String escapedSearchTerm = Pattern.quote(searchTerm);
                    pattern = matchCaseCheckBox.isSelected()
                            ? Pattern.compile(escapedSearchTerm) // Case-sensitive literal
                            : Pattern.compile(escapedSearchTerm, Pattern.CASE_INSENSITIVE); // Case-insensitive literal
            }



            matchedTexts = regexOperations.findAll(inputText,pattern.pattern());

            // Display results or "No match found"
            if (!matchedTexts.isEmpty()) {
                currentIndex = 0; // Reset the index to the first match
                matchedTextArea.setText(matchedTexts.get(currentIndex));
            } else {
                matchedTextArea.setText("No match found");
            }


        } catch (PatternSyntaxException e) {
            matchedTextArea.setText("Invalid regex pattern");
        }


    }

    public void displayPreviousMatch(ActionEvent actionEvent) {
        // Check if there are previous matches
        if (!matchedTexts.isEmpty() && currentIndex > 0) {
            currentIndex--;  // Move to the previous match
            matchedTextArea.clear();  // Clear the previous match display
            matchedTextArea.setText(matchedTexts.get(currentIndex));  // Display the previous match
        }
    }

    public void displayNextMatch(ActionEvent actionEvent) {
        if(!matchedTexts.isEmpty() && currentIndex < matchedTexts.size() -1){
            currentIndex++;
            matchedTextArea.clear();
            matchedTextArea.setText(matchedTexts.get(currentIndex));
        }
    }

    public void replaceCurrentMatch(ActionEvent actionEvent) {
        // Check if there are any matches to replace
        if (matchedTexts.isEmpty()) {
            matchedTextArea.setText("No match to replace");
            return;
        }

        // Validate the replacement text
        String replacement = replaceField.getText();
        if (replacement == null || replacement.isEmpty()) {
            matchedTextArea.setText("Replacement text is empty");
            return;
        }

        // Get the current match from the matchedTextArea or matchedTexts list
        String currentMatch = matchedTextArea.getText();
        if (!currentMatch.equals(matchedTexts.get(currentIndex))) {
            currentMatch = matchedTexts.get(currentIndex); // Fall back to the current index in matchedTexts
        }

        // Retrieve the input text
        String inputText = mainTextArea.getText();
        if (inputText == null || inputText.isEmpty()) {
            matchedTextArea.setText("Input text is empty");
            return;
        }

        String updatedText = regexOperations.replace(inputText,currentMatch,replacement);

        mainTextArea.setText(updatedText);

        matchedTexts.set(currentIndex,replacement);

        // Update UI components to reflect the replacement
        replacedTextArea.setText("Old word: " + currentMatch + "\n\nNew word: " + replacement);
        matchedTextArea.setText(replacement);

    }

    @FXML
    private void replaceAllMatches(ActionEvent actionEvent) {

        // Check if there are any matches to replace
        if (matchedTexts.isEmpty()) {
            matchedTextArea.setText("No matches to replace");
            return;
        }

        // Validate the replacement text
        String replacement = replaceField.getText();
        if (replacement == null || replacement.isEmpty()) {
            matchedTextArea.setText("Replacement text is empty");
            return;
        }

        // Retrieve the input text
        String inputText = mainTextArea.getText();
        if (inputText == null || inputText.isEmpty()) {
            matchedTextArea.setText("Input text is empty");
            return;
        }

        // Create an instance of RegexOperations (if not already available)
        RegexOperations regexOps = new RegexOperations();

        // Use a StringBuilder for efficient string manipulation
        StringBuilder updatedText = new StringBuilder(inputText);

        // Iterate through matched texts and replace them in the input text
        for (String match : matchedTexts) {
            // Use the replaceAll method from RegexOperations to replace all occurrences of the match
            String result = regexOps.replaceAll(updatedText.toString(), match, replacement);

            // Update the StringBuilder with the result from the replacement
            updatedText.setLength(0); // Clear the previous content
            updatedText.append(result);

            // Track the replaced match for history
            replacedMatches.add(match);
        }

        // Update UI components with the new state
        mainTextArea.setText(updatedText.toString()); // Update input area with replaced text
        replacedTextArea.setText("All matches replaced with: " + replacement); // Display replacement summary
        matchedTextArea.setText("All matches replaced"); // Clear matched texts display

        matchedTexts.clear(); // Clear the list of matched texts

    }

    public void handleCountWord(KeyEvent keyEvent) {
        int numOfLines = getLineNumber();

        // Get the cursor position.
        int caretPosition = mainTextArea.getCaretPosition();
        String textBeforeCaret = mainTextArea.getText(0,caretPosition);
        String[] lines = textBeforeCaret.split("\\R");

        int currentColumn = 0;

        if(lines.length != 0){
            currentColumn = lines[lines.length -1].length();
        }




        String[] words = mainTextArea.getText().split("\\s");
        wordCountLabel.setText("Word Count: "+words.length);



//        System.out.println("Lines = "+lines.length);
//        System.out.println("Column "+ currentColumn);
        lineColumnLabel.setText("Ln "+numOfLines+ ", Col "+currentColumn);

        characterCountLabel.setText("Characters: "+mainTextArea.getText().length());
    }

    private int getLineNumber(){
        int caretPosition = mainTextArea.getCaretPosition();
        String[] lines = mainTextArea.getText().split("\\R");
        return lines.length;
    }

    public void handleQuickSearch(MouseEvent mouseEvent) {
    }

    public void addTaskItem(ActionEvent actionEvent) {
    }

    public void removeTaskItem(ActionEvent actionEvent) {
    }

    public void updateTaskItem(ActionEvent actionEvent) {
    }

    public void viewTaskItemDetails(MouseEvent mouseEvent) {
    }

    public void undoSelectedAction(ActionEvent actionEvent) {
    }
}
