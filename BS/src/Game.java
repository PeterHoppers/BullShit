
import java.util.*;

public abstract class Game
{
    IIOHandler h;
    Deck deck; // deck of cards to play with
    Player[] players;
    Player playingPlayer;

    List<Card> discardPile;

    int numPlayers;
    int numCards;

    public Card currCard;

    public Game(int numPlayersAllowed)
    {
        players = new Player[numPlayersAllowed];
        numPlayers = 0;

        h = new IOHandler();
        discardPile = new ArrayList<Card>();
    }

    public abstract void addPlayer(String name);

    public void setupDeck()
    {
        deck = new Deck();
    }

    public void shuffleDeck()
    {
        if (deck == null) return;
        deck.shuffle();
    }

    public void dealDeck(int cardsPerPlayer)
    {
        if (deck == null) setupDeck();

        Card topCard;

        for (int currCards = 0; currCards < cardsPerPlayer; currCards++)
        {
            for(Player player : players)
            {
                topCard = deck.dealCard();

                if (topCard != null)
                    player.hand.addCard(topCard);
            }
        }

        discardPile.add(deck.dealCard());

        playingPlayer = players[0];
    }

    public void gameLoop()
    {
        int currentPlayer = 0;

        while (isGameRunning())
        {
            playingPlayer = players[currentPlayer];
            h.display("\nIt is your turn " + playingPlayer.name + "!");
            playingPlayer.play(discardPile);

            currentPlayer++;

            if (currentPlayer >= players.length) //loop back to the first player
                currentPlayer = 0;
        }
    }


    public abstract boolean isGameRunning();

    public Card getCardFromDeck()
    {
        return deck.dealCard();
    }

    public Card getCardFromPlayer(int playerIndex, int cardIndex)
    {
        return players[playerIndex].removeCardFromHand(cardIndex);
    }

    public int getAmountCardsFromPlayer(int playerIndex)
    {
        return players[playerIndex].cardsInHand();
    }

    public boolean isPlayerIndexMine(int playerIndex, Player player)
    {
        return players[playerIndex].equals(player);
    }

    public String playersToString()
    {
        String toReturn = "";
        int playerNum = 1;

        for (Player player : players)
        {
            toReturn += "Player: " + playerNum + "has " + player.cardsInHand() + " in hand.\n";
        }

        return toReturn;
    }
}

