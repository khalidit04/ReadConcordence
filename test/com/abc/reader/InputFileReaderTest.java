package com.abc.reader;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputFileReaderTest {

    @Test(expected = FileNotFoundException.class)
    public void throwExceptionWhenFilePathNotValid() throws FileNotFoundException {
        InputFileReader.readFileContents("abc");
    }

    @Test
    public void readFileContentsSuccessfully() throws FileNotFoundException {
        Path filePath= Paths.get("test","resources","example.txt").toAbsolutePath();
        var lines = InputFileReader.readFileContents(filePath.toString());
        Assert.assertEquals(lines.size(),4);
    }
    @Test
    public void readFileContentsOfEmptyFile() throws FileNotFoundException {
        Path filePath= Paths.get("test","resources","empty_file.txt").toAbsolutePath();
        var lines = InputFileReader.readFileContents(filePath.toString());
        Assert.assertEquals(lines.size(),0);
    }
}
