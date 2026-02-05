package org.poo.cards.normalMinions;


import org.poo.cards.Minion;
import org.poo.fileio.CardInput;

public class Goliath extends Minion {

    public Goliath(final CardInput input) {
        super(input);
        setRow(Row.FRONT);
        this.setTank(true);
    }

    @Override
    public void executeAbility(final CardInput attackedCard, final CardInput cardToAttack) {

    }
}
