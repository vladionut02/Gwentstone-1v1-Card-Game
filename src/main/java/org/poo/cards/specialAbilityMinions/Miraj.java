package org.poo.cards.specialAbilityMinions;


import org.poo.cards.Minion;
import org.poo.fileio.CardInput;

public class Miraj extends Minion {
    public Miraj(final CardInput input) {
        super(input);
        setRow(Row.FRONT);
    }

    /**
     * Executes the ability of swapping health values between the attacked card and
     * the card to attack.
     * <p>
     * @param attackedCard the card that is being attacked
     * @param cardToAttack the card that initiates the attack
     */
    @Override
    public void executeAbility(final CardInput attackedCard, final CardInput cardToAttack) {
        int temporaryHealth = cardToAttack.getHealth();
        cardToAttack.setHealth(attackedCard.getHealth());
        attackedCard.setHealth(temporaryHealth);
    }
}
