public class BS
{
    public static void main(String[] args)
    {
        BSGameController controller = new BSGameController();
        controller.menuDisplayBlock();
        controller.setupGame();
    }
}

class BSGameController extends GameController
{
    BSGame handler;

    public void setupGame()
    {
        //displayBlock();       // Commented out and will be placed in an options menu

        int playerNum = getPlayerNum();

        handler = new BSGame(playerNum);

        handler.setupDeck();
        handler.shuffleDeck();

        String name = h.getStringInput("What is your name?");
        handler.addPlayer(name);

        for (int index = 1; index < numPlayers; index++)
        {
            String aiName = h.getStringInput("What is the name of AI " + index + " ?");
            int aiIndex = h.getIntInput("What type of AI do you want " + aiName + " to be?" +
                                "\n1) The truthful silent (low chance to lie, low chance to call BS)" +
                                "\n2) The chaotic slinger (high chance to lie, high chance to call BS" +
                                "\n3) The eternal doubter (low chance to lie, high chance to call BS" +
                                "\n4) The sneaky deceiver (high chance to lie, low chance to call BS");

            switch (aiIndex)
            {
                case 1:
                    handler.addAI(aiName, 80, 80);
                    break;
                case 2:
                    handler.addAI(aiName, 50, 50);
                    break;
                case 3:
                    handler.addAI(aiName, 50, 80);
                    break;
                case 4:
                    handler.addAI(aiName, 80, 50);
                    break;
                default:
                    handler.addAI(aiName, 80, 80);
                    break;
            }
        }

        double numCards = 51.0 / numPlayers;
        numCards = Math.ceil(numCards);

        handler.dealDeck((int)numCards);
        handler.gameLoop();

       // execGame(handler);
    }

    public void menuDisplayBlock()
    {
        System.out.println("\t\t\t\t ======= Welcome to the card game BS ======= ");
        System.out.println("\t\t\t\t\t The game where lying is encouraged!");
        System.out.println("\n1. Start a Game");
        System.out.println("2. Learn to Play");
        int option = h.getIntInput("What would you like to do? : ");

        while(option != 2 && option != 1)
        {
            System.out.println("That is not an option in the menu; Please enter a 1 or 2");
            System.out.println("\n1. Start a Game");
            System.out.println("2. Learn the Rules");
            option = h.getIntInput("What would you like to do? (1 or 2) : ");
        }

        if(option == 1)
        {
            return;         // Start game was selected, so exit method and continue with setup
        }

        else if(option == 2)
        {
            rulesDisplayBlock();
            menuDisplayBlock();
        }
        return;
    }
}
