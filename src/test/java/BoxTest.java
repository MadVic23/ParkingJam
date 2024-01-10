import objects.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;



public class BoxTest {

    @Nested
    @DisplayName("Tests for Box class")
    class BoxTests {

        private Box box;

        @BeforeEach
        void setUp() {
            box = new Box(2, 3);
        }

        @Test
        @DisplayName("Test getRow method")
        void testGetRow() {
            int row = box.getRow();
            assertEquals(2, row);
        }

        @Test
        @DisplayName("Test getCol method")
        void testGetCol() {
            int col = box.getCol();
            assertEquals(3, col);
        }

        @Test
        @DisplayName("Test setRow method")
        void testSetRow() {
            box.setRow(4);
            int row = box.getRow();
            assertEquals(4, row);
        }

        @Test
        @DisplayName("Test setCol method")
        void testSetCol() {
            box.setCol(5);
            int col = box.getCol();
            assertEquals(5, col);
        }
    }
}