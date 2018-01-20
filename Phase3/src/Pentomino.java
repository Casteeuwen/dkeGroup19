import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public abstract class Pentomino extends Group {
    Box[][][] boxgrid = new Box[3][3][3];
    double nontransformedbloxsize = 0.5;
    double size_transform;
    final int GRID_SIZE = 3;

    public Pentomino(double x, double y, double z, double size_transformation) {
        size_transform = size_transformation;
        x *= size_transform;
        y *= size_transform;
        z *= size_transform;

        this.setTranslateX(x + nontransformedbloxsize * size_transform / 2);
        this.setTranslateY(y + nontransformedbloxsize * size_transform / 2);
        this.setTranslateZ(z + nontransformedbloxsize * size_transform / 2);

        this.createBeginShape();
        handleLocations();
    }

    public abstract void createBeginShape();


    public Box getNewBox(double ntblocksize, double size_transform) {
        Box newb = new Box(ntblocksize * size_transform, ntblocksize * size_transform, ntblocksize * size_transform);
        PhongMaterial mat = Other_Main_Drawer.createMaterial(Color.GREEN);
        newb.setMaterial(mat);
        return newb;
    }

    public void handleLocations() {
        this.getChildren().clear();
        for (int i = 0; i < boxgrid.length; i++) {
            for (int j = 0; j < boxgrid[0].length; j++) {
                for (int k = 0; k < boxgrid[0][0].length; k++) {
                    if (boxgrid[i][j][k] != null) {
                        boxgrid[i][j][k].setTranslateX(i * nontransformedbloxsize * size_transform);
                        boxgrid[i][j][k].setTranslateY(j * nontransformedbloxsize * size_transform);
                        boxgrid[i][j][k].setTranslateZ(k * nontransformedbloxsize * size_transform);
                        this.getChildren().add(boxgrid[i][j][k]);
                    }
                }
            }
        }
    }

    public void rotate() {
        this.getChildren().clear();
        Box[][][] tempboxes = new Box[3][3][3];
        for (int i = 0; i < boxgrid.length; i++) {
            for (int j = 0; j < boxgrid[0].length; j++) {
                for (int k = 0; k < boxgrid[0][0].length; k++) {

                }
            }
        }
        boxgrid = tempboxes;
    }

    public void rotateMatrixRight(int ticks){
        rotateMatrixLeft(4-ticks);
    }

    public void rotateMatrixLeft(int ticks){ rotateMatrixZ(ticks);}

    public void rotateMatrixZ(int ticks) {
        for (int n = 0; n<ticks;n++) {
            int w = GRID_SIZE;
            int h = GRID_SIZE;
            Box[][][] ret = new Box[h][w][GRID_SIZE];
            for (int i = 0; i < h; ++i) {
                for (int j = 0; j < w; ++j) {
                    for (int k = 0; k<3;k++) {
                        ret[i][j][k] = boxgrid[w - j - 1][i][k];
                    }
                }
            }
            boxgrid = ret;
        }
        handleLocations();
    }

    public void rotateMatrixY(int ticks){
        for (int n = 0; n<ticks;n++) {
            int w = GRID_SIZE;
            int h = GRID_SIZE;
            int d = GRID_SIZE;
            Box[][][] ret = new Box[h][w][d];
            for (int i = 0; i < h; ++i) {
                for (int j = 0; j < w; ++j) {
                    for (int k = 0; k<d;k++) {
                        ret[i][j][k] = boxgrid[d - k - 1][j][i];
                    }
                }
            }
            boxgrid = ret;
        }
        handleLocations();
    }

    public void rotateMatrixX(int ticks){
        for (int n = 0; n<ticks;n++) {
            int w = GRID_SIZE;
            int h = GRID_SIZE;
            int d = GRID_SIZE;
            Box[][][] ret = new Box[h][w][d];
            for (int i = 0; i < h; ++i) {
                for (int j = 0; j < w; ++j) {
                    for (int k = 0; k<d;k++) {
                        ret[i][j][k] = boxgrid[i][k][h-j-1];
                    }
                }
            }
            boxgrid = ret;
        }
        handleLocations();
    }
}
