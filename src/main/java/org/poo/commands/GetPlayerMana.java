package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.gwentstone.Board;
import org.poo.gwentstone.Player;


public class GetPlayerMana implements Command {
    /**
     * Executes the action to get the mana of a player and adds the result to the output.
     *
     * @param board The game board containing the players and game state.
     * @param action The action input containing the command and player index.
     * @param output The JSON array node where the result will be added.
     * @param playerTurn The current player's turn.
     */
    public void execute(final Board board, final ActionsInput action,
                        final ArrayNode output, final int playerTurn) {
        Player player = board.getPlayer(action.getPlayerIdx());
        int mana = player.getMana();
        ObjectNode newNode = output.addObject();
        newNode.put("command", action.getCommand());
        newNode.put("playerIdx", action.getPlayerIdx());
        newNode.put("output", mana);
    }
}
