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

        assertEquals(pose[1], r.get_pose()[1]);
        //is water
        w.get_section(pose[0]+1, pose[1]).set_water(true);
        assertThrows(is_water_exception.class, () -> r.move("S"));
        w.get_section(pose[0]+1, pose[1]).set_water(false);
        //is occuped
        w.get_section(pose[0]+1, pose[1]).set_robot(robots.get(1));
        assertThrows(is_occupied_exception.class, () -> r.move("S"));
        w.get_section(pose[0]+1, pose[1]).remove_robot();
        //mine
        System.out.println(w);
        Mine m = w.get_mines().get(0);
        w.get_section(m.get_x(), m.get_y()).set_robot(r);

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