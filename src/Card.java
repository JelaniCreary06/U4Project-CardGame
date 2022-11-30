public class Card {
    private String cardType, cardEffect = "";
    private int points, position;

    public Card(int points, String cardType) {
        this.points = points; this.cardType = cardType;
    }

    public Card(int points, String cardType, String cardEffect) {
        this.points = points; this.cardType = cardType; this.cardEffect = cardEffect;
    }

    public boolean hasEffect() {
        return !(this.cardEffect.equals(""));
    }

    public String effect() {
        return this.cardEffect;
    }

    public String type() {
        return this.cardType;
    }

    public int points(){
        return this.points;
    }

    public int position() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
