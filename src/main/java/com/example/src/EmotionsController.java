package com.example.src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

public class EmotionsController extends MenuController {

    public Button Resubmit;
    public Slider moodSlider;
    public Button Submit2;
    public Button Resubmit2;
    @FXML
    private Button Submit;
    @FXML
    private TextArea Clear;

    @FXML
    private TextArea Clear2;
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
    public void onSubmitButtonClick(ActionEvent event) {
        Submit2.setText("Thanks for submitting!");
        Clear2.clear();
    }


    @FXML
    public void onResubmitButtonClick(ActionEvent event) {
        Submit2.setText("Submit!");
    }
}
