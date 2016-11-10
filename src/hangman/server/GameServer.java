package hangman.server;

import hangman.communication.GameResult;
import hangman.communication.Message;
import hangman.communication.TurnResult;

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
        score = 0;
    }

    @Override
    public void run() {
        super.run();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            if (reader.readLine().equals(Message.START_GAME)) {
                wordHandler = new WordHandler();
                gameLoop();
            }
            throw new IOException();
        } catch (IOException e) {
            System.out.println(clientSocket.toString() + " happened");
        }

    }

    private void gameLoop() throws IOException {

        int attempts = MAX_ATTEMPTS;
        boolean inGame = true;
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while (inGame) {

            sendTurnResult(attempts);

            if (reader.readLine().equals(Message.GUESS)) {
                String guess = reader.readLine();

                if (wordHandler.charExists(guess)) {
                    if (wordHandler.wordFinished()) {
                        sendGameResult(Message.WIN);
                        score++;
                    }
                } else {
                    attempts--;
                    if(attempts <= 0){
                        sendGameResult(Message.LOSE);
                        inGame = false;
                        score = 0;
                    }
                }

            } else {
                throw new IOException();
            }
        }
    }

    private void sendTurnResult(int attempts) throws IOException{

        TurnResult tr = new TurnResult();
        tr.setAttemptsLeft(attempts);
        tr.setCurrentWord(wordHandler.getWordProgress());

        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
        ObjectOutputStream objOutput = new ObjectOutputStream(clientSocket.getOutputStream());

        writer.println(Message.NEW_TURN);
        objOutput.writeObject(tr);
    }

    private void sendGameResult(String message) throws IOException{

        GameResult gr = new GameResult();
        gr.setResult(message);
        gr.setTotalScore(score);
        gr.setWholeWord(wordHandler.getWordProgress());

        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
        ObjectOutputStream objOutput = new ObjectOutputStream(clientSocket.getOutputStream());

        writer.println(Message.GAME_OVER);
        objOutput.writeObject(gr);
    }
}
