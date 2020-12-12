package aoc;

/**
 * Interface for the solution to each day's problem. Problems always have two parts. The same input will be passed
 * to both parts but implementors can decide to store processed input in class variables so it does not need to be
 * re-processed for the second part if they so choose.
 */
public interface DaySolution {

    void part1(String input);

    void part2(String input);
}
