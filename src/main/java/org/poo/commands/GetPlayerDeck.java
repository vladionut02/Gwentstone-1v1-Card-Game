package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.gwentstone.Board;
import org.poo.gwentstone.Deck;


/**
 * The GetPlayerDeck class implements the Command interface and is responsible for
 * executing the action of retrieving a player's deck from the board and adding it
 * to the output.
 */
public class GetPlayerDeck implements Command {
    @Override
    public void execute(final Board board, final ActionsInput action,
                        final ArrayNode output, final int playerTurn) {
        ObjectNode newNode = output.addObject();
        newNode.put("command", action.getCommand());
        int requestedPlayerNo = action.getPlayerIdx();
        newNode.put("playerIdx", requestedPlayerNo);

        Deck deckToOutput = requestedPlayerNo == 1
                ? board.getPlayer1Deck() : board.getPlayer2Deck();
        deckToOutput.addOutputNode(newNode);
    }
}
