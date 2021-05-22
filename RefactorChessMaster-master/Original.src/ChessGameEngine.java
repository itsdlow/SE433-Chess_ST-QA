import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.MouseEvent;
// -------------------------------------------------------------------------
/**
 * This is the backend behind the Chess game. Handles the turn-based aspects of
 * the game, click events, and determines win/lose conditions.
 *
 * @author Ben Katz (bakatz)
 * @author Myles David II (davidmm2)
 * @author Danielle Bushrow (dbushrow)
 * @version 2010.11.17
 */
public class ChessGameEngine{
    private ChessGamePiece          currentPiece;
    private boolean                 firstClick;
    private SystemParameters.Player currentPlayer;
    private final ChessGameBoard    board;
    private King                    king1;
    private King                    king2;

    //testable addition
//    private JOptionPane          jop;
//    private JDialog              currentDialog;
    private boolean verbose;

    public enum GameCondition
    {
        STALEMATE(-1),
        IN_PLAY(0),
        PLAYER1_LOSS(1),
        PLAYER2_LOSS(2);

        private int value;

        private GameCondition(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    // ----------------------------------------------------------
    /**
     * Create a new ChessGameEngine object. Accepts a fully-created
     * ChessGameBoard. (i.e. all components rendered)
     *
     * @param board
     *            the reference ChessGameBoard
     */
    public ChessGameEngine( ChessGameBoard board )
    {
        this.board = board;
        this.initialize();
    }
    // ----------------------------------------------------------
    /**
     * Resets the game to its original state.
     */
    public void reset()
    {
        this.initialize();

        //NOTE:: Graveyard not cleared on NewGame--BUG FOUND??
        //TODO:: repaint(); (found in ChessGameBoard::resetBoard )

    }

    private void initialize()
    {
        firstClick = true;
        currentPlayer = SystemParameters.Player.ONE;
        verbose = true;

        ( (ChessPanel)board.getParent() ).getGraveyard( SystemParameters.Player.ONE ).clearGraveyard();
        ( (ChessPanel)board.getParent() ).getGraveyard( SystemParameters.Player.TWO ).clearGraveyard();
        ( (ChessPanel)board.getParent() ).getGameBoard().initializeBoard();
        ( (ChessPanel)board.getParent() ).revalidate();
        this.king1 = (King)board.getCell( SystemParameters.getWhiteMainRow(), SystemParameters.getKingColumn() ).getPieceOnSquare();
        this.king2 = (King)board.getCell( SystemParameters.getBlackMainRow(), SystemParameters.getKingColumn() ).getPieceOnSquare();
        ( (ChessPanel)board.getParent() ).getGameLog().clearLog();
        ( (ChessPanel)board.getParent() ).getGameLog().addToLog(
                "A new chess "
                        + "game has been started. Player 1 (white) will play "
                        + "against Player 2 (black). BEGIN!" );
    }

    /**
     * Switches the turn to be the next player's turn.
     */
    private void nextTurn(){
        this.updateCurrentPlayer();
        ( (ChessPanel)board.getParent() ).getGameLog().addToLog(
                "It is now Player " + currentPlayer + "'s turn." );
    }
    private void updateCurrentPlayer()
    {
        currentPlayer = ( currentPlayer == SystemParameters.Player.ONE ) ? SystemParameters.Player.TWO : SystemParameters.Player.ONE;
    }
    // ----------------------------------------------------------
    /**
     * Gets the current player. Used for determining the turn.
     *
     * @return int the current player (1 or 2)
     */
    public SystemParameters.Player getCurrentPlayer(){
        return currentPlayer;
    }
    /**
     * Determines if the requested player has legal moves.
     *
     * @param playerNum
     *            the player to check
     * @return boolean true if the player does have legal moves, false otherwise
     */
    public boolean playerHasLegalMoves( SystemParameters.Player playerNum ){//TODO:: replace with ENUM
        ArrayList<ChessGamePiece> pieces;
        if ( playerNum == SystemParameters.Player.ONE ){
            pieces = board.getAllWhitePieces();
        }
        else if ( playerNum == SystemParameters.Player.TWO ){
            pieces = board.getAllBlackPieces();
        }
        else
        {
            return false;
        }
        for ( ChessGamePiece currPiece : pieces ){
            if ( currPiece.hasLegalMoves( board ) ){
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if the last-clicked piece is a valid piece (i.e. if it is
     * the correct color and if the user actually clicked ON a piece.)
     * @return boolean true if the piece is valid, false otherwise
     */
    private boolean selectedPieceIsValid(){
        if ( currentPiece == null ) // user tried to select an empty square
        {
            return false;
        }
        if ( currentPlayer == SystemParameters.Player.TWO ) // black player
        {
            if ( currentPiece.getColorOfPiece() == ChessGamePiece.BLACK ){
                return true;
            }
            return false;
        }
        else
        // white player
        {
            if ( currentPiece.getColorOfPiece() == ChessGamePiece.WHITE ){
                return true;
            }
            return false;
        }
    }
    /**
     * Determines if the requested King is in check.
     *
     * @param checkCurrent
     *            if true, will check if the current king is in check if false,
     *            will check if the other player's king is in check.
     * @return true if the king is in check, false otherwise
     */
    public boolean isKingInCheck( boolean checkCurrent ){
        if ( checkCurrent ){
            if ( currentPlayer == SystemParameters.Player.ONE ){
                return king1.isChecked( board );
            }
            return king2.isChecked( board );
        }
        else
        {
            if ( currentPlayer == SystemParameters.Player.TWO ){
                return king1.isChecked( board );
            }
            return king2.isChecked( board );
        }
    }
    /**
     * Asks the user if they want to play again - if they don't, the game exits.
     *
     * @param endGameStr
     *            the string to display to the user (i.e. stalemate, checkmate,
     *            etc)
     */
    private void askUserToPlayAgain( String endGameStr ){
        int resp =
            JOptionPane.showConfirmDialog( board.getParent(), endGameStr
                + " Do you want to play again?" );
        if ( resp == JOptionPane.YES_OPTION ){
            reset();
        }
        else
        {
            board.resetBoard( false );
            // System.exit(0);
        }
    }
    /**
     * Determines if the game should continue (i.e. game is in check or is
     * 'normal'). If it should not, the user is asked to play again (see above
     * method).
     */
    private void checkGameConditions(){
        SystemParameters.Player origPlayer = currentPlayer;
        for ( int i = 0; i < SystemParameters.getTotalPlayers(); i++ ){
            GameCondition gameLostRetVal = determineGameLost();
            if ( gameLostRetVal == GameCondition.STALEMATE ){
                askUserToPlayAgain( "Game over - STALEMATE. You should both go"
                    + " cry in a corner!" );
                return;
            }
            else if ( gameLostRetVal == GameCondition.PLAYER1_LOSS || gameLostRetVal == GameCondition.PLAYER2_LOSS ){
                askUserToPlayAgain( "Game over - CHECKMATE. " + "Player "
                    + gameLostRetVal.getValue() + " loses and should go"
                    + " cry in a corner!" );
                return;
            }
            else if ( isKingInCheck( true ) ){
                JOptionPane.showMessageDialog(
                    board.getParent(),
                    "Be careful player " + currentPlayer + ", " +
                    "your king is in check! Your next move must get " +
                    "him out of check or you're screwed.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE );
            }
            this.updateCurrentPlayer();
            // check the next player's conditions as well.
        }
        currentPlayer = origPlayer;
        nextTurn();
    }
    /**
     * Determines if the game is lost. Returns 1 or 2 for the losing player, -1
     * for stalemate, or 0 for a still valid game.
     *
     * @return int 1 or 2 for the losing play, -1 for stalemate, or 0 for a
     *         still valid game.
     */
    //TODO:: replace return with enum
    public GameCondition determineGameLost(){
        if ( king1.isChecked( board ) && !playerHasLegalMoves(SystemParameters.Player.ONE) ) // player 1
        // loss
        {
            return GameCondition.PLAYER1_LOSS;
        }
        if ( king2.isChecked( board ) && !playerHasLegalMoves( SystemParameters.Player.TWO ) ) // player 2
        // loss
        {
            return GameCondition.PLAYER2_LOSS;
        }
        if ( ( !king1.isChecked( board ) && !playerHasLegalMoves( SystemParameters.Player.ONE ) )
            || ( !king2.isChecked( board ) && !playerHasLegalMoves( SystemParameters.Player.TWO ) )
            || ( board.getAllWhitePieces().size() == 1 &&
                board.getAllBlackPieces().size() == 1 ) ) // stalemate
        {
            return GameCondition.STALEMATE;
        }
        return GameCondition.IN_PLAY; // game is still in play
    }

    // ----------------------------------------------------------
    /**
     * Given a MouseEvent from a user clicking on a square, the appropriate
     * action is determined. Actions include: moving a piece, showing the possible
     * moves of a piece, or ending the game after checking game conditions.
     *
     * @param e
     *            the mouse event from the listener
     */
    //TODO:: enable determineActionFromSquareClick to be testable --> returns its side-effect?
    public SystemParameters.Actions determineActionFromSquareClick( MouseEvent e ){
        SystemParameters.Actions action = SystemParameters.Actions.UNKNOWN;
        BoardSquare squareClicked = (BoardSquare)e.getSource();
        ChessGamePiece pieceOnSquare = squareClicked.getPieceOnSquare();
        board.clearColorsOnBoard();
        if ( firstClick ){
            currentPiece = squareClicked.getPieceOnSquare();
            if ( selectedPieceIsValid() ){
                currentPiece.showLegalMoves( board );
                squareClicked.setBackground( Color.GREEN );
                firstClick = false;

                action = SystemParameters.Actions.Select;
            }
            else
            {
                if ( currentPiece != null ){
                    showErrorMessageDialog(squareClicked,
                            "You tried to pick up the other player's piece! "
                                    + "Get some glasses and pick a valid square.",
                            "Illegal move");

                    action = SystemParameters.Actions.Invalid_SelectOpponent;
                }
                else
                {
                    showErrorMessageDialog(squareClicked,
                            "You tried to pick up an empty square! "
                                    + "Get some glasses and pick a valid square.",
                            "Illegal move");

                    action = SystemParameters.Actions.Invalid_SelectEmpty;
                }
            }
        }
        else
        {
            if ( pieceOnSquare == null ||
                !pieceOnSquare.equals( currentPiece ) ) // moving
            {
                boolean moveSuccessful =
                    currentPiece.move(
                        board,
                        squareClicked.getRow(),
                        squareClicked.getColumn() );
                if ( moveSuccessful ){
                    checkGameConditions();

                    action = SystemParameters.Actions.Move;
                }
                else
                {
                    int row = squareClicked.getRow();
                    int col = squareClicked.getColumn();
                    this.showErrorMessageDialog(squareClicked,
                            "The move to row " + ( row + 1 ) + " and column "
                                    + ( col + 1 )
                                    + " is either not valid or not legal "
                                    + "for this piece. Choose another move location, "
                                    + "and try using your brain this time!",
                            "Invalid move");

                    action = SystemParameters.Actions.Invalid_Move;
                }
                firstClick = true;
            }
            else
            // user is just unselecting the current piece
            {
                firstClick = true;
                action = SystemParameters.Actions.Deselect;
            }
        }

        return action;
    }

    public void showErrorMessageDialog(Component parent, String message, String title)
    {
        if(this.verbose)
        {
            JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setVerbose(boolean verbosity)
    {
        this.verbose = verbosity;
    }
}
