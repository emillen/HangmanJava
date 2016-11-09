package hangman.communication;

import java.io.Serializable;

/**
 * Created by daseel on 2016-11-09.
 */
public class TurnResult implements Serializable{

    private String currentWord;
    private int attemptsLeft;

    public String getCurrentWord() {
        return currentWord;
    }

    public void setCurrentWord(String currentWord) {
        this.currentWord = currentWord;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public void setAttemptsLeft(int attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
    }
}
