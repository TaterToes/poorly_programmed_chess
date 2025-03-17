public class BishopAlgorithm {
    DiagonalAlgorithm algorithm = new DiagonalAlgorithm();

    public boolean isValidMove(ChessPiece piece, ChessPiece[] white, ChessPiece[] black, int x, int y) {
        return algorithm.isValidMove(piece, white, black, x, y);
    }
}
