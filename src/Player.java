import java.util.ArrayList;
import java.util.List;

public class Player {
    private int playerId;
    private String name;
    private List<Card> hand;

    public Player(int playerId, String name) {
        this.playerId = playerId;
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void receiveInitialCards(List<Card> cards) {
        hand.addAll(cards);
    }

    public void drawCard(Card card) {
        hand.add(card);
    }

    public void playCard(Card card) {
        hand.remove(card);
    }

    public boolean hasValidCard(CardColor currentColor, int currentValue) {
        for (Card card : hand) {
            if (card.isValidPlay(currentColor, currentValue)) {
                return true;
            }
        }
        return false;
    }
}
