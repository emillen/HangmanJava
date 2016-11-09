package hangman.client.controllers;

import java.net.Socket;

/**
 * Created by daseel on 2016-11-09.
 */
public class StartGameController {

    private Socket socket;


    public void init(Socket socket) {
        this.socket = socket;
    }
}
