import java.util.List;

public abstract class Player
{
    IIOHandler h;
    Game base;
    String name;
    Hand hand;
    boolean hasPlayedCard;

    public Player(String name, Game base)
    {
        h = new IOHandler();
        hand = new Hand();
        this.base = base;
        this.name = name;
    }

    public abstract void play(List<Card> cardsToPlayOn);

    public void drawCardFromDeck()
    {
        Card drawCard = base.getCardFromDeck();
        if (drawCard != null)
            hand.addCard(drawCard);
        else
        {
            h.display("There are no more cards in the deck!");
        }
    }

    public Card removeCardFromHand(int cardIndex)
    {
        Card toRemove = hand.getCard(cardIndex);
        hand.removeCard(toRemove);
        return toRemove;
    }

    public abstract void cardDecision(List<Card> cardPile);
    public abstract String playerChoice();

    protected boolean checkSuits(Card playerCard,  Card topCard)
    {
        return (playerCard.getSuit() == topCard.getSuit());
    }

    protected boolean checkNumber(Card playerCard, Card topCard)
    {
        return (playerCard.getValue() == topCard.getValue());
    }

    public boolean isHandEmpty()
    {
        return (hand.cardNum() == 0);
    }

    public int cardsInHand()
    {
        return hand.cardNum();
    }

    public int numOfACardInHand(int cardValue)
    {
        int numOfCards = 0;

        for (Card card:
             hand.getHand())
        {
            if (card.getValue() == cardValue)
                numOfCards++;
        }

        return numOfCards;
    }

    public int indexOfCardValue(int cardValue)
    {
        for (int i = 0; i < hand.cardNum(); i++)
        {
            if (hand.getCard(i).getValue() == cardValue)
                return i;
        }

        return -1;
    }

    public Card checkIfCardInvalid(String prompt)
    {
        boolean isInvalid = true;
        Card card;
        do
        {
            int otherChoice = h.getIntInput(prompt);
            card = hand.getCard(otherChoice);

            if (card == null)
                h.display("The index you tried to choose is invalid!");
            else
                isInvalid = false;

        }while (isInvalid);

        return card;
    }

    //checks if the number imputed is valid in this circumstance
    public int inputIntValidation(int minNum, int maxNum, String prompt)
    {
        int chosenNum =  h.getIntInput(prompt);

        while (chosenNum < minNum || chosenNum > maxNum)
        {
            h.display("Please pick a valid number");
            chosenNum =  h.getIntInput(prompt);
        }

        return chosenNum;
    }

    public String getValueAsString(int value)
    {
        switch ( value )
        {
            case 1:   return "A";
            case 2:   return "2";
            case 3:   return "3";
            case 4:   return "4";
            case 5:   return "5";
            case 6:   return "6";
            case 7:   return "7";
            case 8:   return "8";
            case 9:   return "9";
            case 10:  return "10";
            case 11:  return "J";
            case 12:  return "Q";
            default:  return "K";
        }
    }

    public String toString()
    {
        return "The Hand of " + name + " is: " + hand;
    }
}


