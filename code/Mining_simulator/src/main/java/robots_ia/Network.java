package robots_ia;

import code.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Objects;

public class Network {
    private Map map;
    private ArrayList<AI> AIs;
    private World world;

    public Network(World world) throws out_of_bound_exception {
        this.world = world;
        //this.map = new Map();
        this.AIs = new ArrayList<AI>();
        for (Robot robot : world.get_robot()) {
            this.AIs.add(new AI(this, this.map, robot));
            this.map.refresh(robot);
        }
    }

    public boolean known_destination(Robot robot) {
        if (this.map.getMines() != null) {
            for (Mine mine : this.map.getMines()) {
                if (mine.get_type() == robot.get_type()) {
                    return true;
                }
            }
        }
        return false;
    }

    public String route(String dest, Robot robot) {
        /* revoie la direction a prendre,
        si mine: renvoie la direction vers la mine la plus proche ( si plusieur mine trouver plus proches
        si entrepot: renvoie la direction vers l'entrepot du robot
        si exploration: renvoie la direction pour explorer la map
         */
        switch (dest) {
            case "exploration": {
                return this.map.exploration(robot);
            }
            case "mine": {
                Pair<String, Integer> nearestMine = new Pair<>("", Integer.MAX_VALUE);
                for (Mine mine : this.map.getMines()) {
                    if (mine.get_type() == robot.get_type()) {
                        Pair<String, Integer> minePath = this.map.dijkstra(new int[]{mine.get_x(), mine.get_y()}, robot);
                        if (minePath.getValue() < nearestMine.getValue()) {
                            nearestMine = minePath;
                        }
                    }
                }
                return nearestMine.getKey();
            }
            case "entrepot": {
                for (Warehouse warehouse : this.map.getWarehouses()) {
                    if (warehouse.get_type() == robot.get_type()) {
                        return this.map.dijkstra(new int[]{warehouse.get_x(), warehouse.get_y()}, robot).getKey();
                    }
                }
            }
            default: {
                return null;
            }
        }
    }

    public void refresh(Robot robot) throws out_of_bound_exception {
        this.map.refresh(robot);
    }

    public Map getMap() {
        return this.map;
    }

    public ArrayList<AI> getAIs() {
        return this.AIs;
    }

    public World getWorld() {
        return this.world;
    }
}
