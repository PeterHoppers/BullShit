import java.util.ArrayList;
import java.util.List;

public class BaseAI extends BSPlayer
{
    byte bsThreshold; //if the amount of points is greater than this, the AI will call BS (0-100)
    byte lieThreshold; //if the amount of points is greater than this, the AI will lie (0 - 100)

    public BaseAI(String name, BSGame base, byte bsThreshold, byte lieThreshold)
    {
        super(name, base);
        this.base = base;
        this.bsThreshold = bsThreshold;
        this.lieThreshold = lieThreshold;
    }

    //method called by the game loop to prompt card playing
    @Override
    public void play(List<Card> cardsToPlayOn)
    {
        hand.cardSort();
        cardDecision(cardsToPlayOn);
    }

    //decides what cards to play
    @Override
    public void cardDecision(List<Card> cardPile)
    {
        List<Card> cardsToPlay = new ArrayList<Card>();

        List<Integer> selections = new ArrayList<Integer>();

        //=================Calculate Which Cards To Play================
        int lieValue = 0; //if this value is greater than
        //Step 1. Decide if there are enough cards to play without lying
        int valueToPlay = base.valueToPlay;

        int matchingCardsInHand = numOfACardInHand(valueToPlay);

        switch (matchingCardsInHand)
        {
            case 4:
                lieValue -= 100;
                break;
            case 3:
                lieValue -= 50;
                break;
            case 2:
                lieValue += 0;
                break;
            case 1:
                lieValue += 20;
                break;
            case 0:
                lieValue += 100;
                break;
        }

        //Step 3. Decide if they should lie or not
        if (lieValue > lieThreshold)
            selections = DecideWhatCardsToLieWith(valueToPlay);
        else
            selections = GrabIndexesOfCardsOfValue(valueToPlay);


        //Step 4. Grab the indexes of the cards they are playing


        //Step 5. Remove the cards from hand
        //in order to remove the cards properly from the hand, we need to sort them from largest to smallest
        int temp;
        for (int i = 0; i < selections.size(); i++)
        {
            for (int j = i + 1; j < selections.size(); j++)
            {
                if (selections.get(i) < selections.get(j))
                {
                    temp = selections.get(i);
                    selections.set(i, selections.get(j));
                    selections.set(j,temp);
                }
            }
        }

        for (Integer num:
                selections)
        {
            Card selectedCard = hand.getCard(num);
            cardsToPlay.add(selectedCard);
            hand.removeCard(selectedCard);
        }

        //Step 6. Play the cards.
        base.addCheckCards(cardsToPlay);
    }

    //calculate if the AI Calls BS
    public boolean callBS(String playerName, int numOfCardsPlayed, int numOfCardDiscarded)
    {
        int levelBS = 0; //the chance that the AI will call BS

        //Step 1. Look at how many cards have been played
        if (numOfCardsPlayed > 4)
            levelBS = 999;

        switch(numOfCardsPlayed)
        {
            case 4:
                levelBS += 90;
                break;
            case 3:
                levelBS += 60;
                break;
            case 2:
                levelBS += 30;
                break;
            case 1:
                levelBS += 0;
                break;
            default:
                break;
        }

        //Step 2. Look at how many cards of that values are in its hand
        int valueToPlay = base.valueToPlay;

        int matchingCardsInHand = numOfACardInHand(valueToPlay);

        switch(matchingCardsInHand)
        {
            case 4:
                levelBS += 110;
                break;
            case 3:
                levelBS += 90;
                break;
            case 2:
                levelBS += 60;
                break;
            case 1:
                levelBS += 30;
                break;
            case 0:
                levelBS += 0;
                break;
            default:
                break;
        }

        //Step 3. Recall how many cards they have already played of that number
        ///also add checking which cards this AI has already played. Issue right now is clearing it when BS is called by someone

        //Step 4. Check the number of cards in the discard pile (less cards, higher chance of calling it)
        if (numOfCardDiscarded < 3)
            levelBS += 40;
        else if (numOfCardDiscarded < 6)
            levelBS += 20;
        else if (numOfCardDiscarded < 9)
            levelBS += 0;
        else if (numOfCardDiscarded < 12)
            levelBS -= 20;
        else
            levelBS -= 40;

        //Step 5. Check the number of cards in its hand (less cards, less chance of calling it)
        int cardsInHand = hand.getCardCount();

        if (cardsInHand < 3)
            levelBS -= 40;
        else if (cardsInHand < 6)
            levelBS -= 20;
        else if (cardsInHand < 9)
            levelBS -= 0;
        else if (cardsInHand < 12)
            levelBS += 20;
        else
            levelBS += 40;

        //Step 6. Check if the player who played is going to win if no one calls BS

        //Step 7. Determine if the threshold is greater than the amount to lie
        if (levelBS > bsThreshold)
            return true;
        else
            return false;
    }

    List<Integer> DecideWhatCardsToLieWith(int value)
    {
        List<Integer> cardIndexes = new ArrayList<Integer>();

        cardIndexes = GrabIndexesOfCardsOfValue(value);
    }

    List<Integer> GrabIndexesOfCardsOfValue(int value)
    {
        return null;
    }
}
