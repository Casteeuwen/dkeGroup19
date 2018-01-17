import javafx.scene.Group;
import javafx.scene.chart.Axis;
import javafx.scene.shape.Box;

public class PentominoF extends Pentomino {
    double nontransformedbloxsize = 0.5;
    Box[][][]  boxgrid = new Box[3][3][3];
    public PentominoF(double x, double y, double z, double size_transform){
        x*=size_transform;
        y*=size_transform;
        z*=size_transform;

//        boxgrid[1][0][0] = getNewBox(nontransformedbloxsize,size_transform);
//        for (int i =0; i<boxgrid.length;i++){
//            for (int j = 0; j<boxgrid[0].length;j++){
//                for (int k = 0; k<boxgrid[0][0].length;k++){
//                    System.out.println("it got to the inner loop");
//                    if (myswitch){
//                        System.out.println("myswitch was true!");
//                        boxgrid[i][j][k] = getNewBox(nontransformedbloxsize,size_transform);
//                        boxgrid[i][j][k].setTranslateX(i*nontransformedbloxsize*size_transform);
//                        boxgrid[i][j][k].setTranslateY(j*nontransformedbloxsize*size_transform);
//                        boxgrid[i][j][k].setTranslateZ(k*nontransformedbloxsize*size_transform);
//                        this.getChildren().add(boxgrid[i][j][k]);
//                        myswitch = false;
//                    }
//                   else{
//                       System.out.println("myswitch was false!");
//                       myswitch = true;
//                   }
//                }
//            }
//        }

        for (int i =0; i<boxgrid.length;i++){
            for (int j = 0; j<boxgrid[0].length;j++){
                for (int k = 0; k<boxgrid[0][0].length;k++){
                    System.out.println("it got to the inner loop");
                    if (((i>0&&j==0)||(i<2&&j==1)||(i==1&&j==2))&&k==0){
                        System.out.println("myswitch was true!");
                        boxgrid[i][j][k] = getNewBox(nontransformedbloxsize,size_transform);
                        boxgrid[i][j][k].setTranslateX(i*nontransformedbloxsize*size_transform);
                        boxgrid[i][j][k].setTranslateY(j*nontransformedbloxsize*size_transform);
                        boxgrid[i][j][k].setTranslateZ(k*nontransformedbloxsize*size_transform);
                        this.getChildren().add(boxgrid[i][j][k]);
                    }
                }
            }
        }



        this.setTranslateX(x+nontransformedbloxsize*size_transform/2);
        this.setTranslateY(y+nontransformedbloxsize*size_transform/2);
        this.setTranslateZ(z+nontransformedbloxsize*size_transform/2);
    }

    public void rotate(){
        this.getChildren().clear();
        Box[][][] tempboxes = new Box[3][3][3];
        for (int i =0; i<boxgrid.length;i++){
            for (int j = 0; j<boxgrid[0].length;j++){
                for (int k = 0; k<boxgrid[0][0].length;k++){
                    if(boxgrid[i][j][k] instanceof Box){
                        System.out.println("Found box at "+i+" "+j+" " +k);
                        double boxx = boxgrid[i][j][k].getTranslateX();
                        tempboxes[j][i][k] = boxgrid[i][j][k];
                        tempboxes[j][i][k].setTranslateX(tempboxes[j][i][k].getTranslateY());
                        tempboxes[j][i][k].setTranslateY(boxx);
                        this.getChildren().add(tempboxes[j][i][k]);
                    }
                }
            }
        }
        boxgrid = tempboxes;

    }
}
