package aoc.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Full set of rules for bag nesting. Used in Day 7.
 */
public class BagRuleGraph {

    private Map<String, Set<BagRule>> bagMapping;
    private Map<String, Set<String>> reverseMappings;

    public BagRuleGraph() {
        this.bagMapping = new HashMap<>();
        this.reverseMappings = new HashMap<>();
    }

    /**
     * Adds a child rule for the given source bag color. The rule will specify how many bags of the dest color must
     * be contained inside a bag of the source color.
     *
     * @param source
     * @param dest
     * @param qty
     */
    public void addChild(String source, String dest, int qty) {
        Set<BagRule> rules = bagMapping.computeIfAbsent(source, s -> new HashSet<>());
        rules.add(new BagRule(dest, qty));
        Set<String> reverseMapping = reverseMappings.computeIfAbsent(dest, s -> new HashSet<>());
        reverseMapping.add(source);
    }

    /**
     * Returns the set of colors of the bags that can contain the color passed in.
     *
     * @param color
     * @return
     */
    public Set<String> getBagsThatCanContainColor(String color) {
        return reverseMappings.getOrDefault(color, Collections.EMPTY_SET);
    }

    /**
     * Returns the set of bagRule objects that apply to a bag of the color specified.
     *
     * @param color
     * @return
     */
    public Set<BagRule> getMapping(String color) {
        return bagMapping.getOrDefault(color, Collections.EMPTY_SET);
    }
}
