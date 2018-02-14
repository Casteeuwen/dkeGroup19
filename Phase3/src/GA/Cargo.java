package GA;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The container in which the boxes will be fit
 */
public class Cargo extends Space {
    private HashMap<Box, Integer> boxes;
    private ArrayList<Box> boxes1;
    private int totalValue;

    /**
     * Constructor
     * @param length length of container
     * @param height height of container
     * @param width width of container
     */
    public Cargo(int length, int height, int width) {
        super(length, height, width);
        boxes = new HashMap<>();
        boxes1 = new ArrayList<>();
        totalValue = 0;
    }

    /**
     * Adds a box to the container
     * @param box box to be added
     * @throws Exception
     */
    public void addBox(Box box) throws Exception {
        try {
            addShape(box, box.getValue());
        } catch (Exception e) {
            throw new Exception("Cargo is full");
        }

        if (boxes.containsKey(box)) {
            boxes.put(box, boxes.get(box) + 1);
        } else {
            boxes.put(box, 1);
        }

        boxes1.add(box);

        totalValue += box.getValue();
    }

    /**
     * Empty the container
     */
    public void clear() {
        initiateContainer();
        boxes.clear();
        totalValue = 0;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public HashMap<Box, Integer> getBoxes() {
        return boxes;
    }

    public ArrayList<Box> getBoxes1() {
        return boxes1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cargo has: \n");
        for (Box b : boxes.keySet()) {
            sb.append(b + ": " + boxes.get(b) + "\n");
        }
        sb.append("Total value: " + totalValue);
        return sb.toString();
    }
}
