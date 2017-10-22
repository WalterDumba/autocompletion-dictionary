package autocompletion.common;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by p-wadumba on 21-11-2016.
 */
public class IO{




    private static long start, end;

    //Maximum allowed to read from a file
    private static final int NUMBER_OF_LINES=400000;





    public static List<String> read(String fileName, boolean ...parallelizedRead) throws IOException {

        Stream<String> streamWords;
        List<String> words = new ArrayList<>(NUMBER_OF_LINES);

        start = System.currentTimeMillis();
        System.out.println("Reading Dictionary...");

        streamWords = lazyLoading(fileName, parallelizedRead);
        streamWords.forEach( ( String currentWord )-> words.add( currentWord ) );

        end = System.currentTimeMillis() - start;
        System.out.println("Dictionary read on (ms): "+end);
        return words;
    }


    private static boolean parallelLoadingRequired(boolean[] parallelizedRead) {
        return parallelizedRead.length!=0 && parallelizedRead[0];
    }


    public static Stream<String>lazyLoading(String fileName, boolean...parallelizedRead)throws IOException{

        try( BufferedReader reader = new BufferedReader(new FileReader(fileName)) ) {

            if(parallelLoadingRequired(parallelizedRead)){
                return reader.lines().parallel();
            }
            return reader.lines();
        }
    }

}
