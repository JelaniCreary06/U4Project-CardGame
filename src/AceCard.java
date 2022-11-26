public class AceCard extends Card {
    private String cardType;

    public AceCard (int cardPoints, String cardType) {
        super (cardPoints);

        this.cardType = cardType;
    }

    public String getCardType() {
        return this.cardType;
    }

    public void skipTurn() {

    }

    public void stealPoints(Player toStealFrom) {

    }
}
