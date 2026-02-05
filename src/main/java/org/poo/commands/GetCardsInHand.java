package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.gwentstone.Board;
import org.poo.gwentstone.Card;

import java.util.ArrayList;

public class GetCardsInHand implements Command {
    /**
     * Executes the action of getting the cards in hand for the specified player.
     *
     * @param board The game board containing the state of the game.
     * @param action The action input containing the command and player index.
     * @param output The JSON array node where the output will be added.
     * @param playerTurn The current player's turn.
     */
    @Override
    public void execute(final Board board, final ActionsInput action,
                        final ArrayNode output, final int playerTurn) {
        ObjectNode newNode = output.addObject();
        newNode.put("command", action.getCommand());
        int requestedPlayerNo = action.getPlayerIdx();
        ArrayList<Card> hand = requestedPlayerNo == 1
                ? board.getPlayer1Hand() : board.getPlayer2Hand();

        newNode.put("playerIdx", requestedPlayerNo);

        ArrayNode outputNode = newNode.putArray("output");

        for (Card card : hand) {
            card.addOutputNode(outputNode);
        }
    }
}
