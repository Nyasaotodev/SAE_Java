package robots_ia;


import code.Mine;
import code.Robot;

public class AI {
    private Network network;
    private Map map;
    private Robot robot;

    public AI(Network network, Map map, Robot robot) {
        this.network = network;
        this.map = map;
        this.robot = robot;
    }

    public void jouer() {
        if(map.getMines().isEmpty()) {
            map.exploration(this.robot);
        }
        else {
            int temp = 0;
            for(int i = 0; i < this.network.known_destination(this.robot).size; i++) {
                if(this.network.known_destination(this.robot).get(i) instanceof Mine && this.network.known_destination(this.robot).get(i).get_type() == this.robot.get_type()) {
                    temp = this.network.known_destination(this.robot).get(i);
                }
            }
        }
    }

    public Network getNetwork() {
        return network;
    }

    public Map getMap() {
        return map;
    }

    public Robot getRobot() {
        return robot;
    }
}
