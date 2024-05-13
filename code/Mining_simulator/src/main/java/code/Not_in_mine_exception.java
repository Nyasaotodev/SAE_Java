package code;

public class Not_in_mine_exception extends Exception {
    public Not_in_mine_exception() {
        super("le robot n'est pas dans une mine");
    }
}