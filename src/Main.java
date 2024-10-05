import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean playAgain = true;
        int player1Score = 0;
        int player2Score = 0;

        Player player1 = null;
        Player player2 = null;

        while (playAgain) {
            int numPlayers = 0;
            while (true) {
                System.out.println("Hur många spelare? (1 eller 2): ");
                if (sc.hasNextInt()) {
                    numPlayers = sc.nextInt();
                    if (numPlayers == 1 || numPlayers == 2) {
                        break;
                    } else {
                        System.out.println("Ogiltligt val. Vänligen ange 1 eller 2.");
                    }
                } else {
                    System.out.println("Ogiltligt val. Vänligen ange en siffra.");
                    sc.next();
                }
            }

            System.out.println("Ange namn för spelare X: ");
            String player1Name = sc.next();
            player1 = new Human('X', player1Name);

            if (numPlayers == 2) {
                System.out.println("Ange namn för spelare O: ");
                String player2Name = sc.next();
                player2 = new Human('O', player2Name);
            } else {
                System.out.println("Välj svårighetsgrad: (Easy, Medium, Hard: ");
                String difficulty;

                while (true) {
                    difficulty = sc.next();
                    if (difficulty.equalsIgnoreCase("Easy") ||
                        difficulty.equalsIgnoreCase("Medium") ||
                        difficulty.equalsIgnoreCase("Hard")) {
                        break;
                    } else {
                        System.out.println("Oglitligt val. Vänligen ange Easy, Medium eller Hard.");
                    }
                }
                player2 = new Robot('O', difficulty);
            }

            Game game = new Game(player1, player2);
            boolean gameOver = false;
            Player currentPlayer = player1;

            while (!gameOver) {
                game.printBoard();
                int position = currentPlayer.makeMove(game.getBoard());

                int row = (position - 1) / 3;
                int col = (position - 1) % 3;

                boolean validMove = game.makeMove(row, col, currentPlayer.getSymbol());

                if (validMove && game.checkForWin()) {
                    game.printBoard();
                    if (currentPlayer instanceof Robot) {
                        System.out.println("Robot vinner!");
                        player2Score++;
                    } else {
                        System.out.println(currentPlayer.getName() + " vinner!");
                        if (currentPlayer == player1) {
                            player1Score++;
                        } else {
                            player2Score++;
                        }
                    }
                    gameOver = true;

                } else if (validMove && game.isBoardFull()) {
                    game.printBoard();
                    System.out.println("Spelet är oavgort!");
                    gameOver = true;
                } else if (validMove) {
                    currentPlayer = (currentPlayer == player1) ? player2 : player1;
                }
            }

            System.out.println("Poängställning: ");
            System.out.println(player1.getName() + ": " + player1Score);
            System.out.println((player2 instanceof Robot ? "Robot" : player2.getName()) + ": " + player2Score);

            System.out.println("Vill ni spela igen? (Ja/Nej): ");
            String response = sc.next();
            playAgain = response.equalsIgnoreCase("Ja");
        }

        System.out.println("Slutresultat: ");
        System.out.println(player1.getName() + ": " + player1Score);
        System.out.println((player2 instanceof Robot ? "Robot" : player2.getName()) + ": " + player2Score);

        if (player1Score > player2Score) {
            System.out.println(player1.getName() + " vann spelet!");
        } else if (player2Score > player1Score) {
            System.out.println((player2 instanceof Robot ? "Robot" : player2.getName()) + " vann spelet!");
        } else {
            System.out.println("Spelet slutade oavgjort!");
        }
        sc.close();
    }
}