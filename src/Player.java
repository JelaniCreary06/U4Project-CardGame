import java.util.*;

public class Player {
    final String COLOR_GREEN = "\u001B[32m", COLOR_RED = "\u001B[31m", COLOR_CYAN = "\u001B[36m";

    ArrayList<Card> cardList = new ArrayList<>();
    private String name;
    private int points;

    /**
     * Initiate a new player Object.
     * @param name The name of the player
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     *
     * @return Returns a String of all the players cards.
     */
    public String viewCards() {
        String toReturn = "\n";

        for (Card card : cardList) {
            toReturn += COLOR_GREEN + "------- ";
        }

        toReturn += "\n";

        for (Card card : cardList) {
            toReturn +=
                   COLOR_GREEN +  "| "
                           + COLOR_RED + card.type() + "." + COLOR_CYAN + card.position() +
                           COLOR_GREEN + " | ";
        }

        toReturn += "\n";

        for (Card card : cardList) {
            toReturn += COLOR_GREEN + "------- ";
        }

        return toReturn;
    }

    /**
     * Sorts and positions all the cards.
     */
    public void sortCards() {
        ArrayList<Card> sortedCardList = new ArrayList<>();

        int cardIncrement = 0;

        for (Card card : cardList) {
            if (card.type().equals("A")) {
                sortedCardList.add(0, card);
                card.setPosition(cardIncrement);
                cardIncrement++;
            }
        }

        for (Card card : cardList) {
            if (card.type().equals("+")) {
                sortedCardList.add(card);
                card.setPosition(cardIncrement);
                cardIncrement++;
            }
        }

        for (Card card : cardList) {
            if (card.type().equals("-")) {
                sortedCardList.add(sortedCardList.size(), card);
                card.setPosition(cardIncrement);
                cardIncrement++;
            }
        }


        cardList = sortedCardList;
    }

    /**
     *
     * @return Returns the name of the player.
     */
    public String name() {
        return this.name;
    }

    /**
     *
     * @return Returns the players points
     */
    public int points() {
        return this.points;
    }

    /**
     * Remove points from the player.
     * @param points The amount ot remove
     */
    public void removePoints(int points) {
        this.points -= points;
    }

    /**
     *  Add points to the player.
     * @param points The amount to add
     */
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     *
     * @return Retrun the players card array.
     */
    public ArrayList cardList() {
        return this.cardList;
    }

    /**
     * Adds a card to the players card array.
     * @param card The card to add.
     */
    public void addCard(Card card) {
        cardList.add(card);
    }

    /**
     * Removes a card from the players card array.
     * @param card The card to remove.
     */
    public void removeCard(Card card) {
        cardList.remove(card);
    }
}
