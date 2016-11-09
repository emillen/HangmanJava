package hangman.client.controllers;

import hangman.client.services.StartGameService;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    public void newGame() {
        StartGameService gameService = new StartGameService(socket);
        gameService.start();
    }


    public class succesHandler implements EventHandler<WorkerStateEvent> {
        @Override
        public void handle(WorkerStateEvent workerStateEvent) {

        }
    }

    public class failHandler implements EventHandler<WorkerStateEvent> {
        @Override
        public void handle(WorkerStateEvent workerStateEvent) {

        }
    }
}
