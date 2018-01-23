import javafx.scene.Group;
import javafx.scene.chart.Axis;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;

/**
 * Specific class that extends Pentomino.
 */
public class PentominoF extends Pentomino {
    public static Color myColor = Color.WHITE;

    /**
     * Construct the super with the given location, size transformation and color
     * @param x
     * @param y
     * @param z
     * @param size_transform
     */
    public PentominoF(double x, double y, double z, double size_transform){
        super(x,y,z,size_transform, myColor);
    }

    /**
     * Filling the boxgrid (3x3x3 array) with boxes, but only for the locations that corresponds to the shape of the pentomino.
     * This method is only called once, when constructing the pentomino object.
     */
    public void createBeginShape(){
        for (int i =0; i<boxgrid.length;i++){
            for (int j = 0; j<boxgrid[0].length;j++){
                for (int k = 0; k<boxgrid[0][0].length;k++){
                    if (((i>0&&j==0)||(i<2&&j==1)||(i==1&&j==2))&&k==1) {
                        boxgrid[i][j][k] = getNewBox(nontransformedbloxsize, size_transform);
                    }
                }
            }
        }
    }

    /**
     * Static method that allows you to set the Color of all pentominoes of this shape.
     * @param c
     */
    public static void setColor(Color c){
        myColor = c;
    }
}
