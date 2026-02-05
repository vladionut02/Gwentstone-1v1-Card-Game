package org.poo.cards.heroes;



import org.poo.cards.Hero;
import org.poo.cards.Minion;
import org.poo.fileio.CardInput;
import org.poo.gwentstone.Board;

import java.util.ArrayList;

public class GeneralKocioraw extends Hero {

    public GeneralKocioraw(final CardInput input) {
        super(input);
    }

    /**
     * Increases the attack damage of all minions in the specified row by 1.
     *
     * @param board The game board containing all rows of minions.
     * @param affectedRow The index of the row whose minions' attack damage will be increased.
     */
    @Override
    public void useAbility(final Board board, final int affectedRow) {
        ArrayList<Minion> row = board.getBoard().get(affectedRow);
        for (Minion minion : row) {
            int currentDamage = minion.getInfo().getAttackDamage();
            minion.getInfo().setAttackDamage(currentDamage + 1);
        }
    }
}
