package org.poo.cards.specialAbilityMinions;


import org.poo.cards.Minion;
import org.poo.fileio.CardInput;

public class Disciple extends Minion {

    public Disciple(final CardInput input) {
        super(input);
        this.setRow(Row.BACK);
    }

    /**
     * Executes the ability of the disciple, which heals the attacked card by increasing its health.
     *
     * @param attackedCard The card that will receive the health boost.
     * @param cardToAttack The card that is intended to use the ability.
     */
    @Override
    public void executeAbility(final CardInput attackedCard, final CardInput cardToAttack) {
        int healthToGive = 2;
        attackedCard.setHealth(attackedCard.getHealth() + healthToGive);
    }
}
