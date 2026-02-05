package org.poo.commands;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.cards.Hero;
import org.poo.cards.Minion;
import org.poo.fileio.ActionsInput;
import org.poo.gwentstone.Board;


public class UseAttackHero implements Command {
    @Override
    public void execute(final Board board, final ActionsInput action,
                        final ArrayNode output, final int playerTurn) {
        int cardAttackerX = action.getCardAttacker().getX();
        int cardAttackerY = action.getCardAttacker().getY();
        Hero attackedHero = board.getPlayerHero(3 - playerTurn);
        if (cardAttackerX >= board.getBoard().size()
                || cardAttackerY >= board.getBoard().get(cardAttackerX).size()) {
            return;
        }
        Minion cardAttacker = board.getBoard().get(cardAttackerX).get(cardAttackerY);
        if (cardAttacker.isFrozen()) {
            String errorMessage = "Attacker card is frozen.";
            errorMessage(action, output, cardAttackerX, cardAttackerY, errorMessage);
            return;
        }
        if (cardAttacker.isHasAttacked()) {
            String errorMessage = "Attacker card has already attacked this turn.";
            errorMessage(action, output, cardAttackerX, cardAttackerY, errorMessage);
            return;
        }

        if (playerTurn == 1) {
            for (int rowIdx = 0; rowIdx < 2; rowIdx++) {
                if (checkTankRow(board, action, output, cardAttackerX, cardAttackerY, rowIdx))
                    return;
            }
        }
        if (playerTurn == 2) {
            for (int rowIdx = 2; rowIdx < 4; rowIdx++) {
                if (checkTankRow(board, action, output, cardAttackerX, cardAttackerY, rowIdx))
                    return;
            }
        }
        cardAttacker.setHasAttacked(true);
        int health = attackedHero.getInfo().getHealth();
        int newHealth = health - cardAttacker.getInfo().getAttackDamage();
        attackedHero.getInfo().setHealth(newHealth);
        if (attackedHero.getInfo().getHealth() <= 0) {
            ObjectNode newNode = output.addObject();
            if (playerTurn == 1) {
                board.setPlayerOneWins(board.getPlayerOneWins() + 1);
                board.setTotalGamesPlayed(board.getTotalGamesPlayed() + 1);
                board.setGameHasFinished(true);
                newNode.put("gameEnded", "Player one killed the enemy hero.");
            } else {
                board.setPlayerTwoWins(board.getPlayerTwoWins() + 1);
                board.setTotalGamesPlayed(board.getTotalGamesPlayed() + 1);
                board.setGameHasFinished(true);
                newNode.put("gameEnded", "Player two killed the enemy hero.");
            }
        }
    }

    //@Override
    public void errorMessage(final ActionsInput action, final ArrayNode output,
                             final int cardAttackerX, final int cardAttackerY,
                             final String errorMessage) {
        ObjectNode newNode = output.addObject();
        newNode.put("command", action.getCommand());
        ObjectNode coordsAttacker = newNode.putObject("cardAttacker");
        coordsAttacker.put("x", cardAttackerX);
        coordsAttacker.put("y", cardAttackerY);
        newNode.put("error", errorMessage);
    }

    private boolean checkTankRow(final Board board, final ActionsInput action,
                                 final ArrayNode output, final int cardAttackerX,
                                 final int cardAttackerY, final int rowIdx) {
        if (rowIdx >= board.getBoard().size()) {
            return false;
        }
        for (int colIdx = 0; colIdx < board.getBoard().get(rowIdx).size(); colIdx++) {
            Minion minion = board.getBoard().get(rowIdx).get(colIdx);
            if (minion.isTank()) {
                String errorMessage = "Attacked card is not of type 'Tank'.";
                errorMessage(action, output, cardAttackerX, cardAttackerY, errorMessage);
                return true;
            }
        }
        return false;
    }
}
