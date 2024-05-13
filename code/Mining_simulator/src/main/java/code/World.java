package code;
import java.util.*;
public class World {

    private Section[][] world = new Section[10][10];
    private ArrayList<Robot> robots_gold = new ArrayList<Robot>();
    private ArrayList<Robot> robots_nickel = new ArrayList<Robot>();
    private ArrayList<Warehouse> warehouses = new ArrayList<Warehouse>();
    private ArrayList<Mine> mines = new ArrayList<Mine>();

    public World(boolean test) throws Exception {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.world[i][j] = new Section(i, j);
            }
        }
        this.world[0][0].set_struct(new Mine(0, 0, 0, ore.gold));
        this.mines.add((Mine)this.world[0][0].get_struct());
        this.world[0][1].set_struct(new Mine(1, 0, 1, ore.nickel));
        this.mines.add((Mine)this.world[0][1].get_struct());
        this.world[0][2].set_struct(new Warehouse(0, 0, 2, ore.gold));
        this.warehouses.add((Warehouse)this.world[0][2].get_struct());
        this.world[0][3].set_water(true);
        this.world[0][4].set_robot(new Robot(0, 2, 2, 0, 2, 0, this, ore.gold));
        this.robots_gold.add(this.world[0][4].get_robot());
        this.world[0][5].set_robot(new Robot(1, 0, 3, 0, 2, 0, this, ore.nickel));
        this.robots_nickel.add(this.world[0][5].get_robot());
    }

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
                this.world[i][j] = new Section(i, j);
            }
        }

        while (water > 0) {
            int x = new Random().nextInt(10);
            int y = new Random().nextInt(10);
            int chance = 10;
            ArrayList<int[]> arounds = around(x, y);
            for (int[] around : arounds) {
                if (this.world[around[0]][around[1]].get_water()) {
                    chance += 15;
                }
            }
            if (new Random().nextInt(100) < chance) {
                this.world[x][y].set_water(true);
                water--;
            }
        }
        int id = 0;
        while (gold_mine > 0) {
            int x = new Random().nextInt(10);
            int y = new Random().nextInt(10);
            if (this.world[x][y].get_water() || this.world[x][y].get_struct() != null || this.world[x][y].get_robot()!=null) {
                System.out.println(true);
                continue;
            }
            this.world[x][y].set_struct(new Mine(0, x, y, ore.gold));
            this.mines.add((Mine)this.world[x][y].get_struct());
            gold_mine--;
            id++;
        }
        while (nickel_mine > 0) {
            int x = new Random().nextInt(10);
            int y = new Random().nextInt(10);
            if (this.world[x][y].get_water() || this.world[x][y].get_struct() != null || this.world[x][y].get_robot()!=null) {
                System.out.println(true);
                continue;
            }
            this.world[x][y].set_struct(new Mine(id, x, y, ore.nickel));
            this.mines.add((Mine)this.world[x][y].get_struct());
            nickel_mine--;
            id++;
        }
        id = 0;
        while (ware_gold > 0) {
            int x = new Random().nextInt(10);
            int y = new Random().nextInt(10);
            if (this.world[x][y].get_water() || this.world[x][y].get_struct() != null || this.world[x][y].get_robot()!=null) {
                System.out.println(true);
                continue;
            }
            this.world[x][y].set_struct(new Warehouse(0, x, y, ore.gold));
            this.warehouses.add((Warehouse)this.world[x][y].get_struct());
            ware_gold--;

            while (gold_robots > 0) {
                int x_rob = new Random().nextInt(10);
                int y_rob = new Random().nextInt(10);
                if (this.world[x_rob][y_rob].get_water() || this.world[x_rob][y_rob].get_robot()!=null) {
                    System.out.println(true);
                    continue;
                }
                this.world[x_rob][y_rob].set_robot(new Robot(id, x_rob, y_rob, x, y, 0, this, ore.gold));
                this.robots_gold.add(this.world[x_rob][y_rob].get_robot());
                gold_robots--;
                id++;
            }
        }
        while (ware_nickel > 0) {
            int x = new Random().nextInt(10);
            int y = new Random().nextInt(10);
            if (this.world[x][y].get_water() || this.world[x][y].get_struct() != null || this.world[x][y].get_robot()!=null) {
                System.out.println(true);
                continue;
            }
            this.world[x][y].set_struct(new Warehouse(1, x, y, ore.nickel));
            this.warehouses.add((Warehouse)this.world[x][y].get_struct());
            ware_nickel--;
            while (nickel_robots > 0) {
                int x_rob = new Random().nextInt(10);
                int y_rob = new Random().nextInt(10);
                if (this.world[x_rob][y_rob].get_water() || this.world[x_rob][y_rob].get_robot()!=null) {
                    System.out.println(true);
                    continue;
                }
                this.world[x_rob][y_rob].set_robot(new Robot(id, x_rob, y_rob, x, y, 0, this, ore.nickel));
                this.robots_nickel.add(this.world[x_rob][y_rob].get_robot());
                nickel_robots--;
                id++;
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

    public Section get_section(int x, int y) throws out_of_bound_exception {
        if (x < 0 || x > 9 || y < 0 || y > 9) {
            throw new out_of_bound_exception();
        } else {
            return this.world[x][y];
        }
    }
    public String toString() {
        String result = "";
        String sep = "";
        for(int i = 0; i < 10 ; i++){sep += "+---";}
        sep += "+\n";
        result += sep;
        for (int i = 0; i < 10; i++) {
            result += "|";
            for (int j = 0; j < 10; j++) {
                if (this.world[i][j].get_struct() != null) {
                    if (this.world[i][j].get_struct() instanceof Mine) {
                        result += "M " + this.world[i][j].get_struct().get_id() + "|";
                    } else {
                        result += "W " + this.world[i][j].get_struct().get_id() + "|";
                    }
                } else if (this.world[i][j].get_water()) {
                    result += "X X|";
                } else {
                    result += "   |";
                }
            }
            result += "\n";
            result += "|";
            for (int j = 0; j < 10; j++) {
                if (this.world[i][j].get_robot() != null) {
                    result += "R " + this.world[i][j].get_robot().get_id() + "|";
                } else if (this.world[i][j].get_water()) {
                    result += "X X|";
                } else {
                    result += "   |";
                }
            }
            result += "\n"+sep;
        }

        result += "\n\n";
        for (Warehouse ware : this.warehouses) {
            result += "Warehouse " + ware.get_id() + ": " + ware.get_storage() + "\n";
        }
        for (Mine mine : this.mines) {
            result += "Mine " + mine.get_id() + ": " + mine.get_storage() + "/" + mine.get_capacity() + "\n";
        }
        for (Robot rob : this.robots_gold) {
            result += "Robot " + rob.get_id() + ": " + rob.get_inventory() + "/" + rob.get_storage() + "\n";
        }
        for (Robot rob : this.robots_nickel) {
            result += "Robot " + rob.get_id() + ": " + rob.get_inventory() + "/" + rob.get_storage() + "\n";
        }
        return result;
    }

    public ArrayList<Robot> get_robot() {
        ArrayList<Robot> result = new ArrayList<Robot>();
        result.addAll(this.robots_gold);
        result.addAll(this.robots_nickel);
        return result;
    }

    public ArrayList<Mine> get_mines() {
        return this.mines;
    }
    public ArrayList<Warehouse> get_warehouses() {
        return this.warehouses;
    }


}