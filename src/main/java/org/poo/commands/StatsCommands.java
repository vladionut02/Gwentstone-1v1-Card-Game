package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.gwentstone.Board;


/**
 * The StatsCommands class implements the Command interface and provides
 * functionality to execute various statistics-related commands on the game board.
 * It handles commands to get the total number of games played, the number of wins
 * for player one, and the number of wins for player two.
 */
public class StatsCommands implements Command {
    /**
     * Executes the given action on the board and appends the result to the output.
     *
     * @param board The game board on which the action is executed.
     * @param action The action to be executed, containing the command.
     * @param output The JSON array node to which the result will be appended.
     * @param playerTurn The current player's turn.
     */
    @Override
    public void execute(final Board board, final ActionsInput action,
                        final ArrayNode output, final int playerTurn) {
        if (action.getCommand().equals("getTotalGamesPlayed")) {
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            newNode.put("output", board.getTotalGamesPlayed());
        } else if (action.getCommand().equals("getPlayerOneWins")) {
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            newNode.put("output", board.getPlayerOneWins());
        } else {
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            newNode.put("output", board.getPlayerTwoWins());
        }
    }
}
