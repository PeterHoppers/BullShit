import java.util.ArrayList;
import java.util.List;

public class BaseAI extends BSPlayer
{
    int bsTolerance; //if the amount of points is greater than this, the AI will call BS (0-100)
    int honesty; //if the amount of points is greater than this, the AI will lie (0 - 100)

    public BaseAI(String name, BSGame base, int bsTolerance, int honesty)
    {
        super(name, base);
        this.base = base;
        this.bsTolerance = bsTolerance;
        this.honesty = honesty;
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
                lieValue -= 40;
                break;
            case 3:
                lieValue -= 0;
                break;
            case 2:
                lieValue += 20;
                break;
            case 1:
                lieValue += 40;
                break;
            case 0:
                lieValue += 70; //they should always lie if they cannot play a card
                break;
        }

        //Step 2. Check Handsize to see if they should lie
        lieValue += (getValueFromNumOfCards() / 2);

        //Step 3. Check Discard Pile size to see if they should lie
        lieValue += (getValueFromDiscard(base.getDiscardPileSize()) / 2);

        h.display("LieValue: " + lieValue + " vs. " + honesty);

        //Step 3. Decide if they should lie or not
        if (lieValue > honesty)
        {
            byte numCardsToLie = 0;

            while (lieValue > honesty)
            {
                numCardsToLie++;

                lieValue -= 60;
            }
            selections = decideWhatCardsToLieWith(valueToPlay, numCardsToLie);
        }
        else
        {
            if (matchingCardsInHand == 0) // if, for some reason, they don't want to lie but cannot avoid it
                selections = decideWhatCardsToLieWith(valueToPlay, (byte)1);
            else
                selections = grabIndexesOfCardsOfValue(valueToPlay);
        }

        h.display(selections + " this is the cards the AI is playing " + lieValue + " vs. " + honesty);


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
                levelBS += 105;
                break;
            case 3:
                levelBS += 50;
                break;
            case 2:
                levelBS += 15;
                break;
            case 1:
                levelBS += -10;
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
                levelBS += 65;
                break;
            case 2:
                levelBS += 45;
                break;
            case 1:
                levelBS += 25;
                break;
            case 0:
                levelBS += -10;
                break;
            default:
                break;
        }

        //Step 3. Recall how many cards they have already played of that number
        ///also add checking which cards this AI has already played. Issue right now is clearing it when BS is called by someone

        //Step 4. Check the number of cards in the discard pile (less cards, higher chance of calling it)
        levelBS += getValueFromDiscard(numOfCardDiscarded);

        //Step 5. Check the number of cards in its hand (less cards, less chance of calling it)
        levelBS += getValueFromNumOfCards();

        //Step 6. Check if the player who played is going to win if no one calls BS
        int oppHandSize = base.getAmountCardsFromPlayerName(playerName);

        if (oppHandSize < 4)
            levelBS += 20;
        else if (oppHandSize < 6)
            levelBS += 10;
        else if (oppHandSize < 10)
            levelBS += 0;
        else if (oppHandSize < 14)
            levelBS -= 10;
        else
            levelBS -= 20;

        h.display(oppHandSize + " is how many cards they have");
        h.display("The levelBS is " + levelBS);

        //Step 7. Determine if the threshold is greater than the amount to lie
        if (levelBS > bsTolerance)
            return true;
        else
            return false;
    }

    List<Integer> decideWhatCardsToLieWith(int cardValue, byte numCards)
    {
        List<Integer> cardIndexes = new ArrayList<Integer>();

        cardIndexes = grabIndexesOfCardsOfValue(cardValue);

        int lieCardIndex = -1;

        //grab the value farthest from the value we're currently playing
        for (int i = 0; i < 12; i++)
        {
            cardValue += base.numPlayers;

            if (cardValue > 13)
                cardValue = cardValue - 13;
        }

        boolean doesPlayerHaveValue = true;

        while (doesPlayerHaveValue)
        {
            lieCardIndex = indexOfCardValue(cardValue);

            h.display("We are now grabbing: " + lieCardIndex);

            if (lieCardIndex == -1) //if the card isn't valid
            {
                cardValue = subtractValue(cardValue, base.numPlayers, 1, 13); //move onto the value card
            }
            else
            {
                if (numCards > 1) //if we need to grab another card
                {
                    cardIndexes.add(lieCardIndex);
                    cardValue = subtractValue(cardValue, base.numPlayers, 1, 13);
                    numCards--;
                }
                else
                {
                    break;
                }
            }
        }

        cardIndexes.add(lieCardIndex);

        return cardIndexes;
    }

    int subtractValue(int value, int subtractValue, int lowValue, int incrementValue)
    {
        value -= subtractValue;

        if (value < lowValue)
            value += incrementValue;

        return value;
    }

    List<Integer> grabIndexesOfCardsOfValue(int cardValue)
    {
        List<Integer> cardIndexes = new ArrayList<Integer>();

        for (int i = 0; i < hand.getCardCount(); i++)
        {
            Card checkCard = hand.getCard(i);

            if (checkCard.getValue() == cardValue)
                cardIndexes.add(i);
        }

        return cardIndexes;
    }

    int getValueFromNumOfCards()
    {
        int cardsInHand = hand.getCardCount();

        if (cardsInHand < 3)
            return -20;
        else if (cardsInHand < 6)
            return -10;
        else if (cardsInHand < 9)
            return 0;
        else if (cardsInHand < 12)
            return 10;
        else
            return 20;
    }

    int getValueFromDiscard(int numOfDiscardCards)
    {
        if (numOfDiscardCards < 3)
            return 20;
        else if (numOfDiscardCards < 6)
            return 10;
        else if (numOfDiscardCards < 9)
            return 0;
        else if (numOfDiscardCards < 12)
            return -10;
        else
            return -20;
    }
}
