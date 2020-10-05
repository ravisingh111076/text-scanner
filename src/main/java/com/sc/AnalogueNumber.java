package com.sc;

public class AnalogueNumber {

    private StringBuilder numberString = new StringBuilder();

    public void append(char character) {
        numberString.append(character);
    }

    public String getNumberString() {
        return numberString.toString();
    }

    @Override
    public String toString() {
        return "AnalogueNumber{" +
                "numberString=" + numberString +
                '}';
    }
}
