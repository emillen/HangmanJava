package hangman.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by daseel on 2016-11-10.
 */
public class WordHandler {

    private String fullWord;
    private String progressWord;
    private HashMap<Character, ArrayList<Integer>> charPositions;


    public WordHandler() throws IOException {

        fullWord = getRandomWord().toLowerCase();
        charPositions = createCharPositions(fullWord);
    }


    public boolean charExists(String charOrWord) {

        charOrWord = charOrWord.toLowerCase();

        if (charOrWord.length() > 1)
            return charOrWord.equals(fullWord);
        else if (charOrWord.length() == 1 && charPositions.containsKey(charOrWord.charAt(0))) {
            makeProgress(charOrWord.charAt(0));
            return true;
        }

        return false;
    }


    private void makeProgress(char c) {
        for (Integer i : charPositions.get(c)) {
            StringBuilder builder = new StringBuilder(progressWord);
            builder.setCharAt(i, c);
            charPositions.remove(c);
            progressWord = builder.toString();

        }
    }

    public String getFullWord(){
        return fullWord;
    }

    public String getWordProgress() {
        return progressWord;
    }

    public boolean wordFinished() {

        return fullWord.equals(progressWord);
    }

    private String getRandomWord() throws IOException {

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

    private HashMap<Character, ArrayList<Integer>> createCharPositions(String word) {

        HashMap<Character, ArrayList<Integer>> characterPositions = new HashMap<>();

        for (int i = 0; i < word.length(); i++) {

            progressWord += "-";
            if (characterPositions.containsKey(word.charAt(i))) {
                characterPositions.get(word.charAt(i)).add(i);
                characterPositions.put(word.charAt(i), characterPositions.get(word.charAt(i)));

            } else {
                ArrayList<Integer> newList = new ArrayList<Integer>();
                newList.add(i);
                characterPositions.put(word.charAt(i), newList);
            }

        }

        return characterPositions;
    }
}
