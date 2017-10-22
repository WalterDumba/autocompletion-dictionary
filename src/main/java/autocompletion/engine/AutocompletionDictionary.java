package autocompletion.engine;

import java.util.Collection;
import java.util.Queue;

public interface AutocompletionDictionary {

    public void add(String word);
    public void addAll(String[] words);
    public void addAll(Collection<String> words);
    public Queue<String> wordsStartingWith(String prefix);
    public boolean remove(String word);
}
