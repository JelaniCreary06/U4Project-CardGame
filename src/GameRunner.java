import java.util.Scanner;

public class GameRunner {
    public static void main(String[] args) {
        final Game GAME = new Game();

        while (!GAME.isGameOver) {
            GAME.newGame(1);
            System.out.println("The game will start in 3 seconds.");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            GAME.assignCards();
            GAME.play();

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
