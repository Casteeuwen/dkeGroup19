package GA;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Packing methodologies
 */
public class Logic {

    public Logic() {

    }

    /**
     * Attempt to pack boxes in the container
     * @param boxes list of boxes
     * @param bps 'box packing sequence' - randomized sequence of boxes to be packed
     * @return a filled container
     */
    public Cargo packBoxes(ArrayList<Box> boxes, int[] bps) throws Exception {
        Cargo cargo = new Cargo(16.5, 4.0, 2.5);
        boolean[] packedBoxes = new boolean[boxes.size()];


        for (int i = 0; i < packedBoxes.length; i++) {
            packedBoxes[i] = false;
        }

        for (int i = 0; i < boxes.size(); i++) {
            int boxIndex = bps[i];

            if (!packedBoxes[boxIndex]) {
                ArrayList<EMS> tmpEMS = new ArrayList<>();
                Box curBox = boxes.get(boxIndex);

                //System.out.println("index " + i);
                //System.out.println("\n[*] Current box: " + curBox);

                ArrayList<EMS> curEMS = cargo.getEms();

                //System.out.println("[*] Current EMS list: ");
                for (EMS e : curEMS) {
                    //System.out.println(" -> " + e);
                }
                //System.out.println();

                cargo.calculateDistanceToOrigin(curEMS);
                Collections.sort(curEMS);

                //System.out.println("[*] Sorted EMS list:");
                for (EMS e : curEMS) {
                    //System.out.println(" -> " + e);
                }
                //System.out.println();

                for (EMS e : curEMS) {
                    tmpEMS.add(e);
                }

                for (EMS ems : curEMS) {

                    if (checkIfBoxFitsInEMS(curBox, ems)) {
                        curBox = getBestRotation(curBox, ems);

                        //System.out.println("Best rotation: " + curBox);
                        curBox.setOrigin(ems.getOrigin());
                        //System.out.println("Box's new origin: " + Arrays.toString(curBox.getOrigin()));
                        cargo.update(tmpEMS, curBox);
                        cargo.addBox(curBox);
                        packedBoxes[boxIndex] = true;
                        break;
                    }
                }


            }
        }
        return cargo;
    }

    /**
     * Calculate the fitness value of a container, measured in amount of space left empty
     * @param cargo container
     * @return a value between 0-1
     */
    public double calculateFitness(Cargo cargo) {
        double boxVolume = 0;
        for (Box b : cargo.getBoxes()) {
            boxVolume += b.getVolume();
        }
        return 1 - (boxVolume / cargo.getVolume());
    }

    /**
     * Checks whether a box fits in the Empty Maximum Space
     * @param box box to be fit
     * @param ems Empty Maximum Space
     * @return True/False
     */
    public boolean checkIfBoxFitsInEMS(Box box, EMS ems) {
        double[][] rotations = box.getRotations();
        for (int i = 0; i < rotations.length; i++) {
            box.setHeight(rotations[i][0]);
            box.setLength(rotations[i][1]);
            box.setWidth(rotations[i][2]);
            if (box.getLength() <= ems.getLength() &&
                    box.getHeight() <= ems.getHeight() &&
                    box.getWidth() <= ems.getWidth()) {
                //System.out.println(" - Box fits in EMS");
                return true;
            }
        }
        //System.out.println(" - Box doesn't fit in EMS");
        return false;
    }

    /**
     * Find the best rotation for the box to be added in
     * @param box the box to be added
     * @param ems Empty Maximum Space
     * @return a box in its best rotation
     */
    public Box getBestRotation(Box box, EMS ems) {
        double[] bestRotation = new double[3];
        double[][] rotations = box.getRotations();

        for (int i = 0; i < rotations.length; i++) {
            bestRotation[0] = rotations[i][0];
            bestRotation[1] = rotations[i][1];
            bestRotation[2] = rotations[i][2];

            box.setHeight(rotations[i][0]);
            box.setLength(rotations[i][1]);
            box.setWidth(rotations[i][2]);

            if (box.getLength() <= ems.getLength() &&
                    box.getHeight() <= ems.getHeight() &&
                    box.getWidth() <= ems.getWidth()) {
                double lengthMargin = ems.getLength() - box.getLength();
                double heightMargin = ems.getHeight() - box.getHeight();
                double widthMargin = ems.getWidth() - box.getWidth();

                if (bestRotation[0] < lengthMargin || bestRotation[1] < heightMargin || bestRotation[2] < widthMargin ) {
                    bestRotation[0] = rotations[i][0];
                    bestRotation[1] = rotations[i][1];
                    bestRotation[2] = rotations[i][2];
                }
            }
        }

        box.setHeight(bestRotation[0]);
        box.setLength(bestRotation[1]);
        box.setWidth(bestRotation[2]);

        return box;
    }

}
