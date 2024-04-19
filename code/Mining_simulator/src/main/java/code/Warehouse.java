package code;
import java.util.*;
abstract public class Warehouse extends Structure {
    public Warehouse(int id_struc, int x, int y, ore type) {
        super(id_struc, x, y, type);
    }
    public void add(int nb) {
        this.storage += nb;
    }

}