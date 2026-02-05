package org.poo.gwentstone;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.poo.cards.normalMinions.Berserker;
import org.poo.cards.normalMinions.Goliath;
import org.poo.cards.normalMinions.Sentinel;
import org.poo.cards.normalMinions.Warden;
import org.poo.cards.specialAbilityMinions.Disciple;
import org.poo.cards.specialAbilityMinions.Miraj;
import org.poo.cards.specialAbilityMinions.TheCursedOne;
import org.poo.cards.specialAbilityMinions.TheRipper;
import org.poo.fileio.CardInput;
import java.util.ArrayList;

/**
 * The Deck class represents a collection of Card objects.
 * The deck can be initialized from a list of CardInput objects.
 */
public class Deck {
    @Getter
    private final ArrayList<Card> cards = new ArrayList<>();

    public Deck(final ArrayList<CardInput> inputCards) {
        initializeDeck(inputCards);
    }

    private void initializeDeck(final ArrayList<CardInput> inputCards) {
        for (CardInput inputCard : inputCards) {
            CardInput card = new CardInput();
            card.setHealth(inputCard.getHealth());
            card.setMana(inputCard.getMana());
            card.setAttackDamage(inputCard.getAttackDamage());
            card.setColors(inputCard.getColors());
            card.setName(inputCard.getName());
            card.setDescription(inputCard.getDescription());
            cards.add(createCard(card));
        }
    }

    /**
     * Creates a new Card object based on the CardInput provided.
     *
     * @param cardInput the input data for creating the card, containing information such as name,
     *                 health, attack damage, mana, etc.
     * @return a new instance of a Card subclass based on the card name in the CardInput, or null
     * if no matching card name is found.
     */
    public static Card createCard(final CardInput cardInput) {
        String cardName = cardInput.getName();
        return switch (cardName) {
            case "Berserker" -> new Berserker(cardInput);
            case "Goliath" -> new Goliath(cardInput);
            case "Sentinel" -> new Sentinel(cardInput);
            case "Warden" -> new Warden(cardInput);
            case "Disciple" -> new Disciple(cardInput);
            case "Miraj" -> new Miraj(cardInput);
            case "The Cursed One" -> new TheCursedOne(cardInput);
            case "The Ripper" -> new TheRipper(cardInput);
            default -> null;
        };
    }

    /**
     * Adds an output node to the specified ObjectNode. The added node represents the state of
     * the current Deck instance and includes details about each card in the deck.
     *
     * @param node the ObjectNode to which the output node will be added. This node should
     *             represent the context or container in which the output details are being
     *             included.
     */
    public void addOutputNode(final ObjectNode node) {
        ArrayNode outputNode = node.withArray("output");
        fillOutput(outputNode);
    }

    /**
     * Fills the provided ArrayNode with the output representation of each card in the deck.
     *
     * @param outputNode the ArrayNode to be filled with card output data
     */
    private void fillOutput(final ArrayNode outputNode) {
        for (Card card : getCards()) {
            card.addOutputNode(outputNode);
        }
    }

    /**
     * Draws a card from the deck.
     * <p>
     * This method removes the first card from the deck and returns it.
     *
     * @return the card that was drawn from the deck
     */
    public Card drawCard() {
        return cards.removeFirst();
    }

    /**
     * Checks if the deck is empty.
     *
     * @return true if the deck has no cards, false otherwise.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
