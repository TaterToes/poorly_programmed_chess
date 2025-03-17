public class MoveValidator {
    KingAlgorithm king = new KingAlgorithm();
    QueenAlgorithm queen = new QueenAlgorithm();
    RookAlgorithm rook = new RookAlgorithm();
    HorseAlgorithm horse = new HorseAlgorithm();
    BishopAlgorithm bishop = new BishopAlgorithm();
    PawnAlgorithm pawn = new PawnAlgorithm();
    
    public boolean isValidMove(ChessPiece piece, ChessPiece[] white, ChessPiece[] black, int x, int y) {
        char type = piece.getType(); 
        
        return switch (type) {
            case 'R' -> rook.isValidMove(piece, white, black, x, y);
            case 'B' -> bishop.isValidMove(piece, white, black, x, y);
            case 'Q' -> queen.isValidMove(piece, white, black, x, y);
            case 'H' -> horse.isValidMove(piece, white, black, x, y);
            case 'K' -> king.isValidMove(piece, white, black, x, y);
            case 'P' -> pawn.isValidMove(piece, white, black, x, y);
            default -> false;
        }; 
}
}
