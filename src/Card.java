public class Card {
    private String cardType, cardEffect = "";
    private int points, position;

    /**
     * Initialize a new Card Object.
     * @param points The amount of points the card has
     * @param cardType Whether this is a decrease, increase or ability card.
     */
    public Card(int points, String cardType) {
        this.points = points; this.cardType = cardType;
    }

    /**
     /**
     * Initialize a new Card Object.
     * @param points The amount of points the card has
     * @param cardType Whether this is a decrease, increase or ability card.
     * @param cardEffect Used if this is an ability card.
     */
    public Card(int points, String cardType, String cardEffect) {
        this.points = points; this.cardType = cardType; this.cardEffect = cardEffect;
    }

    /**
     *
     * @return Determines if this is an ability card or not.
     */
    public boolean hasEffect() {
        return !(this.cardEffect.equals(""));
    }

    /**
     *
     * @return Returns this cards effect if it's an ability card.
     */
    public String effect() {
        return this.cardEffect;
    }

    /**
     *
     * @return Returns the cards type.
     */
    public String type() {
        return this.cardType;
    }

    /**
     *
     * @return Returns the cards points.
     */
    public int points(){
        return this.points;
    }

    /**
     *
     * @return Returns the cards position in the players card array.
     */
    public int position() {
        return this.position;
    }

    /**
     *
     * @param position Sets the position in the players cards array.
     */
    public void setPosition(int position) {
        this.position = position;
    }
}
