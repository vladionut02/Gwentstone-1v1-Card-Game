package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.gwentstone.Board;
import org.poo.gwentstone.Card;

public class GetCardAtPosition implements Command {

    /**
     * Executes the action to get the card at the specified position on the board.
     * If no card is available at the specified position, an appropriate message
     * is added to the output.
     * <p>
     * @param board The game board containing the cards.
     * @param action The action input containing the coordinates (x, y) and command.
     * @param output The JSON array node to which the result will be added.
     * @param playerTurn The current player's turn.
     */
    @Override
    public void execute(final Board board, final ActionsInput action,
                        final ArrayNode output, final int playerTurn) {
        int x = action.getX();
        int y = action.getY();
        Card card;
        try {
            card = board.getBoard().get(x).get(y);
        } catch (Exception ignored) {
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            newNode.put("x", x);
            newNode.put("y", y);
            newNode.put("output", "No card available at that position.");
            return;
        }
        card = board.getBoard().get(x).get(y);
        ObjectNode newNode = output.addObject();
        newNode.put("command", action.getCommand());
        newNode.put("x", x);
        newNode.put("y", y);
        card.addOutputNode(newNode);

    }
}
