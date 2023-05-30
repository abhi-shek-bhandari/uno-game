public class Card {
    private CardColor color;
    private int value;

    public Card(CardColor color, int value) {
        this.color = color;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Card{" +
                "color=" + color +
                ", value=" + value +
                '}';
    }

    public CardColor getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public boolean isValidPlay(CardColor currentColor, int currentValue) {
        return color == currentColor || value == currentValue;
    }

    public boolean isSpecialActionCard() {
        return value == 10 || value == 11 || value == 12;
    }

    public void performSpecialAction(Game game) {
        if (value == 10) {
            game.skipNextPlayer();
        } else if (value == 11) {
            game.reverseDirection();
        } else if (value == 12) {
            game.drawTwoCardsAndSkipNextPlayer();
        } else if (value == 14) {
            game.drawFourCardsAndSkipNextPlayer();
        }
    }
}
