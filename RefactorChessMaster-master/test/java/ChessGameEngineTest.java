//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.function.Executable;
//import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.event.MouseEvent;


public class ChessGameEngineTest
{
    //HELPERS
    public void tryMovePlayerPiece(ChessGameEngine testEngine, BoardSquare piecePosition, BoardSquare toMovePosition)
    {
        //make a move (switch current player)
        MouseEvent e1 = new MouseEvent(piecePosition, 0, 0, 0, 0, 0, 0, false);
        MouseEvent e2 = new MouseEvent(toMovePosition, 0, 0, 0, 0, 0, 0, false);

        //apply
        testEngine.determineActionFromSquareClick(e1);
        testEngine.determineActionFromSquareClick(e2);
    }



    //NOTE:: nothing public to test in constructor...
//    @Test
//    public void ChessGameEngineConstructorTest()
//    {
//    }





    @Test
    public void ResetTest()
    {
        SystemParameters.Player expectedCurrentPlayer = SystemParameters.Player.ONE;

        //setup
        ChessPanel testPanel = new ChessPanel();

        ChessGameBoard testBoard = testPanel.getGameBoard();
        testBoard.resetBoard(false);

        ChessGameEngine testEngine = testPanel.getGameEngine();

        //apply
        testEngine.reset();

        //assert
        assertEquals(testEngine.getCurrentPlayer(), expectedCurrentPlayer);

        //NOT: checking grave yards cleared -- GUI related

        assertEquals(testBoard.getCell( 7, 3 ).getPieceOnSquare().getClass(), King.class);
        assertEquals(testBoard.getCell( 0, 3 ).getPieceOnSquare().getClass(), King.class);
    }

    //NOTE:: by converting "ChessGameEngine::playerHasLegalMoves(int)" to take enum
        //==> no longer have possibility to be given bad input
//    @Test
//    public void InvalidPlayerHasLegalMovesTest()
//    {
//
//        //setup
//        ChessPanel testPanel = new ChessPanel();
//
//        ChessGameBoard testBoard = testPanel.getGameBoard();
//        testBoard.resetBoard(false);
//
//        ChessGameEngine testEngine = testPanel.getGameEngine();
//
//        //apply
//        boolean res = testEngine.playerHasLegalMoves((SystemParameters.Player)3);
//
//        //assert
//        assertEquals(res, false);
//    }
    @Test
    public void Player1HasLegalMovesTest()
    {

        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();

        //apply
        boolean res = testEngine.playerHasLegalMoves(SystemParameters.Player.ONE);

        //assert
        assertEquals(res, true);
    }
    @Test
    public void Player2HasLegalMovesTest()
    {

        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();

        //apply
        boolean res = testEngine.playerHasLegalMoves(SystemParameters.Player.TWO);

        //assert
        assertEquals(res, true);
    }

    @Test
    public void Player1HasNoLegalMovesTest()
    {

        //setup
        ChessPanel testPanel = new ChessPanel();

        ChessGameBoard testBoard = testPanel.getGameBoard();
        testBoard.resetBoard(false);

        ChessGameEngine testEngine = testPanel.getGameEngine();

        //apply
        boolean res = testEngine.playerHasLegalMoves(SystemParameters.Player.ONE);

        //assert
        assertEquals(res, false);
    }

    @Test
    public void isCurrentPlayer1KingInCheckTest()
    {

        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();

        //apply
        boolean res = testEngine.isKingInCheck(true);

        //assert
        assertEquals(res, false);
    }
    @Test
    public void isOpponentPlayer1KingInCheckTest()
    {

        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();

        ChessGameBoard testBoard = testPanel.getGameBoard();
        //testBoard.resetBoard(false);

        //make a move (switch current player)
//        MouseEvent e1 = new MouseEvent(, 0, 0, 0, 0, 0, 0, false);
//        MouseEvent e2 = new MouseEvent(, 0, 0, 0, 0, 0, 0, false);
//        testEngine.determineActionFromSquareClick(e);

        tryMovePlayerPiece(testEngine, testBoard.getCell(6, 0), testBoard.getCell(5, 0));

        // apply
        boolean res = testEngine.isKingInCheck(false);

        //assert
        assertEquals(res, false);
    }
    @Test
    public void isOpponentPlayer2KingInCheckTest()
    {

        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();

        //apply
        boolean res = testEngine.isKingInCheck(false);

        //assert
        assertEquals(res, false);
    }
    @Test
    public void isCurrentPlayer2KingInCheckTest()
    {

        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();

        ChessGameBoard testBoard = testPanel.getGameBoard();

        //make a move (switch current player)
        tryMovePlayerPiece(testEngine, testBoard.getCell(6, 0), testBoard.getCell(5, 0));

        // apply
        boolean res = testEngine.isKingInCheck(true);

        //assert
        assertEquals(res, false);
    }

