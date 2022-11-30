import java.util.*;

public class Player {
    final String COLOR_GREEN = "\u001B[32m", COLOR_RED = "\u001B[31m", COLOR_CYAN = "\u001B[36m";

    ArrayList<Card> cardList = new ArrayList<>();
    private String name;
    private int points;
    public Player(String name) {
        this.name = name;
    }

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
                sortedCardList.add(sortedCardList.size(),card);
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

    public String name() {
        return this.name;
    }

    public int points() {
        return this.points;
    }

    public void removePoints(int points) {
        this.points -= points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public ArrayList cardList() {
        return this.cardList;
    }
    public void addCard(Card card) {
        cardList.add(card);
    }

    public void removeCard(Card card) {
        cardList.remove(card);
    }
}
