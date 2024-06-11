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
            this.AIs.add(new AI(this, this.map, robot));
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

    public void store(Robot robot) {
        this.AIs.remove(robot);
    }

    public void run_lap(Scene scene, Simu_GUI simu_gui, Text lap, Text stats) throws InterruptedException {
        while (!simu_gui.getCompleted()) {
            try {
                Thread.sleep(1000*simu_gui.getSpeed());
                simu_gui.setLap(simu_gui.getLap() + 1);
                lap.setText("lap: " + simu_gui.getLap());
                for (AI ai : this.AIs) {
                    try {
                        ai.jouer();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                stats.setText(simu_gui.stats(this.map.getWorldmap(), this.world));
                simu_gui.Buildings(this.map.getWorldmap(), (VBox) scene.getRoot().getChildrenUnmodifiable().getFirst(), this.world, scene);
            } catch (InterruptedException e) {
                System.out.println(e);
            } catch (out_of_bound_exception e) {
                throw new RuntimeException(e);
            }
            if (this.AIs.isEmpty()) {
                simu_gui.setCompleted(true);
            }
        }

    }
}
