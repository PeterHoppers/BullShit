import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BSPlayer extends Player
{
    //needs access to BSGame instead of the general Game class
    BSGame base;
    public BSPlayer(String name, BSGame base)
    {
        super(name, base);
        this.base = base;
    }

    //method called by the game loop to prompt card playing
    @Override
    public void play(List<Card> cardsToPlayOn)
    {
        hand.cardSort();
        h.display("Your hand is: \n" + hand );
        h.display("You need to play " + getValueAsString(base.valueToPlay));
        try
        {
            cardDecision(cardsToPlayOn);
        }
        catch(IOException e)
        {
            System.out.println("Exception was thrown in BSPlayer for 'play()' method");
        }
    }

    //decides what cards to play
    @Override
    public void cardDecision(List<Card> cardPile) throws IOException
    {
        List<Card> cardsToPlay = new ArrayList<Card>();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String input = "y";
        List<Integer> selections = new ArrayList<Integer>();

        while(input.equalsIgnoreCase("y"))
        {
            System.out.print("Choose a card or cards from your hand: ");

            String[] indexNumsString = new String[4];
            indexNumsString = in.readLine().split(" ");
            int[] indexNums = new int[indexNumsString.length];


            for(int i = 0; i < indexNums.length; i++)
            {
                indexNums[i] = Integer.parseInt(indexNumsString[i]);

                boolean isNumUnique = true;
                //int indexSelected = inputIntValidation(0, cardsInHand(), "Choose a card or cards from your hand:\n ");

                //int numToAdd = indexSelected - 1;
                int numToAdd = indexNums[i]-1;


                for (Integer num :
                        selections) {
                    if (num.equals(numToAdd)) {
                        isNumUnique = false;
                        break;
                    }
                }

                if (isNumUnique) {
                    selections.add(indexNums[i]-1);
                } else {
                    h.display("You already selected card " + indexNums[i] +". The duplicate selection was ignored");
                }

                if (selections.size() == hand.cardNum()) {
                    h.display("You have played all the cards in your hand!");
                    input.equals("Whoops");
                    break;
                }
            }
            input = h.getStringInput("Do you want to choose another card to play? (Y/N)");
        }

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

    public boolean callBS(String playerName, int numOfCardsPlayed, int numOfCardDiscarded)
    {
        String input = h.getStringInput("Do you, " + name + " want to call BS on " + playerName + " who played " + numOfCardsPlayed +
                " " + getValueAsString(base.valueToPlay) +
                " on a discard pile of " + numOfCardDiscarded + " cards? (Y/N)");

        if (input.equalsIgnoreCase("Y"))
            return true;
        else
            return false;
    }

    //currently, there is no choice, so this is null
    public String playerChoice()
    {
        return null;
    }
}
