import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Pentomino extends Group {
    public Box getNewBox(double ntblocksize, double size_transform){
        Box newb = new Box(ntblocksize*size_transform,ntblocksize*size_transform,ntblocksize*size_transform);
        PhongMaterial mat = Other_Main_Drawer.createMaterial(Color.WHITE);
        newb.setMaterial(mat);
        return newb;
    }
}
