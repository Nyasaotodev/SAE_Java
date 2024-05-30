package robots_ia;

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

    public void jouer() {}

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
