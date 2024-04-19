package code;
import java.util.*;

/**
 * 
 */
public class Robot {

    private int id_rob;
    private int storage;
    private int efficiency;
    private int x;
    private int y;
    private int xw;
    private int yw;
    private int inventory;
    private World world;

    public Robot() {
        this.id_rob = 0;
        this.storage = new Random().nextInt(5)+4;
        this.efficiency = new Random().nextInt(2)+1;
        this.x = 0;
        this.y = 0;
        this.xw = 0;
        this.yw = 0;
        this.inventory = 0;
        this.world = new World();
    }

    public Robot(int id_rob, int x, int y, int xw, int yw, int inventory, World world) {
        this.id_rob = id_rob;
        this.x = x;
        this.y = y;
        this.xw = xw;
        this.yw = yw;
        this.storage = new Random().nextInt(5)+4;
        this.efficiency = new Random().nextInt(2)+1;
        this.inventory = inventory;
        this.world = world;
    }

    public void mine() {
        // TODO
    }

    /**
     * @param dir
     */
    public void move(String dir) {
        // TODO implement here
    }

}