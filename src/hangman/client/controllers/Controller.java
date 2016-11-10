package hangman.client.controllers;

import hangman.communication.Result;

import java.net.Socket;

/**
 * Created by daseel on 2016-11-10.
 */
public interface Controller {

    void init(Socket socket, Result result);
}
