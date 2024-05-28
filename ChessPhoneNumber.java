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
            if (x == 3) {
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
        ChessPiece knight = new Knight();
        ChessPiece king = new King();
        ChessPiece bishop = new Bishop();
        ChessPiece pawn = new Pawn();
        ChessPiece queen = new Queen();
        ChessPiece rook = new Rook();

        Map<String, ChessPiece> pieces = new HashMap<>();
        pieces.put("Knight", knight);
        pieces.put("King", king);
        pieces.put("Bishop", bishop);
        pieces.put("Pawn", pawn);
        pieces.put("Queen", queen);
        pieces.put("Rook", rook);

        StringBuilder output = new StringBuilder();
        pieces.forEach((name, piece) -> {
            int count = generateNumbers(piece, 7);
            output.append(name).append(": ").append(count).append("\n");
        });

        // Print to console
        System.out.print(output);

        // Write to a text file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("count_for_each_pieces.txt"))) {
            writer.write(output.toString());
            System.out.println("Output successfully written to count_for_each_pieces.txt");
        } catch (IOException e) {
            System.err.println("Error writing to output.txt: " + e.getMessage());
        }
    }
}