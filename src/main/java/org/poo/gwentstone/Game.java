package org.poo.gwentstone;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.cards.Minion;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.CardInput;
import org.poo.fileio.GameInput;
import org.poo.fileio.Input;
import org.poo.commands.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Game {
    private Board board;

    private Input inputData;
    private ArrayNode output;

    private int startingPlayer;
    private int playerTurn;
    private int roundNumber = 0;
    private int gameSeed;
    private int totalGamesPlayed = 0;
    private int playerOneWins = 0;
    private int playerTwoWins = 0;

    private final Map<String, Command> commandMap = new HashMap<>();

    /**
     * The Game class initializes a map of commands that can be executed during the game.
     * Each command is associated with a specific action that can be performed by the player.
     * <p>
     * Commands:
     * - "getPlayerDeck": Retrieves the player's deck.
     * - "getPlayerHero": Retrieves the player's hero.
     * - "getPlayerTurn": Retrieves the current player's turn.
     * - "placeCard": Places a card on the table.
     * - "getCardsInHand": Retrieves the cards in the player's hand.
     * - "getPlayerMana": Retrieves the player's mana.
     * - "getCardsOnTable": Retrieves the cards on the table.
     * - "cardUsesAttack": Executes an attack using a card.
     * - "getCardAtPosition": Retrieves the card at a specific position.
     * - "cardUsesAbility": Executes a card's ability.
     * - "useAttackHero": Executes an attack on the hero.
     * - "useHeroAbility": Executes the hero's ability.
     * - "getFrozenCardsOnTable": Retrieves the frozen cards on the table.
     * - "getTotalGamesPlayed": Retrieves the total number of games played.
     * - "getPlayerOneWins": Retrieves the number of wins for player one.
     * - "getPlayerTwoWins": Retrieves the number of wins for player two.
     */
    public Game() {
        commandMap.put("getPlayerDeck", new GetPlayerDeck());
        commandMap.put("getPlayerHero", new GetPlayerHero());
        commandMap.put("getPlayerTurn", new GetPlayerTurn());
        commandMap.put("placeCard", new PlaceCard());
        commandMap.put("getCardsInHand", new GetCardsInHand());
        commandMap.put("getPlayerMana", new GetPlayerMana());
        commandMap.put("getCardsOnTable", new GetCardsOnTable());
        commandMap.put("cardUsesAttack", new CardUsesAttack());
        commandMap.put("getCardAtPosition", new GetCardAtPosition());
        commandMap.put("cardUsesAbility", new CardUsesAbility());
        commandMap.put("useAttackHero", new UseAttackHero());
        commandMap.put("useHeroAbility", new UseHeroAbility());
        commandMap.put("getFrozenCardsOnTable", new GetFrozenCards());
        commandMap.put("getTotalGamesPlayed", new StatsCommands());
        commandMap.put("getPlayerOneWins", new StatsCommands());
        commandMap.put("getPlayerTwoWins", new StatsCommands());

    }

    /**
     * Starts the game with the provided input data and outputs the results.
     *
     * @param inputFile the input data for the game, including player decks, heroes, and actions
     * @param output the output node to store the results of the game
     */
    public void startGame(final Input inputFile, final ArrayNode output) {

        this.inputData = inputFile;
        this.output = output;
        int numberOfGames = inputFile.getGames().size();

        ArrayList<ArrayList<CardInput>> playerOneDecks = inputData.getPlayerOneDecks().getDecks();
        ArrayList<ArrayList<CardInput>> playerTwoDecks = inputData.getPlayerTwoDecks().getDecks();

        for (int i = 0; i < numberOfGames; i++) {
            this.board = new Board();
            this.gameSeed = inputFile.getGames().get(i).getStartGame().getShuffleSeed();
            this.startingPlayer = inputFile.getGames().get(i).getStartGame().getStartingPlayer();
            this.playerTurn = startingPlayer;
            this.roundNumber = 0;

            board.setGameHasFinished(false);
            board.setTotalGamesPlayed(totalGamesPlayed);
            board.setPlayerOneWins(playerOneWins);
            board.setPlayerTwoWins(playerTwoWins);

            Player player1 = board.getPlayer1();
            player1.setMana(1);
            Player player2 = board.getPlayer2();
            player2.setMana(1);

            board.setPlayer1Hand(new ArrayList<>());
            board.setPlayer2Hand(new ArrayList<>());

            GameInput currentGame = inputData.getGames().get(i);
            int player1DeckNo = currentGame.getStartGame().getPlayerOneDeckIdx();
            board.setPlayer1Deck(new Deck(playerOneDecks.get(player1DeckNo)));
            int player2DeckNo = currentGame.getStartGame().getPlayerTwoDeckIdx();
            board.setPlayer2Deck(new Deck(playerTwoDecks.get(player2DeckNo)));

            Collections.shuffle(board.getPlayer1Deck().getCards(), new Random(gameSeed));
            Collections.shuffle(board.getPlayer2Deck().getCards(), new Random(gameSeed));

            CardInput hero1Info = currentGame.getStartGame().getPlayerOneHero();
            CardInput hero2Info = currentGame.getStartGame().getPlayerTwoHero();
            board.setHero1(Board.createHero(hero1Info));
            board.setHero2(Board.createHero(hero2Info));

            board.addCardToHand(1);
            board.addCardToHand(2);

            ArrayList<ActionsInput> actions = currentGame.getActions();
            for (ActionsInput action : actions) {
                if (!board.isGameHasFinished() || action.getCommand().startsWith("get")) {
                    parseAction(action);
                } else {
                    break;
                }
            }

            totalGamesPlayed = board.getTotalGamesPlayed();
            playerOneWins = board.getPlayerOneWins();
            playerTwoWins = board.getPlayerTwoWins();

        }
    }


    /**
     * Processes the given action by finding and executing the corresponding command.
     *
     * @param action the action to be processed, containing details such as the command and relevant parameters.
     */
    private void parseAction(final ActionsInput action) {
        Command command = commandMap.get(action.getCommand());
        if (command != null) {
            command.execute(board, action, output, playerTurn);
        } else if (action.getCommand().equals("endPlayerTurn")) {
            roundNumber++;
            if (playerTurn == 1) {
                for (int rowIdx = 2; rowIdx < 4; rowIdx++) {
                    for (int colIdx = 0; colIdx < board.getBoard().get(rowIdx).size(); colIdx++) {
                        Minion minion = board.getBoard().get(rowIdx).get(colIdx);
                        minion.setFrozen(false);
                    }
                }
            } else {
                for (int rowIdx = 0; rowIdx < 2; rowIdx++) {
                    for (int colIdx = 0; colIdx < board.getBoard().get(rowIdx).size(); colIdx++) {
                        Minion minion = board.getBoard().get(rowIdx).get(colIdx);
                        minion.setFrozen(false);
                    }
                }
            }
            playerTurn = 3 - playerTurn;
            if (roundNumber % 2 == 0) {
                board.addCardToHand(1);
                board.addCardToHand(2);

                board.getPlayer1().increaseMana(roundNumber / 2 + 1);
                board.getPlayer2().increaseMana(roundNumber / 2 + 1);

                for (int rowIdx = 0; rowIdx < board.getBoard().size(); rowIdx++) {
                    for (int colIdx = 0; colIdx < board.getBoard().get(rowIdx).size(); colIdx++) {
                        Minion minion = board.getBoard().get(rowIdx).get(colIdx);
                        minion.setHasAttacked(false);
                    }
                }
                board.getHero1().setHasAttacked(false);
                board.getHero2().setHasAttacked(false);
            }
        }
    }

}
