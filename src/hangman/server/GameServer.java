package hangman.server;

import hangman.communication.Message;
import hangman.communication.Result;

import java.io.*;
import java.net.Socket;

/**
 * Created by daseel on 2016-11-09.
 */
public class GameServer extends Thread {

    private final int MAX_ATTEMPTS = 10;


    private Socket clientSocket;
    private WordHandler wordHandler;
    private int score;

    public GameServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        super.run();
        score = 0;
        try {
            initGame();
        } catch (IOException e) {
            System.out.println(clientSocket.toString() + " happened");
        }
    }


    // TODO: 2016-11-10 Create initGame so client can create new game without reset scores
    private void initGame() throws IOException, NullPointerException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        if (reader.readLine().equals(Message.START_GAME)) {
            wordHandler = new WordHandler();
            gameLoop();
        }
    }


    private void gameLoop() throws IOException, NullPointerException {

        int attempts = MAX_ATTEMPTS;


        boolean inGame = true;
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while (inGame) {

            sendResult(attempts, wordHandler.getWordProgress(), Message.NEW_TURN);
            if (reader.readLine().equals(Message.GUESS)) {
                String guess = reader.readLine();

                if (wordHandler.charExists(guess)) {
                    if (wordHandler.wordFinished()) {
                        score++;
                        sendResult(score, wordHandler.getFullWord(), Message.WIN);
                        inGame = false;
                    }
                } else {
                    attempts--;
                    if (attempts <= 0) {
                        score = 0;
                        sendResult(score, wordHandler.getFullWord(), Message.LOSE);
                        inGame = false;
                    }
                }

            } else {
                throw new IOException();
            }
        }
        initGame();
    }

    private void sendResult(int score, String word, String message) throws IOException {

        Result res = new Result();
        res.setScore(score);
        res.setCurrentWord(word);
        res.setMessage(message);

        ObjectOutputStream objOutput = new ObjectOutputStream(clientSocket.getOutputStream());

        objOutput.writeObject(res);
        objOutput.flush();
    }
}
