package autocompletion;

import autocompletion.engine.AutocompletionDictionary;
import autocompletion.engine.Trie;

import java.util.Collection;
import java.util.Queue;

public final class AutocompletionDictionaryImpl implements AutocompletionDictionary {




    private Trie dictionary;

    private static AutocompletionDictionaryImpl instance;



    private AutocompletionDictionaryImpl(){
        this.dictionary = new Trie();
    }




    @Override
    public void add(String word) {
        this.dictionary.add(word);
    }


    /**
     * Add a array of words into dictionary asynchronously
     * and Thread safe
     * @param words
     */
    @Override
    public void addAll(String[] words) {
        this.dictionary.addAsynchronously(words);
    }

    /**
     *Add this collection of words to dictionary
     *
     * @param words
     */
    @Override
    public void addAll(Collection<String> words) {
        words.parallelStream().forEach(this.dictionary::add);
    }

    /**
     *
     * @param prefix
     * @return
     */
    @Override
    public Queue<String> wordsStartingWith(String prefix) {
        return this.dictionary.wordsMatchingWith(prefix);
    }

    /**
     * Remove a given word from dictionary
     * @param word
     * @return
     */
    @Override
    public boolean remove(String word) {
        return this.dictionary.remove(word);
    }


    public PredictedWords predictWordsStartedWith(String prefix){

        Queue<String> guessings       = this.dictionary.wordsMatchingWith( prefix );
        char key                      = prefix.charAt( prefix.length()-1 );
        //Build predicted words
        PredictedWords predictedWords = new PredictedWords();
        predictedWords.setKey(key);
        predictedWords.setWords(guessings);
        return predictedWords;
    }


    /**
     *
     * Get Dictionary instance
     *
     * @return class singleton
     * */
    public static AutocompletionDictionaryImpl instance(){

        if(instance == null){
            synchronized (AutocompletionDictionaryImpl.class){
                if(instance == null){
                    instance = new AutocompletionDictionaryImpl();
                }
            }
        }
        return instance;
    }
}
