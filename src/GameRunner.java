import java.util.ArrayList;
import java.util.Scanner;

public class GameRunner {
    public static void main(String[] args) {
        final Game GAME = new Game();
        final String COLOR_YELLOW = "\u001b[33m", COLOR_WHITE = "\u001B[37m";

        Scanner scanner = new Scanner(System.in);

        boolean gameOver = false;

        System.out.println(
                "The Objective of this game is to gain the most points by the end of the rounds!" +
                        "\nGain points by using cards." +
                        "\n Cards with \"A\" are Ability cards, they either skip rounds, add or subtract points" +
                        " from everyone except you (abilities are random.)" +
                        "\n Cards with \"+\" or \"-\" either add or subtract points."
                        + "To play a card, input the cards number, which is the cyan (blue) number within the card." +
                        "\n You can't view your points, or abilities, the winner is outputted at the end."
                        + "\n\n The game will start in 5 seconds!"
        );

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        do {
            ArrayList<String> initalizedNameList = new ArrayList();
            int peoplePlaying = 0; boolean playWithAI;

            while (peoplePlaying == 0) {
                System.out.print("Creating a new game, input the amount of players." +
                        "\nPlayers (Max is " + GAME.maxPlayers() + "): ");
                peoplePlaying = scanner.nextInt();

                if (peoplePlaying <= 0) System.out.println("There MUST be at least 1 player.");
                else if (peoplePlaying > GAME.maxPlayers()) peoplePlaying = 0;
            }

            if (peoplePlaying > 1) {
                System.out.println("Play with the games AI active? (y/n): ");

                String answer = scanner.nextLine();

                while(!scanner.hasNextLine()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                answer = scanner.nextLine();

                if (answer.toLowerCase().startsWith("y")) playWithAI = true;
                else playWithAI = false;
            } else playWithAI = true;

            System.out.print("\n");

            if (peoplePlaying > 1) {
                for (int i = 0; i < peoplePlaying; i++) {
                    System.out.println("Set a name for Player " + (i+1) + "?"
                            + "\nType \"0\" to cancel. (Will call you by number)\nEnter name:");

                    String name = scanner.nextLine();

                    if (!(name.equals("0"))) initalizedNameList.add(name);
                    else {
                        initalizedNameList.add("Player " + (i+1));
                        initalizedNameList.add("Player 0 (AI)");
                    }
                }
            } else initalizedNameList.add("Player " + peoplePlaying);

            boolean readyToContinue = false;

            readyToContinue = GAME.initalizePlayers(peoplePlaying, playWithAI, initalizedNameList);

            while (!readyToContinue) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("The game has been initialized!\nStarting now.\n");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            ArrayList<Player> playerList = GAME.getPlayers();
            Player gameAI = GAME.AI();

            for (int i = GAME.currentRound(); i < GAME.maxRounds(); i++) {
                System.out.println("\n" + COLOR_WHITE + "Starting Round " + GAME.currentRound());

                for (Player player : playerList) {

                    if (GAME.isSkipActive()) {
                        Player skipper = GAME.playerWhoSkipped();

                        System.out.println("\n" + player.name() + " you've been skipped by " + skipper.name());
                        GAME.skipOver();
                    } else {

                        System.out.print("\n" +
                                COLOR_YELLOW +player.name() + "'s Turn" +
                                player.viewCards() + "\nCard to play: "
                        );
                        boolean inputtedCard = false;

                        if (player == gameAI) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            System.out.println(GAME.playCard(player));
                            inputtedCard = true;
                        }
                        else inputtedCard = GAME.playCard(scanner.nextInt(), player);

                        while(!inputtedCard) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }

                }
            }

            System.out.println(GAME.toString() + "\nGood game!");
            System.out.print("Play again? (y/n): ");
            GAME.clear();

            String answer = scanner.nextLine();

            while(!scanner.hasNextLine()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            answer = scanner.nextLine();

            if (answer.toLowerCase().startsWith("n")) gameOver = true;
        } while (!gameOver);
    }
}
