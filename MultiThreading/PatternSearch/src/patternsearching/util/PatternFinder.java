package patternsearching.util;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PatternFinder {

    public List<Integer> find(File file, String pattern) throws IOException {

        List<Integer> lineNumbers=new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(file));
            int lineNo=1;
            String line;

            while((line=br.readLine())!=null){

                if(line.contains(pattern)){
                    lineNumbers.add(lineNo);
                }
               lineNo++;
            }

        return lineNumbers;

    }
}
