package hangman.server;

import java.net.Socket;

/**
 * Created by daseel on 2016-11-09.
 */
public class GameServer extends Thread {

    private Socket clientSocket;

    public GameServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        super.run();
    }
}
