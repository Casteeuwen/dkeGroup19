import javafx.scene.paint.Color;

/**
 * See documentation of PentominoF class
 */
public class PentominoP extends Pentomino {
    public static Color myColor = Color.WHITE;

    public PentominoP(double x, double y, double z, double size_transform){
        super(x,y,z,size_transform, myColor);
    }

    public void createBeginShape(){
        for (int i =0; i<boxgrid.length;i++){
            for (int j = 0; j<boxgrid[0].length;j++){
                for (int k = 0; k<boxgrid[0][0].length;k++){
                    if (((i>0&&(j<2))||(i==1&&j==2))&&k==1) {
                        boxgrid[i][j][k] = getNewBox(nontransformedbloxsize, size_transform);
                    }
                }
            }
        }
    }

    public static void setColor(Color c){
        myColor = c;
    }
}
