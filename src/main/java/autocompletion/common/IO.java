package autocompletion.common;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by p-wadumba on 21-11-2016.
 */
public class IO{




    private static long start, end;








    public static List<String> read(String fileName, boolean ...parallelizedRead) throws IOException {

        List<String> words;

        start = System.currentTimeMillis();
        System.out.println("Reading Dictionary...");

        try(Stream<String> stream = parallelLoadingRequired( parallelizedRead )?
                Files.lines(Paths.get(fileName)).parallel():
                Files.lines(Paths.get(fileName)) ){

           words = stream.collect( Collectors.toList() );
        }
        end = System.currentTimeMillis() - start;
        System.out.println("Dictionary read on (ms): "+end);
        return words;
    }

    public static String[] read( String fileName ) throws IOException {

        String[] words;
        System.out.println("Reading Dictionary");

        try(Stream<String> stream = Files.lines(Paths.get(fileName)).parallel() ){
            words = stream.toArray( ( size )->new String[size] );
        }
        end = System.currentTimeMillis() - start;
        System.out.println("Dictionary read on (ms):" +end);
        return words;
    }

    private static boolean parallelLoadingRequired(boolean[] parallelizedRead) {
        return parallelizedRead.length!=0 && parallelizedRead[0];
    }


}
