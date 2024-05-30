package robots_ia;

import code.*;

import java.util.ArrayList;

public class Map {
    private World world;
    private Network network;
    private int[][] worldmap;
    private ArrayList<Mine> mines;
    private ArrayList<Warehouse> warehouses;

    public Map(World world, Network network) {
        this.world = world;
        this.network = network;
        this.worldmap = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.worldmap[i][j] = 0;
            }
        }
        this.mines = new ArrayList<Mine>();
        this.warehouses = new ArrayList<Warehouse>();
    }

    public String dijkstra(int[] dest, Robot robot) {}

    public String exploration(Robot robot) {}

    public void refresh(Robot robot) {
        //TODO implement to complete network builder
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
