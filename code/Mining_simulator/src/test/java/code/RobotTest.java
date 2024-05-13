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
        w = new World();
    }
    @AfterEach
    public void tearDown() throws Exception {
        w = null;
    }

    @Test
    void testMove() throws Exception {
        ArrayList<Robot> robots = w.get_robot();
        Robot r = robots.get(0);
        int[] pose = r.get_pose();
        //mouvement
        r.move("N");
        assertEquals(pose[0]-1, r.get_pose()[0]);
        r.move("S");
        assertEquals(pose[0], r.get_pose()[0]);
        r.move("E");
        assertEquals(pose[1]+1, r.get_pose()[1]);
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
        r.mine();
        assertNotEquals(0, r.get_inventory());
        //Inventory full
        r.set_inventory(r.get_storage());
        assertThrows(Inventory_full_exception.class, () -> r.mine());
        //deposit
        Warehouse ware = w.get_warehouses().get(0);
        w.get_section(ware.get_x(), ware.get_y()+1).set_robot(r);
        r.move("O");
        assertEquals(0, r.get_inventory());
        //wrong type
        w.get_section(m.get_x(), m.get_y()).set_robot(robots.get(-1));
        assertThrows(Wrong_type_exception.class, () -> r.mine());
        //not in mine
        w.get_section(m.get_x(), m.get_y()).remove_robot();
        w.get_section(m.get_x()-1, m.get_y()).set_robot(robots.get(0));
        assertThrows(Not_in_mine_exception.class, () -> r.mine());


    }
}