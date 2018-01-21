import com.sun.glass.ui.Size;
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
import javafx.scene.image.Image;
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

/**
 * Constructs a stage with sliders and 3d component, made in javafx
 */
public class Other_Main_Drawer extends Application {

    private static final double SCENE_SIZE = 700;
    private static final double SIZE_TRANSFORMATION = 50;
    private static final double TOOLBAR_HEIGHT = 100;

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

    private Scene scene;
    private SubScene scene3d;

    private Group myGroup = new Group(new AmbientLight(AMBIENT_COLOR), createPointLight());

    /**
     * Gets called at beginning of scene, basic javafx function
     * Constructs the functionality in the 3d part, as well as adding listeners to those components.
     * Also used for setting up of 3d, as well as testing the 3d components.
     */
    public void start(Stage stage) throws Exception {
        scene3d = new SubScene(
                myGroup,
                SCENE_SIZE, SCENE_SIZE,
                true,
                SceneAntialiasing.BALANCED
        );

        camera.setTranslateX(-330);
        camera.setTranslateY(-300);
        scene3d.setCamera(camera);

        //try/catch for using a background image. If image doesn't work, it sets background to a default color.
        try {
            //add h to ...ttps to get a working image, (rendering is weird so i broke it on purpose)
            Image backImg = new Image("ttps://media.istockphoto.com/photos/four-vertical-rows-of-shipping-containers-picture-id515306829?k=6&m=515306829&s=612x612&w=0&h=f-NpL4KEtnd_c1V2ax5IYB3RrTmOB5TCQ-YNQg_OXfQ=");
            ImagePattern pattern = new ImagePattern(backImg);
            scene3d.setFill(pattern);

        }catch(Exception e){
            System.out.println("NO IMAGE FOUND");
            scene3d.setFill(Color.MIDNIGHTBLUE.darker().darker());
        }

        //create outline of container
        createLines(CONTAINERW, CONTAINERH, CONTAINERD, 0, 0, 0);

        myGroup.setDepthTest(DepthTest.ENABLE);

        //configure the sliders for rotation of scene
        BorderPane pane = new BorderPane();
        pane.setCenter(scene3d);
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(360);
        slider.setValue(0);
        slider.setShowTickLabels(false);
        slider.setShowTickMarks(false);
        slider.setMajorTickUnit(10);
        slider.setMinorTickCount(1);
        slider.setBlockIncrement(10);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                int newV = new_val.intValue();
                int oldV = old_val.intValue();
                myGroup.getTransforms().add(new Rotate((newV - oldV), CONTAINERW * SIZE_TRANSFORMATION / 2, CONTAINERH/SIZE_TRANSFORMATION/2, CONTAINERD * SIZE_TRANSFORMATION / 2, Rotate.Y_AXIS));
            }
        });
        Slider slider2 = new Slider();
        slider2.setMin(0);
        slider2.setMax(90);
        slider2.setValue(0);
        slider2.setShowTickLabels(false);
        slider2.setShowTickMarks(false);
        slider2.setMajorTickUnit(10);
        slider2.setMinorTickCount(1);
        slider2.setBlockIncrement(10);
        slider2.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                int newV = new_val.intValue();
                int oldV = old_val.intValue();
                myGroup.getTransforms().add(new Rotate((newV - oldV), CONTAINERW * SIZE_TRANSFORMATION / 2, CONTAINERH/SIZE_TRANSFORMATION/2, CONTAINERD * SIZE_TRANSFORMATION / 2, Rotate.X_AXIS));
            }
        });

        //configure the checkboxes with listeners
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



        //Configure and combine the 2d and 3d scene
        ToolBar toolBar = new ToolBar(slider, slider2,box1,box2,box3);
        toolBar.setMinHeight(TOOLBAR_HEIGHT);
        toolBar.setOrientation(Orientation.HORIZONTAL);
        pane.setTop(toolBar);
        pane.setPrefSize(800, 800);
        scene = new Scene(pane);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        scene3d.setWidth(stage.getWidth());
        scene3d.setHeight(stage.getHeight()-TOOLBAR_HEIGHT);

        //Configure the pentominoes to have the correct color
        PentominoP.setColor(BOX_GREEN);
        PentominoF.setColor(BOX_BLUE);
        PentominoT.setColor(BOX_RED);

        //This part is used for testing... you can ignore it basically. It does show how you'd use some methods in other parts of the program, because all of them used here are accesible to the others
