package aoc.days;

import aoc.DaySolution;
import aoc.model.Food;
import aoc.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Uses a list of ingredients in a made up language to determine which are allergens. In part one, we compute the list
 * of ingredients that cannot be allergens. In part 2, we output an list of ingredients that are known allergens.
 */
public class Day21SolutionImpl implements DaySolution {


    @Override
    public void part1(String input) {
        List<Food> foods = buildFoodList(input);
        Map<String, Set<String>> possibleAllergens = buildPossibleAllergenMapping(foods);

        // any ingredient in our possibleAllergens map COULD be an allergen
        Set<String> ingredientsWithAllergens = possibleAllergens.values().stream().flatMap(Set::stream).collect(Collectors.toSet());

        // the set difference between all the ingredients and the ingredientsWithAllergens set are those that CANNOT
        // have an allergen. First get the set of all ingredients, then use removeAll to get the difference
        Set<String> noAllergenIngredients = foods.stream().map(Food::getIngredients).flatMap(List::stream).collect(Collectors.toSet());
        noAllergenIngredients.removeAll(ingredientsWithAllergens);

        // count the number of times the ingredients show up in the food list
        int count = 0;
        for (String ingredient : noAllergenIngredients) {
            for (Food food : foods) {
                if (food.getIngredients().contains(ingredient)) {
                    count++;
                }
            }
        }
        System.out.println("Ingredients show up " + count + " times");
    }

    @Override
    public void part2(String input) {
        Map<String, Set<String>> possibleAllergens = buildPossibleAllergenMapping(buildFoodList(input));
        SortedMap<String, String> allergenMapping = new TreeMap<>();
        // go over the possibleAllergen mappings and remove anything with a possible list of size 1
        // remove that ingredient from all other possible mappings since we know what it is
        // do this in a loop until all mappings are found.
        while (allergenMapping.size() != possibleAllergens.size()) {
            possibleAllergens.entrySet().stream().filter(e -> e.getValue().size() == 1).forEach(e -> {
                String ingredient = e.getValue().stream().findFirst().get();
                allergenMapping.put(e.getKey(), ingredient);
                possibleAllergens.values().stream().forEach(l -> l.remove(ingredient));
            });
        }

        //Since we want the output alphabetized on the allergen, we can just iterate since we used a sorted map and
        //allergen is the key.
        StringBuilder builder = new StringBuilder();
        for (String value : allergenMapping.values()) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(value);
        }
        System.out.println("Canonical dangerous ingredients: " + builder.toString());
    }

    /**
     * Parses the input and returns a List of Food objects.
     *
     * @param input
     * @return
     */
    private List<Food> buildFoodList(String input) {
        List<Food> foodList = new ArrayList<>();
        for (String line : StringUtil.splitOnLines(input)) {
            foodList.add(new Food(line));
        }
        return foodList;
    }

    /**
     * Builds the mapping of possible allergens by iterating over all the foods in the list and, for each one,
     * building a map of allergen to the intersection of the set of ingredients for all foods where that allergen
     * appears.
     *
     * @param foods
     * @return
     */
    private Map<String, Set<String>> buildPossibleAllergenMapping(List<Food> foods) {
        Map<String, Set<String>> possibleAllergens = new HashMap<>();
        for (Food food : foods) {

            for (String allergen : food.getKnownAllergens()) {
                Set<String> possibleIngredient = possibleAllergens.computeIfAbsent(allergen, i -> {
                    Set<String> ingredients = new HashSet<>();
                    ingredients.addAll(food.getIngredients());
                    return ingredients;
                });
                possibleIngredient.retainAll(food.getIngredients());
            }
        }
        return possibleAllergens;
    }
}
