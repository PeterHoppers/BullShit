import java.util.ArrayList;
import java.util.List;

/**
 *  An object of type Deck represents a deck of playing cards.  The deck
 *  is a regular poker deck that contains 52 regular cards
 */
public class Deck {

    /**
     * An array of 52 cards. The 52 cards of a regular poker deck.
     */
    private Card[] deck;

    /**
     * Keeps track of the number of cards that have been dealt from
     * the deck so far.
     */
    private int cardsUsed;

    /**
     * Constructs a regular 52-card poker deck.  Initially, the cards
     * are in a sorted order.  The shuffle() method can be called to
     * randomize the order.  (Note that "new Deck()" is equivalent
     * to "new Deck(false)".)
     */

    public Deck()
    {
        deck = new Card[52];
        int cardCt = 0; // How many cards have been created so far.
        for ( int suit = 0; suit <= 3; suit++ ) {
            for ( int value = 1; value <= 13; value++ ) {
                deck[cardCt] = new Card(value,suit);
                cardCt++;
            }
        }

        cardsUsed = 0;
    }

    //=============added this===================
    public void removeQueen()
    {
        deck = new Card[51];
        int cardCt = 0; // How many cards have been created so far.
        for ( int suit = 0; suit <= 3; suit++ )
        {
            for ( int value = 1; value <= 13; value++ )
            {
                if (value == 12 && suit == 0) // queen of hearts leaves
                    continue;
                else
                {
                    deck[cardCt] = new Card(value,suit);
                    cardCt++;
                }
            }
        }

        cardsUsed = 0;
    }
    //=======================================

    public boolean isEmpty()
    {
        return (deck.length == 0);
    }

    /**
     * Put all the used cards back into the deck (if any), and
     * shuffle the deck into a random order.
     */
    public void shuffle()
    {
        for ( int i = deck.length-1; i > 0; i-- )
        {
            int rand = (int)(Math.random()*(i+1));
            Card temp = deck[i];
            deck[i] = deck[rand];
            deck[rand] = temp;
        }
        cardsUsed = 0;
    }

    /**
     * As cards are dealt from the deck, the number of cards left
     * decreases.  This function returns the number of cards that
     * are still left in the deck.  The return value would be
     * 52 when the deck is first created or after the deck has been
     * shuffled.  It decreases by 1 each time the dealCard() method
     * is called.
     */
    public int cardsLeft()
    {
        return deck.length - cardsUsed;
    }

    /**
     * Removes the next card from the deck and return it.  It is illegal
     * to call this method if there are no more cards in the deck.  You can
     * check the number of cards remaining by calling the cardsLeft() function.
     * @return the card which is removed from the deck.
     * @throws IllegalStateException if there are no cards left in the deck
     */
    public Card dealCard()
    {
        if (cardsUsed == deck.length)
            return null;
        cardsUsed++;
        return deck[cardsUsed - 1];
        // Programming note:  Cards are not literally removed from the array
        // that represents the deck.  We just keep track of how many cards
        // have been used.
    }

    public List<Card> getDeckInList()
    {
        List<Card> returnDeck = new ArrayList<Card>();

        for (int index = cardsUsed; index < deck.length; index++)
        {
            returnDeck.add(deck[index]);
        }

        return returnDeck;
    }

} // end class Deck
