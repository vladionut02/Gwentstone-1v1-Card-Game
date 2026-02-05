package org.poo.gwentstone;

import lombok.Getter;
import lombok.Setter;

import org.poo.cards.Hero;
import org.poo.cards.Minion;
import org.poo.cards.heroes.EmpressThorina;
import org.poo.cards.heroes.GeneralKocioraw;
import org.poo.cards.heroes.KingMudface;
import org.poo.cards.heroes.LordRoyce;
import org.poo.fileio.CardInput;
import java.util.ArrayList;

public final class Board {
    @Getter
    private final ArrayList<ArrayList<Minion>> board;

    @Getter
    @Setter
    private Deck player1Deck;
    @Getter
    @Setter
    private Deck player2Deck;

    @Setter
    @Getter
    private Player player1;
    @Setter
    @Getter
    private Player player2;

    @Setter
    @Getter
    private ArrayList<Card> player1Hand = new ArrayList<>();
    @Getter
    @Setter
    private ArrayList<Card> player2Hand = new ArrayList<>();
    @Setter
    @Getter
    private Hero hero1;
    @Getter
    @Setter
    private Hero hero2;

    @Getter
    private static final int MAXCARDSROW = 5;
    @Getter
    private static final int MAXROWS = 4;

    @Getter
    @Setter
    private int playerOneWins;
    @Getter
    @Setter
    private int playerTwoWins;
    @Getter
    @Setter
    private int totalGamesPlayed;

    @Getter@Setter
    private boolean gameHasFinished = false;

    public Board() {
        this.board = new ArrayList<>();
        for (int i = 0; i < MAXROWS; i++) {
            board.add(new ArrayList<>());
        }
        this.totalGamesPlayed = 0;
        this.playerOneWins = 0;
        this.playerTwoWins = 0;

        this.player1 = new Player();
        this.player2 = new Player();

    }

    /**
     * Creates a Hero object based on the provided CardInput.
     *
     * @param heroCard the CardInput object containing the hero's details
     * @return a Hero object corresponding to the hero's name in the CardInput,
     *         or null if the hero's name does not match any known heroes
     */
    public static Hero createHero(final CardInput heroCard) {
        return switch (heroCard.getName()) {
            case "Empress Thorina" -> new EmpressThorina(heroCard);
            case "General Kocioraw" -> new GeneralKocioraw(heroCard);
            case "King Mudface" -> new KingMudface(heroCard);
            case "Lord Royce" -> new LordRoyce(heroCard);
            default -> null;
        };
    }


    /**
     * Retrieves the hero for the specified player.
     *
     * @param playerIdx the index of the player (1 or 2)
     * @return the hero of the specified player, or null if the player index is invalid
     */
    public Hero getPlayerHero(final int playerIdx) {
        if (playerIdx == 1) {
            return getHero1();
        } else if (playerIdx == 2) {
            return getHero2();
        } else {
            System.out.println("Invalid player number\n");
            return null;
        }
    }

    /**
     * Retrieves the player based on the player number.
     *
     * @param playerNo the number of the player to retrieve (1 or 2)
     * @return the player corresponding to the given player number, or null if the player number is invalid
     */
    public Player getPlayer(final int playerNo) {
        if (playerNo == 1) {
            return player1;
        } else if (playerNo == 2) {
            return player2;
        }
        return null;
    }

    /**
     * Adds a card to the hand of the specified player.
     * <p>
     * @param playerNo the number of the player (1 or 2) to whom the card will be added
     * <p>
     * If the player number is 1 and player 1's deck is not empty, a card is drawn from player 1's
     *                deck and added to player 1's hand.
     * If the player number is 2 and player 2's deck is not empty, a card is drawn from player 2's
     *                 deck and added to player 2's hand.
     */
    public void addCardToHand(final int playerNo) {
        if (playerNo == 1 && !player1Deck.isEmpty()) {
            player1Hand.add(player1Deck.drawCard());
        } else if (playerNo == 2 && !player2Deck.isEmpty()) {
            player2Hand.add(player2Deck.drawCard());
        }
    }

    /**
     * Removes a card from the specified player's hand.
     *
     * @param playerNo the number of the player (1 or 2)
     * @param cardNo the index of the card to be removed from the player's hand
     */
    public void removeCardFromHand(final int playerNo, final int cardNo) {
        if (playerNo == 1) {
            player1Hand.remove(cardNo);
        } else if (playerNo == 2) {
            player2Hand.remove(cardNo);
        }
    }
}
