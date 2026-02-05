package org.poo.cards.heroes;



import org.poo.cards.Hero;
import org.poo.cards.Minion;
import org.poo.fileio.CardInput;
import org.poo.gwentstone.Board;

import java.util.ArrayList;

public class KingMudface extends Hero {

    public KingMudface(final CardInput input) {
        super(input);
    }

    /**
     * Uses the hero's ability to increase the health of all minions in the specified row by 1.
     *
     * @param board The game board containing all rows and minions.
     * @param affectedRow The index of the row on which the ability will be used.
     */
    @Override
    public void useAbility(final Board board, final int affectedRow) {
        ArrayList<Minion> row = board.getBoard().get(affectedRow);
        for (Minion minion : row) {
            int currentHealth = minion.getInfo().getHealth();
            minion.getInfo().setHealth(currentHealth + 1);
        }
    }
}
