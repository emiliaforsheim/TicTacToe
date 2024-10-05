import java.util.Scanner;

import java.util.Scanner;

public class Human implements Player{
    private char symbol;
    private String name;
    private Scanner sc;

    public Human(char symbol, String name) {
        this.symbol = symbol;
        this.name = name;
        this.sc = new Scanner(System.in);
    }

    @Override
    public int makeMove(char[][] board) {
        System.out.println(name + " (" + symbol + "), ange position (1-9): ");
        return sc.nextInt();
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }
}
