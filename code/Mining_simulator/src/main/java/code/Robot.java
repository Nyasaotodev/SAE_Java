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
    private ore type;

    public Robot() {
        this.id_rob = 0;
        this.storage = new Random().nextInt(5)+4;
        this.efficiency = new Random().nextInt(2)+1;
        this.x = 0;
        this.y = 0;
        this.xw = 0;
        this.yw = 0;
        this.inventory = 0;
        this.world = null;
        this.type = ore.gold;
    }

    public Robot(int id_rob, int x, int y, int xw, int yw, int inventory, World world, ore type) {
        this.id_rob = id_rob;
        this.x = x;
        this.y = y;
        this.xw = xw;
        this.yw = yw;
        this.storage = new Random().nextInt(5)+4;
        this.efficiency = new Random().nextInt(2)+1;
        this.inventory = inventory;
        this.world = world;
        this.type = type;
    }

    public int get_id() {
        return this.id_rob;
    }

    public int get_inventory() {
        return this.inventory;
    }

    public int get_storage() {
        return this.storage;
    }

    public int[] get_pose() {
        int res[] = new int[2];
        res[0] = this.x;
        res[1] = this.y;
        return res;
    }

    public void set_inventory(int inventory) {
        this.inventory = inventory;
    }

    public void mine() throws Exception {
        if(inventory<storage) {
            if (this.world.get_section(x, y).get_struct() instanceof Mine) {
                if (this.world.get_section(x, y).get_struct().get_type() == this.type) {
                    if(this.efficiency<=(this.storage-this.inventory)) {
                        this.inventory = this.inventory + this.world.get_section(this.x,this.y).get_struct().remove(this.efficiency);
                    } else {
                        this.inventory = this.inventory + this.world.get_section(this.x,this.y).get_struct().remove(this.storage-this.inventory);
                    }
                }
                else {
                    throw new Wrong_type_exception();
                }
            }
            else {
                throw new Not_in_mine_exception();
            }
        }
        else {
            throw new Inventory_full_exception();
        }
    }

    public void move(String dir) throws Exception {
        switch (dir) {
            case "N":
                this.world.get_section(this.x-1,this.y).set_robot(this);
                this.world.get_section(this.x,this.y).remove_robot();
                this.x -= 1;
                break;
            case "S":
                this.world.get_section(this.x+1,this.y).set_robot(this);
                this.world.get_section(this.x,this.y).remove_robot();
                this.x += 1;
                break;
            case "E":
                this.world.get_section(this.x,this.y+1).set_robot(this);
                this.world.get_section(this.x,this.y).remove_robot();
                this.y += 1;
                break;
            case "O":
                this.world.get_section(this.x,this.y-1).set_robot(this);
                this.world.get_section(this.x,this.y).remove_robot();
                this.y -= 1;
                break;
            default:
                throw new invalid_argument_exception();
        }
        if(this.x == this.xw && this.y == this.yw) {
            this.world.get_section(this.x, this.y).get_struct().add(this.inventory);
            this.inventory = 0;
        }
    }

    @Override
    public String toString() {
        return "Robot{" +
                "id_rob=" + id_rob +
                ", storage=" + storage +
                ", efficiency=" + efficiency +
                ", inventory=" + inventory +
                ", type=" + type +
                '}';
    }
}