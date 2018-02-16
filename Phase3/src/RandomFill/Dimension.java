package RandomFill;

/**
 * The core of all the objects to be used (i.e. Box, Cargo, Space)
 */
public class Dimension {
    private int length, width, height, volume;
    private int[] coords;

    /**
     * Constructor
     * @param length length of dimension
     * @param height height of dimension
     * @param width width of dimension
     */
    public Dimension(int length, int height, int width) {
        this.length = length;
        this.width = width;
        this.height = height;
        volume = length * width * height;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getVolume() {
        return volume;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setCoords(int[] coords) {
        this.coords = coords;
    }

    public int[] getCoords() {
        return coords;
    }
}
