package org.poo.gwentstone;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;


public class Card {
    protected CardInput input;

    public Card(final CardInput input) {
        this.input = input;
    }

    public CardInput getInfo() {
        return input;
    }

    /**
     * Adds an output node to the given ObjectNode.
     *
     * @param node the ObjectNode to which the output node will be added
     */
    public void addOutputNode(final ObjectNode node) {
        ObjectNode outputNode = node.putObject("output");
        computeOutput(outputNode);
    }

    /**
     * Adds a new ObjectNode to the provided ArrayNode and computes its output.
     *
     * @param node the ArrayNode to which a new ObjectNode will be added
     */
    public void addOutputNode(final ArrayNode node) {
        ObjectNode outputNode = node.addObject();
        computeOutput(outputNode);
    }

    /**
     * Populates the given ObjectNode with the properties of the card.
     *
     * @param outputNode the ObjectNode to be populated with card properties
     * <p>
     * The following properties are added to the outputNode:
     * - "mana": the mana cost of the card
     * - "attackDamage": the attack damage of the card
     * - "health": the health points of the card
     * - "description": the description of the card
     * - "colors": an array of colors associated with the card
     * - "name": the name of the card
     */
    public void computeOutput(final ObjectNode outputNode) {
        outputNode.put("mana", getInfo().getMana());
        outputNode.put("attackDamage", getInfo().getAttackDamage());
        outputNode.put("health", getInfo().getHealth());
        outputNode.put("description", getInfo().getDescription());
        ArrayNode colors = outputNode.withArray("colors");
        for (String color : getInfo().getColors()) {
            colors.add(color);
        }
        outputNode.put("name", getInfo().getName());
    }
}
