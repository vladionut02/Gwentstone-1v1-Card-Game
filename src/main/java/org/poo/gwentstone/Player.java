package org.poo.gwentstone;


import lombok.Getter;
import lombok.Setter;

public final class Player {

    @Setter
    @Getter
    private int mana;
    @Setter
    @Getter
    private boolean hasEndedTurn = false;
    private static final int MAXMANA = 10;

    public Player() {
        this.mana = 1;
    }

    /**
     * Increases the player's mana based on the current round number.
     * The mana increase is capped at a maximum value defined by MAXMANA.
     *
     * @param roundNumber the current round number, which determines the amount of mana to increase
     */
    public void increaseMana(final int roundNumber) {
        int manaIncrease = Math.min(roundNumber, MAXMANA);
        this.mana += manaIncrease;
    }

    /**
     * Decreases the player's mana by the specified amount.
     *
     * @param cardMana the amount of mana to be subtracted from the player's current mana
     */
    public void decreaseMana(final int cardMana) {
        this.mana -= cardMana;
    }

}
