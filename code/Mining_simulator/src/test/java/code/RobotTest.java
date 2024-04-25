package code;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {
    protected Robot r;
    @BeforeEach
    void setUp() throws Exception{
        r = new Robot(1, 1, 1, 1, 1, 0, null, ore.gold);
    }
    @AfterEach
    public void tearDown() throws Exception {
        r = null;
    }

    @Test
    void testMove() throws Exception {
        r.move("N");
        int[] pos = new int[]{0, 1};
        assertEquals(pos, r.get_pos());
        r.move("S");
        pos = new int[]{1, 1};
        assertEquals(pos, r.get_pos());
        r.move("E");
        pos = new int[]{1, 2};
        assertEquals(pos, r.get_pos());
        r.move("W");
        pos = new int[]{1, 1};
        assertEquals(pos, r.get_pos());
    }
}