package hangman.client.controllers;

import hangman.client.ViewSwapper;
import hangman.client.services.StartGameService;
import hangman.communication.Result;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.Socket;
import java.net.URL;

/**
 * Created by daseel on 2016-11-09.
 */
public class StartGameController implements Controller{

    private Socket socket;

    @FXML
    Text scoreText;
    @FXML
    Text resultText;
    @FXML
    Text wordText;
    @FXML
    Button startButton;


    @Override
    public void init(Socket socket, Result result) {
        this.socket = socket;

        scoreText.setText("Total Score: " + result.getScore());

        if (result.getMessage() == null)
            setText(resultText, "New Game");
        else
            setText(resultText, result.getMessage());

        if (result.getCurrentWord() != null)
            setText(wordText, "Full word: " + result.getCurrentWord());
    }


    private void setText(Text text, String newText) {

        text.setVisible(true);
        text.setText(newText);
    }

    @FXML
    public void newGame() {
        StartGameService gameService = new StartGameService(socket);
        gameService.setOnSucceeded(new SuccesHandler());
        gameService.setOnFailed(new FailHandler());
        gameService.start();
    }


    private class SuccesHandler implements EventHandler<WorkerStateEvent> {
        @Override
        public void handle(WorkerStateEvent workerStateEvent) {

            Stage stage = (Stage) scoreText.getParent().getScene().getWindow();
            URL url = getClass().getResource("../views/gameView.fxml");

            Result result = (Result) workerStateEvent.getSource().getValue();

            ViewSwapper.swap(socket, stage, url, result);
        }
    }

    private class FailHandler implements EventHandler<WorkerStateEvent> {
        @Override
        public void handle(WorkerStateEvent workerStateEvent) {
            // TODO: 2016-11-10 Handle unsuccessful game start
            System.out.println("Something is terribly wrong");
        }
    }
}