//        addBox(1, 1, 2, 0, 0, 0,BOX_GREEN);
//        addBox(1, 1, 4, 0, 0, 2,BOX_RED);
//        addBox(2, 2, 4, 0.5, 2, 8,BOX_BLUE);
//        addBox(1, 1, 2, 1, 3, 2,BOX_GREEN);
//        addBox(1, 1, 4, 1.5, 0, 6,BOX_RED);

        Pentomino pent = new PentominoT(0,0,0,SIZE_TRANSFORMATION);
        Pentomino penf = new PentominoF(0,0,6,SIZE_TRANSFORMATION);
        Pentomino penp = new PentominoP(0,0,3,SIZE_TRANSFORMATION);
        myGroup.getChildren().addAll(pent,penp,penf);
        //penf.rotateMatrixLeft(4);
        //penf2.rotateMatrixRight(3);
        //penf2.rotateMatrixY(1);
        //penf2.rotateMatrixX(2);


    }

    /**
     * Basic class that is used for the checkboxes in the 2d part.
     * If it detects a change, it goes through the myGroup, and changes the visibility of the 3dboxes of a given color.
     * This means you can turn visibility off and on based on color of the boxes.
     * TODO add this functionality to pentominoes
     */
    private class myBoxListener implements ChangeListener<Boolean>{
        Color listensfor;
        private myBoxListener(Color lf){
            listensfor = lf;
        }

        public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
            for (Node node : myGroup.getChildrenUnmodifiable()) {
                if (node instanceof ColorBox ) {
                    ColorBox thisbox = (ColorBox) node;
                    Color c = thisbox.getOriginalColor();
                    if (c == listensfor) {
                        if (new_val){thisbox.setVisible(true);}
                        if (!new_val){thisbox.setVisible(false);}
                    }
                }
                if (node instanceof Pentomino ) {
                    Pentomino thispent = (Pentomino) node;
                    Color c = thispent.getOriginalColor();
                    if (c == listensfor) {
                        if (new_val){thispent.setVisible(true);}
                        if (!new_val){thispent.setVisible(false);}
                    }
                }
            }
        }
    }

    /**
     * Makes a pointlight.
     * @return Returns the pointlight.
     */
    private PointLight createPointLight() {
        PointLight light = new PointLight(LIGHT_COLOR);
        light.setTranslateX(SCENE_SIZE / 2d);
        light.setTranslateY(0);
        light.setTranslateZ(-SCENE_SIZE);
        return light;
    }

    /**
     * Constructs a colored box with it's corresponding outline
     * @param w width
     * @param h height
     * @param d depth
     * @param x x-location of closest high corner
     * @param y y-location of closest high corner
     * @param z z-location of closest high corner
     * @param boxc the color you want the box to be
     * TODO add seperate method for each of the boxes (A,B,C), that calls to this one
     */
    public void addBox(double w, double h, double d, double x, double y, double z,Color boxc) {
        //create outline lines of the boxes by calling this method
        createLines(w, h, d, x, y, z);

        //Adjusting size and location to what's handy, default value of size transformation is 1.
        x += (w / 2);
        z += (d / 2);
        y += (h / 2);
        w *= SIZE_TRANSFORMATION;
        h *= SIZE_TRANSFORMATION;
        d *= SIZE_TRANSFORMATION;
        x *= SIZE_TRANSFORMATION;
        y *= SIZE_TRANSFORMATION;
        z *= SIZE_TRANSFORMATION;

        //constructing a new javafx box (made a seperate class ColorBox so it can remember its initial color)
        ColorBox newB = new ColorBox(w, h, d, boxc);
        newB.setTranslateX(x);
        newB.setTranslateY(y);
        newB.setTranslateZ(z);

        //set material to the color given to the box
        newB.setMaterial(createMaterial(boxc));

        //add the given box to the myGroup
        myGroup.getChildren().addAll(newB);
    }

    /**
     * creates a box of lines in the 3d scene
     * (for the record, i called it contW/H/D, because I initially only was gonna use it for the container outline. Now we use it for all boxes.)
     * @param contW width
     * @param contH height
     * @param contD depth
     * @param x x-location
     * @param y y-location
     * @param z z-location
     */
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

    /**
     * Create a line from one Point3d to another
     * @param origin
     * @param target
     */
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

    /**
     * creates a material for a given color
     * @param boxc The color of the material
     * @return the material
     */
    public static PhongMaterial createMaterial( Color boxc) {
        PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseColor(boxc);
        mat.diffuseMapProperty();
        mat.setSpecularColor(boxc);
        return mat;
    }

}