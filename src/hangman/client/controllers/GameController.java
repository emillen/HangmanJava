package hangman.client.controllers;

import hangman.client.views.ViewSwapper;
import hangman.client.services.GameService;
import hangman.communication.Message;
import hangman.communication.Result;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.Socket;
import java.net.URL;

/**
 * Created by daseel on 2016-11-10.
 */
public class GameController implements Controller {

    private Socket socket;

    @FXML
    TextField guessField;
    @FXML
    Text guessesText;
    @FXML
    Text attemptsText;
    @FXML
    Text currentWordText;

    @FXML
    public void sendGuess() {
        GameService gs = new GameService(socket, guessField.getText());
        gs.setOnSucceeded(new SuccessHandler());
        gs.setOnFailed(new FailHandler());
        gs.start();
        guessesText.setText(guessField.getText() + "\n" + guessesText.getText());
        guessField.setText("");
    }

    public void init(Socket socket, Result result) {
        this.socket = socket;
        guessesText.setText("");
        setNewTurn(result);
    }


    private void setNewTurn(Result result) {
        attemptsText.setText("Attempts Left: " + result.getScore());
        currentWordText.setText(result.getCurrentWord());
    }

    private class SuccessHandler implements EventHandler<WorkerStateEvent> {
        @Override
        public void handle(WorkerStateEvent workerStateEvent) {
            Result result = (Result) workerStateEvent.getSource().getValue();


            if (result.getMessage().equals(Message.NEW_TURN)) {
                setNewTurn(result);
            } else {
                Stage stage = (Stage) currentWordText.getParent().getScene().getWindow();
                URL url = getClass().getResource("../views/startGameView.fxml");
                ViewSwapper.swap(socket, stage, url, result);

            }
        }
    }

    private class FailHandler implements EventHandler<WorkerStateEvent> {
        @Override
        public void handle(WorkerStateEvent workerStateEvent) {
            System.out.println("I think something broke");
        }
    }
}