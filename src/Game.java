import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private List<Player> players;
    private Deck deck;
    private static List<Card> discardedCards;
    private CardColor currentColor;
    private int currentValue;
    private boolean clockwise;
    private int currentPlayerIndex;

    public Game(List<String> playerNames) {
        players = new ArrayList<>();
        for (int i = 0; i < playerNames.size(); i++) {
            players.add(new Player(i + 1, playerNames.get(i)));
        }
        deck = new Deck();
        discardedCards = new ArrayList<>();
        currentColor = CardColor.WILD;
        currentValue = -1;
        clockwise = true;
        currentPlayerIndex = 0;
    }

    public void startGame() {
        //Distribute initial cards
        for (Player player : players) {
            List<Card> initialCards = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                initialCards.add(deck.drawCard());
            }
            player.receiveInitialCards(initialCards);
        }
        //Determine first player
        currentPlayerIndex = (int) (Math.random() * players.size());
        Card card = deck.drawCard();
        discardedCards.add(card);
        currentColor = card.getColor();
        currentValue = card.getValue();
    }

    public void playCard(Player player, Card card) {
        if (card.isValidPlay(currentColor, currentValue)) {
            player.playCard(card);
            discardedCards.add(card);
            currentColor = card.getColor();
            currentValue = card.getValue();
            if (card.isSpecialActionCard()) {
                card.performSpecialAction(this);
            } else if (card.getValue() == 13 ) {
                discardedCards.add(card);
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter the next color Sequence");
                System.out.println("blue, yellow, red, green only valid");
                scanner.close();
                switch (scanner.nextLine().toLowerCase()){
                    case "blue":
                        currentColor = CardColor.BLUE;
                        break;
                    case "red":
                        currentColor = CardColor.RED;
                        break;
                    case "yellow":
                        currentColor = CardColor.YELLOW;
                        break;
                    case "green":
                        currentColor = CardColor.GREEN;
                        break;
                    default:
                        System.out.println("Wrong Color input now color will be red");
                        currentColor = CardColor.RED;
                }
                currentValue = 0;
            } else if (card.getValue() == 14) {
                card.performSpecialAction(this);
            } else {
                // Regular numbered card
                if (clockwise) {
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                } else {
                    currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
                }
            }
        }
    }

    public static List<Card> getDiscardedCards() {
        return discardedCards;
    }

    public static void clearDiscardedCards() {
        discardedCards.clear();
    }

    public void skipNextPlayer() {
        if (clockwise) {
            currentPlayerIndex = (currentPlayerIndex + 2) % players.size();
        } else {
            currentPlayerIndex = (currentPlayerIndex - 2 + players.size()) % players.size();
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void reverseDirection() {
        clockwise = !clockwise;
    }

    public void drawTwoCardsAndSkipNextPlayer() {
        Player nextPlayer = getNextPlayer();
        nextPlayer.drawCard(deck.drawCard());
        nextPlayer.drawCard(deck.drawCard());
        skipNextPlayer();
    }

    public void drawFourCardsAndSkipNextPlayer() {
        Player nextPlayer = getNextPlayer();
        nextPlayer.drawCard(deck.drawCard());
        nextPlayer.drawCard(deck.drawCard());
        nextPlayer.drawCard(deck.drawCard());
        nextPlayer.drawCard(deck.drawCard());
        skipNextPlayer();
    }

    Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    private Player getNextPlayer() {
        if (clockwise) {
            return players.get((currentPlayerIndex + 1) % players.size());
        } else {
            return players.get((currentPlayerIndex - 1 + players.size()) % players.size());
        }
    }


    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
