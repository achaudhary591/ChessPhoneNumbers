/*
PROBLEM STATEMENT 

Development Analysis
Introduction
This allows us to gauge where your strengths lie.
While everything we do is currently in Net6 (mainly C#), SOL Server and related technologies we are not
requiring the answers to be provided in that way. Feel free to use any development tools or languages you are
familiar with to complete this test, but .Net is preferred.
This is designed to evaluate your approach, style and understanding of how to solve general programming
tasks. It is not a test on your knowledge of Net, standard libraries, or Ul Design ability. In fact, no GUl is
required - a console app will suffice.
As such feel free to use your own device, development environment and programming language of choice.
Alternatively, we can provide you with a laptop, Visual Studio, and internet access from our offices.
As a guide it should be possible to complete this in around three and a half hours, however you are free to take
as much time as you like. During that time feel free to use any online resources and ask myself or colleagues for
any assistance or questions you may have.
Submission
When you have finished, please provide all source files, along with a text file indicating the count for each
associated chess piece. Please include these counts in your response.
We will then be able to evaluate your submission and get back to you with next steps.
Part of which may be a discussion of your solution, involving you walking us through the design choices you
made as you approached the problem.
Feel free to reach out to development@lemonedge.com with any other questions you may have.

Problem
The following diagram is of a standard telephone keypad. It consists of a 4x3 grid of buttons. Using the valid
moves of a piece from the game of chess, varying combinations of 7-digit phone numbers can be derived. For
example, starting in the upper-right corner (the "3" key) using a rook (which moves any number of spaces
horizontally or vertically), one valid number, after pressing the initial "3" key, is: 314-5289.
1 2 3
4 5 6
7 8 9
* 0 #
Write a program that will count the number of valid 7-digit phone numbers that can be traced out on the
keypad for every given chess piece. The following rules define a valid phone number:
• Seven digits in length
• Cannot start with a 0 or 1
• Cannot contain a * or #
Remember
• This is not a test on your knowledge of development libraries.
• Do not waste time stuck on any one area of functionality, just write a dummy function, and continue.
• Please try to make sure your code compiles and you can talk us through it all when your time is up.
• Please note some of this is deliberately vague to reflect real world scenarios and encourage you to seek
answers from myself or the team for clarity on what is required.
• Please feel free to ask as many questions as you like throughout the test as needed.
• Note that it is possible that some pieces may not have any valid phone numbers.
What we are looking for?
• The correct answers for each individual chess piece.
• Your solution is maintainable by someone who has not seen it.
• Object-oriented design concepts should be used where-ever they make sense.
• It should be easy to extend the program for new requirements;
• New keyboard layouts
Different rules
• New types of chess pieces
Questions?
• How do you know what you have developed is correct?
• How can you make it more efficient?
*/

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

