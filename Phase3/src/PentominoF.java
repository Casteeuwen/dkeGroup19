import javafx.scene.Group;
import javafx.scene.chart.Axis;
import javafx.scene.shape.Box;

public class PentominoF extends Pentomino {


    public PentominoF(double x, double y, double z, double size_transform){
        super(x,y,z,size_transform);
    }

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


}
