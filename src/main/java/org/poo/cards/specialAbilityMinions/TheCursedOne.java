package org.poo.cards.specialAbilityMinions;


import org.poo.cards.Minion;
import org.poo.fileio.CardInput;

public class TheCursedOne extends Minion {

    public TheCursedOne(final CardInput input) {
        super(input);
        this.setRow(Row.BACK);
    }

    /**
     * Executes the special ability of "The Cursed One" card.
     * This ability swaps the attack damage and health of the attacked card.
     *
     * @param attackedCard the card that is being attacked and whose attributes will be swapped
     * @param cardToAttack the card that is performing the attack (not used in this ability)
     */
    @Override
    public void executeAbility(final CardInput attackedCard, final CardInput cardToAttack) {
        int auxDamage = attackedCard.getAttackDamage();
        attackedCard.setAttackDamage(attackedCard.getHealth());
        attackedCard.setHealth(auxDamage);
    }
}
