package robots_ia;


import code.*;
import javafx.util.Pair;

public class AI {
    private Network network;
    private Robot robot;

    public AI(Network network, Robot robot) {
        this.network = network;
        this.robot = robot;
    }

    public void jouer() throws Exception {
        if (this.robot.get_inventory()<this.robot.get_storage()) { //si le robot n'est pas plein il doit miner
            Section section;
            try {
                section = network.getWorld().get_section(this.robot.get_pose()[0], this.robot.get_pose()[1]);
                if (section.get_struct() instanceof Mine && section.get_struct().get_storage() > 0 && section.get_struct().get_type().equals(this.robot.get_type())) { // si il est dans une mine il mine
                    System.out.println("Robot " + this.robot.get_id() + " mining at "+ this.robot.get_pose()[1] + " " + this.robot.get_pose()[0]);
                    this.robot.mine();
                } else { // sinon il se déplace
                    if (network.known_destination(this.robot)) { // si il connais une mine il s'y rend
                        System.out.println("Robot " + this.robot.get_id() + " known destination at"+network.route("mine", this.robot));
                        this.robot.move(network.route("mine", this.robot));
                    } else { // sinon il explore
                        System.out.println("Robot " + this.robot.get_id() + " exploration");
                        String route = network.route("exploration", this.robot);
                        if (route != null) {
                            System.out.println("Robot " + this.robot.get_id() + " moving to " + route);
                            this.robot.move(route);
                        } else if(this.robot.get_inventory()>0) {
                            System.out.println("Robot " + this.robot.get_id() + " moving to warehouse");
                            this.robot.move(network.route("entrepot", this.robot));
                        } else if(section.get_struct() instanceof Warehouse) {
                            System.out.println("Robot " + this.robot.get_id() + " storing");
                            network.store(this);
                        } else {
                            System.out.println("Robot " + this.robot.get_id() + " moving to warehouse");
                            this.robot.move(network.route("entrepot", this.robot));
                        }
                    }
                }
            }
            catch (out_of_bound_exception e) {
                System.out.println("big Robot " + this.robot.get_id() + " out of bound at "+ this.robot.get_pose()[1] + " " + this.robot.get_pose()[0]);
            }
            catch (Inventory_full_exception e) {
                System.out.println("Robot " + this.robot.get_id() + " inventory full at "+ this.robot.get_pose()[1] + " " + this.robot.get_pose()[0]);
            }
            catch (Not_in_mine_exception e) {
                System.out.println("Robot " + this.robot.get_id() + " not in mine at "+ this.robot.get_pose()[1] + " " + this.robot.get_pose()[0]);
            }
            catch (Wrong_type_exception e) {
                System.out.println("Robot " + this.robot.get_id() + " wrong type at "+ this.robot.get_pose()[1] + " " + this.robot.get_pose()[0]);
            }
            catch (is_water_exception e) {
                System.out.println("Robot " + this.robot.get_id() + " is in water at "+ this.robot.get_pose()[1] + " " + this.robot.get_pose()[0]);
            }

        } else {// sinon le robot doit se rendre à l'entrepot
            this.robot.move(network.route("entrepot", this.robot));
        } // on refresh la map
        this.network.refresh(this.robot);
    }



    public Network getNetwork() {
        return network;
    }
    public Robot getRobot() {
        return robot;
    }
}
