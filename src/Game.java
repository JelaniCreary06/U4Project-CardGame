import java.util.*;

public class Game {
    private final int MAX_PLAYER_CARDS = 5, MAX_PLAYERS = 2, MAX_ROUNDS = 4;
    private Player gameAI;
    public Player skipper;
    private ArrayList<Player> playerArrayList = new ArrayList<Player>();
    private ArrayList<String> cardTypeArray = new ArrayList<String>(
            Arrays.asList(
                    "Skip", "Steal"
            )
    );

    private int currentPlayers, currentRound = 1;

    public volatile boolean isGameOver = false, isSkipActive = false;


    private String COLOR_GREEN = "\u001B[32m", COLOR_RED = "\u001B[31m",
    COLOR_WHITE = "\u001B[37m", COLOR_CYAN = "\u001B[36m", COLOR_RESET = "\033[0m";


    public void newGame(int currentPlayers, boolean isAIactive) {
        if (currentPlayers > this.MAX_PLAYERS)
            throw new ArithmeticException("\n\n Too many players, the max is " + this.MAX_PLAYERS);
        else if (currentPlayers <= 0)
            throw new ArithmeticException("\n\n Too few players, you need at least 1.");

        int plrInc = 1;

        if (currentPlayers == 1) {
            plrInc = 0;
        } else if (isAIactive && currentPlayers > 1) {
            plrInc = 0;
        }

        for (int i = plrInc; i <= currentPlayers; i++) {
            Player newPlayer = new Player(i, this);
            playerArrayList.add(newPlayer);

            if (i == 0) {
                this.gameAI = newPlayer;
            }
        }

        playerArrayList.add(playerArrayList.size(), playerArrayList.get(0));
        playerArrayList.remove(0);

        this.currentPlayers = currentPlayers;

    }

    private void play(AceCard card, Player player) {
        switch (card.getCardType()) {
            case "Steal":
                int toSteal = 2;
                if (playerArrayList.size() > 4) toSteal = 5;

                for (Player playerObject : playerArrayList) {
                    playerObject.removePoints(toSteal);
                    player.addPoints(toSteal);
                }
                break;

            case "Skip":
                this.isSkipActive = true;
                break;

        }
    }
    private void play(Card card, Player player) {
        player.addPoints(card.cardNumber());
    }
    public boolean playCard(String card, Player player) {
        boolean isValid = false;

        ArrayList<AceCard> cardList = player.getPlayerCards().get("AceCards");

        if (cardList.size() > 0) {
            AceCard toPlay = cardList.get( (int) (Math.random() * cardList.size()));
            play(toPlay, player);
            player.removeAceCard(toPlay);
            isValid = true;
        }

        return isValid;
    }

    public boolean playCard(int card, Player player) {
        boolean isValid = false;

        ArrayList<Card> playerCards = player.getPlayerCards().get("Cards");

        for (int i = 0; (i < playerCards.size() || !isValid); i++) {
            Card c = playerCards.get(i);

            if (c.cardNumber() == card) {
                play(c, player);
                player.removeCard(c);
                isValid = true;
            }
        }

        return isValid;
    }

    public String playCardAI(Player player) {
        ArrayList aiCards = player.getPlayerCards().get("Cards");
        ArrayList aiAceCards = player.getPlayerCards().get("AceCards");

        String toReturn = "";

        int toPlay = (int) (Math.random() * 2) + 1;

        if ( (toPlay == 1 && aiAceCards.size() > 0) || (aiCards.size() <= 0 && aiAceCards.size() > 0)) {

            AceCard cardToPlay = (AceCard) (aiAceCards.get( (int) (Math.random() * aiAceCards.size()) ));
            play(cardToPlay, player);
            player.removeAceCard(cardToPlay);
            toReturn = "A";

        } else if ( (toPlay == 2 && aiCards.size() > 0) || (aiAceCards.size() <= 0 && aiCards.size() > 0)) {

            Card cardToPlay = (Card) (aiCards.get( (int) (Math.random() * aiCards.size())));
            play(cardToPlay, player);
            player.removeCard(cardToPlay);
            toReturn = "" + cardToPlay.cardNumber();

        } else toReturn += "No available cards to play";

        return toReturn;
    }

    public int getMaxRounds() {
        return this.MAX_ROUNDS;
    }

    public int getCurrentRound() {
        return this.currentRound;
    }

    public void addRound() {
        this.currentRound++;
    }

    public int getMaxPlayers() {return this.MAX_PLAYERS;}

    public ArrayList<Player> getPlayerList() {
        return this.playerArrayList;
    }

    public String viewPlayerCards(Player player) {
        String toReturn = "\n";

        for (int i = 0; i < player.getCardCount() + player.getAceCardCount(); i++) {
            toReturn += COLOR_GREEN + "----- ";
        }

        toReturn += "\n";

        if (player.getAceCardCount() > 0) {
            for (int i = 0; i < player.getAceCardCount(); i++) {
                toReturn += COLOR_GREEN + "| " + COLOR_RED + "A" + COLOR_GREEN + " |";
            }
        }

        if (player.getCardCount() > 0) {
            for (int i = 0; i < player.getCardCount(); i++) {
                Card card = (Card) (player.getPlayerCards().get("Cards").get(i));
                toReturn += COLOR_GREEN + " | " + COLOR_WHITE + card.cardNumber() + COLOR_GREEN + " |";
            }
        }

        toReturn += "\n";

        for (int i = 0; i < player.getCardCount() + player.getAceCardCount(); i++) {
            toReturn += COLOR_GREEN + "----- ";
        }

        return toReturn;
    }

    public void assignCards() {
        for (Player player : playerArrayList) {
            int typeIndex = (int) (Math.random() * cardTypeArray.size());

            AceCard aceCard = new AceCard(5, cardTypeArray.get(typeIndex));

            player.addAceCard(aceCard);

            for (int i = 1; i < this.MAX_PLAYER_CARDS; i++) {
                Card newCard = new Card((int) (Math.random() * 10));
                player.addCard(newCard);
            }
        }

        this.isGameOver = false;
    }

    public void endGame() {
        this.skipper = null;
        this.isSkipActive = false;
        this.playerArrayList.clear();
        this.gameAI = null;
        this.isGameOver = true;
        this.currentRound = 1;
    }

    public String toString() {
        String toReturn = "";
        int highestScore = 0;

        ArrayList<Player> winnerList = new ArrayList<>(
                Arrays.asList()
        );

        for (Player player : playerArrayList) {
          if (player.getPoints() > highestScore)
              highestScore = player.getPoints();
        }

        for (Player player : playerArrayList) {
            if (player.getPoints() == highestScore) winnerList.add(player);
        }

        toReturn += COLOR_CYAN +"Winners: ";

        for (Player player : winnerList) {
            if (player == winnerList.get(0))
                toReturn += player.name;

            else toReturn += ", " + player.name;
        }

        toReturn += COLOR_RESET + "\nRounds played: " + this.currentRound +
                "Player Count: " + this.playerArrayList.size();

        return toReturn;
    }
}
