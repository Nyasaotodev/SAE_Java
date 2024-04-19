package code;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        World world = new World();
        while (true) {
            System.out.println(world);
            for (Robot robot : world.get_robot()) {
                System.out.println(robot);
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. Move | 2. Mine | 3. Exit");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    System.out.println("N. North | S. South | O. West | E. Est");
                    String direction = scanner.next();
                    robot.move(direction);
                } else if (choice == 2) {
                    robot.mine();
                } else if (choice == 3) {
                    System.exit(0);
                }
            }
        }
    }
}
