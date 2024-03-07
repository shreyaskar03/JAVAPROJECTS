import java.util.Scanner;

public class Project4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to The Lost Expedition!");

        // Starting point
        System.out.println("You find yourself in a dense forest. Your expedition team is missing, and you must find them.");
        System.out.println("You come across a fork in the path.");
        System.out.println("1. Take the well-trodden path");
        System.out.println("2. Venture into the mysterious, overgrown path");
        int decision1 = getUserChoice(scanner);

        if (decision1 == 1) {
            // Well-trodden path
            System.out.println("You choose to take the well-trodden path.");
            // Continue the story...
            System.out.println("You walk for a while and find a river blocking your path.");
            System.out.println("1. Try to cross the river");
            System.out.println("2. Search for a bridge");
            int decision2 = getUserChoice(scanner);
            if (decision2 == 1) {
                System.out.println("You attempt to cross the river but are swept away by the strong current.");
                System.out.println("Game over. You failed to find your expedition team.");
            } else {
                System.out.println("You find a sturdy bridge and safely cross the river.");
                System.out.println("You continue your journey.");
                // Continue the story...
            }
        } else if (decision1 == 2) {
            // Overgrown path
            System.out.println("You choose to venture into the mysterious, overgrown path.");
            // Continue the story...
            System.out.println("As you push through the dense foliage, you stumble upon a hidden cave entrance.");
            System.out.println("1. Enter the cave");
            System.out.println("2. Keep following the overgrown path");
            int decision2 = getUserChoice(scanner);
            if (decision2 == 1) {
                System.out.println("You cautiously enter the cave and find a treasure chest!");
                System.out.println("Congratulations! You found the lost treasure of the expedition.");
            } else {
                System.out.println("You decide to continue along the overgrown path.");
                // Continue the story...
            }
        } else {
            System.out.println("Invalid input. Please enter either 1 or 2.");
            return;
        }

        // Add more decision points and outcomes as needed...
    }

    public static int getUserChoice(Scanner scanner) {
        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1 || choice == 2) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter either 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter either 1 or 2.");
            }
        }
        return choice;
    }
}
