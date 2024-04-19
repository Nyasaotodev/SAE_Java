package code;
import java.util.*;

public class Structure {
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

    public ore get_type() {
        return this.type;
    }

}