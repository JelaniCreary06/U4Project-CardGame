import java.util.*;

public class Player {
    protected Game activeGame;
    protected int player, playerPoints = 0;

    protected ArrayList<Card> cardArrayList = new ArrayList<Card>();
    protected ArrayList<AceCard> aceCardArrayList = new ArrayList<AceCard>();

    public Player(int player, Game activeGame) {
        this.player = player;
        this.activeGame = activeGame;
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
}