    @Test
    public void DetermineGameLost_Playing_Test()
    {

        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();

        ChessGameBoard testBoard = testPanel.getGameBoard();

        // apply
        ChessGameEngine.GameCondition res = testEngine.determineGameLost();

        //assert
        assertEquals(res, ChessGameEngine.GameCondition.IN_PLAY);
    }

    @Test
    public void DetermineGameLost_Stalemate_Test()
    {
        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();

        ChessGameBoard testBoard = testPanel.getGameBoard();

        //remove everything from board except for the kings...
        for ( int i = 0; i < 8; i++ )
        {
            for ( int j = 0; j < 8; j++ )
            {
                if ( !(i == 0 && j == 3) && !(i == 7 && j == 3))
                {
                    testBoard.clearCell(i, j);
                }
            }
        }


        // apply
        ChessGameEngine.GameCondition res = testEngine.determineGameLost();

        //assert
        assertEquals(res, ChessGameEngine.GameCondition.STALEMATE);
    }
    @Test
    public void DetermineGameLost_Player1_Test()
    {
        ChessGameEngine.GameCondition expectedPlayer1Loss = ChessGameEngine.GameCondition.PLAYER1_LOSS;
        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();

        ChessGameBoard testBoard = testPanel.getGameBoard();

        //remove everything from board except for the kings...
        for ( int i = 0; i < 8; i++ )
        {
            for ( int j = 0; j < 8; j++ )
            {
                if ( !(i == 0 && j == 3) && !(i == 7 && j == 3))
                {
                    testBoard.clearCell(i, j);
                }
            }
        }
        //add back piece to place Player 1 in check
        BoardSquare checkCell1 = testBoard.getCell(5, 2);
        BoardSquare checkCell2 = testBoard.getCell(5, 3);
        BoardSquare checkCell3 = testBoard.getCell(5, 4);

        checkCell1.setPieceOnSquare(new Rook(testBoard, 5, 2, ChessGamePiece.BLACK));
        checkCell2.setPieceOnSquare(new Rook(testBoard, 5, 3, ChessGamePiece.BLACK));
        checkCell3.setPieceOnSquare(new Rook(testBoard, 5, 4, ChessGamePiece.BLACK));

        // apply
        ChessGameEngine.GameCondition res = testEngine.determineGameLost();

        //assert
        assertEquals(expectedPlayer1Loss, res);
    }
    @Test
    public void DetermineGameLost_Player2_Test()
    {
        ChessGameEngine.GameCondition expectedPlayer2Loss = ChessGameEngine.GameCondition.PLAYER2_LOSS;
        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();

        ChessGameBoard testBoard = testPanel.getGameBoard();

        //Can only lose if you are current player ==> switch to player 2
        //make a move (switch current player)
        tryMovePlayerPiece(testEngine, testBoard.getCell(6, 0), testBoard.getCell(5, 0));

        //remove everything from board except for the kings...
        for ( int i = 0; i < 8; i++ )
        {
            for ( int j = 0; j < 8; j++ )
            {
                if ( !(i == 0 && j == 3) && !(i == 7 && j == 3))
                {
                    testBoard.clearCell(i, j);
                }
            }
        }
        //add back piece to place Player 1 in check
        BoardSquare checkCell1 = testBoard.getCell(2, 2);
        BoardSquare checkCell2 = testBoard.getCell(2, 3);
        BoardSquare checkCell3 = testBoard.getCell(2, 4);

        checkCell1.setPieceOnSquare(new Rook(testBoard, 2, 2, ChessGamePiece.WHITE));
        checkCell2.setPieceOnSquare(new Rook(testBoard, 2, 3, ChessGamePiece.WHITE));
        checkCell3.setPieceOnSquare(new Rook(testBoard, 2, 4, ChessGamePiece.WHITE));

        // apply
        ChessGameEngine.GameCondition res = testEngine.determineGameLost();

        //assert
        assertEquals(expectedPlayer2Loss, res);
    }

    //---------------------------
    //NOTE:: No visible (non-private) side-effects produced from each branch of determineActionFromSquareClick()
    //---------------------------
        //enable determineActionFromSquareClick to be testable --> returns its side-effect

