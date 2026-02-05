package org.poo.commands;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.cards.Hero;
import org.poo.fileio.ActionsInput;
import org.poo.gwentstone.Board;


/**
 * The GetPlayerHero class implements the Command interface and is responsible for
 * executing the action of retrieving a player's hero from the board and adding
 * the relevant information to the output.
 */
public class GetPlayerHero implements Command {
    @Override
    public void execute(final Board board, final ActionsInput action,
                        final ArrayNode output, final int playerTurn) {
        ObjectNode newNode = output.addObject();
        newNode.put("command", action.getCommand());
        int playerIdx = action.getPlayerIdx();
        Hero hero = board.getPlayerHero(playerIdx);
        newNode.put("playerIdx", playerIdx);
        hero.addOutputNode(newNode);
    }
}
