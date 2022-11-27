public class Card {
    protected int cardPoints;
    protected int cardEffect;

    public Card() {
        this.cardPoints = 1;
    }
    public Card(int cardPoints) {
        this.cardPoints = cardPoints;
    }

    public int cardNumber() {
        return this.cardPoints;
    }
    public int cardEffect() {return this.cardEffect;}
}
