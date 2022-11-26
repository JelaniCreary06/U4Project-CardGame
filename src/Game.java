import java.util.*;

public class Game {
    private final int MAX_PLAYER_CARDS = 5, MAX_PLAYERS = 1;

    private PlayerAI gameAI;
    private ArrayList<Player> playerArrayList = new ArrayList<Player>();
    private ArrayList<String> cardTypeArray = new ArrayList<String>(
            Arrays.asList(
                    "Skip", "Steal"
            )
    );

    private int currentPlayers, currentRound;

    public boolean isGameOver = false;

    private String COLOR_GREEN = "\u001B[32m", COLOR_RED = "\u001B[31m",
    COLOR_WHITE = "\u001B[37m";

    public void newGame(int currentPlayers) {
        if (currentPlayers > this.MAX_PLAYERS)
            throw new ArithmeticException("\n\n\n Too many players, the max is " + this.MAX_PLAYERS);

        for (int i = 1; i < currentPlayers + 1; i++) {
            Player newPlayer = new Player(i, this);
            playerArrayList.add(newPlayer);
        }

        this.currentPlayers = currentPlayers;

    }

    public String viewPlayerCards(Player player) {
        String toReturn = "";

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


    public void play() {
        for (Player player : playerArrayList) {
            System.out.println(viewPlayerCards(player));
        }
    }

    public void assignCards() {
        for (Player player : playerArrayList) {
            int typeIndex = (int) (Math.random() * cardTypeArray.size());

            AceCard aceCard = new AceCard(3, cardTypeArray.get(typeIndex));

            player.addAceCard(aceCard);

            for (int i = 1; i < this.MAX_PLAYER_CARDS; i++) {
                Card newCard = new Card((int) (Math.random() * 10));
                player.addCard(newCard);
            }
        }

        this.isGameOver = false;
    }

    public void endGame() {

        for (Player player : playerArrayList) {
            playerArrayList.remove(player);
            /*
            Hashtable<String, ArrayList> allPlayerCards = player.getPlayerCards();
            ArrayList<Card> playerCards = allPlayerCards.get("Cards");
            ArrayList<AceCard> playerAceCards = allPlayerCards.get("AceCards");

            for (Card card : playerCards) {
                playerCards.remove(card);
            }

            for (AceCard aceCard : playerAceCards) {
                playerAceCards.remove(aceCard);
            }

             */
        }

        this.isGameOver = true;
    }
}
