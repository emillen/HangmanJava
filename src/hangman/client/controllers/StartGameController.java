package hangman.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.net.Socket;

/**
 * Created by daseel on 2016-11-09.
 */
public class StartGameController {

    private Socket socket;

    @FXML
    Text scoreText;

    public void init(Socket socket, int score) {
        this.socket = socket;

        scoreText.setText("Total Score: " + score);
    }

    @FXML
    public void newGame(){
        System.out.println("I see you want new game bitch?");
    }
}
