public class OldMaid
{
    public static void main(String[] args)
    {
        GameControllerOldMaid controller = new GameControllerOldMaid();
        controller.execGame();
    }
}

class GameControllerOldMaid
{
    IIOHandler h;
    OldMaidGame handler;

    public GameControllerOldMaid()
    {
        h  = new IOHandler();
    }

    public void execGame()
    {
        displayBlock();

        int playerNum = h.getIntInput("How Many Players are Going to Play? : ");

        while (playerNum <= 1)
        {
            h.display("You cannot play a game with only " + playerNum + " players!");
            playerNum = h.getIntInput("How Many Players are Going to Play? : ");
        }

        handler = new OldMaidGame(playerNum);

        handler.setupDeck();
        handler.discardOneQueen();
        handler.shuffleDeck();

        for (int index = 1; index <= playerNum; index++)
        {
            String name = h.getStringInput("What is the name of Player " + index + " ?");
            handler.addPlayer(name);
        }

        double numCards = 51.0 / playerNum;
        numCards = Math.ceil(numCards);

        handler.dealDeck((int)numCards);
        handler.gameLoop();

    }

    public void displayBlock() {
        System.out.println("This program lets you play the card game Old Maid.");
        System.out.println("The goal of the game is to discard all cards from your hand.");
        System.out.println("You match cards in your hand in order to remove them from your hand.");
        System.out.println("If you cannot match cards, you need to draw one from another player's hand.");
        System.out.println("Game ends when there is only one card left, the lone queen, the old maid!");
        System.out.println();
    }
}



