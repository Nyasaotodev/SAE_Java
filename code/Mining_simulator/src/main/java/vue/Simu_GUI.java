package vue;

import Controller.Handler;
import code.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import robots_ia.Network;


public class Simu_GUI extends Application {
    private Network network;
    private Integer lap;
    private Integer speed;
    private Boolean pause;
    private boolean completed;
    private Handler handler;
    private Scene scene;

    public Simu_GUI() throws Exception {
        this.network = new Network(new World());
        this.lap = 0;
        this.speed = 1;
        this.pause = true;
        this.completed = false;
        this.scene = new Scene(new Group(),509, 659);
        this.handler = new Handler(network, this, scene);
    }

    @Override
    public void start(Stage stage) throws out_of_bound_exception, InterruptedException {
        stage.setTitle("Mining simulator");
        stage.setScene(scene);
        Buildings(network.getMap().getWorldmap(), network.getWorld(), scene, false);
        stage.show();
    }
    public void Buildings(int[][] worldmap, World w, Scene scene, boolean refresh) throws out_of_bound_exception {
        /* 0 = unknown
         * 1 = land
         * 2 = warehouse
         * 3 = mine
         * 4 = water
         * 5 = robot
         */
        if (refresh) {
            ((Group)scene.getRoot()).getChildren().clear();
        }

        VBox root = new VBox();
        VBox map = new VBox();
        HBox hud = new HBox();
        map.setSpacing(1);
        hud.setSpacing(30);

        Text stats = new Text();
        VBox sim_stats = new VBox();
        stats.setText(stats(network.getMap().getWorldmap(), network.getWorld()));
        Text lap = new Text();
        lap.setText("lap: " + this.lap);
        Text speed = new Text();
        speed.setText("speed: " + this.speed);
        Text pause = new Text();
        pause.setText("paused");
        sim_stats.getChildren().addAll(lap, speed, pause);



        HBox buttons = new HBox();
        Button slow = new Button("slow");
        Button fast = new Button("fast");
        Button start = new Button("run lap");

        slow.setOnMouseClicked(handler);
        fast.setOnMouseClicked(handler);
        start.setOnMouseClicked(handler);

        buttons.getChildren().addAll(slow, start, fast);

        hud.getChildren().addAll(stats, buttons, sim_stats);

        ((Group) scene.getRoot()).getChildren().add(root);

        for (int i = 0; i < worldmap.length; i++) {
            HBox row = new HBox();
            row.setSpacing(1);
            for (int j = 0; j < worldmap[i].length; j++) {
                Rectangle cell = new Rectangle(50, 50);
                Section s = w.get_section(i, j);
                Text txt = new Text();
                Rectangle square = new Rectangle(25,25);
                if (worldmap[i][j] == 0) {
                    cell.setFill(Color.BLACK);
                } else if (worldmap[i][j] == 1) {
                    cell.setFill(Color.GREEN);
                } else if (worldmap[i][j] == 2) {
                    cell.setFill(Color.GREEN);
                    txt.setText("W"+s.get_struct().get_id());
                    if (s.get_struct().get_type().equals(ore.gold))
                        square.setFill(Color.YELLOW);
                    else
                        square.setFill(Color.RED);
                    square.setX(51*j);
                    square.setY(i*51);
                    txt.setX(3.25 + 51*j);
                    txt.setY(17.5 + i*51);
                } else if (worldmap[i][j] == 3) {
                    cell.setFill(Color.GREEN);
                    txt.setText("M"+s.get_struct().get_id());
                    if (s.get_struct().get_type().equals(ore.gold))
                        square.setFill(Color.YELLOW);
                    else
                        square.setFill(Color.RED);
                    square.setX(25+51*j);
                    square.setY(i*51);
                    txt.setX(30.25 + 51*j);
                    txt.setY(17.5 + i*51);
                } else if (worldmap[i][j] == 4) {
                    cell.setFill(Color.BLUE);
                } else if (worldmap[i][j] == 5) {
                    cell.setFill(Color.GREEN);
                    txt.setText("R"+s.get_robot().get_id());
                    if (s.get_robot().get_type().equals(ore.gold))
                        square.setFill(Color.YELLOW);
                    else
                        square.setFill(Color.RED);
                    square.setX(25 + 51*j);
                    square.setY(25 + i*51);
                    txt.setX(30.5 + 51*j);
                    txt.setY(42.5 + i*51);
                }
                row.getChildren().add(cell);
                if (txt.getText() != "") {
                    ((Group)scene.getRoot()).getChildren().addAll(square, txt);
                }
            }
            map.getChildren().add(row);
        }

        root.getChildren().addAll(map, hud);
    }

    public String stats(int[][] worldmap, World w) throws out_of_bound_exception {
        String result ="";
        for (Mine mine : w.get_mines()) {
            if (worldmap[mine.get_x()][mine.get_y()] == 3) {
                result += "Mine " + mine.get_id() + ": " + mine.get_storage() + "\n";
            }
        }
        for (Warehouse warehouse : w.get_warehouses()) {
            if (worldmap[warehouse.get_x()][warehouse.get_y()] == 2) {
                result += "Warehouse " + warehouse.get_id() + ": " + warehouse.get_storage() + "\n";
            }

        }
        for (Robot robot : w.get_robot()){
                    result += "Robot " + robot.get_id() + ": " + robot.get_inventory() + "/" + robot.get_storage() + "\n";
        }

        return result;
    }
    public Integer getLap() {
        return lap;
    }
    public void setLap(Integer lap) {
        this.lap = lap;
    }
    public Integer getSpeed() {
        return speed;
    }
    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
    public Boolean getPause() {
        return pause;
    }
    public void setPause(Boolean pause) {
        this.pause = pause;
    }


    public static void main(String[] args) {
        launch(args);
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean b) {
        this.completed = b;
    }
}
