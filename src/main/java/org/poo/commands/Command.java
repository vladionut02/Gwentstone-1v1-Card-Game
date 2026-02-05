package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.gwentstone.Board;


public interface Command {
    /**
     * Executes a command on the given board based on the specified action and player turn.
     *
     * @param board      the game board on which the command is executed
     * @param action     the action to be performed
     * @param output     the output node where the result of the action is stored
     * @param playerTurn the current player's turn
     */
    void execute(Board board, ActionsInput action, ArrayNode output, int playerTurn);

    /**
     * Logs an error message related to a card action.
     *
     * @param action The action that caused the error.
     * @param output The output array node where the error will be logged.
     * @param cardAttackerX The x-coordinate of the attacking card.
     * @param cardAttackerY The y-coordinate of the attacking card.
     * @param cardAttackedX The x-coordinate of the attacked card.
     * @param cardAttackedY The y-coordinate of the attacked card.
     * @param errorMessage The error message to be logged.
     */
    default void error(ActionsInput action, ArrayNode output, int cardAttackerX, int cardAttackerY,
                       int cardAttackedX, int cardAttackedY, String errorMessage) {
        ObjectNode newNode = output.addObject();
        newNode.put("command", action.getCommand());
        ObjectNode coordsAttacker = newNode.putObject("cardAttacker");
        coordsAttacker.put("x", cardAttackerX);
        coordsAttacker.put("y", cardAttackerY);
        ObjectNode coordsAttacked = newNode.putObject("cardAttacked");
        coordsAttacked.put("x", cardAttackedX);
        coordsAttacked.put("y", cardAttackedY);
        newNode.put("error", errorMessage);
    }
}
