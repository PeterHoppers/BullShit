import java.util.ArrayList;
import java.util.List;

public class BaseAI extends BSPlayer
{
    byte bsThreshold; //if the amount of points is greater than this, the AI will call BS (0-100)
    byte lieThreshold; //if the amount of points is greater than this, the AI will lie (0 - 100)

    public BaseAI(String name, BSGame base)
    {
        super(name, base);
        this.base = base;
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
        //Step 1. Figure out if there are any cards that they can play without lying
        int valueToPlay = base.valueToPlay;

        int matchingCardsInHand = numOfACardInHand(valueToPlay);
        //Step 2. Decide if there are enough cards to play without lying
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

        //Step 3. Check if there are any cards that are good to lie with (cards that will be unable to be played for a while)

        //Step 4. Decide if they should lie or not
        //if (lieValue > lieThreshold)
            //DecideWhatCardsToLieWith()
        //else
            //selections = GrabCardsOfAValue(valueToPlay)

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

        //Step 5. Check the number of cards in its hand (less cards, less chance of calling it)

        //Step 6. Check if the player who played is going to win if no one calls BS

        if (levelBS > bsThreshold)
            return true;
        else
            return false;
    }
}
