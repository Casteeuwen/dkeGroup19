import javafx.scene.paint.Color;
import javafx.scene.shape.Box;

/**
 * Simple class that extends the javafx3d box class.
 * Only extra functionality is that it remembers it's original color.
 * This is handy for setting visibility on and off for different colors
 */
public class ColorBox extends Box {
    private Color myColor;

    /**
     * Construct a javafx with a given w,h,d.
     * Register the Color of that box.
     * @param w
     * @param h
     * @param d
     * @param boxc
     */
    public ColorBox(double w,double h,double d,Color boxc){
        super(w,h,d);
        myColor= boxc;
    }

    public Color getOriginalColor(){
        return myColor;
    }
}