class ChessPhoneNumbers {
    private static final int[][] keypad = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {-1, 0, -1}
    };

    // Abstract ChessPiece class
    abstract static class ChessPiece {
        abstract List<int[]> getPossibleMoves(int x, int y);
    }

    // Knight class
    static class Knight extends ChessPiece {
        @Override
        public List<int[]> getPossibleMoves(int x, int y) {
            List<int[]> moves = new ArrayList<>();
            moves.add(new int[]{x + 2, y + 1});
            moves.add(new int[]{x + 2, y - 1});
            moves.add(new int[]{x - 2, y + 1});
            moves.add(new int[]{x - 2, y - 1});
            moves.add(new int[]{x + 1, y + 2});
            moves.add(new int[]{x + 1, y - 2});
            moves.add(new int[]{x - 1, y + 2});
            moves.add(new int[]{x - 1, y - 2});
            return filterValidMoves(moves);
        }
    }

    // King class
    static class King extends ChessPiece {
        @Override
        public List<int[]> getPossibleMoves(int x, int y) {
            List<int[]> moves = new ArrayList<>();
            moves.add(new int[]{x + 1, y});
            moves.add(new int[]{x - 1, y});
            moves.add(new int[]{x, y + 1});
            moves.add(new int[]{x, y - 1});
            moves.add(new int[]{x + 1, y + 1});
            moves.add(new int[]{x + 1, y - 1});
            moves.add(new int[]{x - 1, y + 1});
            moves.add(new int[]{x - 1, y - 1});
            return filterValidMoves(moves);
        }
    }

    static class Rook extends ChessPiece {
        @Override
        public List<int[]> getPossibleMoves(int x, int y) {
            List<int[]> moves = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                if (i != x) moves.add(new int[]{i, y});
            }
            for (int i = 0; i < 3; i++) {
                if (i != y) moves.add(new int[]{x, i});
            }
            return filterValidMoves(moves);
        }
    }

    static class Bishop extends ChessPiece {
        @Override
        public List<int[]> getPossibleMoves(int x, int y) {
            List<int[]> moves = new ArrayList<>();
            for (int i = 1; i < 4; i++) {
                if (x + i < 4 && y + i < 3) moves.add(new int[]{x + i, y + i});
                if (x + i < 4 && y - i >= 0) moves.add(new int[]{x + i, y - i});
                if (x - i >= 0 && y + i < 3) moves.add(new int[]{x - i, y + i});
                if (x - i >= 0 && y - i >= 0) moves.add(new int[]{x - i, y - i});
            }
            return filterValidMoves(moves);
        }
    }

    static class Queen extends ChessPiece {
        @Override
        public List<int[]> getPossibleMoves(int x, int y) {
            List<int[]> moves = new ArrayList<>();
            // Combining Rook and Bishop moves
            for (int i = 0; i < 4; i++) {
                if (i != x) moves.add(new int[]{i, y});
            }
            for (int i = 0; i < 3; i++) {
                if (i != y) moves.add(new int[]{x, i});
            }
            for (int i = 1; i < 4; i++) {
                if (x + i < 4 && y + i < 3) moves.add(new int[]{x + i, y + i});
                if (x + i < 4 && y - i >= 0) moves.add(new int[]{x + i, y - i});
                if (x - i >= 0 && y + i < 3) moves.add(new int[]{x - i, y + i});
                if (x - i >= 0 && y - i >= 0) moves.add(new int[]{x - i, y - i});
            }
            return filterValidMoves(moves);
        }
    }

    static class Pawn extends ChessPiece {
        @Override
        public List<int[]> getPossibleMoves(int x, int y) {
            List<int[]> moves = new ArrayList<>();
            moves.add(new int[]{x - 1, y});
            if (x >= 2) {
                moves.add(new int[]{x - 2, y});
            }
            return filterValidMoves(moves);
        }
    }

    private static List<int[]> filterValidMoves(List<int[]> moves) {
        List<int[]> validMoves = new ArrayList<>();
        for (int[] move : moves) {
            if (isValidMove(move[0], move[1])) {
                validMoves.add(new int[]{move[0], move[1]});
            }
        }
        return validMoves;
    }

    private static boolean isValidMove(int x, int y) {
        return x >= 0 && x < 4 && y >= 0 && y < 3 && keypad[x][y] != -1;
    }

    public static int generateNumbers(ChessPiece piece, int length) {
        int count = 0;
        for (int i = 0; i < keypad.length; i++) {
            for (int j = 0; j < keypad[i].length; j++) {
                if (keypad[i][j] != -1 && keypad[i][j] != 0 && keypad[i][j] != 1) {
                    count += generateNumbersHelper(piece, i, j, length - 1);
                }
            }
        }
        return count;
    }

    private static int generateNumbersHelper(ChessPiece piece, int x, int y, int remainingDigits) {
        if (remainingDigits == 0) {
            return 1;
        }
        int count = 0;
        List<int[]> moves = piece.getPossibleMoves(x, y);
        for (int[] move : moves) {
            count += generateNumbersHelper(piece, move[0], move[1], remainingDigits - 1);
        }
        return count;
    }

    public static void main(String[] args) {
        Map<String, ChessPiece> pieces = new HashMap<>();
        pieces.put("Knight", new Knight());
        pieces.put("King", new King());
        pieces.put("Bishop", new Bishop());
        pieces.put("Pawn", new Pawn());
        pieces.put("Queen", new Queen());
        pieces.put("Rook", new Rook());

        StringBuilder output = new StringBuilder();
        pieces.forEach((name, piece) -> {
            int count = 0;
            for (int i = 2; i < keypad.length; i++) {
                for (int j = 0; j < keypad[i].length; j++) {
                    if (keypad[i][j] != -1) {
                        count += generateNumbersHelper(piece, i, j, 6);
                    }
                }
            }
            output.append(name).append(": ").append(count).append("\n");
        });

        // Print to console
        System.out.print(output);

        // Write to a text file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("count_for_each_pieces.txt"))) {
            writer.write(output.toString());
            System.out.println("Output successfully written to count_for_each_pieces.txt");
        } catch (IOException e) {
            System.err.println("Error writing to count_for_each_pieces.txt: " + e.getMessage());
        }
    }
}