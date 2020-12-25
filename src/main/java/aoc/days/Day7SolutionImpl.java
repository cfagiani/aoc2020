package aoc.days;

import aoc.DaySolution;
import aoc.Part1RequiredException;
import aoc.model.BagRule;
import aoc.model.BagRuleGraph;
import aoc.util.StringUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Builds a graph of Bag nesting rules and then uses that graph to determine how many bags can contain a bag of a
 * specific color (part 1) and how many bags a bag of a specific color must contain (part 2).
 */
public class Day7SolutionImpl implements DaySolution {

    BagRuleGraph ruleGraph;

    /**
     * Finds all possible nestings of bags that contain a gold bag.
     *
     * @param input
     */
    @Override
    public void part1(String input) {
        initRuleGraph(input);
        String targetBag = "shiny gold";
        Set<String> colors = new HashSet<>();
        Stack<String> toExplore = new Stack<>();
        toExplore.addAll(ruleGraph.getBagsThatCanContainColor(targetBag));
        while (!toExplore.isEmpty()) {
            String color = toExplore.pop();
            if (!colors.contains(color) && !color.equals(targetBag)) {
                //first time we've seen this color so add it to the search horizon
                toExplore.addAll(ruleGraph.getBagsThatCanContainColor(color));
                colors.add(color);
            }
        }
        long outerMostBags = colors.stream().filter(c -> !ruleGraph.getMapping(c).isEmpty()).count();
        System.out.println("There are " + outerMostBags + " bags that can contain the " + targetBag + " bag");

    }

    @Override
    public void part2(String input) {
        if(ruleGraph == null){
            throw new Part1RequiredException();
        }
        int count = 0;
        Stack<BagRule> rulesToSatisfy = new Stack<>();
        String myBag = "shiny gold";
        rulesToSatisfy.addAll(ruleGraph.getMapping(myBag));
        while (!rulesToSatisfy.isEmpty()) {
            BagRule rule = rulesToSatisfy.pop();
            count += rule.getQuantity();
            for (int i = 0; i < rule.getQuantity(); i++) {
                rulesToSatisfy.addAll(ruleGraph.getMapping(rule.getColor()));
            }
        }
        System.out.println("One " + myBag + " contains " + count + " bags.");
    }

    private void initRuleGraph(String input) {
        ruleGraph = new BagRuleGraph();
        List<String> rules = StringUtil.splitOnLines(input);
        for (String r : rules) {
            if (r.contains(" contain no other bags")) {
                continue;
            }
            String[] parts = r.split(" bags contain");
            String[] mappings = parts[1].split(",");
            for (String mapping : mappings) {
                String[] ruleParts = mapping.trim().split(" ", 2);

                ruleGraph.addChild(parts[0], ruleParts[1].replace(" bags", "")
                    .replace(" bag", "").replace(".", ""), Integer.parseInt(ruleParts[0]));

            }
        }
    }
}
