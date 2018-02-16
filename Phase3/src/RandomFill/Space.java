package RandomFill;
/**
 * The space in which the Cargo and the Box will exist
 */

public class Space extends Dimension {
    private int[][][] container;
    private int[] origin;

    /**
     * Constructor
     * @param length length of space
     * @param height height of space
     * @param width width of space
     */
    public Space(int length, int height, int width) {
        super(length, height, width);
        origin = new int[]{0, 0, 0};
        container = new int[length][height][width];
        initiateContainer();
    }

    /**
     * Creates an empty container
     */
    public void initiateContainer() {
        for (int i = 0; i < getLength(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                for (int k = 0; k < getWidth(); k++) {
                    container[i][j][k] = 0;
                }
            }
        }
    }

    /**
     * Check whether the dimensions of the object fit at the given coordinates
     * @param dimension a dimension object that needs to be fitted
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     * @return True/False
     * @throws ArrayIndexOutOfBoundsException
     */

    public boolean fits(Dimension dimension, int x, int y, int z) throws ArrayIndexOutOfBoundsException {
        if (container.length < x + dimension.getLength() ||
                container[0].length < y + dimension.getHeight() ||
                container[0][0].length < z + dimension.getWidth()) {
            return false;
        }

        for (int i = 0; i < dimension.getLength(); i++) {
            for (int j = 0; j < dimension.getHeight(); j++) {
                for (int k = 0; k < dimension.getWidth(); k++) {
                    if (container[i + x][j + y][k + z] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Add a thing to the container
     * @param dimension dimensions of object to be added
     * @param value value of object to be added
     * @throws Exception
     */
    public void addShape(Dimension dimension, int value) throws Exception {
        int[] coords = getCoords(dimension);
        if (coords[0] == -1) {
            throw new Exception("Cargo is full");
        } else {
            dimension.setCoords(coords);
            for (int i = 0; i < dimension.getLength(); i++) {
                for (int j = 0; j < dimension.getHeight(); j++) {
                    for (int k = 0; k < dimension.getWidth(); k++) {
                        container[i + coords[0]][j + coords[1]][k + coords[2]] = value;
                    }
                }
            }
        }
    }

    /**
     * Find the next coordinates on which to place the object to be added
     * @param dimension dimensions of object to be added
     * @return coordinates
     */
    public int[] getCoords(Dimension dimension) {
        for (int i = 0; i < getLength(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                for (int k = 0; k < getWidth(); k++) {
                    if (container[i][j][k] == 0 && fits(dimension, i, j, k)) {
                        dimension.setCoords(new int[]{i, j, k});
                        return new int[]{i, j, k};
                    }
                }
            }
        }
        return new int[]{-1, -1, -1};
    }

    /**
     * Print the Cargo as a 3D matrix
     */
    public void printContainer() {
        for (int i = 0; i < getLength(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                for (int k = 0; k < getWidth(); k++) {
                    System.out.print(container[i][j][k]);
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public int[] getOrigin() {
        return origin;
    }
}
