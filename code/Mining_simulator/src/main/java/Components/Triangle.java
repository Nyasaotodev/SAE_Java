package Components;

import javafx.scene.shape.Polygon;

public class Triangle extends Polygon {

    private String text = "";
    public Triangle(double x, double y, double size, double angle) {
        super();
        double x1 = x + size * Math.cos(Math.toRadians(angle));
        double y1 = y + size * Math.sin(Math.toRadians(angle));
        double x2 = x + size * Math.cos(Math.toRadians(angle + 120));
        double y2 = y + size * Math.sin(Math.toRadians(angle + 120));
        double x3 = x + size * Math.cos(Math.toRadians(angle + 240));
        double y3 = y + size * Math.sin(Math.toRadians(angle + 240));
        this.getPoints().addAll(new Double[]{x1, y1, x2, y2, x3, y3});
    }
    
    public void set_text(String text) {
        this.text = text;
    }

    public String get_text() {
        return this.text;
    }
}