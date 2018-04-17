import java.util.List;

public class BSGame extends Game
{
    /**
     * This pile of cards is what the other players call BS on
     */
    List<Card> checkPile;
    BSPlayer[] players;
    int valueToPlay = 1;

    public  BSGame(int numPlayersAllowed)
    {
        super(numPlayersAllowed);
        players =  new BSPlayer[numPlayersAllowed];
    }

    //adds a player to the list of players
    @Override
    public void addPlayer(String name)
    {
        players[numPlayers] = new BSPlayer(name, this);
        numPlayers++;
    }

    public void addAI(String name, int bsIndex, int lieIndex)
    {
        players[numPlayers] = new BaseAI(name, this, bsIndex, lieIndex);
        numPlayers++;
    }

    @Override
    public void dealDeck(int cardsPerPlayer)
    {
        if (deck == null) setupDeck();

        Card topCard;

        for (int currCards = 0; currCards < cardsPerPlayer; currCards++)
        {
            for(BSPlayer player : players)
            {
                topCard = deck.dealCard();

                if (topCard != null)
                    player.hand.addCard(topCard);
            }
        }

        playingPlayer = players[0];
    }

    //checks if the game has ended
    @Override
    public boolean isGameRunning()
    {
        for(Player player : players)
        {
            if (player.isHandEmpty())
            {
                return false;
            }
        }
        return true;

    }

    @Override
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

            ///figure out if the player lied of not
            boolean hasLied;
            int calledBS = promptCallBS(currentPlayer);

            if (calledBS != -1)
            {
                Player calledPlayer = players[calledBS];
                h.display(calledPlayer.name + " has called BS!");
                hasLied = checkIfPlayerLied();

                if (!hasLied)
                {
                    h.display(playingPlayer.name + " did not lie, meaning that " + calledPlayer.name + " get the discard pile.");
                    addCheckCardsToDiscard();
                    addDiscardToHand(calledPlayer);
                }
                else
                {
                    h.display(playingPlayer.name + " did lie, meaning that they get the discard pile.");
                    addCheckCardsToDiscard();
                    addDiscardToHand(playingPlayer);
                }
            }
            else
            {
                h.display("No one called BS, so " + playingPlayer.name + " adds their cards to the discard pile.");
                addCheckCardsToDiscard();
            }

            valueToPlay ++;

            if (valueToPlay > 13) // 13 == king
                valueToPlay = 1; // 1 == ace
        }

        h.display(playingPlayer.name + " has won the game!");
    }


    int promptCallBS(int currentPlayer)
    {
        //go around to each player and see if they call BS or not
        boolean calledBS = false;
        int playerReact = currentPlayer;
        for (int playNum = 0; playNum < players.length - 1; playNum++) //minus one prevents it from viewing itself
        {
            calledBS = players[playerReact].callBS(playingPlayer.name, checkPile.size(), discardPile.size());

            if (calledBS)
                break;

            playerReact++;

            if (playerReact >= players.length) //loop back to the first player
                playerReact = 0;
        }

        if (calledBS)
            return  playerReact;
        else
            return -1;
    }

    boolean checkIfPlayerLied()
    {
        for (Card checkCard: checkPile)
        {
            if(checkCard.getValue() != valueToPlay)
            {
                h.display("Card: " + checkCard.getValue() + " vs. " + valueToPlay);
                return true;
            }
        }

        return  false;
    }

    public void addCheckCards(List<Card> checkPile)
    {
        this.checkPile = checkPile;
    }

    void addCheckCardsToDiscard()
    {
        for (Card checkCard:
                checkPile)
        {
            discardPile.add(checkCard);
        }
    }

    void addDiscardToHand(Player player)
    {
        if (discardPile == null | discardPile.isEmpty()) return;
        for (Card discard:
                discardPile)
        {
            player.hand.addCard(discard);
        }

        discardPile.clear();
    }
}
