package GA;
import java.util.Arrays;

/**
 * Class that represents a box, which will further be added to the container
 */
public class Box {
    private double[] origin;
    private double length, height, width, value, volume;
    private String name;


    public Box(double length, double height, double width, double value, String name) throws Exception {
        if (length < 0 || height < 0 || width < 0) {
            throw new Exception("Negative parameter for box dimensions!");
        } else {
            this.length = length;
            this.height = height;
            this.width = width;
            this.value = value;
            this.name = name;
            volume = length * height * width;
        }
        origin = new double[]{0 ,0 ,0};
    }

    public double[] getOrigin() {
        return origin;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public double getHeight() {
        return height;
    }

    public double getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public double getVolume() {
        return volume;
    }

    public double[][] getRotations() {
        double[][] rotations = new double[6][3];
        rotations[0] = new double[]{getLength(), getHeight(), getWidth()};
        rotations[1] = new double[]{getLength(), getWidth(), getHeight()};
        rotations[2] = new double[]{getWidth(), getHeight(), getLength()};
        rotations[3] = new double[]{getWidth(), getLength(), getHeight()};
        rotations[4] = new double[]{getHeight(), getWidth(), getLength()};
        rotations[5] = new double[]{getHeight(), getLength(), getWidth()};
        return rotations;
    }

    @Override
    public String toString() {
        return name + " - origin: " + Arrays.toString(origin) + " length: " + length + " height: " + height + " width: " + width;
    }

    public void setOrigin(double[] origin) {
        this.origin = origin;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
