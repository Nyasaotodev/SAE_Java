package code;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {
        World world = new World();
        int arret = 0;
        while (arret == 0) {
            for (Robot robot : world.get_robot()) {
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
                    }
                }
                catch (Exception e) {
                    System.out.println("Vous ne pouvez pas avancer là");
                    TimeUnit.SECONDS.sleep(3);
                }
            }
        }
    }
}
