package Components;

import code.Section;
import code.World;
import code.out_of_bound_exception;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Map extends VBox {
    public Map(World w) throws out_of_bound_exception {
        super();
        for (int i = 0; i < 10; i++) {
            HBox row = new HBox();
            row.setSpacing(1);
            for (int j = 0; j < 10; j++) {
                Section s = w.get_section(i, j);
                Rectangle square = new Rectangle(50,50);
                if (s.get_water())
                    square.setFill(Color.BLUE);
                else
                    square.setFill(Color.GREEN);
                row.getChildren().add(square);
            }
            this.setSpacing(1);
            this.getChildren().add(row);
        }
    }
}
