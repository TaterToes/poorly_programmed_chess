public class DiagonalAlgorithm {
    PieceSearch search = new PieceSearch();
    BoardSize board = new BoardSize();

    public boolean isValidMove(ChessPiece piece, ChessPiece[] white, ChessPiece[] black, int targetX, int targetY) {
        // Check if target is within board
        if (targetX < 0 || targetX > board.getColumnAmount() || targetY < 0 || targetY > board.getRowAmount()) {
            return false;
        }
        
        int startX = piece.getPosition()[0];
        int startY = piece.getPosition()[1];
        
        // Check for diagonal move: the absolute differences must be equal
        if (Math.abs(targetX - startX) != Math.abs(targetY - startY)) {
            return false;
        }

        int stepX = Integer.compare(targetX, startX);
        int stepY = Integer.compare(targetY, startY);
        
        int x = startX + stepX;
        int y = startY + stepY;
        
        // Check every square along the diagonal path before the target
        while (x != targetX && y != targetY) {
            if (x < 0 || x >= board.getColumnAmount() || y < 0 || y >= board.getRowAmount()) {
                return false;
            }
            if (search.findPiece(white, black, x, y) != null) {
                return false; // Path is blocked
            }
            x += stepX;
            y += stepY;
        }
        
        ChessPiece targetPiece = search.findPiece(white, black, x, y);
        if (targetPiece != null) {
            if (targetPiece.getSide() == piece.getSide()) {
                return false;
            }
        }

        return true;
    }
}
