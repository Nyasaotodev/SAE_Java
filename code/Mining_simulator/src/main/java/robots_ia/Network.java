package robots_ia;

import code.*;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;
import vue.Game_GUI;
import vue.Simu_GUI;

import java.util.ArrayList;
import java.util.Objects;

public class Network {
    private Map map;
    private ArrayList<AI> AIs;
    private World world;

    public Network(World world) throws out_of_bound_exception {
        this.world = world;
        this.map = new Map(this.world, this);
        this.AIs = new ArrayList<AI>();
        for (Robot robot : world.get_robot()) {
            this.AIs.add(new AI(this,  robot));
            this.map.refresh(robot);
        }
    }

    public boolean known_destination(Robot robot) {
        if (this.map.getMines() != null) {
            for (Mine mine : this.map.getMines()) {
                if (mine.get_type() == robot.get_type() && mine.get_storage() > 0) {
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
        System.out.println("finding route to " + dest);

        switch (dest) {
            case "exploration": {
                System.out.println("sending route for exploration");
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
                System.out.println("sending best route for mine");
                return nearestMine.getKey();
            }
            case "entrepot": {
                for (Warehouse warehouse : this.map.getWarehouses()) {
                    if (warehouse.get_type() == robot.get_type()) {
                        System.out.println("sending route for warehouse");
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

    public void store(Robot robot) {
        this.AIs.remove(robot);
    }

    public void run_lap() throws InterruptedException {
        for (AI ai : this.AIs) {
            try {
                System.out.println("----------------------\n\nRobot " + ai.getRobot().get_id() + " turn\n\n----------------------\n\n");
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        System.out.print(this.map.getWorldmap()[i][j] + " ");
                    }
                    System.out.println();
                }
                ai.jouer();
                System.out.println("----------------------\n\nRobot " + ai.getRobot().get_id() + " end\n\n----------------------");
            } catch (Exception e) {
                System.out.println(e + "exeption");
            }
        }
        System.out.println("----------------------\n\nEnd of lap\n\n----------------------\n\n");
    }
}
