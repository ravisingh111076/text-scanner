package com.sc;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NumberReader {

    private static void validateLine(String ... line) {
        Stream.of(line).forEach(l -> {
            if(l.length() != 27) throw new UnknownFormatConversionException("not valid line length:" + l.length());
        }
        );

    }
    public static List<AnalogueNumber> processInput(String line1, String line2, String line3) {
        validateLine(line1,line2,line3);
        List<AnalogueNumber> numbers = transformToAnalogueNumberList(line1, line2, line3);
        return numbers;
    }

    private static List<AnalogueNumber> transformToAnalogueNumberList(String... lines) {
        //initialising empty
        List<AnalogueNumber> numbers = initializeList(lines[0]);
        IntStream.range(0, 3).forEach(lineIdx -> {
            updateAnalogueNumberList(numbers, lines[lineIdx]);
        });
        return numbers;
    }

    private static List<AnalogueNumber> initializeList(String line) {
        int count = line.length() / 3;
        List<AnalogueNumber> numbersLst = new ArrayList<>(count);
        IntStream.range(0, count).forEach(idx -> numbersLst.add(new AnalogueNumber()));
        return numbersLst;
    }

    private static void updateAnalogueNumberList(List<AnalogueNumber> numbers, String line) {
        IntStream.range(0, line.length() / 3).forEach(numberPos -> {
            int startInclusive = numberPos * 3;
            IntStream.range(startInclusive, startInclusive + 3).forEach(charIdx -> {
                numbers.get(numberPos).append(line.charAt(charIdx));
            });
        });
    }

    public static String getDigitalString(AnalogueNumber aNumber) {

        switch (aNumber.getNumberString()) {
            case " _ | ||_|":
                return "0";
            case "     |  |":
                return "1";
            case " _  _||_ ":
                return "2";
            case " _  _| _|":
                return "3";
            case "   |_|  |":
                return "4";
            case " _ |_  _|":
                return "5";
            case " _ |_ |_|":
                return "6";
            case " _   |  |":
                return "7";
            case " _ |_||_|":
                return "8";
            case " _ |_| _|":
                return "9";
            default:
                return "ILL";
        }
    }
}
