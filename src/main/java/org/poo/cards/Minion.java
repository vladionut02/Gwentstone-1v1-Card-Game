package org.poo.cards;


import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CardInput;
import org.poo.gwentstone.Card;

@Setter
@Getter
public abstract class Minion extends Card {
    public enum Row {
        FRONT,
        BACK
    }

    private boolean isFrozen = false;
    private boolean hasAttacked = false;
    private boolean isTank = false;
    private Row row;


    public Minion(final CardInput input) {
        super(input);
    }

    /**
     * Decreases the health of the minion by the specified attack damage.
     *
     * @param attackDamage the amount of damage to subtract from the minion's health
     */
    public void decreaseHealth(final int attackDamage) {
        input.setHealth(input.getHealth() - attackDamage);
    }

    /**
     * Executes the ability of the minion card.
     *
     * @param attackedCard the card that is being attacked
     * @param cardToAttack the card that is performing the attack
     */
    public abstract void executeAbility(CardInput attackedCard, CardInput cardToAttack);
}
