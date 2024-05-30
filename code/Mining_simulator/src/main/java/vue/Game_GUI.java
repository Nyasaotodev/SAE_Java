package vue;

import Components.Map;
import Components.Triangle;
import Controller.Session;
import code.*;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Game_GUI extends Application {
    private World world = new World();
    private Robot current;

    public Game_GUI() throws Exception {
    }

    public void set_current(Robot robot) {
        this.current = robot;
    }
    public World get_world() {
        return this.world;
    }
    private Session session;
    public Session get_session() {
        return this.session;
    }
    private Map map;

    @Override
    public void start(Stage stage) throws out_of_bound_exception {
        stage.setTitle("Mining simulator");
        HBox root = new HBox();
        Scene scene = new Scene(new Group(),800, 509);
        Session session = new Session(world, this, scene);
        stage.setScene(scene);
        ((Group) scene.getRoot()).getChildren().add(root);
        map = new Map(world);
        VBox side = new VBox();
        VBox texts = new VBox();
            Text stats = new Text();
            stats.setText(stats());
            Text current = new Text();
            current.setText("current robot:" + this.current.get_id());
            VBox controller = new VBox();
                HBox firstRow = new HBox();
                    Rectangle square = new Rectangle(49,50);
                    square.setFill(Color.WHITE);
                    Rectangle square2 = new Rectangle(50,50);
                    square2.setFill(Color.WHITE);

                    Triangle up = new Triangle(50, 50, 33, 270);
                    up.setFill(Color.BLACK);
                    up.set_text("up");
                    up.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, new EventHandler(){

                        @Override
                        public void handle(Event event) {
                            try {
                                session.action("up");
                            } catch (InterruptedException | out_of_bound_exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

                    firstRow.setSpacing(1);
                    firstRow.getChildren().addAll(square, up, square2);

                HBox secondRow = new HBox();
                    Triangle left = new Triangle(50, 50, 32, 180);
                    left.setFill(Color.BLACK);
                    left.set_text("left");
                    left.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, new EventHandler(){

                        @Override
                        public void handle(Event event) {
                            try {
                                session.action("left");
                            } catch (InterruptedException | out_of_bound_exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

                    Rectangle square5 = new Rectangle(50,50);
                    square5.setFill(Color.WHITE);
                    Rectangle square6 = new Rectangle(50,50);
                    square6.setFill(Color.WHITE);

                    Triangle right = new Triangle(50, 50, 32, 0);
                    right.setFill(Color.BLACK);
                    right.set_text("right");
                    right.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, new EventHandler(){

                        @Override
                        public void handle(Event event) {
                            try {
                                session.action("right");
                            } catch (InterruptedException | out_of_bound_exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

                    Rectangle action = new Rectangle(50,50);
                    action.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, new EventHandler(){

                        @Override
                        public void handle(Event event) {
                            try {
                                session.action("mine");
                            } catch (InterruptedException | out_of_bound_exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

                    secondRow.setSpacing(3.5);
                    secondRow.getChildren().addAll(left, square5, right, square6, action);

                HBox thirdRow = new HBox();
                    Rectangle square3 = new Rectangle(49,50);
                    square3.setFill(Color.WHITE);
                    Rectangle square4 = new Rectangle(50,50);
                    square4.setFill(Color.WHITE);

                    Triangle down = new Triangle(50, 50, 33, 90);
                    down.set_text("down");
                    down.setFill(Color.BLACK);
                    down.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, new EventHandler(){

                        @Override
                        public void handle(Event event) {
                            try {
                                session.action("down");
                            } catch (InterruptedException | out_of_bound_exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

                    thirdRow.getChildren().addAll(square3, down, square4);

            controller.setSpacing(1);
            controller.getChildren().addAll(firstRow, secondRow, thirdRow);

        Buildings(world, scene);
        texts.getChildren().addAll(stats, current);
        side.getChildren().addAll(texts, controller);
        root.getChildren().addAll(map, side);
        stage.show();
    }
    public void Buildings(World w, Scene scene) throws out_of_bound_exception {
        for (int i = 0; i < 10; i++) {
            HBox row = new HBox();
            for (int j = 0; j < 10; j++) {
                Section s = w.get_section(i, j);
                Structure struct = s.get_struct();
                if (struct instanceof Mine) {
                    Rectangle square = new Rectangle(25,25);
                    Text txt = new Text();
                    txt.setText("M"+struct.get_id());
                    if (struct.get_type().equals(ore.gold))
                        square.setFill(Color.YELLOW);
                    else
                        square.setFill(Color.RED);
                    square.setX(25+51*j);
                    square.setY(i*51);
                    txt.setX(30.5 + 51*j);
                    txt.setY(17.5 + i*51);
                    ((Group)scene.getRoot()).getChildren().addAll(square, txt);
                }
                if (struct instanceof Warehouse) {
                    Rectangle square = new Rectangle(25,25);
                    Text txt = new Text();
                    txt.setText("W"+struct.get_id());
                    if (struct.get_type().equals(ore.gold))
                        square.setFill(Color.YELLOW);
                    else
                        square.setFill(Color.RED);
                    square.setX(51*j);
                    square.setY(i*51);
                    txt.setX(3.5 + 51*j);
                    txt.setY(17.5 + i*51);
                    ((Group)scene.getRoot()).getChildren().addAll(square, txt);
                }
                if (s.get_robot() != null) {
                    Rectangle square = new Rectangle(25,25);
                    Text txt = new Text();
                    txt.setText("R"+s.get_robot().get_id());
                    if (s.get_robot().get_type().equals(ore.gold))
                        square.setFill(Color.YELLOW);
                    else
                        square.setFill(Color.RED);
                    square.setX(25 + 51*j);
                    square.setY(25 + i*51);
                    txt.setX(30.5 + 51*j);
                    txt.setY(42.5 + i*51);

                    ((Group)scene.getRoot()).getChildren().addAll(square, txt);
                }
            }
        }
    }

    public String stats(){
        String result ="";
        for (Warehouse ware : world.get_warehouses()) {
            result += "Warehouse " + ware.get_id() + ": " + ware.get_storage() + "\n";
        }
        for (Mine mine : world.get_mines()) {
            result += "Mine " + mine.get_id() + ": " + mine.get_storage() + "/" + mine.get_capacity() + "\n";
        }
        for (Robot rob : world.get_robot()) {
            result += "Robot " + rob.get_id() + ": " + rob.get_inventory() + "/" + rob.get_storage() +"\n";
        }
        return result;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void set_map(Map map) {
        this.map = map;
    }
}
