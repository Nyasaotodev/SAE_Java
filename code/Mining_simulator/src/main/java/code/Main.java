package code;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.ArrayDeque;

public class Main {
    public static void main(String[] args) throws Exception {
        World world = new World();
        int arret = 0;
        Boolean success;
        ArrayDeque<Robot> robots = new ArrayDeque<Robot>();
        for (Robot world_robot : world.get_robot()) {
            robots.offer(world_robot);
        }
        while (arret == 0) {
            success = true;
            Robot robot = robots.getFirst();
            System.out.println(world);
            System.out.println(robot);
            Scanner scanner = new Scanner(System.in);
            System.out.println("1. Move | 2. Mine | 3. Exit");
            int choice = scanner.nextInt();
            try {
                if (choice == 1) {
                    System.out.println("N. North | S. South | O. West | E. Est");
                    String direction = scanner.next();
                    robot.move(direction);
                } else if (choice == 2) {
                    robot.mine();
                } else if (choice == 3) {
                    arret += 1;
                    break;
                } else {
                    success = false;
                    System.out.println("Invalid choice");
                    TimeUnit.SECONDS.sleep(2);
                }
            }
            catch (Exception e) {
                success = false;
                System.out.println(e.getMessage());
                TimeUnit.SECONDS.sleep(2);
            }
            if (success) {
                robots.poll();
                robots.offer(robot);
            }

            int i;
            for (i=0 ; i<10 ; i++) {
                System.out.println("\n");
                }

        }
    }
}
