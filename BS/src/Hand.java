import java.util.ArrayList;
import java.util.List;

/**
 * An object of type Hand represents a hand of cards.  The
 * cards belong to the class Card.  A hand is empty when it
 * is created, and any number of cards can be added to it.
 */
public class Hand {

    private ArrayList<Card> hand;   // The cards in the hand.

    /**
     * Create a hand that is initially empty.
     */
    public Hand()
    {
        hand = new ArrayList<Card>();
    }

    /**
     * Remove all cards from the hand, leaving it empty.
     */
    public void clear()
    {
        hand.clear();
    }

    public int cardNum()
    {
        return hand.size();
    }

    /**
     * Add a card to the hand.  It is added at the end of the current hand.
     * @param c the non-null card to be added.
     * @throws NullPointerException if the parameter c is null.
     */
    public void addCard(Card c)
    {
        if (c == null)
            throw new NullPointerException("Can't add a null card to a hand.");
        hand.add(c);
    }

    /**
     * Remove a card from the hand, if present.
     * @param c the card to be removed.  If c is null or if the card is not in
     * the hand, then nothing is done.
     */
    public void removeCard(Card c)
    {
        hand.remove(c);
    }

    public void removeCard(int position)
    {
        if (position < 0 || position >= hand.size())
            throw new IllegalArgumentException("Position does not exist in hand: "
                    + position);
        hand.remove(position);
    }

    /**
     * Returns the number of cards in the hand.
     */
    public int getCardCount()
    {
        return hand.size();
    }

    public Card getCard(int position)
    {
        if (position < 0 || position >= hand.size())
            return null;
        return hand.get(position);
    }

    public void cardSort()
    {
        int handLength = getCardCount();

        for (int i = 0; i < handLength-1; i++)
        {
            for (int j = 0; j < handLength - i - 1; j++)
            {
                if (hand.get(j).getValue() > hand.get(j + 1).getValue())
                {
                    // swap temp and arr[i]
                    Card temp = hand.get(j);
                    hand.set(j, hand.get(j+1));
                    hand.set(j+1, temp);
                }
            }
        }
    }

    public List<Card> getHand()
    {
        return hand;
    }

    public String toString()
    {
        String toReturn = "";

        for (int card = 0; card < hand.size(); card++)
        {
            toReturn += "" + (card + 1) + ". " + hand.get(card) + "\n";
        }

        return toReturn;
    }
}//end of class Hand
