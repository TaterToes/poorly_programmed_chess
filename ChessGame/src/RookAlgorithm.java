public class RookAlgorithm {
    StraightLineAlgorithm algorithm = new StraightLineAlgorithm();
    
    public boolean isValidMove(ChessPiece piece, ChessPiece[] white, ChessPiece[] black, int x, int y) {
        return algorithm.isValidMove(piece, white, black, x, y);
    }
}
