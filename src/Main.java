import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of players:");
        int numPlayers = scanner.nextInt();
        scanner.nextLine();
        List<String> playerNames = getPlayerNames(scanner, numPlayers);

        Game game = new Game(playerNames);
        game.startGame();

        while (!isGameFinished(game)) {
            Player currentPlayer = game.getCurrentPlayer();
            System.out.println("Current player: " + currentPlayer.getName());
            List<Card> discardedCards = Game.getDiscardedCards();
            if (!discardedCards.isEmpty()) {
                System.out.println("Current card: " + discardedCards.get(discardedCards.size() - 1));
            } else {
                System.out.println("No card has been discarded yet.");
            }

            Card cardToPlay = promptPlayerToPlayCard(scanner, currentPlayer,game.getDeck());

            game.playCard(currentPlayer, cardToPlay);
            System.out.println("----------------------------------");
        }

        Player winner = getWinner(game);
        System.out.println("Game over! The winner is: " + winner.getName());
    }

    private static List<String> getPlayerNames(Scanner scanner, int numPlayers) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Enter the name for player " + (i+1) + ":");
            list.add(scanner.nextLine());
        }
        return list;
    }

    private static boolean isGameFinished(Game game) {
        for (Player player : game.getPlayers()) {
            if (player.getHand().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static Card promptPlayerToPlayCard(Scanner scanner, Player currentPlayer, Deck deck) {
        while (true) {
            System.out.println("Index of card begin from 0: ");
            System.out.println("Your current hand: ");
            int count = 0;
            for (Card card : currentPlayer.getHand()) {
                System.out.println("Enter " + count++ + " for card: " + card);
            }
            System.out.println("Enter the index of the card you want to play:");
            System.out.println("Enter 10 to draw card from deck:");
            int cardIndex = scanner.nextInt();
            if (cardIndex == 10){
                Card card = deck.drawCard();
                currentPlayer.getHand().add(card);
            } else {
                if (cardIndex >= currentPlayer.getHand().size()) {
                    return currentPlayer.getHand().get(currentPlayer.getHand().size() - 1);
                }
                return currentPlayer.getHand().get(cardIndex);
            }
        }
    }

    private static Player getWinner(Game game) {
        for (Player player : game.getPlayers()) {
            if (player.getHand().isEmpty()) {
                return player;
            }
        }
        return new Player(1000, "No Winner in this Game");
    }
}


