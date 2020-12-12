package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Solution executor for 2020 Advent of Code (https://adventofcode.com/2020). This driver program will execute the
 * solution for a specific day after loading the input file (if there is one) for the day. When running the program,
 * the user must supply the day to run as well as the root directory for where all input files are stored. Results are
 * printed to the console.
 */
public class SolutionRunner {

    public static void main(String[] args) throws IOException, ReflectiveOperationException {
        checkUsage(args);
        String input = loadInput(args[0], args[1]);
        DaySolution solution = loadSolutionClass(args[0]);
        execute(solution, input);
    }

    /**
     * Uses refletion to find the solution implementation based on the day number passed in. Solutions must reside in the
     * aoc.days package and be named DayNSolutionImpl where N is the day number.
     *
     * @param dayNumber
     * @return
     * @throws ReflectiveOperationException
     */
    private static DaySolution loadSolutionClass(String dayNumber) throws ReflectiveOperationException {
        Class<DaySolution> solutionClass = (Class<DaySolution>) Class.forName("aoc.days.Day" + dayNumber + "SolutionImpl");
        return solutionClass.getConstructor().newInstance();
    }

    /**
     * Checks command line arguments and exists if they are not valid.
     *
     * @param args
     */
    private static void checkUsage(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java aoc.SolutionRunner <day> <inputDir>");
            System.exit(1);
        } else {
            String dayMsg = "Day argument must be an integer between 1 and 25";
            try {
                int dayNum = Integer.parseInt(args[0]);
                if (dayNum < 1 || dayNum > 25) {
                    System.err.println(dayMsg);
                    System.exit(1);
                }
            } catch (NumberFormatException e) {
                System.err.println(dayMsg);
                System.exit(1);
            }
        }
    }

    /**
     * Loads the input file, if present, into a string. The input file must be located in a directory named "dayX" where
     * X is the day number to load. This directory must be located in the rootDir passed in. If there is no such file,
     * then this method returns null.
     *
     * @param dayNumber
     * @param rootDir
     * @return
     * @throws IOException
     */
    private static String loadInput(String dayNumber, String rootDir) throws IOException {
        String input = null;
        Path inputPath = Paths.get(rootDir, "day" + dayNumber, "input.txt");
        if (Files.exists(inputPath)) {
            input = Files.readString(inputPath);
        }
        return input;
    }

    /**
     * Runs both days from the day solution and outputs the timing each part takes.
     *
     * @param solution
     * @param input
     */
    private static void execute(DaySolution solution, String input) {
        System.out.println("Part 1:\n");
        long start = System.currentTimeMillis();
        solution.part1(input);
        System.out.println("\n\nTook " + (System.currentTimeMillis() - start) + " ms");
        System.out.println("\n\n==========\n\n");
        System.out.println("Part 2:\n");
        start = System.currentTimeMillis();
        solution.part2(input);
        System.out.println("\n\nTook " + (System.currentTimeMillis() - start) + " ms");
    }
}
