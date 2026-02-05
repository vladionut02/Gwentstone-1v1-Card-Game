package org.poo.commands;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.cards.Minion;
import org.poo.fileio.ActionsInput;
import org.poo.gwentstone.Board;


/**
 * The GetFrozenCards class implements the Command interface and is responsible for
 * executing the action of retrieving all frozen cards from the board.
 */
public class GetFrozenCards implements Command {
    /**
     * Executes the action to get all frozen cards on the board and adds them to the output.
     *
     * @param board The game board containing the minions.
     * @param action The action input containing the command details.
     * @param output The JSON array node where the output will be added.
     * @param playerTurn The current player's turn.
     */
    public void execute(final Board board, final ActionsInput action,
                        final ArrayNode output, final int playerTurn) {
        ObjectNode newNode = output.addObject();
        newNode.put("command", action.getCommand());
        ArrayNode outputNode = newNode.withArray("output");
        for (int rowIdx = 0; rowIdx < board.getBoard().size(); rowIdx++) {
            //ArrayNode rowNode = outputNode.addArray();
            for (int colIdx = 0; colIdx < board.getBoard().get(rowIdx).size(); colIdx++) {
                Minion minion = board.getBoard().get(rowIdx).get(colIdx);
                if (minion.isFrozen()) {
                    minion.addOutputNode(outputNode);
                }
            }
        }
    }
}
