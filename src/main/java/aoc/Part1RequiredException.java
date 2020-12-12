package aoc;

public class Part1RequiredException extends RuntimeException {
    public Part1RequiredException() {
        super("Part 1 must be run prior to running part 2");
    }
}
