package autocompletion;

import java.util.Queue;

/**
 * Created by p-wadumba on 21-11-2016.
 */
public class PredictedWords {

    private char key;
    private Queue<String> words;




    public char getKey() {
        return key;
    }

    public void setKey(char key) {
        this.key = key;
    }

    public Queue<String> getWords() {
        return words;
    }

    public void setWords(Queue<String> words) {
        this.words = words;
    }
}
