package org.poo.commands;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.cards.Minion;
import org.poo.fileio.ActionsInput;
import org.poo.gwentstone.Board;
import org.poo.gwentstone.Card;
import org.poo.gwentstone.Player;


import java.util.ArrayList;

public class PlaceCard implements Command {
    /**
     * Executes the action of placing a card from the player's hand onto the board.
     *
     * @param board      The game board where the card will be placed.
     * @param action     The action input containing details about the card to be placed.
     * @param output     The output node where any error messages will be added.
     * @param playerTurn The current player's turn (1 or 2).
     */
    @Override
    public void execute(final Board board, final ActionsInput action,
                        final ArrayNode output, final int playerTurn) {
        int cardIdxToPlace = action.getHandIdx();
        ArrayList<Card> hand = playerTurn == 1 ? board.getPlayer1Hand() : board.getPlayer2Hand();
        if (cardIdxToPlace < 0 || cardIdxToPlace >= hand.size()) {
            ObjectNode newNode = output.addObject();
            newNode.put("output", "Invalid card index.");
            return;
        }

        Minion cardToPlace = (Minion) hand.get(cardIdxToPlace);
        Minion.Row row = cardToPlace.getRow();
        Player player = playerTurn == 1 ? board.getPlayer1() : board.getPlayer2();

        if (cardToPlace.getInfo().getMana() > player.getMana()) {
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            newNode.put("handIdx", cardIdxToPlace);
            newNode.put("error", "Not enough mana to place card on table.");
            return;
        } else if (!checkRow(cardToPlace, board, playerTurn)) {
            ObjectNode newNode = output.addObject();
            newNode.put("error", "Not enough space on table to place card.");
            return;
        }

        int rowIdx = getRowIdx(playerTurn, row);
        board.getBoard().get(rowIdx).add(cardToPlace);
        player.decreaseMana(cardToPlace.getInfo().getMana());
        board.removeCardFromHand(playerTurn, cardIdxToPlace);
    }

    private static int getRowIdx(final int playerTurn, final Minion.Row row) {
        int rowIdx = -1;
        if (playerTurn == 2) {
            if (row == Minion.Row.FRONT) {
                rowIdx = 1;
            } else if (row == Minion.Row.BACK) {
                rowIdx = 0;
            }
        } else if (playerTurn == 1) {
            if (row == Minion.Row.FRONT) {
                rowIdx = 2;
            } else if (row == Minion.Row.BACK) {
                rowIdx = 3;
            }
        }
        return rowIdx;
    }

    private boolean checkRow(final Minion card,
                             final Board board,
                             final int playerIdx) {
        int rowIdx = -1;
        Minion.Row row = card.getRow();
        if (playerIdx == 2) {
            if (row == Minion.Row.FRONT) {
                rowIdx = 1;
            } else if (row == Minion.Row.BACK) {
                rowIdx = 0;
            }
        } else if (playerIdx == 1) {
            if (row == Minion.Row.FRONT) {
                rowIdx = 2;
            } else if (row == Minion.Row.BACK) {
                rowIdx = 3;
            }
        } else {
            return false;
        }
        if (rowIdx < 0 || rowIdx >= Board.getMAXROWS()) {
            return false;
        }

        return board.getBoard().get(rowIdx).size()
                < Board.getMAXCARDSROW();
    }
}
