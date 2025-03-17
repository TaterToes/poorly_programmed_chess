import java.util.Scanner;

public class MainScreen {
    boolean mainMenuStart = true;
    private final Scanner scanner = new Scanner(System.in);

    public final String getPlayerMove() {
        return scanner.nextLine();
    }

    public void mainMenu() {
        OUTER: while (true) {
            if (mainMenuStart) {
                System.out.println("Pick your choices, Game or Quit");
                mainMenuStart = false;
            }

            String playerInput = getPlayerMove();
            switch (playerInput.toLowerCase()) {
                case "game" -> {
                    Game game = new Game();
                    
                    System.out.println("Starting game..");
                    mainMenuStart = true;
                    game.playGame();
                }
                case "quit" -> {
                    break OUTER;
                }
                default -> System.out.println("Please type one of the followings, Game or Quit");
            }
        }
    }

    public static void main(String[] args) {
        MainScreen mainScreen = new MainScreen();

        mainScreen.mainMenu();
    }
}
