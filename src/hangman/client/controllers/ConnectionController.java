package hangman.client.controllers;

import hangman.client.services.ConnectionService;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionController {

    @FXML
    TextField ipField;
    @FXML
    TextField portField;
    @FXML
    Text warningText;
    @FXML
    Button connectButton;
    @FXML
    ProgressIndicator progress;


    @FXML
    private void ipFieldAction() {

        portField.requestFocus();
    }



    @FXML
    private void tryConnection() {

        try {
            InetAddress address = InetAddress.getByName(ipField.getText());
            int portNumber = Integer.parseInt(portField.getText());
            ConnectionService connectionService = new ConnectionService(address, portNumber);
            warningText.setVisible(false);
            connectButton.setDisable(true);
            progress.setVisible(true);
            connectionService.setOnFailed(new FailHandler());
            connectionService.setOnSucceeded(new SuccessHandler());
            connectionService.start();

        } catch (UnknownHostException e) {
            changeWarningText("Not valid ip address");
        } catch (NumberFormatException e) {
            changeWarningText("Not a valid port-number");
        }
    }


    private void reset(){

    }

    private void changeWarningText(String text) {
        warningText.setText(text);
        warningText.setVisible(true);
    }


    private class SuccessHandler implements EventHandler<WorkerStateEvent> {
        @Override
        public void handle(WorkerStateEvent workerStateEvent) {
            System.out.println("Did i get here?");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/startGameView.fxml"));
            Stage stage = (Stage) ipField.getParent().getScene().getWindow();
            Parent root;
            try {
                root = (Parent) loader.load();
                StartGameController controller = loader.<StartGameController>getController();
                controller.init((Socket) workerStateEvent.getSource().getValue());
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    private class FailHandler implements EventHandler<WorkerStateEvent> {
        @Override
        public void handle(WorkerStateEvent workerStateEvent) {
            changeWarningText("Could not connect");
            connectButton.setDisable(false);
            progress.setVisible(false);
        }
    }
}
