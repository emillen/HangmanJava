package hangman.server;

import hangman.communication.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by daseel on 2016-11-09.
 */
public class GameServer extends Thread {

    private final int MAX_ATTEMPTS = 10;

    private Socket clientSocket;
    private String word;
    private HashMap<Character, List<Integer>> charPositions;

    public GameServer(Socket clientSocket) {
        this.clientSocket = clientSocket;

    }

    @Override
    public void run() {
        super.run();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            if (reader.readLine().equals(Message.START_GAME)) {
                gameLoop();
                word = getRandomWord();

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
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
        ObjectInputStream objInput = new ObjectInputStream(clientSocket.getInputStream());
        ObjectOutputStream objOutput = new ObjectOutputStream(clientSocket.getOutputStream());


        while (inGame) {


        }
    }

    HashMap<Character, List<Integer>> createCharPositions(String word) {

        HashMap<Character, List<Integer>> characterPositions = new HashMap<>();

        for (int i = 0; i < word.length(); i++) {
            if (characterPositions.containsKey(word.charAt(i)))
                characterPositions.put(word.charAt(i), characterPositions.get(word.charAt(i)).add(i));
            else
            characterPositions.put(word.charAt(i), new ArrayList<>().add(i));

        }

        return characterPositions;
    }

    String getRandomWord() throws IOException {

        String fileName = "res/words.txt";
        BufferedReader reader;
        Random rand;
        List<String> wordsList = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(fileName));

            String s;
            while ((s = reader.readLine()) != null) wordsList.add(s);

            rand = new Random();
            return wordsList.get(rand.nextInt(wordsList.size()));

        } catch (FileNotFoundException e) {
            throw new IOException();
        }
    }
}
