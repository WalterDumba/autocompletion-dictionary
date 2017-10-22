package autocompletion.common;

import autocompletion.engine.Trie;

import java.util.concurrent.RecursiveAction;

/**
 * Created by p-wadumba on 22-11-2016.
 */
public class TrieInsertionTask extends RecursiveAction{




    private static int NUMBER_OF_THREADS    = Runtime.getRuntime().availableProcessors();
    private static int SEQUENTIAL_THRESHOLD;
    private Trie trie;
    private int lowIndex, highIndex;
    private static String[] words;





    public TrieInsertionTask(Trie trie, String[] words, int lowIndex, int highIndex){
        this.trie       = trie;
        this.words      = words;
        this.lowIndex   = lowIndex;
        this.highIndex  = highIndex;
        this.before();
    }


    private void before() {
        if(SEQUENTIAL_THRESHOLD == 0){
            SEQUENTIAL_THRESHOLD = words.length/ NUMBER_OF_THREADS;
        }
    }


    @Override
    protected void compute() {

        if(highIndex - lowIndex <= SEQUENTIAL_THRESHOLD){
            trie.addAll(words);
        }
        //Or else dived for conquer
        else{
            int mid = lowIndex + (highIndex-lowIndex)/2;
            TrieInsertionTask childLeft  = new TrieInsertionTask(trie, words,lowIndex, mid);
            TrieInsertionTask childRight = new TrieInsertionTask(trie, words, mid, highIndex);
            childLeft.fork();
            childRight.compute();
            //An then we wait for termination
            childLeft.join();
        }
    }
}
