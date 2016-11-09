package hangman.communication;

import java.io.Serializable;

/**
 * Created by daseel on 2016-11-09.
 */
public class GameResult implements Serializable {

    private String result;
    private int totalScore;
    private String wholeWord;

    public GameResult(){}

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getWholeWord() {
        return wholeWord;
    }

    public void setWholeWord(String wholeWord) {
        this.wholeWord = wholeWord;
    }
}
