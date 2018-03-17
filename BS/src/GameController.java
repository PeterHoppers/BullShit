public class GameController
{
    IIOHandler h;
    int numPlayers;

    public GameController()
    {
        h  = new IOHandler();
    }

    public int getPlayerNum()
    {
        int playerNum = h.getIntInput("How Many Players are Going to Play? : ");

        while (playerNum <= 1)
        {
            h.display("You cannot play a game with only " + playerNum + " players!");
            playerNum = h.getIntInput("How Many Players are Going to Play? : ");
        }

        numPlayers = playerNum;
        return numPlayers;
    }

    public void execGame(Game handler)
    {
        handler.setupDeck();
        handler.shuffleDeck();

        for (int index = 1; index <= numPlayers; index++)
        {
            String name = h.getStringInput("What is the name of Player " + index + " ?");
            handler.addPlayer(name);
        }

        double numCards = 51.0 / numPlayers;
        numCards = Math.ceil(numCards);

        handler.dealDeck((int)numCards);
        handler.gameLoop();
    }
}
