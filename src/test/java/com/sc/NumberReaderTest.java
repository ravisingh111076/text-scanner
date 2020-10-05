package com.sc;

import org.junit.Assert;
import org.junit.Test;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UnknownFormatConversionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NumberReaderTest {

    @Test
    public void testSingleChunk() {
        List<String> lines = buildLinesFromFile("singleChunk");

        IntStream.range(0, lines.size()).skip(2).forEach(index -> {
            List<AnalogueNumber> listOfDigits = NumberReader.processInput(lines.get(index - 2),
                    lines.get(index - 1),
                    lines.get(index));
            //test size
            Assert.assertEquals(9, listOfDigits.size());
            IntStream.range(0, 8).forEachOrdered(in ->
                    //all are zero
                    Assert.assertEquals("0",
                            NumberReader.getDigitalString(listOfDigits.get(in))));
        });
    }

    @Test(expected = UnknownFormatConversionException.class)
    public void testInValidDataInput_ExpectedException() {
        List<String> lines = buildLinesFromFile("invalidData");
        IntStream.range(0, lines.size()).skip(2).forEach(index -> NumberReader.processInput(lines.get(index - 2),
                    lines.get(index - 1),
                    lines.get(index))
        );
    }

    private List<String> buildLinesFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(getClass().getClassLoader().getResource(fileName).getPath()))) {
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    @Test
    public void testMultipleChunk() {

        List<String> lines = buildLinesFromFile("multipleChunks");
        int indexCounter = 0;

        for (int i = 0; i < lines.size(); i++) {
            indexCounter++;
            if (indexCounter == 3) {
                //after each 3 increase index
                List<AnalogueNumber> listOfDigits = NumberReader.processInput(lines.get(i - 2),
                        lines.get(i - 1),
                        lines.get(i));
                //test size
                Assert.assertEquals(9, listOfDigits.size());
                IntStream.range(1, 9).forEachOrdered(in ->
                        //all are zero
                        Assert.assertEquals("" + in,
                                NumberReader.getDigitalString(listOfDigits.get(in - 1))));
                i++;
                indexCounter = 0;
            }
        }
    }

    @Test
    public void testMultipleChunkWithIllegalRow() {

        List<String> lines = buildLinesFromFile("multipleChunksWithIllegalRow");
        int indexCounter = 0;

        for (int lineNo = 0; lineNo < lines.size(); lineNo++) {
            indexCounter++;
            if (indexCounter == 3) {
                //after each 3 increase index
                List<AnalogueNumber> listOfDigits = NumberReader.processInput(lines.get(lineNo - 2),
                        lines.get(lineNo - 1),
                        lines.get(lineNo));
                //test size
                Assert.assertEquals(9, listOfDigits.size());

                //check Illegal value at line 1, value at index 6
                if(lineNo == 6)
                Assert.assertEquals("ILL",
                            NumberReader.getDigitalString(listOfDigits.get(6)));

                IntStream.range(1, 9).forEachOrdered(in -> {
                            //all are zero
                          if( in != 7)
                                Assert.assertEquals("" + in,
                                        NumberReader.getDigitalString(listOfDigits.get(in - 1)));
                        });

                lineNo++;
                indexCounter = 0;
            }
        }
    }

    @Test
    public void testMultipleChunkWithRepetitiveRow() {

        List<String> lines = buildLinesFromFile("multipleChunksWithRepetitiveValue");
        int indexCounter = 0;

        for (int lineNo = 0; lineNo < lines.size(); lineNo++) {
            indexCounter++;
            if (indexCounter == 3) {
                //after each 3 increase index
                List<AnalogueNumber> listOfDigits = NumberReader.processInput(lines.get(lineNo - 2),
                        lines.get(lineNo - 1),
                        lines.get(lineNo));
                //test size
                Assert.assertEquals(9, listOfDigits.size());
                //check repetitive 8 value at line 1, value at index 6
                if(lineNo == 6)
                    Assert.assertEquals("8",
                            NumberReader.getDigitalString(listOfDigits.get(6)));
                };

                lineNo++;
                indexCounter = 0;
            }

    }
}
