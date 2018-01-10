import javafx.application.Application;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BeginScreen extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        ArrayList<Object> myList = new ArrayList<>();
        Label parcelA = new Label("Parcel A");
        myList.add(parcelA);
        Label amA = new Label("How many parcels of A?");
        myList.add(amA);
        TextField amountA = new TextField();
        myList.add(amountA);
        Label valA = new Label("What's the value of each parcel of A?");
        myList.add(valA);
        TextField valueA = new TextField();
        myList.add(valueA);

        Label parcelB = new Label("Parcel B");
        myList.add(parcelB);
        Label amB = new Label("How many parcels of B?");
        myList.add(amB);
        TextField amountB = new TextField();
        myList.add(amountB);
        Label valB = new Label("What's the value of each parcel of B?");
        myList.add(valB);
        TextField valueB = new TextField();
        myList.add(valueB);

        Label parcelC = new Label("Parcel C");
        myList.add(parcelC);
        Label amC = new Label("How many parcels of C?");
        myList.add(amC);
        TextField amountC = new TextField();
        myList.add(amountC);
        Label valC = new Label("What's the value of each parcel of C?");
        myList.add(valC);
        TextField valueC = new TextField();
        myList.add(valueC);

        Button startbutton = new Button("Start program with theese parameters!");
        startbutton.setMinHeight(primaryStage.getHeight()/2);
        startbutton.setMinWidth(primaryStage.getWidth());



        GridPane layout1 = new GridPane();
        layout1.setHgap(10);
        layout1.setVgap(10);
        int index = 0;

        for (int i = 0; i<3;i++){
            for (int j = 0; j<5;j++){
                layout1.add((javafx.scene.Node)myList.get(index),i,j);
                index++;
            }
        }

        VBox boxl = new VBox();
        boxl.getChildren().addAll(layout1,startbutton);

        Scene scene1 = new Scene(boxl,300,300);
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
