public class PawnAlgorithm {
    // Assume these are declared elsewhere in your class:
    PieceSearch search = new PieceSearch();
    BoardSize board = new BoardSize();

    public boolean isValidMove(ChessPiece piece, ChessPiece[] white, ChessPiece[] black, int targetX, int targetY) {
        // Check board boundaries (assuming 0-based indexing)
        if (targetX < 1 || targetX > board.getColumnAmount() || targetY < 1 || targetY > board.getRowAmount()) {
            System.out.println("Move invalid: target position (" + targetX + ", " + targetY + ") is out of board boundaries.");
            return false;
        }

        int startX = piece.getPosition()[0];
        int startY = piece.getPosition()[1];

        // Determine direction: white moves upward (+1) and black downward (-1)
        int direction = (piece.getSide() == 'W') ? 1 : -1;
        int dx = targetX - startX;
        int dy = targetY - startY;

        // 1. Single square forward move:
        if (dx == 0 && dy == direction) {
            if (search.findPiece(white, black, targetX, targetY) == null) {
                System.out.println("Valid move: single square forward move.");
                return true;
            } else {
                System.out.println("Move invalid: target square (" + targetX + ", " + targetY + ") is not empty for a single square move.");
            }
        }

        // 2. Two-square forward move (only on pawn's first move):
        if (dx == 0 && dy == 2 * direction) {
            if (!piece.getMovedOnce() && !piece.getMovedTwice()) {
                int intermediateY = startY + direction;
                if (search.findPiece(white, black, targetX, intermediateY) == null &&
                        search.findPiece(white, black, targetX, targetY) == null) {
                    System.out.println("Valid move: two-square forward move.");
                    // Mark pawn as en passant vulnerable for one move:
                    piece.setEnPassantVulnerable(true);
                    return true;
                } else {
                    if (search.findPiece(white, black, targetX, intermediateY) != null) {
                        System.out.println("Move invalid: intermediate square (" + targetX + ", " + intermediateY + ") is not empty.");
                    }
                    if (search.findPiece(white, black, targetX, targetY) != null) {
                        System.out.println("Move invalid: target square (" + targetX + ", " + targetY + ") is not empty.");
                    }
                }
            } else {
                System.out.println("Move invalid: pawn has already moved more then once," + "), cannot move two squares.");
            }
        }

        // 3. Diagonal move for capture:
        if (Math.abs(dx) == 1 && dy == direction) {
            ChessPiece targetPiece = search.findPiece(white, black, targetX, targetY);
            // Normal diagonal capture: enemy piece on the target square.
            if (targetPiece != null && targetPiece.getSide() != piece.getSide()) {
                System.out.println("Valid move: diagonal capture of enemy piece at (" + targetX + ", " + targetY + ").");
                return true;
            }
            // En passant capture:
            if (targetPiece == null) {
                // For en passant, check the adjacent square on the pawn’s starting rank.
                ChessPiece adjacentPawn = search.findPiece(white, black, targetX, startY);
                if (adjacentPawn != null && adjacentPawn.getSide() != piece.getSide() 
                        && adjacentPawn.getType() == 'P') {
                    if (adjacentPawn.isEnPassantVulnerable()) {
                        System.out.println("Valid move: en passant capture on enemy pawn at (" + targetX + ", " + startY + ").");
                        return true;
                    } else {
                        System.out.println("Move invalid: en passant failed, adjacent pawn at (" + targetX + ", " + startY + ") is not en passant vulnerable.");
                    }
                } else {
                    if (adjacentPawn == null) {
                        System.out.println("Move invalid: en passant failed, no enemy pawn adjacent at (" + targetX + ", " + startY + ").");
                    } else {
                        System.out.println("Move invalid: en passant failed, adjacent pawn at (" + targetX + ", " + startY + ") is not a pawn or is on the same side.");
                    }
                }
            }
        }

        System.out.println("Move invalid: none of the valid move conditions were met for move from (" + startX + ", " + startY + ") to (" + targetX + ", " + targetY + ").");
        return false;
    }
}