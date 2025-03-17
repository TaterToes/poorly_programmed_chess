public class ChessPiece {
    private int[] position = new int[2];
    private char type;
    private final char side;

    private boolean enPassantVulnerable = false;
    private boolean CastleVulnerable = false;

    private boolean moved_once = false;
    private boolean moved_twice = false;

    public ChessPiece(int[] position, char side, char type) {
        this.position = position;

        this.side = side;
        this.type = type;
    }

    // Alive or Dead
    private boolean pieceState = true;

    public void setPosition(int x, int y) {
        position[0] = x;
        position[1] = y;
    }

    public int[] getPosition() {
        return position;
    }

    public int[] getStoredPosition() {
        return position;
    }

    public boolean isEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public void setEnPassantVulnerable(boolean vulnerable) {
        enPassantVulnerable = vulnerable;
    }

    public void setPieceState() {
        pieceState = false;

        position[0] = -1;
        position[1] = -1;
    }

    public boolean getPieceState() {
        return pieceState;
    }

    public char getType() {
        return type;
    }

    public void setType(char a) {
        type = a;
    }

    public boolean getCastleState() {
        return CastleVulnerable;
    }

    public void setCastleState(boolean a) {
        CastleVulnerable = a;
    }

    public boolean getMovedOnce() {
        return moved_once;
    }

    public void setMovedOnce(boolean a) {
        moved_once = a;
    }

    public boolean getMovedTwice() {
        return moved_twice;
    }

    public void setMovedTwice(boolean a) {
        moved_twice = a;
    }
    

    public char getSide() {
        return side;
    }
}
