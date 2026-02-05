package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.gwentstone.Board;
import org.poo.gwentstone.Card;

/**
 * The GetCardsOnTable class implements the Command interface and is responsible for
 * retrieving the cards on the table and adding them to the output.
 */
public class GetCardsOnTable implements Command {
    /**
     * Executes the action to get the cards on the table and adds the result to the output.
     *
     * @param board The game board containing the cards.
     * @param action The action input containing the command details.
     * @param output The JSON array node to which the output will be added.
     * @param playerTurn The current player's turn.
     */
    public void execute(final Board board, final ActionsInput action,
                        final ArrayNode output, final int playerTurn) {
        ObjectNode newNode = output.addObject();
        newNode.put("command", action.getCommand());
        ArrayNode outputNode = newNode.withArray("output");
        for (int rowIdx = 0; rowIdx < board.getBoard().size(); rowIdx++) {
            ArrayNode rowNode = outputNode.addArray();
            for (int colIdx = 0; colIdx < board.getBoard().get(rowIdx).size(); colIdx++) {
                Card card = board.getBoard().get(rowIdx).get(colIdx);
                card.addOutputNode(rowNode);
            }
        }
    }
}
