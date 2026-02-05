package org.poo.cards.heroes;



import org.poo.cards.Hero;
import org.poo.cards.Minion;
import org.poo.fileio.CardInput;
import org.poo.gwentstone.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EmpressThorina extends Hero {

    public EmpressThorina(final CardInput input) {
        super(input);
    }

    /**
     * Uses the ability of Empress Thorina to remove the minion with the highest health
     * from the specified row on the board.
     *
     * @param board The game board where the ability is used.
     * @param affectedRow The index of the row on the board that is affected by the ability.
     */
    @Override
    public void useAbility(final Board board, final int affectedRow) {
        ArrayList<Minion> row = board.getBoard().get(affectedRow);
        Minion maxHealthMinion = Collections.max(row,
                Comparator.comparingInt(minion -> minion.getInfo().getHealth()));
        row.remove(maxHealthMinion);
    }
}
