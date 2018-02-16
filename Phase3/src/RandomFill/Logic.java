package RandomFill;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Packing methodologies
 */
public class Logic {
    private Random random;
    private Box A, B, C;
    private ArrayList<Box> boxes;

    public Logic() {
        random = new Random();
        A = new Box(10, 10, 20, 3, "A");
        B = new Box(10, 15, 20, 4, "B");
        C = new Box(15, 15, 15, 5, "C");
        boxes = new ArrayList<>();
        boxes.add(A);
        boxes.add(B);
        boxes.add(C);
    }

    /**
     * Attempt to pack boxes in the container
     * @param boxes list of boxes
     * @param bps 'box packing sequence' - randomized sequence of boxes to be packed
     * @return a filled container
     */
//    public Cargo packBoxes(ArrayList<Box> boxes, int[] bps) {
//        Cargo cargo = new Cargo(165, 40, 25);
//        boolean[] packedBoxes = new boolean[boxes.size()];
//        for (int i = 0; i < packedBoxes.length; i++) {
//            packedBoxes[i] = false;
//        }
//
//        for (int i = 0; i < boxes.size(); i++) {
//            int boxIndex = bps[i];
//
//            if (!packedBoxes[boxIndex]) {
//                Box curBox = boxes.get(boxIndex);
//                ArrayList<EMS> curEMS = ems.createEMS(cargo, curBox);
//                Collections.sort(curEMS);
//
//                for (EMS ems : curEMS) {
//                    if (checkIfBoxFitsInEMS(curBox, ems)) {
//                        curBox = getBestRotation(curBox, ems);
//                        try {
//                           cargo.addBox(curBox);
//                        } catch (Exception e) {
//
//                        }
//                        packedBoxes[boxIndex] = true;
//                        break;
//                    }
//                }
//
//            }
//        }
//        return cargo;
//    }

    /**
     * Calculate the fitness value of a container, measured in amount of space left empty
     * @param cargo container
     * @return a value between 0-1
     */
    public double calculateFitness(Cargo cargo) {
        double boxVolume = 0;
        for (Box b : cargo.getBoxes().keySet()) {
            boxVolume += b.getVolume() * cargo.getBoxes().get(b);
        }
        return 1 - boxVolume / cargo.getVolume();
    }

    /**
     * Checks whether a box fits in the Empty Maximum Space
     * @param box box to be fit
     * @param ems Empty Maximum Space
     * @return True/False
     */
//    public boolean checkIfBoxFitsInEMS(Box box, EMS ems) {
//        int[][] rotations = box.getRotations();
//        for (int i = 0; i < rotations.length; i++) {
//            box.setHeight(rotations[i][0]);
//            box.setLength(rotations[i][1]);
//            box.setWidth(rotations[i][2]);
//            if (box.getLength() <= ems.getLength() &&
//                    box.getHeight() <= ems.getHeight() &&
//                    box.getWidth() <= ems.getWidth()) {
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * Find the best rotation for the box to be added in
     * @param box the box to be added
     * @param ems Empty Maximum Space
     * @return a box in its best rotation
     */
//    public Box getBestRotation(Box box, EMS ems) {
//        int[] bestRotation = new int[3];
//        int[][] rotations = box.getRotations();
//
//        for (int i = 0; i < rotations.length; i++) {
//            bestRotation[0] = rotations[i][0];
//            bestRotation[1] = rotations[i][1];
//            bestRotation[2] = rotations[i][2];
//
//            box.setHeight(rotations[i][0]);
//            box.setLength(rotations[i][1]);
//            box.setWidth(rotations[i][2]);
//
//            if (box.getLength() <= ems.getLength() &&
//                    box.getHeight() <= ems.getHeight() &&
//                    box.getWidth() <= ems.getWidth()) {
//                int lengthMargin = ems.getLength() - box.getLength();
//                int heightMargin = ems.getHeight() - box.getHeight();
//                int widthMargin = ems.getWidth() - box.getWidth();
//
//                if (bestRotation[0] < lengthMargin || bestRotation[1] < heightMargin || bestRotation[2] < widthMargin ) {
//                    bestRotation[0] = rotations[i][0];
//                    bestRotation[1] = rotations[i][1];
//                    bestRotation[2] = rotations[i][2];
//                }
//            }
//        }
//
//        box.setHeight(bestRotation[0]);
//        box.setLength(bestRotation[1]);
//        box.setWidth(bestRotation[2]);
//
//        return box;
//    }

    /**
     * Fills the cargo with boxes based on RNG
     * @return a randomly packed container
     */
    public Cargo fillRandomly() {
        Box box = null;
        Cargo cargo = new Cargo(165, 40, 25);
        while (true) {
            switch(random.nextInt(3)) {
                case 0:
                    box = new Box(10, 10, 20, 3, "A");
                    break;
                case 1:
                    box = new Box(10, 15, 20, 4, "B");
                    break;
                case 2:
                    box = new Box(15, 15, 15, 5, "C");
                    break;
                default:
                    break;
            }
            try {
                cargo.addBox(box);
            } catch (Exception e) {
                break;
            }
        }
        return cargo;
    }
}
