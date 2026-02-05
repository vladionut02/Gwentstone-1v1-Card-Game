package org.poo.commands;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.cards.Hero;
import org.poo.fileio.ActionsInput;
import org.poo.gwentstone.Board;
import org.poo.gwentstone.Player;


public class UseHeroAbility implements Command {
    /**
     * Executes the hero's ability on the specified row of the board.
     *
     * @param board      The game board on which the action is performed.
     * @param action     The action input containing the command and affected row.
     * @param output     The output node to store the result of the action.
     * @param playerTurn The current player's turn (1 or 2).
     */
    public void execute(final Board board, final ActionsInput action,
                        final ArrayNode output, final int playerTurn) {
        Hero hero = board.getPlayerHero(playerTurn);
        Player player = board.getPlayer(playerTurn);
        if (hero.getInfo().getMana() > player.getMana()) {
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            newNode.put("affectedRow", action.getAffectedRow());
            newNode.put("error", "Not enough mana to use hero's ability.");
            return;
        }
        if (hero.isHasAttacked()) {
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            newNode.put("affectedRow", action.getAffectedRow());
            newNode.put("error", "Hero has already attacked this turn.");
            return;
        }
        if (hero.getInfo().getName().equals("Lord Royce")
                || hero.getInfo().getName().equals("Empress Thorina")) {
            if (playerTurn == 1) {
                if (action.getAffectedRow() == 2 || action.getAffectedRow() == 3) {
                    ObjectNode newNode = output.addObject();
                    newNode.put("command", action.getCommand());
                    newNode.put("affectedRow", action.getAffectedRow());
                    newNode.put("error", "Selected row does not belong to the enemy.");
                    return;
                }
            } else {
                if (action.getAffectedRow() == 0 || action.getAffectedRow() == 1) {
                    ObjectNode newNode = output.addObject();
                    newNode.put("command", action.getCommand());
                    newNode.put("affectedRow", action.getAffectedRow());
                    newNode.put("error", "Selected row does not belong to the enemy.");
                    return;
                }
            }
        } else {
            if (playerTurn == 1) {
                if (action.getAffectedRow() == 0 || action.getAffectedRow() == 1) {
                    ObjectNode newNode = output.addObject();
                    newNode.put("command", action.getCommand());
                    newNode.put("affectedRow", action.getAffectedRow());
                    newNode.put("error", "Selected row does not belong to the current player.");
                    return;
                }
            } else {
                if (action.getAffectedRow() == 2 || action.getAffectedRow() == 3) {
                    ObjectNode newNode = output.addObject();
                    newNode.put("command", action.getCommand());
                    newNode.put("affectedRow", action.getAffectedRow());
                    newNode.put("error", "Selected row does not belong to the current player.");
                    return;
                }
            }
        }
        int currentMana = player.getMana();
        int heroMana = hero.getInfo().getMana();
        player.setMana(currentMana - heroMana);
        hero.useAbility(board, action.getAffectedRow());
        hero.setHasAttacked(true);
    }
}
