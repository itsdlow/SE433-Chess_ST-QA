//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.ArrayList;

public class ChessGamePieceTest {

    @Test
    public void chessGamePieceConstructorTest(){
        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();
        ChessGameBoard testBoard = testPanel.getGameBoard();
        ChessGamePiece queen = new Queen(testBoard, 7, 4, ChessGamePiece.WHITE);
        int expectedRow = 7;
        int expectedColumn = 4;

        //apply
        int actualRow = queen.getRow();
        int actualColumn = queen.getColumn();

        //assert
        assertEquals(expectedRow,actualRow);
        assertEquals(expectedColumn,actualColumn);
    }

    @Test
    public void isEnemyTest() {
        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();
        ChessGameBoard testBoard = testPanel.getGameBoard();
        ChessGamePiece whitePiece = testBoard.getCell(1,0).getPieceOnSquare();
        ChessGamePiece blackPiece = testBoard.getCell(7, 0).getPieceOnSquare();

        //apply

        //square out of bounds:
        boolean isEnemy1 = whitePiece.isEnemy(testBoard, 8, 8);
        boolean isEnemy2 = whitePiece.isEnemy(testBoard, -1, -1);
        //if enemy piece == null:
        boolean isEnemy3 = whitePiece.isEnemy(testBoard, 3, 3);
        //if enemy piece == true:
        boolean isEnemy4 = whitePiece.isEnemy(testBoard, 7,0);
        boolean isEnemy5 = blackPiece.isEnemy(testBoard, 1, 0);
        //if enemy piece == false:
        boolean isEnemy6 = whitePiece.isEnemy(testBoard, 1, 1);
        boolean isEnemy7 = blackPiece.isEnemy(testBoard, 7,1);
        //assert
        assertFalse(isEnemy1);
        assertFalse(isEnemy2);
        assertFalse(isEnemy3);
        assertTrue(isEnemy4);
        assertTrue(isEnemy5);
        assertFalse(isEnemy6);
        assertFalse(isEnemy7);
    }

    @Test
    public void calculatePossibleNSEWMovesTest(){

        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();
        ChessGameBoard testBoard = testPanel.getGameBoard();
        ArrayList<String> expectedMovesList = new ArrayList<String>();
        ArrayList<String> actualMovesList = new ArrayList<String>();

        //removes all pieces:
        for ( int i = 0; i < 8; i++ )
        {
            for ( int j = 0; j < 8; j++ )
            {
                testBoard.clearCell(i, j);
            }
        }

        ChessGamePiece rook = new Rook(testBoard, 4, 4, ChessGamePiece.BLACK);
        testBoard.getCell(4,4).setPieceOnSquare(rook);

        //apply

        expectedMovesList.add("3,4");
        expectedMovesList.add("2,4");
        expectedMovesList.add("1,4");
        expectedMovesList.add("0,4");
        expectedMovesList.add("5,4");
        expectedMovesList.add("6,4");
        expectedMovesList.add("7,4");
        expectedMovesList.add("4,3");
        expectedMovesList.add("4,2");
        expectedMovesList.add("4,1");
        expectedMovesList.add("4,0");
        expectedMovesList.add("4,5");
        expectedMovesList.add("4,6");
        expectedMovesList.add("4,7");

        actualMovesList = rook.calculatePossibleMoves(testBoard);

        //assert
        assertEquals(expectedMovesList,actualMovesList);
    }

    @Test
    public void calculatePossibleNWNESWSEMovesTest(){

        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();
        ChessGameBoard testBoard = testPanel.getGameBoard();
        ArrayList<String> expectedMovesList = new ArrayList<String>();
        ArrayList<String> actualMovesList = new ArrayList<String>();

        //removes all pieces:
        for ( int i = 0; i < 8; i++ )
        {
            for ( int j = 0; j < 8; j++ )
            {
                testBoard.clearCell(i, j);
            }
        }

        ChessGamePiece bishop = new Bishop(testBoard, 4, 4, ChessGamePiece.BLACK);
        testBoard.getCell(4,4).setPieceOnSquare(bishop);

        //apply

        expectedMovesList.add("3,5");
        expectedMovesList.add("2,6");
        expectedMovesList.add("1,7");
        expectedMovesList.add("3,3");
        expectedMovesList.add("2,2");
        expectedMovesList.add("1,1");
        expectedMovesList.add("0,0");
        expectedMovesList.add("5,5");
        expectedMovesList.add("6,6");
        expectedMovesList.add("7,7");
        expectedMovesList.add("5,3");
        expectedMovesList.add("6,2");
        expectedMovesList.add("7,1");

        actualMovesList = bishop.calculatePossibleMoves(testBoard);

        //assert
        assertEquals(expectedMovesList,actualMovesList);
    }

