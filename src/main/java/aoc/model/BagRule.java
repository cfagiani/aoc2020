package aoc.model;

import java.util.Objects;

/**
 * Single rule for bag nesting. It represents the number of bags of a given color that must be used.
 * Used in Day 7.
 */
public class BagRule {
    private String color;
    private int quantity;

    public BagRule(String color, int quantity) {
        this.color = color;
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BagRule bagRule = (BagRule) o;
        return quantity == bagRule.quantity &&
            Objects.equals(color, bagRule.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, quantity);
    }
}
