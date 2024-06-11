package Controller;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import robots_ia.Network;
import vue.Simu_GUI;

public class Handler implements EventHandler<MouseEvent> {

    private Network network;
    private Simu_GUI gui;
    private Scene scene;

    public Handler(Network network, Simu_GUI gui, Scene scene) {
        this.network = network;
        this.gui = gui;
        this.scene = scene;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof Button) {
            Button button = (Button) mouseEvent.getSource();
            switch (button.getText()) {
                case "slow": {
                    if (this.gui.getSpeed() > 1) {
                        this.gui.setSpeed(this.gui.getSpeed() - 1);
                        button.getScene().getRoot().getChildrenUnmodifiable().forEach(node -> {
                            if (node instanceof VBox){
                                VBox vBox = (VBox) node;
                                vBox.getChildren().forEach(node1 -> {
                                    if (node1 instanceof HBox){
                                        HBox hBox = (HBox) node1;
                                        hBox.getChildren().forEach(node2 -> {
                                            if (node2 instanceof VBox){
                                                VBox vBox1 = (VBox) node2;
                                                vBox1.getChildren().forEach(node3 -> {
                                                    if (node3 instanceof Text && ((Text) node3).getText().contains("speed")){
                                                        Text text = (Text) node3;
                                                        text.setText("speed: " + this.gui.getSpeed());
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                    break;
                }
                case "fast": {
                    if (this.gui.getSpeed() < 3) {
                        this.gui.setSpeed(this.gui.getSpeed() + 1);
                        button.getScene().getRoot().getChildrenUnmodifiable().forEach(node -> {
                            if (node instanceof VBox){
                                VBox vBox = (VBox) node;
                                vBox.getChildren().forEach(node1 -> {
                                    if (node1 instanceof HBox){
                                        HBox hBox = (HBox) node1;
                                        hBox.getChildren().forEach(node2 -> {
                                            if (node2 instanceof VBox){
                                                VBox vBox1 = (VBox) node2;
                                                vBox1.getChildren().forEach(node3 -> {
                                                    if (node3 instanceof Text && ((Text) node3).getText().contains("speed")){
                                                        Text text = (Text) node3;
                                                        text.setText("speed: " + this.gui.getSpeed());
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                    break;
                }
                case "start": {
                    this.gui.setPause(true);
                    button.setText("play");
                    button.getScene().getRoot().getChildrenUnmodifiable().forEach(node -> {
                        if (node instanceof VBox){
                            VBox vBox = (VBox) node;
                            vBox.getChildren().forEach(node1 -> {
                                if (node1 instanceof HBox){
                                    HBox hBox = (HBox) node1;
                                    hBox.getChildren().forEach(node2 -> {
                                        if (node2 instanceof VBox){
                                            VBox vBox1 = (VBox) node2;
                                            vBox1.getChildren().forEach(node3 -> {
                                                if (node3 instanceof Text && ((Text) node3).getText().contains("paused")){
                                                    Text lap =  (Text) vBox1.getChildren().getFirst();
                                                    Text stats = ((Text) ((HBox) button.getParent().getParent()).getChildren().getFirst());
                                                    ((HBox) button.getParent()).getChildren().clear();
                                                    try {
                                                        network.run_lap(scene, this.gui, lap, stats);
                                                    } catch (InterruptedException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                    System.out.println("start");
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                    break;
                }
            }
        }
    }
}



