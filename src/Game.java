import java.util.*;

public class Game {
    private final int MAX_CARDS = 4, MAX_ABILITY_CARDS = 2, MAX_PLAYERS = 2, MAX_ROUNDS = 4;
    private Player gameAI, playerWhoSkipped;

    private ArrayList<Player> playerList;

    String types[] = new String[2], effects[] = new String[3];

    private String COLOR_GREEN = "\u001B[32m", COLOR_RED = "\u001B[31m",
        COLOR_WHITE = "\u001B[37m", COLOR_CYAN = "\u001B[36m", COLOR_YELLOW = "\u001b[33m",
            COLOR_RESET = "\033[0m";

    private int currentRound;

    private boolean skipActive;

    /**
     * Initialize a new Game Object.
     */
    public Game() {
        types[0] = "+"; types[1] = "-";

        effects[0] = "Steal"; effects[1] = "Skip";
        effects[2] = "PointBoost";

        this.currentRound = 1;
        this.skipActive = false;

    }

    /**
     * Uses the inputted card to play the game.
     * @param card The card to play.
     * @param player The player playing the card.
     */
    private void play(Card card, Player player) {
        if (card.type().equals(types[0])) {
            player.addPoints(card.points());

        } else if (card.type().equals(types[1])) {
            player.removePoints(2);
        } else if (card.hasEffect()) {
            if (card.effect().equals("Steal")) {
                for (Player toSteal : playerList) {
                    toSteal.removePoints(3);
                    player.addPoints(1);
                }
            } else if (card.effect().equals("Skip")) {
                this.playerWhoSkipped = player;
                this.skipActive = true;

            } else if (card.effect().equals("PointBoost")) {
                for (Player toAdd : playerList) {
                    toAdd.addPoints(3);
                    if (toAdd == player) player.addPoints(5);
                }
            }
        }
    }

    /**
     * Chooses the players chosen card based off it's position, and plays it.
     * @param plrCard The position of the card.
     * @param player The player who's choosing the card.
     * @return Retun if the card has been found and played successfully.
     */
    public boolean playCard(int plrCard, Player player) {
        boolean cardPlayed = false;

        ArrayList<Card> tempCardList = player.cardList();

        boolean cardFound = false;

        for (int i = 0; (i < tempCardList.size() && !cardFound); i++) {
            Card card = tempCardList.get(i);

            if (card.position() == plrCard) {
                cardFound = true;
                play(card, player);
                player.removeCard(card);
                cardPlayed = true;
            }
        }

        if (cardPlayed) player.sortCards();

        return cardPlayed;
    }

    /**
     * This chooses the card for the AI and plays it.
     * @param player The player Object of the AI.
     * @return Returns the AI's move choice to display it.
     */
    public String playCard(Player player) {
        String cardToPlay = "";
        if (player == this.gameAI) {
            ArrayList<Card> tempCardList = player.cardList();

            int num = (int) (Math.random() * tempCardList.size());

            for (int i = 0; i < tempCardList.size(); i++) {
                Card card = tempCardList.get(i);

                if (card.position() == num) {
                    play(card, player);
                    player.removeCard(card);
                    cardToPlay = "" + card.position();
                }
            }
        }
        return cardToPlay;
    }

    /**
     * Initializes the player list with the chosen amount of players.
     * @param playerCount The amount of players.
     * @param aiActive Whether or not the AI is playing.
     * @param playerNames The name array of everyone.
     * @return Return whether it's finished initializing or not.
     */
    public boolean initalizePlayers(int playerCount, boolean aiActive, ArrayList<String> playerNames) {
        playerList = new ArrayList<Player>();
        boolean readyToContinue = false;

        if (playerCount > MAX_PLAYERS)
            throw new ArithmeticException("\n\n Too many players, the max is: " + MAX_PLAYERS);

        int plrInc;

        if (aiActive) plrInc = 0;
        else plrInc = 1;

        for (int i = playerCount; i >= plrInc; i--) {
            String playerName ;

            if (i == 0) playerName = "Player 0 (AI)";
            else playerName = playerNames.get(i - 1);

            Player newPlayer = new Player(playerName);

            if (i == 0) {
                this.gameAI = newPlayer;
                playerList.add(playerList.size(), newPlayer);
            } else {
                playerList.add(newPlayer);
                playerNames.remove(playerName);
            }

            for (int ci = 0; ci < MAX_CARDS; ci++) {
                Card newCard = new Card(((int)(Math.random() * 4) + 2), types[(int) (Math.random() * types.length)], "");
                newPlayer.addCard(newCard);
            }

            for (int ai = 0; ai < MAX_ABILITY_CARDS; ai++) {
                Card newCard = new Card(9, "A", effects[(int) (Math.random() * effects.length)]);
                newPlayer.addCard(newCard);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                newPlayer.sortCards();
            }

        }

        readyToContinue = true;

        return readyToContinue;
    }


    /**
     * @return Returns the player list as an array.
     */
    public ArrayList<Player> getPlayers() {
        if (playerList.isEmpty())
            throw new ArrayIndexOutOfBoundsException("\n\n There's no players!");

        return this.playerList;
    }

    /**
     * Resets all the variables for the game.
     */
    public void clear() {
        this.playerList.clear();
        this.playerList = null;
        this.gameAI = null;
        this.playerWhoSkipped = null;

        this.currentRound = 1;

        this.skipActive = false;

    }

    /**
     *
     * @return Return whether there is an active skip ability.
     */
    public boolean isSkipActive() {
        return skipActive;
    }

    /**
     *
     * @return Return the player who played the skip card.
     */
    public Player playerWhoSkipped() {
        return playerWhoSkipped;
    }

    /**
     * Resets the skip value back to false.
     */
    public void skipOver() {
        this.skipActive = false;
        this.playerWhoSkipped = null;
    }

    /**
     *
     * @return Returns the games player AI.
     */
    public Player AI() {
        return this.gameAI;
    }

    /**
     *
     * @return The starting rounds.
     */
    public int currentRound() {
        return this.currentRound;
    }

    /**
     * Not used anymore (adds 1 to rounds)
     */
    public void incRound() {
        this.currentRound++;
    }

    /**
     *
     * @return Returns the max rounds.
     */
    public int maxRounds() {
        return this.MAX_ROUNDS;
    }

    /**
     *
     * @return Returns the max amount of players.
     */
    public int maxPlayers() {
        return this.MAX_PLAYERS;
    }

    /**
     *
     * @return Returns the winner.
     */
    public Player getWinner() {
        Player toReturn = null;
        int i = 0;

        for (Player player : playerList) {
            if (player.points() > i) {
                i = player.points();
                toReturn = player;
            }
        }

        return toReturn;
    }

    /**
     *
     * @return Returns the data of the game.
     */
    public String toString() {
        return "Winner: " + getWinner().name()
                + "\nRounds: " + currentRound();
    }
}
