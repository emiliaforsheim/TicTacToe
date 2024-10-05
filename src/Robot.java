import java.util.Random;

public class Robot implements Player{
    private char symbol;
    private char opponentSymbol;
    private String difficulty;
    private Random random;
    private String name;

    public Robot(char symbol, String difficulty) {
        this.symbol = symbol;
        this.opponentSymbol = (symbol == 'X') ? 'O' : 'X';
        this.difficulty = difficulty;
        this.random = new Random();
        this.name = "Robot";
    }

    @Override
    public int makeMove(char[][] board) {
        if (difficulty.equalsIgnoreCase("Easy")) {
            return randomMove(board);
        } else if (difficulty.equalsIgnoreCase("Medium")) {
            if (random.nextBoolean()) {
                return randomMove(board);
            } else {
                return bestMove(board);
            }
        } else {
            return bestMove(board);
        }
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    private int randomMove(char[][] board) {
        int move;
        do {
            move = random.nextInt(9);
        } while (board[move / 3][move % 3] != ' ');
        return move + 1;
    }

    private int bestMove(char[][] board) {
        int[] bestMove = minimax(board, symbol);
        return bestMove[0] * 3 + bestMove[1] + 1;
    }

    private int[] minimax (char[][] board, char currentPlayer) {
        int bestScore = (currentPlayer == symbol) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] bestMove = {-1, -1};

        if (checkWinner(board, symbol)) return new int[] {-1, -1, 10};
        if (checkWinner(board, opponentSymbol)) return new int[] {-1, -1, -10};
        if (isBoardFull(board)) return new int[] {-1, -1, 0};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = currentPlayer;
                    int score;
                    if (currentPlayer == symbol) {
                        score = minimax(board, opponentSymbol)[2];
                    } else {
                        score = minimax(board, symbol)[2];
                    }
                    board[i][j] = ' ';

                    if (currentPlayer == symbol) {
                        if (score > bestScore) {
                            bestScore = score;
                            bestMove[0] = i;
                            bestMove[1] = j;
                        }
                    } else {
                        if (score < bestScore) {
                            bestScore = score;
                            bestMove[0] = i;
                            bestMove[1] = j;
                        }
                    }
                }
            }
        }
        return new int[] {bestMove[0], bestMove[1], bestScore};
    }

    private boolean checkWinner(char[][] board, char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    private boolean isBoardFull (char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') return false;
            }
        }
        return true;
    }
}
