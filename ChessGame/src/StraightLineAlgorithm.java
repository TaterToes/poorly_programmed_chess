public class StraightLineAlgorithm {
    PieceSearch search = new PieceSearch();
    BoardSize board = new BoardSize();

    public boolean isValidMove(ChessPiece piece, ChessPiece[] white, ChessPiece[] black, int targetX, int targetY) {
        if (targetX < 0 || targetX > board.getColumnAmount() || targetY < 0 || targetY > board.getRowAmount()) {
            return false;
        }

        int startX = piece.getPosition()[0];
        int startY = piece.getPosition()[1];

        if (startX != targetX && startY != targetY) {
            return false;
        }

        int stepX = Integer.compare(targetX, startX);
        int stepY = Integer.compare(targetY, startY);

        int x = startX + stepX;
        int y = startY + stepY;

        while (x != targetX || y != targetY) {
            if (search.findPiece(white, black, x, y) != null) {
                return false;
            }

            if (x < 1 || x > board.getColumnAmount() || y < 1 || y > board.getRowAmount()) {
                return false;
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