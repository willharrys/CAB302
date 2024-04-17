package com.example.src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.BreakIterator;
import java.text.NumberFormat;

public class HelloController {
    @FXML
    private TextArea Clear;
    @FXML
    private Button Submit;
    @FXML
    private Button NextPage;



    @FXML
    protected void onHelloButtonClick() {
       Submit.setText("Thanks for submitting!");
       Clear.clear();



    }
    @FXML
    protected void ResubmitButtonClick(){
        Submit.setText("Submit!");
    }

    @FXML
    protected void NextPageClick()
        throws IOException {
            Stage stage = (Stage) NextPage.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homepage-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
            stage.setScene(scene);

    }
}