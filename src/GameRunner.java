import java.util.ArrayList;
import java.util.Scanner;

public class GameRunner {
    public static void main(String[] args) {
        final String COLOR_YELLOW = "\u001b[33m";

        Scanner scanner = new Scanner(System.in);

        final Game GAME = new Game();
        ArrayList<Player> playerList;

        do {
            int newPlrCount; boolean isAIactive = false;

            System.out.print("Creating a new game.\n " + "" +
                    "Input the amount of players (max is " + GAME.getMaxPlayers() + "):");
            newPlrCount = scanner.nextInt();

            if (newPlrCount > 1) {
                System.out.println("Play with the AI? (y/n)");

                if (scanner.nextLine().startsWith("y".toLowerCase()))
                    isAIactive = true;
            }

            GAME.newGame(newPlrCount, isAIactive);
            System.out.println("The game will start in 3 seconds.");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            GAME.assignCards();
            playerList = GAME.getPlayerList();

            for (Player player : playerList) {
                if (!(player.player == 0)) {
                    System.out.print("\nSet a name for Player " + player.player + " ?" +
                            "\n Enter a name, or \"0\" for no name.\n" +
                            "Enter name here: ");

                    if (scanner.hasNextInt() && (scanner.nextInt() == 0) )
                        player.setName("Player " + player.player);
                    else player.setName("" + scanner.nextLine());
                }
            }
            int maxRounds = GAME.getMaxRounds(), firstRound = GAME.getCurrentRound();

            while (GAME.getCurrentRound() < GAME.getMaxRounds()) {
                for (Player player : playerList) {
                    String playerName = player.name;
                    if (player.player == 0) playerName = "Player 0 (AI)";

                    if (GAME.skipper != null) {
                        System.out.println(playerName + " has been skipped " +
                                "by " + GAME.skipper.name + "!");
                        GAME.skipper = null;
                        GAME.isSkipActive = false;
                    }
                    else {

                        System.out.println(COLOR_YELLOW +
                                playerName + "'s Turn" +
                                GAME.viewPlayerCards(player));
                        System.out.print("Card to play: ");


                        if (player.player == 0) {
                            System.out.print(GAME.playCardAI(player));
                        } else {
                            boolean inputNowValid = false;

                            do {
                                inputNowValid = GAME.playCard(scanner.nextLine(), player);
                            } while (!inputNowValid);
                        }

                        while (!scanner.hasNextLine() || !scanner.hasNextInt()) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        System.out.print("\n");

                    }
                }
                System.out.println("\nEnd of round " + firstRound);
                firstRound++;
            }

            System.out.print(GAME.toString());
            GAME.endGame();

            System.out.println("That was a fun game!\n" +
                    "Want to play again? (y/n): ");
            if (scanner.nextLine().startsWith("y".toLowerCase()))
                GAME.isGameOver = true;
        } while (!GAME.isGameOver);
    }
}
