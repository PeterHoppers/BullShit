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

        execGame(handler);
    }

    public void menuDisplayBlock()
    {
        System.out.println("\t\t\t\t ======= Welcome to the card game BS ======= ");
        System.out.println("\t\t\t\t\t  The game where lying is encouraged!");
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

    public void displayBlock()
    {
        System.out.println("This program lets you play the card game BS.");
        System.out.println("The goal of the game is to discard all cards from your hand.");
        System.out.println("You discard a certain number of card on your turn");
        System.out.println("Since you discard face down, you can lie about what cards you played.");
        System.out.println("If someone calls BS, and you lied, you have to take the discard pile.");
        System.out.println("If someone calls BS, and you told the truth, they have to take the discard pile.");
        System.out.println();
    }
}
