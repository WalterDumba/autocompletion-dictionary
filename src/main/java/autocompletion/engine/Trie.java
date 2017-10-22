package autocompletion.engine;

import autocompletion.common.TrieInsertionTask;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

import static autocompletion.engine.Trie.Node.ASCII_TABLE_SIZE;


/**
 * Created by p-wadumba on 17-11-2016.
 */
public class Trie {



    protected Node root;




    public void add(String word){
        this.root = add(this.root, word, 0);
    }


    public void addAll(Collection<String> collection) {

        Objects.requireNonNull(collection, "collection cannot be null!");
        collection.forEach(this::add);
    }

    /**
     *
     * @param words
     */
    public void addAll(String[] words){

        Objects.requireNonNull(words, "collection cannot be null!");
        Stream.of(words).forEach(this::add);
    }

    public void addAsynchronously(String[] words ){
        ForkJoinPool.commonPool().invoke( new TrieInsertionTask(this, words, 0, words.length) );
    }


    public boolean remove(String word){
        //TODO:
        return false;
    }

    public Queue<String> wordsMatchingWith(String prefix){
        Queue<String>queue = new ArrayDeque<>();

        //Lookup the root subtree where words starts with prefix
        Node subTree = search(this.root, prefix, 0);
        collect(subTree, prefix, queue);
        return queue;
    }






    /**===================================================================================
     |                               Utilities                                           |
     ====================================================================================*/


    private static Node search(Node node, String prefix, int depth){

        if(node == null){
            return null;
        }
        if(depth == prefix.length()){
            return node;
        }
        char subTreeIdxStartsWith = prefix.charAt( depth );
        return search( node.children[subTreeIdxStartsWith], prefix, depth + 1);
    }
    private static void collect(Node node, String prefix, Queue<String> queue){

        //Figure out the subtree which holds the prefix, and then get all words from that tree
        if(node == null){
            return;
        }
        if(node.isFullWord()){
            queue.add(prefix);
        }
        for(char c =0; c < ASCII_TABLE_SIZE; ++c){
            collect(node.children[c], prefix+c, queue);
        }
    }
    private static Node add(Node node, String word, int depth){

        if( node == null ){
            node = new Node(); //Its mean we reach the insertion point
        }
        if( depth == word.length() ){
            node.setFullWord(true);
            return node;
        }
        char subTreeKey = word.charAt( depth );

        node.children[ subTreeKey ] = add( node.children[ subTreeKey ], word, depth + 1 );
        return  node;
    }



    /** Trie Node */
    static class Node{

        static final int ASCII_TABLE_SIZE = 128;
        private Node[] children;
        private boolean isFullWord;


        private Node(){
            this.children = new Node[ASCII_TABLE_SIZE];
        }
        private boolean isFullWord() {
            return this.isFullWord;
        }
        public void setFullWord(boolean fullWord) {
            this.isFullWord = fullWord;
        }
    }



}





