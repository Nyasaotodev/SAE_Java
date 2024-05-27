package Controller;

import Components.Map;
import code.Robot;
import code.World;
import code.out_of_bound_exception;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import vue.Game_GUI;

import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;

public class Session {

    private World world;
    private ArrayDeque<Robot> robots = new ArrayDeque<Robot>();
    private Game_GUI gui;
    private Scene scene;
    public Session(World world, Game_GUI gui, Scene scene) {
        this.world = world;
        for (Robot world_robot : world.get_robot()) {
            robots.offer(world_robot);
        }
        this.gui = gui;
        gui.set_current(robots.getFirst());
        this.scene = scene;
    }
    public void action(String choice) throws InterruptedException, out_of_bound_exception {
        int arret = 0;
        Boolean success;
            success = true;
            Robot robot = robots.getFirst();
            try {
                if (choice == "up") {
                    robot.move("N");
                    Rectangle square = new Rectangle(25,25);
                    square.setFill(Color.GREEN);
                    square.setX(25 + 51*robot.get_pose()[1]);
                    square.setY(25 + (robot.get_pose()[0]+1)*51);
                    ((Group)scene.getRoot()).getChildren().add(square);
                } else if (choice == "down") {
                    robot.move("S");
                    Rectangle square = new Rectangle(25,25);
                    square.setFill(javafx.scene.paint.Color.GREEN);
                    square.setX(25 + 51*robot.get_pose()[1]);
                    square.setY(25 + (robot.get_pose()[0]-1)*51);
                    ((Group)scene.getRoot()).getChildren().add(square);
                } else if (choice == "left") {
                    robot.move("O");
                    Rectangle square = new Rectangle(25,25);
                    square.setFill(javafx.scene.paint.Color.GREEN);
                    square.setX(25 + 51*(robot.get_pose()[1]+1));
                    square.setY(25 + robot.get_pose()[0]*51);
                    ((Group)scene.getRoot()).getChildren().add(square);
                } else if (choice == "right") {
                    robot.move("E");
                    Rectangle square = new Rectangle(25,25);
                    square.setFill(javafx.scene.paint.Color.GREEN);
                    square.setX(25 + 51*(robot.get_pose()[1]-1));
                    square.setY(25 + (robot.get_pose()[0])*51);
                    ((Group)scene.getRoot()).getChildren().add(square);
                } else if (choice == "mine") {
                    robot.mine();
                } else {
                    success = false;
                    TimeUnit.SECONDS.sleep(2);
                }
            } catch (Exception e) {
                success = false;
                System.out.println(e.getMessage());
                TimeUnit.SECONDS.sleep(2);
            }
            if (success) {
                System.out.println("moved succesfully");
                robots.poll();
                robots.offer(robot);
                gui.set_current(robots.getFirst());
                ((VBox)((VBox)((HBox)scene.getRoot().getChildrenUnmodifiable().getFirst()).getChildren().getLast()).getChildren().getFirst()).getChildren().clear();
                ((VBox)((VBox)((HBox)scene.getRoot().getChildrenUnmodifiable().getFirst()).getChildren().getLast()).getChildren().getFirst()).getChildren().add(new Text(gui.stats()));
                ((VBox)((VBox)((HBox)scene.getRoot().getChildrenUnmodifiable().getFirst()).getChildren().getLast()).getChildren().getFirst()).getChildren().add(new Text("current robot:" + robots.getFirst().get_id()));
                gui.Buildings(world, scene);
                }

            }
            }



