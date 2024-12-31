package com.labs.textprocessor.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class UIController implements Initializable {

    @FXML private BooleanProperty searchBarVisible = new SimpleBooleanProperty(true);


    @FXML
    public BooleanProperty searchBarVisibleProperty() {
        return searchBarVisible;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleOpenOptionClicked(ActionEvent actionEvent) {
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
