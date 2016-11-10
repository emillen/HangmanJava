package hangman.client.views;

import hangman.client.controllers.Controller;
import hangman.client.controllers.StartGameController;
import hangman.communication.Result;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;

/**
 * Created by daseel on 2016-11-10.
 */
public class ViewSwapper {

    public static void swap(Socket socket, Stage stage, URL url, Result result){

        FXMLLoader loader = new FXMLLoader(url);
        Parent root;
        try {
            root = (Parent) loader.load();
            Controller controller = (Controller) loader.getController();
            controller.init(socket, result);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
