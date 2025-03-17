public final class Board {
    ChessPiece[] dummyWhitePiece;
    ChessPiece[] dummyBlackPiece;

    BoardSize boardSize = new BoardSize();

    int row = boardSize.getRowAmount();
    int column = boardSize.getColumnAmount();

    //Rook, Bishop, Horse, King, Queen, Pawn
    char[] PieceType = {'R', 'H', 'B', 'Q', 'K', 'P'};
    
    // Row x Columm Board
    // Sets up the Board

    // We can play on this board, and after the game ends, board self destructs.
    public Board() {
        ChessPiece[] white = new ChessPiece[column * 2];
        ChessPiece[] black = new ChessPiece[column * 2];

        // Creating Pawns
        for (int i = column; i < white.length; i++) {
            white[i] = new ChessPiece(new int[]{i - column + 1 ,2}, 'W', PieceType[5]);
            black[i] = new ChessPiece(new int[]{i - column + 1 , row - 1}, 'B', PieceType[5]);
        }

        // Creating Rooks, Bishops, And Horses
        for (int i = 0; i < (column / 2) - 1; i++) {
            int pieceTypeNum = i%3;
            white[i] = new ChessPiece(new int[]{i + 1 ,1}, 'W', PieceType[pieceTypeNum]);
            white[column - i - 1] = new ChessPiece(new int[]{column - i ,1}, 'W', PieceType[pieceTypeNum]);

            black[i] = new ChessPiece(new int[]{i + 1 ,row}, 'B', PieceType[pieceTypeNum]);
            black[column - i - 1] = new ChessPiece(new int[]{column - i ,row}, 'B', PieceType[pieceTypeNum]);

            if (i == 0) {
                white[i].setCastleState(true);
                white[column - i - 1].setCastleState(true);

                black[i].setCastleState(true);
                black[column - i - 1].setCastleState(true);
            }
        }

        white[(column/2) - 1] = new ChessPiece(new int[]{column/2, 1}, 'W', PieceType[3]);
        white[(column/2)] = new ChessPiece(new int[]{(column/2) + 1, 1}, 'W', PieceType[4]);

        white[(column/2)].setCastleState(true);

        black[(column/2) - 1] = new ChessPiece(new int[]{column/2, row}, 'B', PieceType[3]);
        black[(column/2)] = new ChessPiece(new int[]{(column/2) + 1, row}, 'B', PieceType[4]);

        black[(column/2)].setCastleState(true);

        dummyWhitePiece = white;
        dummyBlackPiece = black;
    }

    public ChessPiece[] getWhitePieces() {
        return dummyWhitePiece;
    }

    public ChessPiece[] getBlackPieces() {
        return dummyBlackPiece;
    }
}
