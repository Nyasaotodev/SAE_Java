package code;
import java.util.*;
public class is_occupied_exception extends Exception {
    public is_occupied_exception() {
        super("A robot is already in this section");
    }

}