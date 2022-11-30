import java.util.*;

public class Game {
    private final int MAX_CARDS = 4, MAX_ABILITY_CARDS = 2, MAX_PLAYERS = 2, MAX_ROUNDS = 4;
    private Player gameAI, playerWhoSkipped;

    private ArrayList<Player> playerList = new ArrayList<Player>();

    String types[] = new String[2], effects[] = new String[3];

    private String COLOR_GREEN = "\u001B[32m", COLOR_RED = "\u001B[31m",
        COLOR_WHITE = "\u001B[37m", COLOR_CYAN = "\u001B[36m", COLOR_YELLOW = "\u001b[33m",
            COLOR_RESET = "\033[0m";

    private int currentRound = 1;

    public Game() {
        types[0] = "#"; types[1] = "@";

        effects[0] = "Steal"; effects[1] = "Skip";
        effects[2] = "PointBoost";

    }

    private void play(Card card, Player player) {

    }

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

    public String playCard(Player player) {
        String cardType = "";
        if (player == this.gameAI) {
            ArrayList<Card> tempCardList = player.cardList();

            int num = (int) (Math.random() * tempCardList.size());

            for (int i = 0; i < tempCardList.size(); i++) {
                Card card = tempCardList.get(i);

                if (card.position() == num) {
                    play(card, player);
                    player.removeCard(card);
                    cardType = card.type();
                }
            }
        }
        return cardType;
    }

    public boolean initalizePlayers(int playerCount, boolean aiActive, ArrayList<String> playerNames) {
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
                Card newCard = new Card(((int)(Math.random() * 4) + 2), types[(int) (Math.random() * types.length)]);
                newPlayer.addCard(newCard);
            }

            for (int ai = 0; ai < MAX_ABILITY_CARDS; ai++) {
                Card newCard = new Card(9, "A",effects[(int) (Math.random() * effects.length)]);
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


    public ArrayList<Player> getPlayers() {
        if (playerList.isEmpty())
            throw new ArrayIndexOutOfBoundsException("\n\n There's no players!");

        return this.playerList;
    }

    public Player AI() {
        return this.gameAI;
    }

    public int currentRound() {
        return this.currentRound;
    }

    public void incRound() {
        this.currentRound++;
    }

    public int maxRounds() {
        return this.MAX_ROUNDS;
    }

    public int maxPlayers() {
        return this.MAX_PLAYERS;
    }
}
