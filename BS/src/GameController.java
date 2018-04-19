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
        // ===== Potentially Fix entering string for player number ===== \\
        int playerNum = h.getIntInput("How Many Players are Going to Play? : ");

        while (playerNum <= 1)
        {
            h.display("You cannot play a game with less than 2 players!");
            playerNum = h.getIntInput("How many players are going to play? : ");
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

    public void rulesDisplayBlock()
    {
        System.out.println("\n\t\t\t\t\t\t ======= BS Game Rules ======= ");
        System.out.println("\t\t  The goal of the game is to discard all cards from your hand.");
        System.out.println("\t\t\t\tYou discard a certain number of card on your turn");
        System.out.println("\t Since you discard face down, you can lie about what cards you played.");
        System.out.println("\nFor example: " +
                           "\nThe game starts with someone playing Aces.");
        System.out.println("Say Player 1 has Two Aces, One Jack, Two 7's, and Three 4's.");
        System.out.println("Player 1 can choose to play any number of cards in their hand. " +
                           "\nAce or otherwise. But the player must say they are Aces.");
        System.out.println("After Player 1 has selected cards to play, the first player to their left can choose to call BS.");
        System.out.println("If they do not call BS, it continues around until someone calls BS or it gets back to the player.");
        System.out.println("If someone calls BS, and you lied, you have to take the discard pile.");
        System.out.println("If someone calls BS, and you told the truth, they have to take the discard pile.");
        System.out.println("Play continues in in this fashion until someone has no cards left in hand.");
        h.getStringInput("Press 'Enter' to return to the main menu.");
    }
}
