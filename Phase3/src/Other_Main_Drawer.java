import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Other_Main_Drawer extends Application {

    private static final double SCENE_SIZE = 300;
    private static final double BOX_EDGE_LENGTH = 100;
    private static final double SIZE_TRANSFORMATION = 1;

    private static final Color BOX_COLOR     = Color.rgb(250, 0, 150,0.9);
    private static PhongMaterial boxMat = createMaterial();
    private static final Color AMBIENT_COLOR = Color.rgb(30, 30, 30);
    private static final Color LIGHT_COLOR   = Color.WHITE;

    private PerspectiveCamera camera = new PerspectiveCamera();

    private Group myGroup = new Group(  new AmbientLight(AMBIENT_COLOR),
                            createPointLight(),
                            createBox(200,100,RotateTransition.INDEFINITE),
                            createBox(350,200,1)
    );

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(
                myGroup,
                SCENE_SIZE, SCENE_SIZE,
                true,
                SceneAntialiasing.BALANCED
        );
        scene.setFill(Color.MIDNIGHTBLUE.darker().darker());

        camera.setTranslateY(-100);
        scene.setCamera(camera);
        addBox(100,300,200,400,300,300);

        stage.setScene(scene);
        stage.show();
    }

    private PointLight createPointLight() {
        PointLight light = new PointLight(LIGHT_COLOR);
        light.setTranslateX( SCENE_SIZE / 2d);
        light.setTranslateY(0);
        light.setTranslateZ(-SCENE_SIZE);

        return light;
    }

    private Box createBox(double x, double y, double rotateCount) {
        final Box box = new Box(BOX_EDGE_LENGTH, BOX_EDGE_LENGTH, BOX_EDGE_LENGTH);
        box.setTranslateX(x);
        box.setTranslateY(y);
        box.setTranslateZ(BOX_EDGE_LENGTH / 2d);
        box.setMaterial(boxMat);
        return box;
    }

    public void addBox(double w, double h, double d, double x, double y,double z){
        //Adjusting size and location to what's handy, default value of size transformation is 1.
        w *= SIZE_TRANSFORMATION;
        h *= SIZE_TRANSFORMATION;
        d *= SIZE_TRANSFORMATION;
        x *= SIZE_TRANSFORMATION;
        y *= SIZE_TRANSFORMATION;
        z *= SIZE_TRANSFORMATION;

        Box newB = new Box( w,  h,  d);
        newB.setTranslateX(x);
        newB.setTranslateY(y);
        newB.setTranslateZ(z);

        newB.setMaterial(boxMat);


        myGroup.getChildren().add(newB);

    }

//    private void rotateAroundYAxis(Box box, double rotateCount) {
//        RotateTransition rotate = new RotateTransition(ROTATION_DURATION, box);
//        rotate.setFromAngle(0);
//        rotate.setToAngle(360);
//        rotate.setAxis(Rotate.Y_AXIS);
//        rotate.setCycleCount((int)rotateCount);
//        rotate.setInterpolator(Interpolator.LINEAR);
//        rotate.play();
//    }

    public static void main(String[] args) {
        launch(args);
    }

    private static PhongMaterial createMaterial(){
        PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseColor(BOX_COLOR);  // Note alpha of 0.6
        mat.diffuseMapProperty();
        mat.setSpecularColor(BOX_COLOR);
        return mat;
    }

}