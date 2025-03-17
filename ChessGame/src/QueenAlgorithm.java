public class QueenAlgorithm {
    DiagonalAlgorithm diagonalAlgorithm = new DiagonalAlgorithm();
    StraightLineAlgorithm lineAlgorithm = new StraightLineAlgorithm();

    public boolean isValidMove(ChessPiece piece, ChessPiece[] white, ChessPiece[] black, int x, int y) {
        return diagonalAlgorithm.isValidMove(piece, white, black, x, y) || lineAlgorithm.isValidMove(piece, white, black, x, y);
    }
}
