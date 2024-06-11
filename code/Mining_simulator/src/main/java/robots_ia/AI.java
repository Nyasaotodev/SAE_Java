package robots_ia;


import code.Mine;
import code.Robot;
import code.Section;
import code.Warehouse;
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
        if (this.robot.get_inventory()<this.robot.get_storage()) { //si le robot n'est pas plein il doit miner
            Section section = network.getWorld().get_section(this.robot.get_pose()[0], this.robot.get_pose()[1]);
            if (section.get_struct() instanceof Mine && section.get_struct().get_storage() > 0) { // si il est dans une mine il mine
                this.robot.mine();
            } else { // sinon il se déplace
                if (network.known_destination(this.robot)) { // si il connais une mine il s'y rend
                    this.robot.move(network.route("mine", this.robot));
                } else { // sinon il explore
                    String route = network.route("exploration", this.robot);
                    if (route != null) {
                        this.robot.move(route);
                    } else if(this.robot.get_inventory()>0) {
                        this.robot.move(network.route("entrepot", this.robot));
                    } else if(section.get_struct() instanceof Warehouse) {
                        network.store(this.robot);
                    } else {
                        this.robot.move(network.route("entrepot", this.robot));
                    }
                }
            }
        } else {// sinon le robot doit se rendre à l'entrepot
            this.robot.move(network.route("entrepot", this.robot));
        } // on refresh la map
        this.network.refresh(this.robot);
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
