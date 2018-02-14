package GA;

/**
 * A box that's going to be added to the container
 */
public class Box extends Space implements Cloneable, Comparable<Box> {
    private int value;
    private double density;
    private String name;

    /**
     * Constructor
     * @param length length of the box
     * @param height height of the box
     * @param width width of the box
     * @param value value of the box
     * @param name name of the box
     */
    public Box(int length, int height, int width, int value, String name) {
        super(length, height, width);
        this.value = value;
        this.name = name;
        density = value / getVolume();
    }

    /**
     * Get all possible rotations of the box
     * @return
     */
    public int[][] getRotations() {
        int[][] rotations = new int[6][3];
        rotations[0] = new int[]{getLength(), getHeight(), getWidth()};
        rotations[1] = new int[]{getLength(), getWidth(), getHeight()};
        rotations[2] = new int[]{getWidth(), getHeight(), getLength()};
        rotations[3] = new int[]{getWidth(), getLength(), getHeight()};
        rotations[4] = new int[]{getHeight(), getWidth(), getLength()};
        rotations[5] = new int[]{getHeight(), getLength(), getWidth()};
        return rotations;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (getClass() != object.getClass()) {
            return false;
        }

        Box compared = (Box) object;

        if (value != compared.getValue() ||
                name == null ||
                !name.equals(compared.getName()) ||
                density != compared.getDensity() ||
                getHeight() != compared.getHeight() ||
                getLength() != compared.getLength() ||
                getWidth() != compared.getWidth()) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        if (name == null) {
            return -1;
        }

        return getLength() + getWidth() + getHeight() + name.hashCode() + value;
    }

    public Box clone() {
        return new Box (getLength(), getWidth(), getHeight(), value, name);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public double getDensity() {
        return density;
    }

    @Override
    public String toString() {
        return "Box " + name;
    }

    @Override
    public int compareTo(Box other) {
        if (density == other.density) {
            return 0;
        } else if (density > other.density) {
            return 1;
        } else {
            return -1;
        }
    }
}
