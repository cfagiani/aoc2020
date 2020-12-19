package aoc.days;

import aoc.DaySolution;
import aoc.util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Evaluates bus timetable information to find wait time for a bus given a specific arrival time (part 1) and the
 * minimum wait time such that a bus leaves at subsequent minutes (part 2).
 */
public class Day13SolutionImpl implements DaySolution {
    @Override
    public void part1(String input) {

        List<String> lines = StringUtil.splitOnLines(input);
        long targetTime = Long.parseLong(lines.get(0));
        long minBus = Long.MAX_VALUE;
        long minTime = Long.MAX_VALUE;
        String[] routes = lines.get(1).split(",");
        for (String route : routes) {
            if (route.equals("x")) {
                continue;
            }
            long routeNum = Long.parseLong(route);

            long waitTime = routeNum - (targetTime % routeNum);
            if (waitTime < minTime) {
                minTime = waitTime;
                minBus = routeNum;
            }
        }
        System.out.println("Product of waitTime and route is " + (minBus * minTime));
    }

    /**
     * Uses "chinese remainder theorem" to solve system of congruences in form of
     * x % n == b
     * <p>
     * sample input: 17,x,13,19
     * system of congruences..
     * t % 17 == 0
     * (t+2) % 13 == 0
     * (t+3) % 19 == 0
     *
     * @param input
     */
    @Override
    public void part2(String input) {
        List<String> routesString = Arrays.asList(StringUtil.splitOnLines(input).get(1).split(","));
        List<Long> routes = routesString.stream().map(s -> s.equals("x") ? -1 : Long.parseLong(s)).collect(Collectors.toList());
        long productAll = routes.stream().filter(r -> r > 0).reduce(1L, (a, b) -> a * b);
        long startTime = 0;
        for (int i = 0; i < routes.size(); i++) {
            if (routes.get(i) < 0) {
                continue;
            }
            long route = routes.get(i);
            long nI = (productAll / route);
            long bs = (-i % route) + route;
            startTime += bs * nI * getInverse(nI, (int) route);
        }
        System.out.println("Start at " + (startTime % productAll));
    }


    /**
     * Finds the inverse of a num % modulo.
     *
     * @param num
     * @param modulo
     * @return
     */
    private static int getInverse(long num, int modulo) {
        // num * candidate == 1 (mod modulo)
        // increment candidate until solution is found
        int candidate = 1;
        while ((num * candidate) % modulo != 1) {
            candidate++;
        }
        return candidate;
    }
}
