package org.poo.cards;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CardInput;
import org.poo.gwentstone.Board;
import org.poo.gwentstone.Card;

public abstract class Hero extends Card {
    private static final int HERO_STARTING_HEALTH = 30;
    private static final int HERO_ATTACK_DAMAGE = 0;
    @Getter
    @Setter
    private boolean hasAttacked = false;

    public Hero(final CardInput input) {
        super(input);
        input.setHealth(HERO_STARTING_HEALTH);
        input.setAttackDamage(HERO_ATTACK_DAMAGE);
    }

    /**
     * Uses the hero's special ability on the specified row of the board.
     *
     * @param board the game board on which the ability is used
     * @param affectedRow the row of the board that is affected by the ability
     */
    public abstract void useAbility(Board board, int affectedRow);

    /**
     * Computes the output for the hero and populates the given ObjectNode with the hero's details.
     *
     * @param outputNode the ObjectNode to be populated with the hero's details
     *                   including mana, description, colors, name, and health.
     */
    @Override
    public void computeOutput(final ObjectNode outputNode) {
        outputNode.put("mana", getInfo().getMana());
        outputNode.put("description", getInfo().getDescription());
        ArrayNode colors = outputNode.withArray("colors");
        for (String color : getInfo().getColors()) {
            colors.add(color);
        }
        outputNode.put("name", getInfo().getName());
        outputNode.put("health", getInfo().getHealth());
    }
}
