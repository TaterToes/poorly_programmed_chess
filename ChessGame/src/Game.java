import java.util.Scanner;

// The dumbest way to program a chess board game.
// Why give the board memory? Instead, we give the pieces memory, and now we have to ask every individual piece where they are?
// This is simply a challenge to myself, where the pieces don't know where the other pieces are. We will have to figure out how to
// make the pieces communicate.
public class Game {
    private final Scanner scanner = new Scanner(System.in);
    BoardSize boardSize = new BoardSize();

    ChessPiece[] white = new ChessPiece[boardSize.getColumnAmount() * 2];
    ChessPiece[] black = new ChessPiece[boardSize.getColumnAmount() * 2];

    public final String getPlayerMove() {
        return scanner.nextLine();
    }

    public boolean gameOver = false;

    public void isGameOver(ChessPiece[] white, ChessPiece[] black) {
        for (ChessPiece piece : white) {
            if (piece.getType() == 'K' && !piece.getPieceState()) {
                gameOver = true;
            }
        }

        for (ChessPiece piece : black) {
            if (piece.getType() == 'K' && !piece.getPieceState()) {
                gameOver = true;
            }
        }
    }

    TextToMoveConverter converter = new TextToMoveConverter();
    PieceSearch pieceSearch = new PieceSearch();
    MoveValidator moveValidator = new MoveValidator();

    public void printChessBoard(ChessPiece[] white, ChessPiece[] black) {
        for (int row = boardSize.getRowAmount(); row > 0; row--) {
            for (int col = 1; col <= boardSize.getColumnAmount(); col++) {
                ChessPiece piece = pieceSearch.findPiece(white, black, col, row);
                if (piece != null) {
                    System.out.print(piece.getSide() + "" + piece.getType() + " ");
                } else {
                    System.out.print("-- ");
                }
            }
            System.out.println();
        }
    }

