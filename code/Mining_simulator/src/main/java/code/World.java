package code;
import java.util.*;
public class World {

    private Section[][] sections = new Section[10][10];
    private ArrayList<Robot> robots_gold = new ArrayList<Robot>();
    private ArrayList<Robot> robots_nickel = new ArrayList<Robot>();
    private ArrayList<Warehouse> warehouses = new ArrayList<Warehouse>();
    private ArrayList<Mine> mines = new ArrayList<Mine>();

    public World() throws Exception {
        int water = new Random().nextInt(10)+10;
        int gold_mine = new Random().nextInt(1)+1;
        int nickel_mine = new Random().nextInt(1)+1;
        int gold_robots = new Random().nextInt(4)+1;
        int nickel_robots = new Random().nextInt(4)+1;
        int ware_gold = 1;
        int ware_nickel = 1;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.sections[i][j] = new Section(i, j);
            }
        }

        while (water > 0) {
            int x = new Random().nextInt(10);
            int y = new Random().nextInt(10);
            int chance = 20;
            ArrayList<int[]> arounds = around(x, y);
            for (int[] around : arounds) {
                if (this.sections[around[0]][around[1]].get_water()) {
                    chance += 20;
                }
            }
            if (new Random().nextInt(100) < chance) {
                this.sections[x][y].set_water(true);
                water--;
            }
        }
        int id = 0;
        while (gold_mine > 0) {
            int x = new Random().nextInt(10);
            int y = new Random().nextInt(10);
            if (this.sections[x][y].get_water() || this.sections[x][y].get_struct() != null) {
                continue;
            }
            id++;
            this.sections[x][y].set_struct(new Mine(0, x, y, ore.gold));
            this.mines.add((Mine)this.sections[x][y].get_struct());
            gold_mine--;
        }
        while (nickel_mine > 0) {
            int x = new Random().nextInt(10);
            int y = new Random().nextInt(10);
            if (this.sections[x][y].get_water() || this.sections[x][y].get_struct() != null) {
                continue;
            }
            id++;
            this.sections[x][y].set_struct(new Mine(id, x, y, ore.nickel));
            this.mines.add((Mine)this.sections[x][y].get_struct());
            nickel_mine--;
        }
        id = 0;
        while (ware_gold > 0) {
            int x = new Random().nextInt(10);
            int y = new Random().nextInt(10);
            if (this.sections[x][y].get_water() || this.sections[x][y].get_struct() != null) {
                continue;
            }
            this.sections[x][y].set_struct(new Warehouse(0, x, y, ore.gold));
            this.warehouses.add((Warehouse)this.sections[x][y].get_struct());
            ware_gold--;

            while (gold_robots > 0) {
                int x_rob = new Random().nextInt(10);
                int y_rob = new Random().nextInt(10);
                if (this.sections[x_rob][y_rob].get_water() || this.sections[x_rob][y_rob].get_struct() != null) {
                    continue;
                }
                id++;
                this.sections[x_rob][y_rob].set_robot(new Robot(id, x_rob, y_rob, x, y, 0));
                this.robots_gold.add(this.sections[x_rob][y_rob].get_robot());
                gold_robots--;
            }
        }
        while (ware_nickel > 0) {
            int x = new Random().nextInt(10);
            int y = new Random().nextInt(10);
            if (this.sections[x][y].get_water() || this.sections[x][y].get_struct() != null) {
                continue;
            }
            this.sections[x][y].set_struct(new Warehouse(1, x, y, ore.nickel));
            this.warehouses.add((Warehouse)this.sections[x][y].get_struct());
            ware_nickel--;
            while (nickel_robots > 0) {
                int x_rob = new Random().nextInt(10);
                int y_rob = new Random().nextInt(10);
                if (this.sections[x_rob][y_rob].get_water() || this.sections[x_rob][y_rob].get_struct() != null) {
                    continue;
                }
                id++;
                this.sections[x_rob][y_rob].set_robot(new Robot(id, x_rob, y_rob, x, y, 0));
                this.robots_nickel.add(this.sections[x_rob][y_rob].get_robot());
                nickel_robots--;
            }
        }

    }

    public ArrayList<int[]> around (int x, int y) {
        ArrayList<int[]> result = new ArrayList<int[]>();
        if (x > 0) {
            result.add(new int[]{x-1, y});
        }
        if (x < 9) {
            result.add(new int[]{x+1, y});
        }
        if (y > 0) {
            result.add(new int[]{x, y-1});
        }
        if (y < 9) {
            result.add(new int[]{x, y+1});
        }
        return result;
    }

    public Section get_section(int x, int y) {
        return this.sections[x][y];
    }
    public void display() {
        // TODO implement here
    }

}