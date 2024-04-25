package code;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MineTest {
    protected Mine m;
    @BeforeEach
    void setUp() throws Exception{
        m = new Mine(1, 1, 1, ore.gold);
    }
    @AfterEach
    public void tearDown() throws Exception {
        m = null;
    }

    @Test
    void testRemove() throws is_empty_exception {
        assertEquals(0, m.remove(0));
        assertEquals(50, m.remove(50));
        assertEquals(0, m.remove(50));
    }
}