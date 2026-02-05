package org.poo.cards.heroes;

import org.poo.cards.Hero;
import org.poo.cards.Minion;
import org.poo.fileio.CardInput;
import org.poo.gwentstone.Board;
import java.util.ArrayList;

public class LordRoyce extends Hero {

    public LordRoyce(final CardInput input) {
        super(input);
    }

    /**
     * Uses the hero's ability to freeze all minions in the specified row on the board.
     *
     * @param board The game board where the ability is used.
     * @param affectedRow The index of the row on the board where the ability will be applied.
     */
    @Override
    public void useAbility(final Board board, final int affectedRow) {
        ArrayList<Minion> row = board.getBoard().get(affectedRow);
        for (Minion minion : row) {
            minion.setFrozen(true);
        }
    }
}
