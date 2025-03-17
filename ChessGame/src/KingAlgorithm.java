public class KingAlgorithm {
    PieceSearch search = new PieceSearch();
    BoardSize board = new BoardSize();

    public boolean isValidMove(ChessPiece piece, ChessPiece[] white, ChessPiece[] black, int targetX, int targetY) {
        // Check if target is within board boundaries.
        if (targetX < 0 || targetX >= board.getColumnAmount() || targetY < 0 || targetY >= board.getRowAmount()) {
            return false;
        }
        
        int startX = piece.getPosition()[0];
        int startY = piece.getPosition()[1];
        
        int dx = Math.abs(targetX - startX);
        int dy = Math.abs(targetY - startY);
        
        // Normal king move: one square in any direction (but not staying in place)
        if (dx <= 1 && dy <= 1 && (dx != 0 || dy != 0)) {
            ChessPiece targetPiece = search.findPiece(white, black, targetX, targetY);
            return !(targetPiece != null && targetPiece.getSide() == piece.getSide());
        }
        
        if (dx == 2 && dy == 0) {
            // The king must be castle vulnerable.
            if (!piece.getCastleState()) {
                System.out.println("King is not castle vulnerable.");
                return false;
            }
            
            // Determine if it's kingside or queenside castling.
            boolean isKingside = (targetX > startX);
            int rookX = isKingside ? board.getColumnAmount() : 1;
            
            // Retrieve the rook at the appropriate side.
            ChessPiece rook = search.findPiece(white, black, rookX, startY);
            if (rook == null) {
                return false;
            }
            if (rook.getSide() != piece.getSide()) {
                return false;
            }
            if (!rook.getCastleState()) {
                return false;
            }
            
            // Check that all squares between the king and rook are empty.
            if (isKingside) {
                for (int x = startX + 1; x <= startX + 2; x++) {
                    if (search.findPiece(white, black, x, startY) != null) {
                        return false;
                    }
                }
            } else {
                for (int x = startX - 1; x > rookX; x--) {
                    if (search.findPiece(white, black, x, startY) != null) {
                        return false;
                    }
                }
            }
            
            return true;
        }
        
        // If it's neither a standard king move nor a valid castling move, return false.
        return false;
    }  
}
