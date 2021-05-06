
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test {

    @Test
    public void BoardSquareConstructorTest()
    {
        int expectedRow = 1;
        int expectedCol = 1;
        ChessGamePiece expectedGP = null;

        //setup
        BoardSquare testSquare = new BoardSquare(expectedRow, expectedCol, expectedGP);

        //


        //
        assertEquals(testSquare.getRow(), expectedRow);
        assertEquals(testSquare.getColumn(), expectedRow);
        assertEquals(testSquare.getPieceOnSquare(), expectedGP);
    }

}
