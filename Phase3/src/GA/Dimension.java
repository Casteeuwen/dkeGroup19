package GA;
import java.util.Arrays;

/**
 * Superclass of Cargo and EMS classes, contains the origin, length, height and width of object of those classes
 */
public class Dimension {
    private double[] origin;
    private double length, height, width;

    public Dimension(double[] origin, double length, double height, double width) throws Exception {
//        if (length < 0 || height < 0 || width < 0) {
//            throw new Exception("Negative parameter for cargo dimensions!");
//        } else {
            this.length = length;
            this.height = height;
            this.width = width;
//        }
        this.origin = origin;
    }

    public double getHeight() {
        return height;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double[] getOrigin() {
        return origin;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setOrigin(double[] origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return "origin: " + Arrays.toString(getOrigin()) + " length: " + getLength() + " height: " + getHeight() + " width: " + getWidth();
    }
}
