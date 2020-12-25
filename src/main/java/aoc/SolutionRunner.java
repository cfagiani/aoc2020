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

    private static final String ALL_DAYS = "all";
    private static final int START_DAY = 1;
    private static final int END_DAY = 25;

    public static void main(String[] args) throws IOException, ReflectiveOperationException {
        checkUsage(args);
        int startDay = START_DAY;
        int endDay = END_DAY;
        if (!ALL_DAYS.equalsIgnoreCase(args[0])) {
            // we're running a specific day. Set start and end to that value
            startDay = Integer.parseInt(args[0]);
            endDay = startDay;
        }
        long start = System.currentTimeMillis();
        for (int i = startDay; i <= endDay; i++) {
            System.out.println("\n===DAY " + i + "===");
            String input = loadInput(i, args[1]);
            try {
                DaySolution solution = loadSolutionClass(i);
                execute(solution, input);
            } catch (ReflectiveOperationException ex) {
                System.out.println("Solution implementation not found");
            }
        }
        System.out.println("\nTotal time: " + ((double)(System.currentTimeMillis() - start))/1000.0+" seconds");

    }

    /**
     * Uses reflection to find the solution implementation based on the day number passed in. Solutions must reside in the
     * aoc.days package and be named DayNSolutionImpl where N is the day number.
     *
     * @param dayNumber
     * @return
     * @throws ReflectiveOperationException
     */
    private static DaySolution loadSolutionClass(int dayNumber) throws ReflectiveOperationException {
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
            String dayMsg = "Day argument must be an integer between 1 and 25 or 'all'";
            try {
                if (ALL_DAYS.equalsIgnoreCase(args[0])) {
                    return;
                }
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
    private static String loadInput(int dayNumber, String rootDir) throws IOException {
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
