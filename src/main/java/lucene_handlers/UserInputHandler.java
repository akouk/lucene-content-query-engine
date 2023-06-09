package lucene_handlers;

import java.util.Scanner;

public class UserInputHandler {
    private Scanner scanner;

    public UserInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public String getInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public int getIntInput(String prompt, int maxFiles) {
        int numHits;
        while (true) {
            System.out.println(prompt);
            numHits = scanner.nextInt();
            scanner.nextLine();
            if (numHits <= maxFiles) {
                break;
            }
            System.out.println("The number of results cannot be greater than the number of files. Please enter a number less than or equal to " + maxFiles);
        }
        return numHits;
    }

    public void close() {
        scanner.close();
    }

}
