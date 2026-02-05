package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.gwentstone.Board;


public class GetPlayerTurn implements Command {
    /**
     * Executes the action to get the current player's turn and adds the result to the output.
     *
     * @param board The game board on which the action is performed.
     * @param action The action input containing the command details.
     * @param output The JSON array node where the result will be added.
     * @param playerTurn The current player's turn.
     */
    @Override
    public void execute(final Board board, final ActionsInput action,
                        final ArrayNode output, final int playerTurn) {
        ObjectNode newNode = output.addObject();
        newNode.put("command", action.getCommand());
        newNode.put("output", playerTurn);
    }
}
