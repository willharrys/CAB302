package com.example.src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.text.BreakIterator;
import java.text.NumberFormat;

public class HelloController {
    @FXML
    private TextArea Clear;
    @FXML
    private Button Submit;


    @FXML
    protected void onHelloButtonClick() {
       Submit.setText("Thanks for submitting!");
       Clear.clear();

    }
}