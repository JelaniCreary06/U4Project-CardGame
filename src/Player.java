import java.util.*;

public class Player {
    protected Game activeGame;
    protected int player, playerPoints = 0;
    protected String name;

    protected ArrayList<Card> cardArrayList = new ArrayList<Card>();
    protected ArrayList<AceCard> aceCardArrayList = new ArrayList<AceCard>();

    public Player(int player, Game activeGame) {
        this.player = player;
        this.activeGame = activeGame;
        this.name = "Player " + this.player;
    }

    public Player(int player, Game activeGame, String name) {
        this.player = player;
        this.activeGame = activeGame;
        this.name = name;
    }

    public Hashtable<String, ArrayList> getPlayerCards() {
        return new Hashtable<>(
                Map.of(
                        "Cards", cardArrayList,
                        "AceCards", aceCardArrayList
                )
        );
    }

    public int getAceCardCount() {
        return aceCardArrayList.size();
    }

    public int getCardCount() {
        return cardArrayList.size();
    }

    public void addPoints(int points) {
        this.playerPoints += points;
    }

    public void removePoints(int points) {
        this.playerPoints -= points;
    }

    public int getPoints() {return this.playerPoints;}

    public void addCard(Card card) {
        cardArrayList.add(card);
    }

    public void removeCard(Card card) {
        cardArrayList.remove(card);
    }

    public void addAceCard(AceCard aceCard) {
        aceCardArrayList.add(aceCard);
    }

    public void removeAceCard(AceCard aceCard) {
        aceCardArrayList.remove(aceCard);
    }

    public void setName(String name) {this.name = name;}
}