    public void playGame() {
        boolean isWhiteTurn = true;
        Board board = new Board();

        String input = new String();

        white = board.getWhitePieces();
        black = board.getBlackPieces();

        ChessPiece dummyGamePiece = new ChessPiece(new int[] { -1, -1 }, 'A', 'A');
        int[] dummyPosition = new int[] { -1, -1 };

        boolean validPiece = true;
        boolean validMove = true;

        while (!gameOver) {
            if (validMove && validPiece) {
                printChessBoard(white, black);
            }

            if (isWhiteTurn && validMove && validPiece) {
                System.out.println("White's Turn! Pick your piece at the square!:");
            } else if (validMove && validPiece) {
                System.out.println("Black's Turn! Pick your piece at the square!:");
            }
            if (validMove) {
                String pickPiece = getPlayerMove();
                int[] row_columm_pieceValue = new int[] { converter.getColumnValue(pickPiece),
                        converter.getRowValue(pickPiece) };

                input = pickPiece;

                if (row_columm_pieceValue[0] == -1 || row_columm_pieceValue[1] == -1) {
                    validPiece = false;
                } else {

                    ChessPiece piece = pieceSearch.findPiece(white, black, row_columm_pieceValue[0],
                            row_columm_pieceValue[1]);
                    if (piece != null) {
                        if (piece.getPosition()[0] == -1) {
                            validPiece = false;
                        } else {
                            if (isWhiteTurn && piece.getSide() != 'W') {
                                validPiece = false;
                            } else if (!isWhiteTurn && piece.getSide() != 'B') {
                                validPiece = false;
                            } else {
                                validPiece = true;
                                dummyGamePiece = piece;
                            }
                        }
                    } else {
                        validPiece = false;
                    }

                }
            }
            if (validPiece) {
                if (isWhiteTurn && validMove) {
                    System.out.println("White! Pick your move now!");
                } else if (validMove) {
                    System.out.println("Black! Pick your move now!");
                }

                String move = getPlayerMove();
                int[] column_row_moveValue = new int[] { converter.getColumnValue(move), converter.getRowValue(move) };

                input = move;

                if (column_row_moveValue[0] == -1 || column_row_moveValue[1] == -1) {
                    validMove = false;
                } else {
                    if (!moveValidator.isValidMove(dummyGamePiece, white, black, column_row_moveValue[0],
                            column_row_moveValue[1])) {
                        validMove = false;
                    } else {
                        validMove = true;
                        dummyPosition = column_row_moveValue;
                    }
                }
            }

            if (input.toLowerCase().equals("break")) {
                validMove = true;
                validPiece = true;
                continue;
            }

            if (validMove && validPiece) {
                if (isWhiteTurn) {
                    for (ChessPiece piece : white) {
                        if (piece.getPosition()[0] == dummyGamePiece.getPosition()[0]
                                && piece.getPosition()[1] == dummyGamePiece.getPosition()[1]) {
                            if (!piece.getMovedOnce() && !piece.getMovedTwice()) {
                                piece.setMovedOnce(true);
                            } else if (piece.getMovedTwice()) {
                                piece.setMovedOnce(false);
                                piece.setMovedTwice(true);
                            }

                            int originalX = piece.getPosition()[0];
                            int originalY = piece.getPosition()[1];
                            int targetX = dummyPosition[0];
                            int targetY = dummyPosition[1];

                            boolean enPassantOccurred = false;


                            if (piece.getType() == 'P') {
                                int dx = targetX - originalX;
                                int dy = targetY - originalY;

                                if (targetY == boardSize.getRowAmount()) {
                                    piece.setType('Q');
                                }

                                if (Math.abs(dx) == 1 && dy == 1) {
                                    boolean destinationOccupied = false;
                                    for (ChessPiece enemy : black) {
                                        if (enemy.getPosition()[0] == targetX && enemy.getPosition()[1] == targetY) {
                                            destinationOccupied = true;
                                            break;
                                        }
                                    }
                                    if (!destinationOccupied) {
                                        for (ChessPiece enemy : black) {
                                            if (enemy.getType() == 'P' &&
                                                    enemy.getPosition()[0] == targetX &&
                                                    enemy.getPosition()[1] == originalY &&
                                                    enemy.isEnPassantVulnerable()) {
                                                enemy.setPieceState(); // capture the pawn en passant
                                                enPassantOccurred = true;
                                                break;
                                            }
                                        }
                                    }
                                }

                                if (piece.getMovedOnce() && dy == 2 && dx == 0) {
                                    piece.setEnPassantVulnerable(true);
                                }
                            }

        
                            if (piece.getType() == 'K' && Math.abs(targetX - originalX) == 2 && originalY == targetY && piece.getMovedOnce()) {
                                int rookX = (targetX > originalX) ? boardSize.getColumnAmount() - 1 : 0;
                                int newRookX = (targetX > originalX) ? targetX - 1 : targetX + 1;

                                for (ChessPiece rook : white) {
                                    if (rook.getType() == 'R' && rook.getPosition()[0] == rookX
                                            && rook.getPosition()[1] == originalY && !rook.getMovedOnce()) {
                                        rook.setPosition(newRookX, originalY);
                                        break;
                                    }
                                }
                            }

                            piece.setPosition(targetX, targetY);

                            if (!enPassantOccurred) {
                                for (ChessPiece enemy : black) {
                                    if (enemy.getPosition()[0] == targetX && enemy.getPosition()[1] == targetY) {
                                        enemy.setPieceState();
                                        break;
                                    }
                                }
                            }

                            break;
                        }
                    }
                } else {
                    for (ChessPiece piece : black) {
                        if (piece.getPosition()[0] == dummyGamePiece.getPosition()[0]
                                && piece.getPosition()[1] == dummyGamePiece.getPosition()[1]) {
                            if (!piece.getMovedOnce() && !piece.getMovedTwice()) {
                                piece.setMovedOnce(true);
                            } else if (piece.getMovedTwice()) {
                                piece.setMovedOnce(false);
                                piece.setMovedTwice(true);
                            }
                    
                            int originalX = piece.getPosition()[0];
                            int originalY = piece.getPosition()[1];
                            int targetX = dummyPosition[0];
                            int targetY = dummyPosition[1];
                    
                            boolean enPassantOccurred = false;
                    
                            if (piece.getType() == 'P') {
                                int dx = targetX - originalX;
                                int dy = targetY - originalY;  // For black, forward movement means dy is negative
                    
                                // Promotion: when a black pawn reaches row 1, it gets promoted.
                                if (targetY == 1) {
                                    piece.setType('Q');
                                }
                    
                                // Capture move: for black, moving diagonally forward (dy == -1)
                                if (Math.abs(dx) == 1 && dy == -1) {
                                    boolean destinationOccupied = false;
                                    for (ChessPiece enemy : white) {
                                        if (enemy.getPosition()[0] == targetX && enemy.getPosition()[1] == targetY) {
                                            destinationOccupied = true;
                                            break;
                                        }
                                    }
                                    if (!destinationOccupied) {
                                        for (ChessPiece enemy : white) {
                                            if (enemy.getType() == 'P' &&
                                                enemy.getPosition()[0] == targetX &&
                                                enemy.getPosition()[1] == originalY &&
                                                enemy.isEnPassantVulnerable()) {
                                                enemy.setPieceState(); // capture the pawn en passant
                                                enPassantOccurred = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                    
                                if (piece.getMovedOnce() && dy == -2 && dx == 0) {
                                    piece.setEnPassantVulnerable(true);
                                }
                            }
                    
                            if (piece.getType() == 'K' && Math.abs(targetX - originalX) == 2 && originalY == targetY && piece.getMovedOnce()) {
                                int rookX = (targetX > originalX) ? boardSize.getColumnAmount() : 1;
                                int newRookX = (targetX > originalX) ? targetX - 1 : targetX + 1;
                    
                                for (ChessPiece rook : black) {
                                    if (rook.getType() == 'R' && rook.getPosition()[0] == rookX
                                            && rook.getPosition()[1] == originalY && !rook.getMovedOnce()) {
                                        rook.setPosition(newRookX, originalY);
                                        break;
                                    }
                                }
                            }
                    
                            piece.setPosition(targetX, targetY);
                    
                            if (!enPassantOccurred) {
                                for (ChessPiece enemy : white) {
                                    if (enemy.getPosition()[0] == targetX && enemy.getPosition()[1] == targetY) {
                                        enemy.setPieceState();
                                        break;
                                    }
                                }
                            }
                    
                            break;
                        }
                    }
                    
                }

                isGameOver(white, black);

                if (gameOver) {
                    if (isWhiteTurn) {
                        System.out.println("Game Ended! White Wins!");
                    } else {
                        System.out.println("Game Ended! Black Wins!");
                    }
                    break;
                } 

                isWhiteTurn = !isWhiteTurn;
            } else if (validMove) {
                if (isWhiteTurn) {
                    System.out.println("White, that piece doesn't exist, isn't yours, or is dead, pick another piece.");
                } else {
                    System.out.println("Black, that piece doesn't exist, isn't yours, or is dead, pick another piece.");
                }
            } else {
                if (isWhiteTurn) {
                    System.out.println("White, that move is invalid, pick another move.");
                } else {
                    System.out.println("Black, that move is invalid, pick another move.");
                }
            }
        }
    }
}
