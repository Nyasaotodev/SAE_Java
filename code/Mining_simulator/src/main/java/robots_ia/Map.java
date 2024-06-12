package robots_ia;

import code.*;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Map {
    private World world;
    private Network network;
    private int[][] worldmap;
    private ArrayList<Mine> mines;
    private ArrayList<Warehouse> warehouses;

    public Map(World world, Network network) throws out_of_bound_exception {
        this.world = world;
        this.network = network;

        /* 0 = unknown
         * 1 = land
         * 2 = warehouse
         * 3 = mine
         * 4 = water
         * 5 = robot
         */
        this.worldmap = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.worldmap[i][j] = 0;
            }
        }
        this.mines = new ArrayList<>();
        this.warehouses = new ArrayList<>();

        for (Warehouse warehouse : world.get_warehouses()) {
            this.warehouses.add(warehouse);
            this.worldmap[warehouse.get_x()][warehouse.get_y()] = 2;
            for (int[] around : this.world.around(warehouse.get_x(), warehouse.get_y())) {
                Section section = this.world.get_section(around[0], around[1]);
                if (section.get_struct() instanceof Mine mine) {
                    this.mines.add(mine);
                    this.worldmap[mine.get_x()][mine.get_y()] = 3;
                } else if (section.get_struct() instanceof Warehouse warehouse1) {
                    this.worldmap[warehouse1.get_x()][warehouse1.get_y()] = 2;
                } else if (section.get_water()) {
                    this.worldmap[around[0]][around[1]] = 4;
                } else if (section.get_robot() != null) {
                    this.worldmap[around[0]][around[1]] = 5;
                } else {
                    this.worldmap[around[0]][around[1]] = 1;
                }
            }
        }
        for (Robot robot : world.get_robot()) {
            this.worldmap[robot.get_pose()[0]][robot.get_pose()[1]] = 5;
            for (int[] around : this.world.around(robot.get_pose()[0], robot.get_pose()[1])) {
                Section section = this.world.get_section(around[0], around[1]);
                if (section.get_struct() instanceof Mine mine) {
                    if (!this.mines.contains(mine)) {
                        this.mines.add(mine);
                    }
                    this.worldmap[mine.get_x()][mine.get_y()] = 3;
                } else if (section.get_struct() instanceof Warehouse warehouse1) {
                    this.worldmap[warehouse1.get_x()][warehouse1.get_y()] = 2;
                } else if (section.get_water()) {
                    this.worldmap[around[0]][around[1]] = 4;
                } else if (section.get_robot() != null) {
                    this.worldmap[around[0]][around[1]] = 5;
                } else {
                    this.worldmap[around[0]][around[1]] = 1;
                }
            }
        }
    }

    public Pair<String, Integer> dijkstra(int[] dest, Robot robot) {
        System.out.println("searching route with dijkstra");
        int distance = 1;
        int[] pointeur = dest;
        ArrayList<int[]> file = new ArrayList<>();
        file.add(pointeur);
        int[][] wMap = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                wMap[i][j] = 0;
            }
        }
        wMap[pointeur[0]][pointeur[1]] = -1;

        int cpt_dist = 4;
        int cpt = 1;
        while (!file.isEmpty() && wMap[robot.get_pose()[0]][robot.get_pose()[1]] == 0) {
            int[] current = file.getFirst();
            file.removeFirst();

            if (current[0] < 9) {
                if (wMap[current[0]+1][current[1]] == 0
                        && this.worldmap[current[0]+1][current[1]] != 4
                        && this.worldmap[current[0]+1][current[1]] != 5) {
                    wMap[current[0]+1][current[1]] = distance;
                    file.add(new int[]{current[0]+1, current[1]});
                }}
            if (current[0] > 0) {
                if (wMap[current[0]-1][current[1]] == 0
                        && this.worldmap[current[0]-1][current[1]] != 4
                        && this.worldmap[current[0]-1][current[1]] != 5) {
                    wMap[current[0]-1][current[1]] = distance;
                    file.add(new int[]{current[0]-1, current[1]});
                }}
            if (current[1] < 9) {
                if (wMap[current[0]][current[1]+1] == 0
                        && this.worldmap[current[0]][current[1]+1] != 4
                        && this.worldmap[current[0]][current[1]+1] != 5) {
                    wMap[current[0]][current[1]+1] = distance;
                    file.add(new int[]{current[0], current[1]+1});
                }}
            if (current[1] > 0) {
                if (wMap[current[0]][current[1]-1] == 0
                        && this.worldmap[current[0]][current[1]-1] != 4
                        && this.worldmap[current[0]][current[1]-1] != 5) {
                    wMap[current[0]][current[1]-1] = distance;
                    file.add(new int[]{current[0], current[1]-1});
                }}
            cpt_dist--;
            if (cpt_dist ==0 ) {
                distance++;
                cpt++;
                cpt_dist = 4*cpt;
            }
        }

        String minimum ="";
        int[] dir = new int[4];
        if (robot.get_pose()[0] < 9) {
            dir[0] = wMap[robot.get_pose()[0]+1][robot.get_pose()[1]];
        } else {
            dir[0] = 100;
        }
        if (robot.get_pose()[0] > 0) {
            dir[1] = wMap[robot.get_pose()[0]-1][robot.get_pose()[1]];
        } else {
            dir[1] = 100;
        }
        if (robot.get_pose()[1] < 9) {
            dir[2] = wMap[robot.get_pose()[0]][robot.get_pose()[1]+1];
        } else {
            dir[2] = 100;
        }
        if (robot.get_pose()[1] > 0) {
            dir[3] = wMap[robot.get_pose()[0]][robot.get_pose()[1]-1];
        } else {
            dir[3] = 100;
        }

        int min = 100;
        for (int i = 0; i < 4; i++) {
            if (dir[i] < min) {
                min = dir[i];
                switch (i) {
                    case 0 -> minimum = "S";
                    case 1 -> minimum = "N";
                    case 2 -> minimum = "E";
                    case 3 -> minimum = "W";
                }
            }
        }
        System.out.println("route found"+ new Pair<>(minimum, wMap[robot.get_pose()[0]][robot.get_pose()[1]]));
        return new Pair<>(minimum, wMap[robot.get_pose()[0]][robot.get_pose()[1]]);


    }



    public String exploration(Robot robot) {
        int distance = 1;
        int[] pointeur = robot.get_pose();
        ArrayList<int[]> file = new ArrayList<>();
        file.add(pointeur);
        ArrayList<int[]> unknown = new ArrayList<int[]>();
        int[][] wMap = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                wMap[i][j] = 0;
            }
        }
        wMap[pointeur[0]][pointeur[1]] = -1;

        int cpt_dist = 4;
        int cpt = 1;
        int cap = 1000;
        while (!file.isEmpty() && distance < cap) {
            System.out.println("searching route for exploration");
            int[] current = file.getFirst();
            file.removeFirst();

            if (current[0] < 9 && this.worldmap[current[0]+1][current[1]] != 0) {
                if (wMap[current[0]+1][current[1]] == 0
                        && this.worldmap[current[0]+1][current[1]] != 4
                        && this.worldmap[current[0]+1][current[1]] != 5) {
                    wMap[current[0]+1][current[1]] = distance;
                    file.add(new int[]{current[0]+1, current[1]});
                } else {
                    unknown.add(new int[]{current[0]+1, current[1]});
                    cap = distance;
                    }}
            if (current[0] > 0 && this.worldmap[current[0]-1][current[1]] != 0) {
                if (wMap[current[0]-1][current[1]] == 0
                        && this.worldmap[current[0]-1][current[1]] != 4
                        && this.worldmap[current[0]-1][current[1]] != 5) {
                    wMap[current[0]-1][current[1]] = distance;
                    file.add(new int[]{current[0]-1, current[1]});
                } else {
                    unknown.add(new int[]{current[0]-1, current[1]});
                    cap = distance;
                }}
            if (current[1] < 9 && this.worldmap[current[0]][current[1]+1] != 0) {
                if (wMap[current[0]][current[1]+1] == 0
                        && this.worldmap[current[0]][current[1]+1] != 4
                        && this.worldmap[current[0]][current[1]+1] != 5) {
                    wMap[current[0]][current[1]+1] = distance;
                    file.add(new int[]{current[0], current[1]+1});
                } else {
                    unknown.add(new int[]{current[0], current[1]+1});
                    cap = distance;
                }}
            if (current[1] > 0 && this.worldmap[current[0]][current[1]-1] != 0) {
                if (wMap[current[0]][current[1]-1] == 0
                        && this.worldmap[current[0]][current[1]-1] != 4
                        && this.worldmap[current[0]][current[1]-1] != 5) {
                    wMap[current[0]][current[1]-1] = distance;
                    file.add(new int[]{current[0], current[1]-1});
                } else {
                    unknown.add(new int[]{current[0], current[1]-1});
                    cap = distance;
                }}
            cpt_dist--;
            if (cpt_dist ==0 ) {
                distance++;
                cpt++;
                cpt_dist = 4*cpt;
            }
        }

        System.out.println("out of while");

        if (unknown.isEmpty()) {
            System.out.println("no exploration route found");
            return null;
        } else {
            System.out.println("Calculating best exploration route");
            try {
                int min = 100;
                int[] min_pos = new int[2];
                for (int[] pos : unknown) {
                    if (wMap[pos[0]][pos[1]] < min) {
                        min = wMap[pos[0]][pos[1]];
                        min_pos = pos;
                    }
                }
                for (int[] pos : unknown) {
                    if (wMap[pos[0]][pos[1]] > wMap[min_pos[0]][min_pos[1]]) {
                        unknown.remove(pos);
                    }
                }
            } catch (Exception e) {
                System.out.println("error occured while calculating best exploration route");
            }
            if (unknown.size() == 1) {
                System.out.println("exploration route found "+ dijkstra(unknown.get(0), robot).getKey());
                return dijkstra(unknown.get(0), robot).getKey();
            }

            int[] nbr_unknown_adjascents = new int[unknown.size()];


            for (int i = 0; i < unknown.size(); i++) {
                ArrayList<int[]> file_unknown = new ArrayList<>();
                file_unknown.add(unknown.get(i));
                ArrayList<int[]> unknown_already_seen = new ArrayList<>();


                while (!file_unknown.isEmpty()) {
                    int[] current = file_unknown.get(0);
                    unknown_already_seen.add(current);
                    file_unknown.remove(current);

                    if (current[0] + 1 < 9) {
                        if (worldmap[current[0] + 1][current[1]] == 0 && !unknown_already_seen.contains(new int[]{current[0] + 1, current[1]})) {
                            nbr_unknown_adjascents[i]++;
                            file_unknown.add(new int[]{current[0] + 1, current[1]});
                        }
                    }
                    if (current[0] - 1 > 0) {
                        if (worldmap[current[0] - 1][current[1]] == 0 && !unknown_already_seen.contains(new int[]{current[0] - 1, current[1]})) {
                            nbr_unknown_adjascents[i]++;
                            file_unknown.add(new int[]{current[0] - 1, current[1]});
                        }
                    }
                    if (current[1] + 1 < 9) {
                        if (worldmap[current[0]][current[1] + 1] == 0 && !unknown_already_seen.contains(new int[]{current[0], current[1] + 1})) {
                            nbr_unknown_adjascents[i]++;
                            file_unknown.add(new int[]{current[0], current[1] + 1});
                        }
                    }
                    if (current[1] - 1 > 0) {
                        if (worldmap[current[0]][current[1] - 1] == 0 && !unknown_already_seen.contains(new int[]{current[0], current[1] - 1})) {
                            nbr_unknown_adjascents[i]++;
                            file_unknown.add(new int[]{current[0], current[1] - 1});
                        }
                    }
                    System.out.println("file_unknow"+file_unknown);

                }
            }

            int max = 0;
            int max_pos = 0;
            for (int i = 0; i < unknown.size(); i++) {
                if (nbr_unknown_adjascents[i] > max) {
                    max = nbr_unknown_adjascents[i];
                    max_pos = i;
                }
            }
            System.out.println("exploration route found"+ dijkstra(unknown.get(max_pos), robot).getKey());
            return dijkstra(unknown.get(max_pos), robot).getKey();
        }
    }

    public void refresh(Robot robot) {
        this.worldmap[robot.get_pose()[0]][robot.get_pose()[1]] = 5;
        for (int[] around : this.world.around(robot.get_pose()[0], robot.get_pose()[1])) {
            Section section;
            try {
                section = this.world.get_section(around[0], around[1]);
            } catch (out_of_bound_exception e) {
                System.out.println("coucou");
                continue;
            }
            if (section.get_struct() instanceof Mine mine) {
                if(!this.mines.contains(mine)) {
                    this.mines.add(mine);
                }
                this.worldmap[mine.get_x()][mine.get_y()] = 3;
            } else if (section.get_water()) {
                this.worldmap[around[0]][around[1]] = 4;
            } else if (section.get_robot() != null) {
                this.worldmap[around[0]][around[1]] = 5;
            } else {
                this.worldmap[around[0]][around[1]] = 1;
            }
        }
    }

    public World getWorld() {
        return world;
    }

    public int[][] getWorldmap() {
        return worldmap;
    }

    public Network getNetwork() {
        return network;
    }

    public ArrayList<Mine> getMines() {
        return mines;
    }

    public ArrayList<Warehouse> getWarehouses() {
        return warehouses;
    }

}
