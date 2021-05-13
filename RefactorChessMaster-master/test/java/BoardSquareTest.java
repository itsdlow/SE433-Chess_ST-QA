import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BoardSquareTest {

    @Test
    public void BoardSquareConstructorTest() {
        int expectedRow = 1;
        int expectedCol = 1;
        ChessGamePiece expectedGP = null;

        //setup
        BoardSquare testSquare = new BoardSquare(expectedRow, expectedCol, expectedGP);

        //


        //
        assertEquals(testSquare.getRow(), expectedRow);
        assertEquals(testSquare.getColumn(), expectedCol);
        assertEquals(testSquare.getPieceOnSquare(), expectedGP);
    }

    @Test
    public void getRowTest() {
        int expectedRow = 1;
        int expectedCol = 1;
        ChessGamePiece expectedGP = null;

        //setup
        BoardSquare testSquare = new BoardSquare(expectedRow, expectedCol, expectedGP);

        //


        //
        assertEquals(testSquare.getRow(), expectedRow);
    }

    @Test
    public void getColumnTest() {
        int expectedRow = 1;
        int expectedCol = 1;
        ChessGamePiece expectedGP = null;

        //setup
        BoardSquare testSquare = new BoardSquare(expectedRow, expectedCol, expectedGP);

        //


        //
        assertEquals(testSquare.getColumn(),expectedCol);
    }

    @Test
    public void getPieceOnSquareTest(){
        int expectedRow = 1;
        int expectedCol = 1;
        ChessGamePiece expectedGP = null;

        //setup
        BoardSquare testSquare = new BoardSquare(expectedRow, expectedCol, expectedGP);

        //


        //
        assertEquals(testSquare.getPieceOnSquare(), expectedGP);
    }

    @Test
    public void setPieceOnSquareTest(){
        int expectedRow = 1;
        int expectedCol = 1;
        ChessGamePiece expectedGP = null;

        //setup
        BoardSquare testSquare = new BoardSquare(expectedRow, expectedCol, expectedGP);
        ChessGameBoard chessBoard = new ChessGameBoard();
        Knight testKnight = new Knight(chessBoard, expectedRow, expectedCol, 1);

        //
        testSquare.setPieceOnSquare(testKnight);

        //
        assertEquals(testSquare.getPieceOnSquare(), testKnight);

    }

    @Test
    public void clearSquareTest(){
        int expectedRow = 1;
        int expectedCol = 1;
        ChessGamePiece initGP = null;

        //setup
        ChessGameBoard chessBoard = new ChessGameBoard();
        Knight testKnight = new Knight(chessBoard, expectedRow, expectedCol, 1);
        BoardSquare testSquare = new BoardSquare(expectedRow, expectedCol, testKnight);

        //
        testSquare.removeAll();

        //
        assertNull(testSquare.getPieceOnSquare());
    }
}
