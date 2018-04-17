public class BS
{
    public static void main(String[] args)
    {
        BSGameController controller = new BSGameController();
        controller.setupGame();
    }
}

class BSGameController extends GameController
{
    BSGame handler;

    public void setupGame()
    {
        displayBlock();

        int playerNum = getPlayerNum();

        handler = new BSGame(playerNum);

        handler.setupDeck();
        handler.shuffleDeck();

        String name = h.getStringInput("What is your name?");
        handler.addPlayer(name);

        for (int index = 1; index < numPlayers; index++)
        {
            String aiName = h.getStringInput("What is the name of AI " + index + " ?");
            int bsIndex = h.getIntInput("What is their chance to call BS?");
            int lieIndex = h.getIntInput("What is their chance to call lie?");
            handler.addAI(aiName, bsIndex, lieIndex);
        }

        double numCards = 51.0 / numPlayers;
        numCards = Math.ceil(numCards);

        handler.dealDeck((int)numCards);
        handler.gameLoop();

       // execGame(handler);
    }

    public void displayBlock() {
        System.out.println("This program lets you play the card game BS.");
        System.out.println("The goal of the game is to discard all cards from your hand.");
        System.out.println("You have to discard a certain number of card on your turn");
        System.out.println("Since you discard face down, you can lie about what cards you played.");
        System.out.println("If someone calls BS, and you lied, you have to take the discard pile.");
        System.out.println("If someone calls BS, and you told the truth, they have to take the discard pile.");
        System.out.println();
    }
}
