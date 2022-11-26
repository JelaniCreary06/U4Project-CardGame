public class Card {
    protected int cardPoints;

    public Card() {
        this.cardPoints = 1;
    }
    public Card(int cardPoints) {
        this.cardPoints = cardPoints;
    }

    public int cardNumber() {
        return this.cardPoints;
    }
}