    //first click tests
    @Test
    public void determineActionFromSquareClick_EmptySquare_Test()
    {
        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();
        ChessGameBoard testBoard = testPanel.getGameBoard();

        testEngine.setVerbose(false);
        BoardSquare emptyCell = testBoard.getCell(4, 0);

        //create event to make a move
        MouseEvent e1 = new MouseEvent(emptyCell, 0, 0, 0, 0, 0, 0, false);

        // apply
        SystemParameters.Actions res = testEngine.determineActionFromSquareClick(e1);

        //assert
        assertEquals(SystemParameters.Actions.Invalid_SelectEmpty, res);
    }
    @Test
    public void determineActionFromSquareClick_OpponentSquare_Test()
    {
        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();
        ChessGameBoard testBoard = testPanel.getGameBoard();

        testEngine.setVerbose(false);
        BoardSquare opponentCell = testBoard.getCell(0, 0);

        //create event to make a move
        MouseEvent e1 = new MouseEvent(opponentCell, 0, 0, 0, 0, 0, 0, false);

        // apply
        SystemParameters.Actions res = testEngine.determineActionFromSquareClick(e1);

        //assert
        assertEquals(SystemParameters.Actions.Invalid_SelectOpponent, res);
    }
    @Test
    public void determineActionFromSquareClick_Select_Test()
    {
        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();
        ChessGameBoard testBoard = testPanel.getGameBoard();

        testEngine.setVerbose(false);
        BoardSquare pawnCell = testBoard.getCell(6, 0);

        //create event to make a move
        MouseEvent e1 = new MouseEvent(pawnCell, 0, 0, 0, 0, 0, 0, false);

        // apply
        SystemParameters.Actions res = testEngine.determineActionFromSquareClick(e1);

        //assert
        assertEquals(SystemParameters.Actions.Select, res);
    }

    //
    //2nd click tests
    //

    //double click piece
    @Test
    public void determineActionFromSquareClick_Deselect_Test()
    {
        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();
        ChessGameBoard testBoard = testPanel.getGameBoard();

        testEngine.setVerbose(false);
        BoardSquare pawnCell = testBoard.getCell(6, 0);

        //create event to make a move
        MouseEvent e1 = new MouseEvent(pawnCell, 0, 0, 0, 0, 0, 0, false);
        testEngine.determineActionFromSquareClick(e1);

        // apply
        SystemParameters.Actions res = testEngine.determineActionFromSquareClick(e1);

        //assert
        assertEquals(SystemParameters.Actions.Deselect, res);
    }

    @Test
    public void determineActionFromSquareClick_Move_Test()
    {
        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();
        ChessGameBoard testBoard = testPanel.getGameBoard();

        testEngine.setVerbose(false);
        BoardSquare pawnCell = testBoard.getCell(6, 0);
        BoardSquare toMoveCell = testBoard.getCell(4, 0);


        //create event to make a move
        MouseEvent e1 = new MouseEvent(pawnCell, 0, 0, 0, 0, 0, 0, false);
        testEngine.determineActionFromSquareClick(e1);

        MouseEvent e2 = new MouseEvent(toMoveCell, 0, 0, 0, 0, 0, 0, false);

        // apply
        SystemParameters.Actions res = testEngine.determineActionFromSquareClick(e2);

        //assert
        assertEquals(SystemParameters.Actions.Move, res);
    }

    @Test
    public void determineActionFromSquareClick_InvalidMove_Test()
    {
        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();
        ChessGameBoard testBoard = testPanel.getGameBoard();

        testEngine.setVerbose(false);
        BoardSquare pawnCell = testBoard.getCell(6, 0);
        BoardSquare toMoveCell = testBoard.getCell(3, 0);


        //create event to make a move
        MouseEvent e1 = new MouseEvent(pawnCell, 0, 0, 0, 0, 0, 0, false);
        testEngine.determineActionFromSquareClick(e1);

        MouseEvent e2 = new MouseEvent(toMoveCell, 0, 0, 0, 0, 0, 0, false);

        // apply
        SystemParameters.Actions res = testEngine.determineActionFromSquareClick(e2);

        //assert
        assertEquals(SystemParameters.Actions.Invalid_Move, res);
    }

//    //first click tests
//    @Test
//    public void determineActionFromSquareClick_EmptySquare_Test()
//    {
//        //setup
//        ChessPanel testPanel = new ChessPanel();
//        ChessGameEngine testEngine = testPanel.getGameEngine();
//        ChessGameBoard testBoard = testPanel.getGameBoard();
//
//        // apply
//        BoardSquare emptyCell = testBoard.getCell(4, 0);
//
//        //make a move (switch current player)
//        MouseEvent e1 = new MouseEvent(emptyCell, 0, 0, 0, 0, 0, 0, false);
//
//        // Set a 2 second timer
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(500);
//                } catch (Exception e) {
//                }
//                testEngine.disposeMessageDialog();
//            }
//
//        }).start();
//        SystemParameters.Actions res = testEngine.determineActionFromSquareClick(e1);
//
//        //assert
//        assertEquals(SystemParameters.Actions.Invalid_SelectEmpty, res);
//    }

}
