import java.util.List;

public class OldMaidPlayer extends Player
{
    public OldMaidPlayer(String name, Game base)
    {
        super(name, base);
    }

    @Override
    public void play(List<Card> cardsToPlayOn)
    {
        boolean playerPlaying = true;

        int playerChoice = 0;

        while (playerPlaying)
        {
            hand.cardSort();
            String input = playerChoice();

            switch (input.toUpperCase())
            {
                case "M":
                case "1":
                    cardDecision(cardsToPlayOn);
                    break;
                case "L":
                case "2":
                    playerChoice = selectOtherPlayer();
                    h.display("Player " + (playerChoice + 1) + " has " + base.getAmountCardsFromPlayer(playerChoice) +  " cards in his hand.");
                    break;
                case "E":
                case "3":
                    int cardChoice = 0;

                    playerChoice = selectOtherPlayer();

                    int cardsInThatHand = base.getAmountCardsFromPlayer(playerChoice);

                    h.display("There are " + cardsInThatHand + " cards in that hand.");
                    cardChoice =  inputIntValidation(0, cardsInThatHand - 1, "Choose a card to draw: ");

                    Card drawn = base.getCardFromPlayer(playerChoice, cardChoice);
                    hand.addCard(drawn);

                    h.display("You have drawn " + drawn + ". Would you like to match it with any of your other cards? \n" + hand);
                    String answ = h.getStringInput("Y/N:");

                    if (answ.equalsIgnoreCase("y"))
                    {
                        //==========Match Decision=========
                        Card otherCard = checkIfCardInvalid("Choose another card to match: ");

                        if (checkNumber(drawn, otherCard))
                        {
                            h.display("You successfully matched two " + drawn.getValueAsString() + "s!");
                            hand.removeCard(drawn);
                            cardsToPlayOn.add(drawn);
                            hand.removeCard(otherCard);
                            cardsToPlayOn.add(otherCard);
                        }
                        else
                        {
                            h.display(drawn + " does not match " + otherCard);
                        }

                    }

                    playerPlaying = false;	// end his turn, stop playing
                    break;
                default:
                    h.display("Please select a valid option");
                    break;
            }
        }
    }

    public int selectOtherPlayer()
    {
        int playerChoice = 0;
        boolean isSelecting = true;

        do
        {
            playerChoice = h.getIntInput("Choose a player to choose a card from : ");

            if (playerChoice < 0)
                h.display("Please pick a number greater than 0");
            else if (playerChoice >=  base.numPlayers)
                h.display("There are not that many players!");
            else if (base.isPlayerIndexMine(playerChoice, this))
                h.display("You cannot choose yourself!");
            else if (base.getAmountCardsFromPlayer(playerChoice) < 1)
                h.display("You cannot take cards from a player holding no cards!");
            else
                isSelecting = false;

        }while (isSelecting);

        return playerChoice;
    }


    public void cardDecision(List<Card> discardPile)
    {
        if (cardsInHand() < 2)
        {
            h.display("You cannot match cards with less than 2 cards in hand!");
            return;
        }

        Card firstCard, secondCard;

        firstCard = checkIfCardInvalid("Choose a card from your hand : ");

        secondCard = checkIfCardInvalid("Choose another card from your hand to match: ");


        if (checkNumber(firstCard, secondCard))
        {
            h.display("You successfully matched two " + firstCard.getValueAsString() + "s!");
            hand.removeCard(firstCard);
            discardPile.add(firstCard);
            hand.removeCard(secondCard);
            discardPile.add(secondCard);
        }
        else
        {
            h.display(firstCard + " does not match " + secondCard);
        }
    }

    @Override
    public String playerChoice()
    {
        h.display("Your hand is: \n" + hand );

        h.display("What Would You Like to Do?");
        h.display("Match a card (M/1)");
        h.display("Look at the amount of card in an opponent's hand (L/2)");
        h.display("End your turn and draw an opponent's card(E/3)");

        String input = h.getStringInput("Enter A Choice");
        return input;
    }

}
