import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
        initializeDeck();
        shuffle();
    }

    private void initializeDeck() {
        for (CardColor color : CardColor.values()) {
            if (color != CardColor.WILD){
                for (int value = 0; value <= 9; value++) {
                    cards.add(new Card(color, value));
                    if (value != 0) {
                        cards.add(new Card(color, value));
                    }
                }
            }
        }
        // Add special action cards
        for (int i = 0; i < 2; i++) {
            for (CardColor color : CardColor.values()) {
                cards.add(new Card(color, 10)); // Skip card
                cards.add(new Card(color, 11)); // Reverse card
                cards.add(new Card(color, 12)); // Draw two card
            }
        }
        // Add wild cards
        for (int i = 0; i < 4; i++) {
            cards.add(new Card(CardColor.WILD, 13));
        }
        // Add wild draw four cards
        for (int i = 0; i < 4; i++) {
            cards.add(new Card(CardColor.WILD, 14));
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            replenishDeck();
        }
        return cards.remove(0);
    }

    private void replenishDeck() {
        cards.addAll(Game.getDiscardedCards());
        Game.clearDiscardedCards();
        shuffle();
    }
}
