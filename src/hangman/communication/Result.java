package hangman.communication;

import java.io.Serializable;

/**
 * Created by daseel on 2016-11-09.
 */
public class Result implements Serializable{

    private String message;
    private String currentWord;
    private int score;


    public String getCurrentWord() {
        return currentWord;
    }

    public void setCurrentWord(String currentWord) {
        this.currentWord = currentWord;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
