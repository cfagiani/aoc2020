package aoc.model;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Model for a player of the Combat card game.
 * Used in day 22.
 */
public class CombatPlayer {
    private Deque<Long> deck;

    protected CombatPlayer(Deque<Long> deck) {
        this.deck = deck;
    }

    /**
     * Creates a new player and initializes its deck with the values from the input list.
     *
     * @param cards
     */
    public CombatPlayer(List<String> cards) {
        deck = new LinkedList<>();
        for (String line : cards) {
            if (!line.isBlank() && !line.contains("Player")) {
                deck.addLast(Long.parseLong(line));
            }
        }
    }

    /**
     * Adds the cards passed in to the BOTTOM of the deck.
     *
     * @param cards
     */
    public void addToDeck(Long... cards) {
        for (Long card : cards) {
            deck.addLast(card);
        }
    }

    /**
     * Returns the top card from the player's deck (removing it from the deck).
     *
     * @return
     */
    public Long getTopCard() {
        return deck.pop();
    }

    public boolean hasCards() {
        return !deck.isEmpty();
    }

    public int deckSize() {
        return deck.size();
    }

    /**
     * Computes a score for a player.
     *
     * @return
     */
    public Long getScore() {
        long total = 0;
        long mult = deck.size();
        while (!deck.isEmpty()) {
            total += mult * deck.pop();
            mult--;
        }
        return total;
    }

    /**
     * Returns a string representation of a deck
     *
     * @return
     */
    public String deckToString() {
        StringBuilder builder = new StringBuilder();
        deck.stream().forEach(c -> builder.append(c).append(" "));
        return builder.toString();
    }


    /**
     * Returns a new CombatPlayer with a deck that contains a copy of the first count cards in its deck.
     *
     * @param count
     * @return
     */
    public CombatPlayer copy(Long count) {
        LinkedList<Long> clonedList = (LinkedList<Long>) ((LinkedList<Long>) (this.deck)).clone();
        while (clonedList.size() > count) {
            clonedList.removeLast();
        }
        return new CombatPlayer(clonedList);
    }
}
