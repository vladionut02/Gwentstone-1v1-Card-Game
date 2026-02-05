package org.poo.commands;


import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.cards.Minion;
import org.poo.fileio.ActionsInput;
import org.poo.gwentstone.Board;


import java.util.ArrayList;

public class CardUsesAttack implements Command {
    /**
     * Executes the attack action on the board.
     *
     * @param board      The game board where the action takes place.
     * @param action     The action input containing details about the attacker
     *                   and the attacked cards.
     * @param output     The output node to store any error messages or results.
     * @param playerTurn The current player's turn (1 or 2).
     */
    @Override
    public void execute(final Board board, final ActionsInput action,
                        final ArrayNode output, final int playerTurn) {
        int cardAttackerX = action.getCardAttacker().getX();
        int cardAttackerY = action.getCardAttacker().getY();
        int cardAttackedX = action.getCardAttacked().getX();
        int cardAttackedY = action.getCardAttacked().getY();
        if (cardAttackerX >= board.getBoard().size() || cardAttackedX >= board.getBoard().size()) {
            return;
        }
        if (cardAttackerY >= board.getBoard().get(cardAttackerX).size()
            || cardAttackedY >= board.getBoard().get(cardAttackedX).size()) {
            return;
        }
        Minion cardAttacker = board.getBoard().get(cardAttackerX).get(cardAttackerY);
        Minion cardAttacked = board.getBoard().get(cardAttackedX).get(cardAttackedY);
        if (playerTurn == 1) {
            if (cardAttackedX == 2 || cardAttackedX == 3) {
                String errorMessage = "Attacked card does not belong to the enemy.";
                error(action, output, cardAttackerX, cardAttackerY,
                        cardAttackedX, cardAttackedY, errorMessage);
                return;
            }
        } else {
            if (cardAttackedX == 0 || cardAttackedX == 1) {
                String errorMessage = "Attacked card does not belong to the enemy.";
                error(action, output, cardAttackerX, cardAttackerY,
                        cardAttackedX, cardAttackedY, errorMessage);
                return;
            }
        }
        if (cardAttacker.isHasAttacked()) {
            String errorMessage = "Attacker card has already attacked this turn.";
            error(action, output, cardAttackerX, cardAttackerY,
                    cardAttackedX, cardAttackedY, errorMessage);
            return;
        }
        if (cardAttacker.isFrozen()) {
            String errorMessage = "Attacker card is frozen.";
            error(action, output, cardAttackerX, cardAttackerY,
                    cardAttackedX, cardAttackedY, errorMessage);
            return;
        }
        if (cardAttacked.isTank()) {
            cardAttacker.setHasAttacked(true);
            cardAttacked.decreaseHealth(cardAttacker.getInfo().getAttackDamage());
            if (cardAttacked.getInfo().getHealth() <= 0) {
                ArrayList<Minion> row = board.getBoard().get(cardAttackedX);
                row.remove(cardAttacked);
            }
            return;
        }

        if (playerTurn == 1) {
            for (int rowIdx = 0; rowIdx < 2; rowIdx++) {
                if (checkTankRow(board, action, output, cardAttackerX,
                        cardAttackerY, cardAttackedX, cardAttackedY, rowIdx)) {
                    return;
                }
            }
            cardAttacker.setHasAttacked(true);
            cardAttacked.decreaseHealth(cardAttacker.getInfo().getAttackDamage());
            if (cardAttacked.getInfo().getHealth() <= 0) {
                ArrayList<Minion> row = board.getBoard().get(cardAttackedX);
                row.remove(cardAttacked);
            }
        }
        if (playerTurn == 2) {
            for (int rowIdx = 2; rowIdx < 4; rowIdx++) {
                if (checkTankRow(board, action, output, cardAttackerX,
                        cardAttackerY, cardAttackedX, cardAttackedY,
                        rowIdx)) {
                    return;
                }
            }
            cardAttacker.setHasAttacked(true);
            cardAttacked.decreaseHealth(cardAttacker.getInfo().getAttackDamage());
            if (cardAttacked.getInfo().getHealth() <= 0) {
                ArrayList<Minion> row = board.getBoard().get(cardAttackedX);
                row.remove(cardAttacked);
            }
        }

    }

    private boolean checkTankRow(final Board board, final ActionsInput action,
                                 final ArrayNode output, final int cardAttackerX,
                                 final int cardAttackerY, final int cardAttackedX,
                                 final int cardAttackedY, final int rowIdx) {
        if (rowIdx >= board.getBoard().size()) {
            return false;
        }
        for (int colIdx = 0; colIdx < board.getBoard().get(rowIdx).size(); colIdx++) {
            Minion minion = board.getBoard().get(rowIdx).get(colIdx);
            if (minion.isTank()) {
                String errorMessage = "Attacked card is not of type 'Tank'.";
                error(action, output, cardAttackerX, cardAttackerY, cardAttackedX, cardAttackedY,
                        errorMessage);
                return true;
            }
        }
        return false;
    }
}
