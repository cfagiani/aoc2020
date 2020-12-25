package aoc.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a food as a list of its ingredients as well as a list of allergens it contains.
 * Used in Day 21.
 */
public class Food {
    private static final String ALLERGEN_IND = "(contains ";
    private List<String> ingredients;
    private List<String> knownAllergens;

    public Food(String line) {
        knownAllergens = new ArrayList<>();
        String ingredientString = line.trim();
        if (line.contains("(contains")) {
            ingredientString = line.substring(0, line.indexOf(ALLERGEN_IND));
            knownAllergens = Arrays.asList(line.substring(line.indexOf(ALLERGEN_IND) + ALLERGEN_IND.length())
                .replace(")", "").trim().split(", "));
        } else {
            knownAllergens = Collections.emptyList();
        }
        ingredients = Arrays.asList(ingredientString.split(" "));
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getKnownAllergens() {
        return knownAllergens;
    }
}
