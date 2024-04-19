package code;
public class Section {
    private int x;
    private int y;
    private boolean water;
    private Structure struct;
    private Robot robot;

    // constructor

    public Section(int x, int y) {
        this.x = x;
        this.y = y;
        this.water = false;
        this.struct = null;
        this.robot = null;
    }

    // getters

    public Structure get_struct() {
        return this.struct;
    }

    public Robot get_robot() {
        return this.robot;
    }

    // setters

    public void set_robot(Robot robot) throws Exception {
        if (this.robot != null) {
            throw new is_occupied_exception();
        } else if  (this.water) {
            throw new is_water_exception();
        } else {
            this.robot = robot;
        }
    }

    public void set_struct(Structure struct) {
        this.struct = struct;
    }

    public void set_water(boolean water) { this.water = water; }

    // remove

    public void remove_robot() {
        this.robot = null;
    }



}