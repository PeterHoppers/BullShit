public class OldMaidGame extends Game
{
    public OldMaidGame(int numPlayersAllowed)
    {
        super(numPlayersAllowed);
    }

    public void discardOneQueen()
    {
        deck.removeQueen();
    }

    @Override
    public void addPlayer(String name)
    {
        players[numPlayers] = new OldMaidPlayer(name, this);
        numPlayers++;
    }

    @Override
    public boolean isGameRunning()
    {
        int numHandsEmpty = 0;

        for(Player player : players)
        {
            if (player.isHandEmpty())
                numHandsEmpty++;
        }

        if (numHandsEmpty < (numPlayers - 1))
            return true;
        else // numHandsEmpty >= numPlayers - 1
        {
            for(Player player : players)
            {
                if (!player.isHandEmpty())
                {
                    h.display("Sorry! " + player.name + " has lost the game!");
                    break;
                }
            }
            return false;
        }

    }

}

