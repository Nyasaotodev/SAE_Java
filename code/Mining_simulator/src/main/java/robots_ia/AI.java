package robots_ia;


import code.Mine;
import code.Robot;
import javafx.util.Pair;

public class AI {
    private Network network;
    private Map map;
    private Robot robot;

    public AI(Network network, Map map, Robot robot) {
        this.network = network;
        this.map = map;
        this.robot = robot;
    }

    public void jouer() throws Exception {
        if(network.known_destination(this.robot) == null) {
            map.exploration(this.robot);
        }
        else {
            Pair <String, Integer> shorter = new Pair<>("", 100);
            for(Mine mine: map.getMines()) {
                    Pair <String, Integer> route = network.route(new int[]{mine.get_x(), mine.get_y()}, this.robot);
                    if(route.getValue() < shorter.getValue()) {
                        shorter = route;
                    }
                }
            robot.move(shorter.getKey());

            }
        }


    public Network getNetwork() {
        return network;
    }
    public Map getMap() {
        return this.map;
    }
    public Robot getRobot() {
        return robot;
    }
}