    @Test
    public void isPieceOnScreenTest(){
        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();
        ChessGameBoard testBoard = testPanel.getGameBoard();
        ChessGraveyard testGrave = testPanel.getGraveyard(SystemParameters.Player.ONE);
        ChessGamePiece pawn = testBoard.getCell(1, 0).getPieceOnSquare();
        ChessGamePiece rook = testBoard.getCell(0, 0).getPieceOnSquare();
        testGrave.addPiece(rook);

        //apply

        //piece is on screen:
        boolean isPieceTrue = pawn.isPieceOnScreen();
        //piece is not on screen:
        boolean isPieceFalse = rook.isPieceOnScreen();

        //assert
        assertTrue(isPieceTrue);
        assertFalse(isPieceFalse);
    }

    @Test
    public void initialGetCurrentAttackersTest(){
        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();
        ChessGameBoard testBoard = testPanel.getGameBoard();
        ChessGamePiece whitePiece = testBoard.getCell(6,0).getPieceOnSquare();
        ChessGamePiece blackPiece = testBoard.getCell(1,0).getPieceOnSquare();
        ArrayList<ChessGamePiece> expectedWhiteAttackers = new ArrayList<ChessGamePiece>();
        ArrayList<ChessGamePiece> expectedBlackAttackers = new ArrayList<ChessGamePiece>();

        //apply
        ArrayList<ChessGamePiece> actualWhiteAttackers = blackPiece.getCurrentAttackers(testBoard);
        ArrayList<ChessGamePiece> actualBlackAttackers = whitePiece.getCurrentAttackers(testBoard);

        //assert
        assertEquals(expectedWhiteAttackers, actualWhiteAttackers);
        assertEquals(expectedBlackAttackers, actualBlackAttackers);
    }

    @Test
    public void multipleGetCurrentAttackersTest(){
        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();
        ChessGameBoard testBoard = testPanel.getGameBoard();

        //removes all pieces:
        for ( int i = 0; i < 8; i++ )
        {
            for ( int j = 0; j < 8; j++ )
            {
                testBoard.clearCell(i, j);
            }
        }

        ChessGamePiece whitePawn = new Pawn(testBoard, 5, 2, ChessGamePiece.WHITE);
        ChessGamePiece blackPawn = new Pawn(testBoard, 4,3, ChessGamePiece.BLACK);
        testBoard.getCell(5,2).setPieceOnSquare(whitePawn);
        testBoard.getCell(4,3).setPieceOnSquare(blackPawn);

        ArrayList<ChessGamePiece> expectedWhiteAttackers = new ArrayList<ChessGamePiece>();
        ArrayList<ChessGamePiece> expectedBlackAttackers = new ArrayList<ChessGamePiece>();

        //apply
        expectedWhiteAttackers.add(testBoard.getCell(5,2).getPieceOnSquare());
        expectedBlackAttackers.add(testBoard.getCell(4,3).getPieceOnSquare());
        ArrayList<ChessGamePiece> actualWhiteAttackers = blackPawn.getCurrentAttackers(testBoard);
        ArrayList<ChessGamePiece> actualBlackAttackers = whitePawn.getCurrentAttackers(testBoard);

        //assert
        assertEquals(expectedWhiteAttackers, actualWhiteAttackers);
        assertEquals(expectedBlackAttackers, actualBlackAttackers);
    }

    @Test
    public void hasLegalMovesTest() {
        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();
        ChessGameBoard testBoard = testPanel.getGameBoard();
        ChessGamePiece pawn = testBoard.getCell(1, 0).getPieceOnSquare();
        ChessGamePiece king = testBoard.getCell(0,4).getPieceOnSquare();

        //apply

        //piece is legally able to move:
        boolean trueCase = pawn.hasLegalMoves(testBoard);
        //piece is not legally able to move:
        boolean falseCase = king.hasLegalMoves(testBoard);

        //assert
        assertTrue(trueCase);
        assertFalse(falseCase);
    }
    @Test
    public void canMoveTest(){
        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();
        ChessGameBoard testBoard = testPanel.getGameBoard();
        ChessGamePiece pawn = testBoard.getCell(1,0).getPieceOnSquare();
        //apply

        //pawn can move to square == true:
        boolean moveTrue = pawn.move(testBoard, 2,0);
        //pawn can move to square == false:
        boolean moveFalse = pawn.move(testBoard,3,3);

        //assert
        assertTrue(moveTrue);
        assertFalse(moveFalse);
    }

    @Test
    public void testMoveForKingSafetyTest(){

        //setup
        ChessPanel testPanel = new ChessPanel();
        ChessGameEngine testEngine = testPanel.getGameEngine();
        ChessGameBoard testBoard = testPanel.getGameBoard();

        //apply

        //removes all pieces except for Kings:
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

        ChessGamePiece enemyPawn = new Pawn(testBoard, 5, 4, ChessGamePiece.BLACK);
        ChessGamePiece yourKing = testBoard.getCell(7,3).getPieceOnSquare();
        testBoard.getCell(5,4).setPieceOnSquare(enemyPawn);

        //apply
        boolean falseSafe = yourKing.move(testBoard,6,3);
        boolean trueSafe = yourKing.move(testBoard, 7, 2);

        //assert
        assertFalse(falseSafe);
        assertTrue(trueSafe);

    }
}