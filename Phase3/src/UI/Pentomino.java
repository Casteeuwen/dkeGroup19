package UI;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

/**
 * This class is a collection of ColorBox objects, grouped together to form a pentomino.
 * The class does not function seperately, you need the child objects (pentominoT,pentominoF,pentominoP).
 * In javafx, you can treat this object as a simple shape, which is really useful.
 */
public abstract class Pentomino extends Group {
    Box[][][] boxgrid = new Box[3][3][3];
    double nontransformedbloxsize = 0.5;
    double size_transform;
    final int GRID_SIZE = 3;
    Color pentcolor;

    /**
     * Constructs a Pentomino
     * @param x
     * @param y
     * @param z
     * @param size_transformation
     * @param c
     */
    public Pentomino(double x, double y, double z, double size_transformation, Color c) {
        pentcolor = c;

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

    /**
     * Has to be overriden by child-class. Constructs the beginarray of boxes
     */
    public abstract void createBeginShape();

    /**
     * Creates a javafx box at the right blocksize and transformation.
     * Also sets it to the right material color.
     * You basically call this method for each of the 5 boxes of the pentomino.
     * @param ntblocksize
     * @param size_transform
     * @return
     */
    public Box getNewBox(double ntblocksize, double size_transform) {
        Box newb = new Box(ntblocksize * size_transform, ntblocksize * size_transform, ntblocksize * size_transform);
        PhongMaterial mat = Other_Main_Drawer.createMaterial(pentcolor);
        newb.setMaterial(mat);
        return newb;
    }

    /**
     * Sets the location of all the boxes in the boxarray to the right ones, corresponding to the index in the boxarray grid.
     * Is called when a pentomino is constructed, but also after a rotation.
     */
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


    /**
     * Rotate Pentomino around Z axis. All rotation is done around the middle of the boxgrid. So boxgrid[1][1][1].
     * @param ticks every tick is 90 degrees. So 4 ticks is a full rotation
     */
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

    /**
     * Rotate Pentomino around Y axis. All rotation is done around the middle of the boxgrid. So boxgrid[1][1][1].
     * @param ticks every tick is 90 degrees. So 4 ticks is a full rotation
     */
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

    /**
     * Rotate Pentomino around x axis. All rotation is done around the middle of the boxgrid. So boxgrid[1][1][1].
     * @param ticks every tick is 90 degrees. So 4 ticks is a full rotation
     */
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

    /**
     * @return original color of pentomino, used for setting visibility
     */
    public Color getOriginalColor(){
        return pentcolor;
    }



}
