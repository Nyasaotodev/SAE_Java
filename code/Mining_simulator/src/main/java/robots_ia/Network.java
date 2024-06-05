package robots_ia;

import code.World;
import code.Robot;

import java.util.ArrayList;

public class Network {
    private Map map;
    private ArrayList<AI> AIs;
    private World world;

    public Network(World world) {
        this.world = world;
        this.map = new Map();
        this.AIs = new ArrayList<AI>();
        for (Robot robot : world.get_robot()) {
            AIs.add(new AI(this, map, robot));
            map.refresh(robot);
        }
    }

    public int[] known_destination(Robot robot) {}

    public double route(int[] dest, Robot robot) { //renvoyer tuple (distance int, direction String)

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
