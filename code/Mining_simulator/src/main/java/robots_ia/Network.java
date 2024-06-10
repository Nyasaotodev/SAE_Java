package robots_ia;

import code.Mine;
import code.World;
import code.Robot;
import javafx.util.Pair;

import java.util.ArrayList;

public class Network {
    private Map map;
    private ArrayList<AI> AIs;
    private World world;

    public Network(World world) {
        this.world = world;
        //this.map = new Map();
        this.AIs = new ArrayList<AI>();
        for (Robot robot : world.get_robot()) {
            AIs.add(new AI(this, map, robot));
            map.refresh(robot);
        }
    }

    public ArrayList<Mine> known_destination(Robot robot) {
        ArrayList<Mine> mines = new ArrayList<Mine>();
        if (map.getMines() != null) {
            for (Mine mine : map.getMines()) {
                if (mine.get_type() == robot.get_type()) {
                    mines.add(mine);
                }
            }
            return mines;
        }
        return null;
    }

    public Pair<String, Integer> route(int[] dest, Robot robot) { //renvoyer tuple (direction String, distance int)

    }

    public Map getMap() {
        return map;
    }

    public ArrayList<AI> getAIs() {
        return AIs;
    }

    public World getWorld() {
        return world;
    }
}
