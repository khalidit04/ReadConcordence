package com.abc.reader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InputFileReader {
    public static List<String> readFileContents(String filePath) throws FileNotFoundException{
        List<String> lines= new ArrayList<>();
        try{
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null)
                lines.add(line);

        }catch (IOException e){
            throw new FileNotFoundException(e.getMessage());
        }

        return  lines;
    }
}
