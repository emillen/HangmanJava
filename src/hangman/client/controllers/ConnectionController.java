package hangman.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ConnectionController {

    @FXML
    TextField ipField;
    @FXML
    TextField portField;
    @FXML
    Button connectButton;


    @FXML
    private void ipFieldAction(){

        portField.requestFocus();
    }

}
