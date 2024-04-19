package code;
import java.util.*;

public class Mine extends Structure {
    private int capacity;
    public Mine(int id_struc, int x, int y, ore type) {
        super(id_struc, x, y, type);
        this.capacity = new Random().nextInt(50)+50;
        this.storage = this.capacity;
    }
    public int remove(int nb) throws is_empty_exception {
        if (this.storage == 0) {
            throw new is_empty_exception();
        } else if (this.storage < nb) {
            int result = this.storage;
            this.storage = 0;
            return result;
        } else {
            this.storage -= nb;
            return nb;
        }
    }
    public int get_capacity() {
        return this.capacity;
    }
    public void add(int nb){}


}