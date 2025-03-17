public class PieceSearch {
    public ChessPiece findPiece(ChessPiece[] white, ChessPiece[] black, int x, int y) {
        for (ChessPiece piece : white) {
            if(piece != null && piece.getPosition()[0] == x && piece.getPosition()[1] == y && piece.getPieceState()) {
                return piece;
            }
        }
        for (ChessPiece piece : black) {
            if(piece != null && piece.getPosition()[0] == x && piece.getPosition()[1] == y && piece.getPieceState()) {
                return piece;
            }
        }
        return null;
    }
}
