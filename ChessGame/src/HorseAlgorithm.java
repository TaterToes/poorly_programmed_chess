public class HorseAlgorithm {
    PieceSearch search = new PieceSearch();
    BoardSize board = new BoardSize();

    public boolean isValidMove(ChessPiece piece, ChessPiece[] white, ChessPiece[] black, int targetX, int targetY) {
        // Check if the target position is within the board
        if (targetX < 0 || targetX > board.getColumnAmount() || targetY < 0 || targetY > board.getRowAmount()) {
            return false;
        }
        
        int startX = piece.getPosition()[0];
        int startY = piece.getPosition()[1];
        
        // Calculate the differences in coordinates
        int dx = Math.abs(targetX - startX);
        int dy = Math.abs(targetY - startY);
        
        if (!((dx == 2 && dy == 1) || (dx == 1 && dy == 2))) {
            return false;
        }
        
        ChessPiece targetPiece = search.findPiece(white, black, targetX, targetY);
        return !(targetPiece != null && targetPiece.getSide() == piece.getSide());
    }
}
