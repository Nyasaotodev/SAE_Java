package code;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {
    protected World w;
    @BeforeEach
    void setUp() throws Exception{
        w = new World(true);
    }
    @AfterEach
    public void tearDown() throws Exception {
        w = null;
    }

    @Test
    void testMove() throws Exception {
        Robot r = w.get_robot().get(0);
        r.move("N");
        assertEquals(1, r.get_pose()[0]);
        r.move("S");
        assertEquals(2, r.get_pose()[0]);
        r.move("O");
        assertEquals(1, r.get_pose()[1]);
        r.move("E");
        assertEquals(2, r.get_pose()[1]);

        r.move("N");
        r.move("O");
        r.move("O");
        r.move("N");
        r.mine();
        assertNotEquals(0, r.get_inventory());

        r.move("E");
        assertThrows(Exception.class, () -> r.mine());

        r.move("E");
        assertEquals(0, r.get_inventory());
        assertThrows(Exception.class, () -> r.move("E"));

        r.move("S");
        r.move("E");
        r.move("E");
        assertThrows(Exception.class, () -> r.move("N"));
    }
}