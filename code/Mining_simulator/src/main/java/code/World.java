package code;
import java.util.*;
public class World {

    private Section[][] sections = new Section[10][10];
    private ArrayList<Robot> robots_gold = new ArrayList<Robot>();
    private ArrayList<Robot> robots_nickel = new ArrayList<Robot>();

    public World() {
    }

    public Section get_section(int x, int y) {
        return this.sections[x][y];
    }
    public void display() {
        // TODO implement here
    }

}