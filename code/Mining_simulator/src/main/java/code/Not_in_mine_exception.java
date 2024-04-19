package code;

public class Not_in_mine_exception extends Exception {
    public Not_in_mine_exception() {
        super("La mine n'est pas du bon type");
    }
}