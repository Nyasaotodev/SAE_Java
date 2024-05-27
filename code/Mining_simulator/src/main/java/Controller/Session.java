package Controller;

import code.Robot;
import code.World;
import javafx.scene.Scene;
import vue.Game_GUI;

import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;

public class Session {

    private World world;
    private ArrayDeque<Robot> robots = new ArrayDeque<Robot>();
    private Game_GUI gui;
    public Session(World world, Game_GUI gui) {
        this.world = world;
        for (Robot world_robot : world.get_robot()) {
            robots.offer(world_robot);
        }
        this.gui = gui;
        gui.set_current(robots.getFirst());
    }
    public void action(String choice) throws InterruptedException {
        int arret = 0;
        Boolean success;
            success = true;
            Robot robot = robots.getFirst();
            try {
                if (choice == "up") {
                    robot.move("N");
                } else if (choice == "down") {
                    robot.move("S");
                } else if (choice == "left") {
                    robot.move("O");
                } else if (choice == "right") {
                    robot.move("E");
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
            }
            }

        }

