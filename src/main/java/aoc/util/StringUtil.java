package aoc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtil {

    /**
     * Breaks the string passed in into an ArrayList of Integer objects. The string is split by any whitespace (spaces,
     * tabs, or newlines).
     *
     * @param input
     * @return
     */
    public static List<Integer> convertToInts(String input) {
        List<Integer> intList = new ArrayList<>();
        for (String line : input.split("\\s+")) {
            if (!line.isBlank()) {
                intList.add(Integer.parseInt(line));
            }
        }
        return intList;
    }

    /**
     * Breaks the string passed in into an ArrayList of Long objects. The string is split by any whitespace (spaces,
     * tabs, or newlines).
     *
     * @param input
     * @return
     */
    public static List<Long> convertToLong(String input) {
        List<Long> longList = new ArrayList<>();
        for (String line : input.split("\\s+")) {
            if (!line.isBlank()) {
                longList.add(Long.parseLong(line));
            }
        }
        return longList;
    }


    /**
     * Splits the input into an arrayList of Strings where each list item is a single line.
     *
     * @param input
     * @return
     */
    public static List<String> splitOnLines(String input) {
        return Arrays.asList(input.split("\\r?\\n"));
    }
}
