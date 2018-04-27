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
            System.out.println("The four AI Personalities are:" +
                            "\n1) The Truthful Silent (low chance to lie, low chance to call BS)" +
                            "\n2) The Chaotic Slinger (high chance to lie, high chance to call BS)" +
                            "\n3) The Eternal Doubter (low chance to lie, high chance to call BS)" +
                            "\n4) The Sneaky Deceiver (high chance to lie, low chance to call BS)");
            int aiIndex = h.getIntInput("What type of personality would you like " + aiName + " to have? : ");


            switch (aiIndex)
            {
                case 1:
                    handler.addAI(aiName, 90, 80);
                    break;
                case 2:
                    handler.addAI(aiName, 70, 50);
                    break;
                case 3:
                    handler.addAI(aiName, 70, 80);
                    break;
                case 4:
                    handler.addAI(aiName, 90, 50);
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
        System.out.println("\nEnter '1' to Start a new Game");
        System.out.println("Enter '2' to View the Rules");
        int option = h.getIntInput("What would you like to do? : ");

        while(option != 2 && option != 1)
        {
            System.out.println("That is not an option in the menu; Please enter a 1 or 2");
            System.out.println("\nEnter '1' to Start a new Game");
            System.out.println("Enter '2' to View the Rules");
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
