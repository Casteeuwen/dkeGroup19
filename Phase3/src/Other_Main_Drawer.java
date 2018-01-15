import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Other_Main_Drawer extends Application {

    private static final double SCENE_SIZE = 300;
    private static final double BOX_EDGE_LENGTH = 100;
    private static final double SIZE_TRANSFORMATION = 0.5;

    private static final double CONTAINERW = 500;
    private static final double CONTAINERH = 400;
    private static final double CONTAINERD = 500;

    private static final Color BOX_COLOR     = Color.rgb(250, 0, 150,0.9);
    private static PhongMaterial boxMat = createMaterial();
    private static final Color AMBIENT_COLOR = Color.rgb(30, 30, 30);
    private static final Color LIGHT_COLOR   = Color.WHITE;

    private PerspectiveCamera camera = new PerspectiveCamera();

    private Group myGroup = new Group(  new AmbientLight(AMBIENT_COLOR), createPointLight());

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(
                myGroup,
                SCENE_SIZE, SCENE_SIZE,
                true,
                SceneAntialiasing.BALANCED
        );
        scene.setFill(Color.MIDNIGHTBLUE.darker().darker());

        camera.setTranslateX(-300);
        camera.setTranslateY(-300);
        scene.setCamera(camera);

        addBox(100,300,100,0,0,0);
        //addBox(200,200,200,400,300,100);

        createContainerLines(CONTAINERW,CONTAINERH,CONTAINERD);


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

    public void addBox(double w, double h, double d, double x, double y,double z){
        //Adjusting size and location to what's handy, default value of size transformation is 1.
        x+=(w/2);
        z+=(d/2);
        y+=(h/2);

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

    public void createContainerLines (double contW,double contH,double contD){
        contW*=SIZE_TRANSFORMATION;
        contH*=SIZE_TRANSFORMATION;
        contD*=SIZE_TRANSFORMATION;

        Point3D p1 = new Point3D(0,0,0);
        Point3D p2 = new Point3D(contW,0,0);
        Point3D p3 = new Point3D(0,contH,0);
        Point3D p4 = new Point3D(contW,contH,0);
        createLine(p1,p2);
        createLine(p1,p3);
        createLine(p3,p4);
        createLine(p2,p4);

        Point3D p5 = new Point3D(0,0,contD);
        Point3D p6 = new Point3D(contW,0,contD);
        Point3D p7 = new Point3D(0,contH,contD);
        Point3D p8 = new Point3D(contW,contH,contD);
        createLine(p5,p6);
        createLine(p5,p7);
        createLine(p7,p8);
        createLine(p6,p8);

        createLine(p1,p5);
        createLine(p2,p6);
        createLine(p3,p7);
        createLine(p4,p8);
    }


    public void createLine (Point3D origin, Point3D target) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(1, height);

        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        myGroup.getChildren().add(line);
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