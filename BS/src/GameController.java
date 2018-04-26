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
        int playerNum = h.getIntInput("How Many People are Going to Play? : ");

        while (playerNum <= 2 || playerNum > 10)
        {
            if(playerNum <= 2)
            {
                h.display("You cannot play a game with less than 3 players!");
                playerNum = h.getIntInput("How Many People are Going to Play? : ");
            }
            else if (playerNum > 10)
            {
                h.display("You cannot play a game with more than 10 players!");
                playerNum = h.getIntInput("How Many People are Going to Play? : ");
            }

        }

        numPlayers = playerNum;
        return numPlayers;
    }
/*  ========== Not used for BS Game ==========
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
*/
    public void rulesDisplayBlock()
    {
        System.out.println("\n\t\t\t\t\t\t ======= BS Game Rules ======= "
                + "\n\t\t  The goal of the game is to discard all cards from your hand."
                + "\n\t\t\t\tYou discard a certain number of card on your turn"
                + "\n\t Since you discard face down, you can lie about what cards you played."
                + "\nFor example: "
                + "\nThe game starts with someone playing Aces."
                + "\nSay Player 1 has Two Aces, One Jack, Two 7's, and Three 4's."
                + "\nPlayer 1 can choose to play any number of cards in their hand. "
                + "\nAce or otherwise. But the player must say they are Aces."
                + "\nAfter Player 1 has selected cards to play, the first player to their left can choose to call BS."
                + "\nIf they do not call BS, it continues around until someone calls BS or it gets back to the player."
                + "\nIf someone calls BS, and you lied, you have to take the discard pile."
                + "\nIf someone calls BS, and you told the truth, they have to take the discard pile."
                + "\nThe next player will then begin their turn by selecting 2's from their hand."
                + "\nPlay continues in this fashion until someone has no cards left in hand.");
        h.getStringInput("\nType 'Back' and press enter to return to the Main Menu.");
    }
}
