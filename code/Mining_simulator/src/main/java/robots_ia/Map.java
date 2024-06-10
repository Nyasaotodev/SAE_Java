package robots_ia;

import code.*;
import javafx.util.Pair;

import java.util.ArrayList;

public class Map {
    private World world;
    private Network network;
    private int[][] worldmap;
    private ArrayList<Mine> mines;
    private ArrayList<Warehouse> warehouses;

    public Map(World world, Network network) throws out_of_bound_exception {
        this.world = world;
        this.network = network;

        /* 0 = unknown
         * 1 = land
         * 2 = warehouse
         * 3 = mine
         * 4 = water
         * 5 = robot
         */
        this.worldmap = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.worldmap[i][j] = 0;
            }
        }
        this.mines = new ArrayList<>();
        this.warehouses = new ArrayList<>();

        for (Warehouse warehouse : world.get_warehouses()) {
            this.warehouses.add(warehouse);
            this.worldmap[warehouse.get_x()][warehouse.get_y()] = 2;
            for (int[] around : this.world.around(warehouse.get_x(), warehouse.get_y())) {
                Section section = this.world.get_section(around[0], around[1]);
                if (section.get_struct() instanceof Mine mine) {
                    this.mines.add(mine);
                    this.worldmap[mine.get_x()][mine.get_y()] = 3;
                } else if (section.get_struct() instanceof Warehouse warehouse1) {
                    this.warehouses.add(warehouse1);
                    this.worldmap[warehouse1.get_x()][warehouse1.get_y()] = 2;
                } else if (section.get_water()) {
                    this.worldmap[around[0]][around[1]] = 4;
                } else if (section.get_robot() != null) {
                    this.worldmap[around[0]][around[1]] = 5;
                } else {
                    this.worldmap[around[0]][around[1]] = 1;
                }
            }
        }
        for (Robot robot : world.get_robot()) {
            this.worldmap[robot.get_pose()[0]][robot.get_pose()[1]] = 5;
            for (int[] around : this.world.around(robot.get_pose()[0], robot.get_pose()[1])) {
                Section section = this.world.get_section(around[0], around[1]);
                if (section.get_struct() instanceof Mine mine) {
                    this.mines.add(mine);
                    this.worldmap[mine.get_x()][mine.get_y()] = 3;
                } else if (section.get_struct() instanceof Warehouse warehouse1) {
                    this.warehouses.add(warehouse1);
                    this.worldmap[warehouse1.get_x()][warehouse1.get_y()] = 2;
                } else if (section.get_water()) {
                    this.worldmap[around[0]][around[1]] = 4;
                } else if (section.get_robot() != null) {
                    this.worldmap[around[0]][around[1]] = 5;
                } else {
                    this.worldmap[around[0]][around[1]] = 1;
                }
            }
        }
    }

    public Pair<String, Integer> dijkstra(int[] dest, Robot robot) {
        //TODO retourne un tuple avec la direction et la distance
        return null;
    }

    public String exploration(Robot robot) {
        //TODO retourne la direction pour explorer la map
        return null;
    }

    public void refresh(Robot robot) throws out_of_bound_exception {
        for (int[] around : this.world.around(robot.get_pose()[0], robot.get_pose()[1])) {
            Section section = this.world.get_section(around[0], around[1]);
            if (section.get_struct() instanceof Mine mine) {
                this.mines.add(mine);
                this.worldmap[mine.get_x()][mine.get_y()] = 3;
            } else if (section.get_struct() instanceof Warehouse warehouse1) {
                this.warehouses.add(warehouse1);
                this.worldmap[warehouse1.get_x()][warehouse1.get_y()] = 2;
            } else if (section.get_water()) {
                this.worldmap[around[0]][around[1]] = 4;
            } else if (section.get_robot() != null) {
                this.worldmap[around[0]][around[1]] = 5;
            } else {
                this.worldmap[around[0]][around[1]] = 1;
            }
        }
    }

    public World getWorld() {
        return world;
    }

    public int[][] getWorldmap() {
        return worldmap;
    }

    public Network getNetwork() {
        return network;
    }

    public ArrayList<Mine> getMines() {
        return mines;
    }

    public ArrayList<Warehouse> getWarehouses() {
        return warehouses;
    }
}
