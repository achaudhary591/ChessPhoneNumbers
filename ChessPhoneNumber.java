/*
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
•
Seven digits in length
Cannot start with a 0 or 1
Cannot contain a * or #
 */

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@SuppressWarnings("unchecked")
public class ChessPhoneNumber {

    enum ChessPiece {
        Pawn, Knight, Bishop, Rook, Queen, King
    }

    private static final Map<ChessPiece, List<Integer>[]> chessPiecesMoveMap = new HashMap<>();

    static {
        chessPiecesMoveMap.put(ChessPiece.Pawn,
                new List[] { Arrays.asList(), Arrays.asList(4, 7), Arrays.asList(5, 8), Arrays.asList(6, 9),
                        Arrays.asList(7), Arrays.asList(8), Arrays.asList(9), Arrays.asList(), Arrays.asList(0),
                        Arrays.asList() });
        chessPiecesMoveMap.put(ChessPiece.Knight,
                new List[] { Arrays.asList(4, 6), Arrays.asList(6, 8), Arrays.asList(7, 9), Arrays.asList(4, 8),
                        Arrays.asList(0, 3, 9), Arrays.asList(), Arrays.asList(0, 1, 7), Arrays.asList(2, 6),
                        Arrays.asList(1, 3), Arrays.asList(2, 4) });
        chessPiecesMoveMap.put(ChessPiece.Bishop,
                new List[] { Arrays.asList(7, 9), Arrays.asList(5, 9), Arrays.asList(4, 6), Arrays.asList(5, 7),
                        Arrays.asList(2, 8), Arrays.asList(1, 3, 7, 9), Arrays.asList(2, 8), Arrays.asList(0, 3, 5),
                        Arrays.asList(4, 6), Arrays.asList(0, 1, 5) });
        chessPiecesMoveMap.put(ChessPiece.Rook,
                new List[] { Arrays.asList(2, 5, 8), Arrays.asList(2, 3, 4, 7), Arrays.asList(0, 1, 3, 5, 8),
                        Arrays.asList(1, 2, 6, 9), Arrays.asList(1, 5, 6, 7), Arrays.asList(0, 2, 4, 6, 8),
                        Arrays.asList(3, 4, 5, 9), Arrays.asList(1, 4, 8, 9), Arrays.asList(0, 2, 5, 7, 9),
                        Arrays.asList(3, 6, 7, 8) });
        chessPiecesMoveMap.put(ChessPiece.Queen,
                new List[] { Arrays.asList(2, 5, 7, 8, 9), Arrays.asList(2, 3, 4, 5, 7, 9),
                        Arrays.asList(0, 1, 3, 4, 5, 6, 8), Arrays.asList(1, 2, 5, 6, 7, 9),
                        Arrays.asList(1, 2, 5, 6, 7, 8), Arrays.asList(0, 1, 2, 3, 4, 6, 7, 8, 9),
                        Arrays.asList(2, 3, 4, 5, 8, 9), Arrays.asList(0, 1, 3, 4, 5, 8, 9),
                        Arrays.asList(0, 2, 4, 5, 6, 7, 9), Arrays.asList(0, 1, 3, 5, 6, 7, 8) });
        chessPiecesMoveMap.put(ChessPiece.King,
                new List[] { Arrays.asList(7, 8, 9), Arrays.asList(2, 4, 5), Arrays.asList(1, 3, 4, 5, 6),
                        Arrays.asList(2, 5, 6), Arrays.asList(1, 2, 5, 7, 8), Arrays.asList(1, 2, 3, 4, 6, 7, 8, 9),
                        Arrays.asList(2, 3, 5, 8, 9), Arrays.asList(0, 4, 5, 8), Arrays.asList(0, 4, 5, 6, 7, 9),
                        Arrays.asList(0, 5, 6, 8) });
    }

    public static BigInteger[] countPhoneNumbers(List<Integer>[] pieceMOves, int numberLength) {
        if (numberLength < 1) {
            throw new IllegalArgumentException("Number length must be at least 1");
        }

        BigInteger[][] result = new BigInteger[2][10];
        int currentIndex = 0;
        int previousIndex = 1;

        // Initialize with ZERO for all digits
        Arrays.fill(result[currentIndex], BigInteger.ZERO);

        // Set valid starting positions (digits 2-9) to ONE
        for (int i = 2; i <= 9; i++) {
            result[currentIndex][i] = BigInteger.ONE;
        }

        for (int iteration = 1; iteration < numberLength; iteration++) {
            currentIndex = iteration % 2;
            previousIndex = 1 - currentIndex;
            Arrays.fill(result[currentIndex], BigInteger.ZERO);
            for (int p = 2; p <= 9; p++) { // Start from 2, since 0 and 1 are not valid starting digits
                for (Integer next : pieceMOves[p]) {
                    if (next >= 2 && next <= 9) { // Ensure next is a valid digit
                        result[currentIndex][next] = result[currentIndex][next].add(result[previousIndex][p]);
                    }
                }
            }
        }

        return result[previousIndex];
    }

    public static void main(String[] args) {

        HashMap<String, ChessPiece> pieces = new HashMap<>();

        pieces.put("Knight", ChessPiece.Knight);
        pieces.put("King", ChessPiece.King);
        pieces.put("Bishop", ChessPiece.Bishop);
        pieces.put("Pawn", ChessPiece.Pawn);
        pieces.put("Queen", ChessPiece.Queen);
        pieces.put("Rook", ChessPiece.Rook);

        StringBuilder output = new StringBuilder();
        
        int phoneNumberDigits = 7;

        output.append("Number of valid ").append(phoneNumberDigits).append("-digit phone numbers").append("\n");

        // traverse pieces map
        for (Map.Entry<String, ChessPiece> piece : pieces.entrySet()) {
            BigInteger[] results = countPhoneNumbers(chessPiecesMoveMap.get(piece.getValue()), phoneNumberDigits);

            BigInteger total = BigInteger.ZERO;

            for (int i = 0; i < results.length; i++) {
                total = total.add(results[i]);
            }
            output.append(piece.getKey()).append(": ").append(total).append("\n");
        }

        System.out.println(output);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("count_for_each_pieces.txt"))) {
            writer.write(output.toString());
            System.out.println("Output successfully written to count_for_each_pieces.txt");
        } catch (IOException e) {
            System.err.println("Error writing to count_for_each_pieces.txt: " + e.getMessage());
        }
    }
}
