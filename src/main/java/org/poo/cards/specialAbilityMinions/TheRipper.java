package org.poo.cards.specialAbilityMinions;


import org.poo.cards.Minion;
import org.poo.fileio.CardInput;

public class TheRipper extends Minion {
    public TheRipper(final CardInput input) {
        super(input);
        setRow(Row.FRONT);
    }

    /**
     * Executes the special ability of The Ripper card, which reduces the attack damage
     * of the attacked card by a specified number of points.
     *
     * @param attackedCard the card that is being attacked and whose attack damage will be reduced
     * @param cardToAttack the card that is performing the attack (not used in this method)
     */
    @Override
    public void executeAbility(final CardInput attackedCard, final CardInput cardToAttack) {
        int pointsToReduce = 2;
        int currentAttackDamage = attackedCard.getAttackDamage();
        if (currentAttackDamage < pointsToReduce) {
            attackedCard.setAttackDamage(0);
        } else {
            attackedCard.setAttackDamage(currentAttackDamage - pointsToReduce);
        }
    }
}
