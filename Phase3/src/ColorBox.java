import javafx.scene.paint.Color;
import javafx.scene.shape.Box;

public class ColorBox extends Box {
    private Color myColor;
    public ColorBox(double w,double h,double d,Color boxc){
        super(w,h,d);
        myColor= boxc;
    }

    public Color getOriginalColor(){
        return myColor;
    }
}
