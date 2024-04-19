package code;
import java.util.*;

abstract  public class Structure {
    protected int id_struc;
    protected int storage;
    protected int x;
    protected int y;
    protected  ore type;

    public Structure(int id_struc, int x, int y, ore type) {
        this.id_struc = id_struc;
        this.storage = 0;
        this.x = x;
        this.y = y;
        this.type = type;

    }
    public int get_storage() {
        return this.storage;
    }
  
    public int get_id() {
        return this.id_struc;
    }

    public int get_x() {
        return this.x;
    }
    public int get_y() {
        return this.y;
      
    public ore get_type() {
        return this.type;
    }


    abstract public int remove(int efficiency) throws Exception;

}