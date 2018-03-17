import java.util.Scanner;

class IOHandler implements IIOHandler {
    Scanner sc;

    IOHandler() {
        this.sc = new Scanner(System.in);
    }

    public void displayBlock() {
        System.out.println("This program lets you play the card game Crazy Eights.");
        System.out.println("The goal of the game is to discard all cards from your hand.");
        System.out.println("You can match suit or rank. If you match rank, you can discard multiple cards of that rank.");
        System.out.println("Eights are a special case. They can be placed anytime and you can select the next suit.");
        System.out.println("If you cannot play a card, you need to draw one from the deck.");
        System.out.println();
    }

    public void display(String message) {
        System.out.println(message);
    }

    public String getStringInput(String prompt) {
        System.out.println(prompt);
        String input = this.sc.next();
        return input;
    }

    public int getIntInput(String prompt) {
        System.out.print(prompt);
        int convert = this.sc.nextInt();
        return convert;
    }
}

