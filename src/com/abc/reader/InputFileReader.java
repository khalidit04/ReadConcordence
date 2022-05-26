package com.abc.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class InputFileReader {
    public static List<String> readFileContents(String filePath){
        List<String> lines= new ArrayList<>();
        try{
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null)
                lines.add(line);

        }catch (Exception e){
            throw new RuntimeException("Error occured while reading the file",e);
        }

        return  lines;
    }
}
