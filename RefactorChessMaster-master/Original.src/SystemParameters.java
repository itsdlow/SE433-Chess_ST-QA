//----------------------------------------------------------
// DeAngelo Wilson 5/21/21
//----------------------------------------------------------

//
// A class holding the global constants of the ChessMaster (const, enums, etc.)
//

public class SystemParameters
{
    private static final int BOARD_DIMENSION_X = 8;
    private static final int BOARD_DIMENSION_Y = 8;

    private static final int GUI_WIDTH = 800;
    private static final int GUI_HEIGHT = 600;

    //BLACK
    private static final int BLACK_MAIN_ROW = 0;
    private static final int BLACK_PAWN_ROW = 1;

    //WHITE
    private static final int WHITE_MAIN_ROW = 7;
    private static final int WHITE_PAWN_ROW = 6;

    //BOTH
    private static final int ROOK_COL_LEFT = 0;
    private static final int ROOK_COL_RIGHT = 7;
    private static final int KNIGHT_COL_LEFT = 1;
    private static final int KNIGHT_COL_RIGHT = 6;
    private static final int BISHOP_COL_LEFT = 2;
    private static final int BISHOP_COL_RIGHT = 5;
    private static final int KING_COL = 3;
    private static final int QUEEN_COL = 4;

    private static final int GAME_PIECE_TO_STRING_LIMIT = 6;
    public static int getGamePieceToStringLimit()
    {
        return GAME_PIECE_TO_STRING_LIMIT;
    }

    public static int getGUIWidth()
    {
        return GUI_WIDTH;
    }
    public static int getGUIHeight()
    {
        return GUI_HEIGHT;
    }


    public static int getBoardDimensionX()
    {
        return BOARD_DIMENSION_X;
    }
    public static int getBoardDimensionY()
    {
        return BOARD_DIMENSION_Y;
    }

    public static int getBlackPawnRow()
    {
        return BLACK_PAWN_ROW;
    }
    public static int getWhitePawnRow()
    {
        return WHITE_PAWN_ROW;
    }

    public static int getBlackMainRow() {return BLACK_MAIN_ROW;};
    public static int getWhiteMainRow() {return WHITE_MAIN_ROW;};

    public static int getKingColumn() {return KING_COL;};

    //returns whether given in is a main row + returns
    public static boolean isMainRow(int row)
    {
        boolean ret = false;
        if(row == WHITE_MAIN_ROW)
        {
            ret = true;
        }
        else if(row == BLACK_MAIN_ROW)
        {
            ret = true;
        }

        return ret;
    }

    public static int getColor(int row)
    {
        int ret = 0;
        if(row == WHITE_MAIN_ROW)
        {
            ret = ChessGamePiece.WHITE;
        }
        else if(row == BLACK_MAIN_ROW)
        {
            ret = ChessGamePiece.BLACK;
        }

        return ret;
    }

    public static boolean isRookColumn(int col)
    {
        boolean ret = false;
        if(col == SystemParameters.ROOK_COL_LEFT || col == SystemParameters.ROOK_COL_RIGHT)
        {
            ret = true;
        }

        return ret;
    }
    public static boolean isKnightColumn(int col)
    {
        boolean ret = false;
        if(col == SystemParameters.KNIGHT_COL_LEFT || col == SystemParameters.KNIGHT_COL_RIGHT)
        {
            ret = true;
        }

        return ret;
    }
    public static boolean isBishopColumn(int col)
    {
        boolean ret = false;
        if(col == SystemParameters.BISHOP_COL_LEFT || col == SystemParameters.BISHOP_COL_RIGHT)
        {
            ret = true;
        }

        return ret;
    }
    public static boolean isKingColumn(int col)
    {
        boolean ret = false;
        if(col == SystemParameters.KING_COL)
        {
            ret = true;
        }

        return ret;
    }
    public static boolean isQueenColumn(int col)
    {
        boolean ret = false;
        if(col == SystemParameters.QUEEN_COL)
        {
            ret = true;
        }

        return ret;
    }





    public static int getBishopNumMoves()
    {
        return BOARD_DIMENSION_X;
    }
    public static int getQueenNumMoves()
    {
        return BOARD_DIMENSION_X;
    }
    public static int getRookNumMoves()
    {
        return BOARD_DIMENSION_X;
    }




    public enum Actions
    {
        Move,
        Deselect,
        Select,
        Invalid_Move,
        Invalid_SelectEmpty,
        Invalid_SelectOpponent,

        UNKNOWN
    }

    public enum Player
    {
        ONE(1),
        TWO(2);

        private int value;

        private Player(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    public static int getTotalPlayers()
    {
        return 2;
    }
}
