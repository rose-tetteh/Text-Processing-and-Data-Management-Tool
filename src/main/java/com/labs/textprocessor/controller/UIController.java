package com.labs.textprocessor.controller;

import com.labs.textprocessor.regex.TextFileService;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UIController implements Initializable {

    public TextArea mainTextArea;

    @FXML
    private ComboBox<String> fontBox;

    @FXML
    private ComboBox<Integer> fontSizeBox;

    @FXML private BooleanProperty searchBarVisible = new SimpleBooleanProperty(true);


    @FXML
    public BooleanProperty searchBarVisibleProperty() {
        return searchBarVisible;
    }

    private TextFileService textFileService;

    public UIController(){
        this.textFileService = new TextFileService();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpFontCombo();
        setUpSizeComboBox();

    }


    /**
     * Sets up the font selection combo box with all available system fonts.
     * This method initializes the font dropdown with alphabetically sorted system fonts
     * and sets a default font value.
     *
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



    public void handleOpenOptionClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        // Show open file dialog
        Stage stage = (Stage) mainTextArea.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

    }

    public void handleSaveOptionClicked(ActionEvent actionEvent) {
    }

    public void applyFormatting(ActionEvent actionEvent) {
    }

    public void searchAndDisplayMatches(KeyEvent keyEvent) {
    }

    public void displayPreviousMatch(ActionEvent actionEvent) {
    }

    public void displayNextMatch(ActionEvent actionEvent) {
    }

    public void replaceCurrentMatch(ActionEvent actionEvent) {
    }

    public void replaceAllMatches(ActionEvent actionEvent) {
    }

    public void handleCountWord(KeyEvent keyEvent) {
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
