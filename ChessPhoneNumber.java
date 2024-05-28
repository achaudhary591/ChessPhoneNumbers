import java.util.ArrayList;
import java.util.List;

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
        @Override`
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
            // Horizontal moves
            for (int i = 0; i < 8; i++) {
                moves.add(new int[]{x, i});
                moves.add(new int[]{i, y});
            }
            return filterValidMoves(moves);
        }
    }

    static class Bishop extends ChessPiece {
        @Override
        public List<int[]> getPossibleMoves(int x, int y) {
            List<int[]> moves = new ArrayList<>();
            for (int i = 1; i < 8; i++) {
                moves.add(new int[]{x + i, y + i});
                moves.add(new int[]{x - i, y + i});
                moves.add(new int[]{x + i, y - i});
                moves.add(new int[]{x - i, y - i});
            }
            return filterValidMoves(moves);
        }
    }

    static class Queen extends ChessPiece {
        @Override
        public List<int[]> getPossibleMoves(int x, int y) {
            List<int[]> moves = new ArrayList<>();
            // Combining Rook and Bishop moves
            for (int i = 0; i < 8; i++) {
                moves.add(new int[]{x, i});
                moves.add(new int[]{i, y});
                moves.add(new int[]{x + i, y + i});
                moves.add(new int[]{x - i, y + i});
                moves.add(new int[]{x + i, y - i});
                moves.add(new int[]{x - i, y - i});
            }
            return filterValidMoves(moves);
        }
    }

    static class Pawn extends ChessPiece {
        @Override
        public List<int[]> getPossibleMoves(int x, int y) {
            List<int[]> moves = new ArrayList<>();
            // Assuming pawn moves forward only (not capturing other pieces)
            moves.add(new int[]{x - 1, y});
            // Optionally, add initial two-step move for pawns in the second row.
            if (x == 2) {
                moves.add(new int[]{x - 2, y});
            }
            return filterValidMoves(moves);
        }
    }


    // Helper method to filter valid moves
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

    // Method to generate phone numbers
    public static int generateNumbers(ChessPiece piece, int length) {
        int count = 0;
        for (int i = 0; i < keypad.length; i++) {
            for (int j = 0; j < keypad[i].length; j++) {
                if (keypad[i][j] != -1) {
                    count += generateNumbersHelper(piece, i, j, length - 1);
                }
            }
        }
        return count;
    }

    // Recursive helper method
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

    // Main method to run the program
    public static void main(String[] args) {
        ChessPiece knight = new Knight();
        ChessPiece king = new King();
        ChessPiece bishop = new Bishop();
        ChessPiece pawn = new Pawn();
        ChessPiece queen = new Queen();
        ChessPiece rook = new Rook();

        System.out.println("Phone numbers generated by Knight: " + generateNumbers(knight, 7));
        System.out.println("Phone numbers generated by King: " + generateNumbers(king, 7));
        System.out.println("Phone numbers generated by Queen: " + generateNumbers(queen, 7));
        System.out.println("Phone numbers generated by Bishop: " + generateNumbers(bishop, 7));
        System.out.println("Phone numbers generated by Pawn: " + generateNumbers(pawn, 7));
        System.out.println("Phone numbers generated by Rook: " + generateNumbers(rook, 7));
    }
}

