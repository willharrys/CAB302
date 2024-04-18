package com.example.src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class SupportController extends MenuController {


    public void openLink(ActionEvent event) throws URISyntaxException, IOException {
        java.awt.Desktop.getDesktop().browse(new URI("http://www.google.com"));
    }

    public void openLink2(ActionEvent event) throws URISyntaxException, IOException {
        java.awt.Desktop.getDesktop().browse(new URI("https://www.beyondblue.org.au/"));
    }
    public void openLink3(ActionEvent event) throws URISyntaxException, IOException{
        java.awt.Desktop.getDesktop().browse(new URI("https://kidshelpline.com.au/"));
    }

}
