package autocompletion.app;

import autocompletion.AutocompletionDictionaryImpl;
import autocompletion.engine.AutocompletionDictionary;
import autocompletion.common.IO;
import autocompletion.common.LoggingInvocationHandler;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/**
 * Created by p-wadumba on 18-11-2016.
 */
public class App {





    public static void main(String[] args){


        //Reading our dictionary words from a file
        final File file  = new File("src/main/words.txt");

        List<String> dictionaryWords = readFile( file.getPath(), true);
        /*
            I'm proxying the instance just to get the execution times logged,
            i'm also aware that if i want to measure the execution times its not at all the best way to do that
         */
        AutocompletionDictionary dictionaryProxy = ( AutocompletionDictionary )
                Proxy.newProxyInstance(
                        ClassLoader.getSystemClassLoader(),
                        new Class[]{ AutocompletionDictionary.class },
                        new LoggingInvocationHandler( AutocompletionDictionaryImpl.instance() )
                );

        dictionaryProxy.addAll(dictionaryWords);

        Queue<String> words = dictionaryProxy.wordsStartingWith("lu");
    }


    private static List<String> readFile(String fileName, boolean ...parallelized) {
        try {
            List<String> dictionaryWords = IO.read( fileName, parallelized );
            return  dictionaryWords;
        } catch (IOException e) {
            throw new RuntimeException("Error on reading file", e);
        }
    }
}
