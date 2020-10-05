package com.sc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DigitalBoard {

    static Consumer<List<AnalogueNumber>> displayLine = (numbers) ->  {
        System.out.println(numbers.stream()
                .map(analogueNumber -> NumberReader.getDigitalString(analogueNumber))
                .collect(Collectors.joining()));
    };

    static Function<String, List<String>> buildLinesFromFile = (filePath)-> {
        List<String> lines;
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return lines;
    };

    public static void displayAll(String filePath) {
        List<String> lines = buildLinesFromFile.apply(filePath);
        int indexCounter = 0;

        for (int i = 0; i < lines.size(); i++) {
            indexCounter ++;
            if (indexCounter == 3) {
                //after each 3 increase index
                displayLine.accept(NumberReader.processInput(lines.get(i - 2),
                        lines.get(i - 1),
                        lines.get(i)));
                i++;
                indexCounter = 0;
            }
        }
    }

    public static void main(String ...args) {

        System.out.println("singleChunk Data:");
        DigitalBoard.displayAll(DigitalBoard.class.
                getClassLoader().getResource("singleChunk").getPath());

        System.out.println("multipleChunks Data:");

        DigitalBoard.displayAll(DigitalBoard.class.
                getClassLoader().getResource("multipleChunks").getPath());

        System.out.println("multipleChunksWithIllegalRow Data:");

        DigitalBoard.displayAll(DigitalBoard.class.
                getClassLoader().getResource("multipleChunksWithIllegalRow").getPath());

        System.out.println("multipleChunksWithRepetitiveValue Data:");

        DigitalBoard.displayAll(DigitalBoard.class.
                getClassLoader().getResource("multipleChunksWithRepetitiveValue").getPath());
    }
}
