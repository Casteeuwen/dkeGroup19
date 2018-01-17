import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Other_Main_Drawer extends Application implements ActionListener {

    private static final double SCENE_SIZE = 700;
    private static final double SIZE_TRANSFORMATION = 60;

    private static final double CONTAINERW = 2.5;
    private static final double CONTAINERH = 4;
    private static final double CONTAINERD = 16.5;

    private static final double BOX_OPACITY = 0.5;
    private static final Color BOX_DARK = Color.rgb(0, 0, 0, 0);
    private static final Color BOX_GREEN = Color.rgb(0,200,0,BOX_OPACITY);
    private static final Color BOX_BLUE = Color.rgb(0,0,200,BOX_OPACITY);
    private static final Color BOX_RED = Color.rgb(200,0,0,BOX_OPACITY);

    private static final Color AMBIENT_COLOR = Color.rgb(255, 255, 255);
    private static final Color LIGHT_COLOR = Color.WHITE;

    private PerspectiveCamera camera = new PerspectiveCamera();

    private Timer tm;
    private double rotation = 1;

    private Scene scene;
    private SubScene scene3d;

    private Group myGroup = new Group(new AmbientLight(AMBIENT_COLOR), createPointLight());

    @Override
    public void start(Stage stage) throws Exception {
        scene3d = new SubScene(
                myGroup,
                SCENE_SIZE, SCENE_SIZE,
                true,
                SceneAntialiasing.BALANCED
        );
        scene3d.setFill(Color.MIDNIGHTBLUE.darker().darker());

        camera.setTranslateX(-300);
        camera.setTranslateY(-300);
        scene3d.setCamera(camera);
        scene3d.setWidth(300);


        createLines(CONTAINERW, CONTAINERH, CONTAINERD, 0, 0, 0);

        myGroup.setDepthTest(DepthTest.ENABLE);


        BorderPane pane = new BorderPane();
        pane.setCenter(scene3d);
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(360);
        slider.setValue(0);
        slider.setShowTickLabels(false);
        slider.setShowTickMarks(false);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);

        CheckBox box1 = new CheckBox("Green");
        box1.selectedProperty().addListener(new myBoxListener(BOX_GREEN));
        box1.setSelected(true);

        CheckBox box2 = new CheckBox("Red");
        box2.selectedProperty().addListener(new myBoxListener(BOX_RED));
        box2.selectedProperty().addListener(new myBoxListener(BOX_RED));
        box2.setSelected(true);

        CheckBox box3 = new CheckBox("Blue");
        box3.selectedProperty().addListener(new myBoxListener(BOX_BLUE));
        box3.setSelected(true);

        addBox(1, 1, 2, 0, 0, 0,BOX_GREEN);
        addBox(1, 1, 4, 0, 0, 2,BOX_RED);
        addBox(2, 2, 4, 2, 2, 8,BOX_BLUE);


        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                int newV = new_val.intValue();
                int oldV = old_val.intValue();
                myGroup.getTransforms().add(new Rotate((newV - oldV), CONTAINERW * SIZE_TRANSFORMATION / 2, 0, CONTAINERD * SIZE_TRANSFORMATION / 2, Rotate.Y_AXIS));
            }
        });

        ToolBar toolBar = new ToolBar(slider,box1,box2,box3);
        toolBar.setMinHeight(100);
        toolBar.setOrientation(Orientation.HORIZONTAL);
        pane.setTop(toolBar);
        pane.setPrefSize(800, 800);
        scene = new Scene(pane);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        scene3d.setWidth(stage.getWidth());

    }

    private class myBoxListener implements ChangeListener<Boolean>{
        Color listensfor;
        private myBoxListener(Color lf){
            listensfor = lf;
        }

        public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
            for (Node node : myGroup.getChildrenUnmodifiable()) {
                if (node instanceof ColorBox) {
                    ColorBox thisbox = (ColorBox) node;
                    Color c = thisbox.getOriginalColor();
                    if (c == listensfor) {
                        if (new_val){thisbox.setMaterial(createMaterial(listensfor));}
                        if (!new_val){thisbox.setMaterial(createMaterial(BOX_DARK));}
                    }
                }
            }
        }
    }


    private PointLight createPointLight() {
        PointLight light = new PointLight(LIGHT_COLOR);
        light.setTranslateX(SCENE_SIZE / 2d);
        light.setTranslateY(0);
        light.setTranslateZ(-SCENE_SIZE);
        return light;
    }

    public void addBox(double w, double h, double d, double x, double y, double z,Color boxc) {
        //Adjusting size and location to what's handy, default value of size transformation is 1.
        createLines(w, h, d, x, y, z);
        x += (w / 2);
        z += (d / 2);
        y += (h / 2);

        w *= SIZE_TRANSFORMATION;
        h *= SIZE_TRANSFORMATION;
        d *= SIZE_TRANSFORMATION;
        x *= SIZE_TRANSFORMATION;
        y *= SIZE_TRANSFORMATION;
        z *= SIZE_TRANSFORMATION;

        ColorBox newB = new ColorBox(w, h, d, boxc);
        newB.setTranslateX(x);
        newB.setTranslateY(y);
        newB.setTranslateZ(z);


        newB.setMaterial(createMaterial(boxc));
        myGroup.getChildren().addAll(newB);

    }

    public void createLines(double contW, double contH, double contD, double x, double y, double z) {
        x *= SIZE_TRANSFORMATION;
        y *= SIZE_TRANSFORMATION;
        z *= SIZE_TRANSFORMATION;
        contW *= SIZE_TRANSFORMATION;
        contH *= SIZE_TRANSFORMATION;
        contD *= SIZE_TRANSFORMATION;

        Point3D p1 = new Point3D(x, y, z);
        Point3D p2 = new Point3D(contW + x, y, z);
        Point3D p3 = new Point3D(x, contH + y, z);
        Point3D p4 = new Point3D(contW + x, contH + y, z);
        createLine(p1, p2);
        createLine(p1, p3);
        createLine(p3, p4);
        createLine(p2, p4);

        Point3D p5 = new Point3D(x, y, contD + z);
        Point3D p6 = new Point3D(contW + x, y, contD + z);
        Point3D p7 = new Point3D(x, contH + y, contD + z);
        Point3D p8 = new Point3D(contW + x, contH + y, contD + z);
        createLine(p5, p6);
        createLine(p5, p7);
        createLine(p7, p8);
        createLine(p6, p8);

        createLine(p1, p5);
        createLine(p2, p6);
        createLine(p3, p7);
        createLine(p4, p8);
    }


    public void createLine(Point3D origin, Point3D target) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(2, height);

        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        myGroup.getChildren().add(line);
    }


    public static void main(String[] args) {
        launch(args);
    }

    private static PhongMaterial createMaterial( Color boxc) {
        PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseColor(boxc);
        mat.diffuseMapProperty();
        mat.setSpecularColor(boxc);
        return mat;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }




}