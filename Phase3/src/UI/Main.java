package UI;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.stage.Stage;

public class Main extends Application {
    final double BLOCK_SIZE = 25;
    final double LENGTH = 16.5;
    final double WIDTH = 2.5;
    final double HEIGHT = 4.0;


    @Override
    public void start(Stage primaryStage) throws Exception {

        Box box = new Box();
        box.setWidth(WIDTH * BLOCK_SIZE);
        box.setHeight(HEIGHT * BLOCK_SIZE);
        box.setDepth(LENGTH * BLOCK_SIZE);

        box.setTranslateX(200);
        box.setTranslateY(150);
        box.setTranslateZ(0);

        //box.setCullFace(CullFace.BACK);

        box.setDrawMode(DrawMode.FILL);

        // The root node
        Group root = new Group(box);

        // List that holds all the nodes
        // Add nodes (objects) through list.add();
        // ObservableList list = root.getChildren();


        // Preparing the scene
        Scene scene = new Scene(root, 600, 300);

        // Set background color
        scene.setFill(Color.GRAY);

        // Set up the camera
        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(0);
        scene.setCamera(camera);

        // Preparing the stage (application window)
        primaryStage.setTitle("Phase 3");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
       launch(args);
    }
}
