package aoc.days;

import aoc.DaySolution;
import aoc.model.CombatPlayer;
import aoc.util.StringUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Plays Combat in part 1 and RecursiveCombat in part 2.
 */
public class Day22SolutionImpl implements DaySolution {

    public static long counter = 0L;

    @Override
    public void part1(String input) {
        List<String> lines = StringUtil.splitOnLines(input);
        CombatPlayer player1 = new CombatPlayer(lines.subList(0, lines.indexOf("Player 2:")));
        CombatPlayer player2 = new CombatPlayer(lines.subList(lines.indexOf("Player 2:"), lines.size()));
        while (player1.hasCards() && player2.hasCards()) {
            Long c1 = player1.getTopCard();
            Long c2 = player2.getTopCard();
            if (c1 > c2) {
                player1.addToDeck(c1, c2);
            } else {
                player2.addToDeck(c2, c1);
            }
        }
        System.out.println("Player 1: " + player1.getScore());
        System.out.println("Player 2: " + player2.getScore());

    }

    @Override
    public void part2(String input) {
        List<String> lines = StringUtil.splitOnLines(input);
        CombatPlayer player1 = new CombatPlayer(lines.subList(0, lines.indexOf("Player 2:")));
        CombatPlayer player2 = new CombatPlayer(lines.subList(lines.indexOf("Player 2:"), lines.size()));
        playGame(player1, player2);
        System.out.println("Player 1: " + player1.getScore());
        System.out.println("Player 2: " + player2.getScore());
    }

    /**
     * Plays RecursiveCombat. If this game instance has seen the same deck configuration, it immediately ends the game
     * with a win for player 1. Otherwise it will play a round.
     * <p>
     * Returns either 1 or 2 depending on which player won the game.
     *
     * @param player1
     * @param player2
     * @return
     */
    private int playGame(CombatPlayer player1, CombatPlayer player2) {
        counter++;
        Set<String> deckHistory = new HashSet<>();
        while (player1.hasCards() && player2.hasCards()) {
            String deckHistString = player1.deckToString() + "xx" + player2.deckToString();
            if (deckHistory.contains(deckHistString)) {
                return 1;
            }
            deckHistory.add(deckHistString);
            Long c1 = player1.getTopCard();
            Long c2 = player2.getTopCard();
            playRound(player1, c1, player2, c2);
        }
        if (player1.hasCards()) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Evaluates a round of recursive combat. If both cards are less than or equal to the deck sizes, this will trigger
     * a recursive sub-game to determine who wins this round. The sub game is played with players that have decks
     * consisting of a number of cards equal to the card they played in this round. If either card is greater than the
     * player's deck size, then the larger card wins.
     * <p>
     * Winner of the round adds both cards to their deck (their own card first).
     * <p>
     * Returns either 1 or 2 depending on which player won the round.
     *
     * @param p1
     * @param p2
     * @return
     */
    private int playRound(CombatPlayer p1, Long card1, CombatPlayer p2, Long card2) {
        if (card1 <= p1.deckSize() && card2 <= p2.deckSize()) {

            int winner = playGame(p1.copy(card1), p2.copy(card2));
            if (winner == 1) {
                p1.addToDeck(card1, card2);
            } else {
                p2.addToDeck(card2, card1);
            }
            return winner;
        } else {
            if (card1 > card2) {
                p1.addToDeck(card1, card2);
                return 1;
            } else {
                p2.addToDeck(card2, card1);
                return 2;
            }
        }
    }
}
