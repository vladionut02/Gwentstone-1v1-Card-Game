package org.poo.cards.normalMinions;


import org.poo.cards.Minion;
import org.poo.fileio.CardInput;


public class Berserker extends Minion {

    public Berserker(final CardInput input) {
        super(input);
        setRow(Row.BACK);
    }

    @Override
    public void executeAbility(final CardInput attackedCard, final CardInput cardToAttack) {

    }
}
