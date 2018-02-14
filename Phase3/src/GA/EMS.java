package GA;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

/**
 * Class that takes care of calculating the Empty Maximum Space generated after a box is placed in the container
 */
public class EMS implements Comparable<EMS> {
    private ArrayList<EMS> ems;
    private int[] origin;
    private int length, height, width;
    private double distanceToOrigin;

    /**
     * Constructor
     */
    public EMS() {
        ems = new ArrayList<>();
        distanceToOrigin = 0;
    }

    /**
     * Second constructor, creates an Empty Maximum Space
     * @param origin origin of the EMS
     * @param length length of EMS
     * @param height height of EMS
     * @param width width of EMS
     */
    public EMS(int[] origin, int length, int height, int width) {
        this.origin = origin;
        this.length = length;
        this.height = height;
        this.width = width;
    }

    /**
     * Check whether the box goes outside the container
     * @param cargo container
     * @param box box to be checked
     * @return True/False
     */
    public boolean isBoxOutside(Cargo cargo, Box box) {
        int[] vertex1 = cargo.getOrigin();
        int[] vertex2 = {vertex1[0] + cargo.getLength(), vertex1[1] + cargo.getHeight(), vertex1[2] + cargo.getWidth()};

        int[] box_vertex1 = cargo.getCoords(box);
        int[] box_vertex2 = box_vertex1.clone();
        int[] box_vertex3 = box_vertex1.clone();
        int[] box_vertex4 = box_vertex1.clone();
        box_vertex2[1] += box.getHeight();
        box_vertex3[2] += box.getWidth();
        box_vertex4[0] += box.getLength();

        if (vertex1[1] >= box_vertex2[1] ||
                vertex2[1] <= box_vertex1[1] ||
                vertex2[0] <= box_vertex1[0] ||
                vertex1[0] >= box_vertex4[0] ||
                vertex1[2] >= box_vertex3[2] ||
                vertex2[2] <= box_vertex1[2]) {
            return true;
        }

        return false;
    }

    /**
     * Create an Empty Maximum Space
     * @param cargo container
     * @param box box
     * @return a list of all possible EMS for the given box
     */
    public ArrayList<EMS> createEMS(Cargo cargo, Box box) {
        ems = new ArrayList<>();
        ArrayList<EMS> emsToValidate = new ArrayList<>();
        int[] cargo_origin = cargo.getOrigin();
        int[] box_origin = cargo.getCoords(box);

        if (!isBoxOutside(cargo, box)) {
            EMS ems1 = new EMS(cargo_origin, box_origin[0] - cargo_origin[0] , cargo.getHeight(), cargo.getWidth());

            EMS ems2 = new EMS(cargo_origin, cargo.getLength(), cargo.getHeight(), box_origin[2] - cargo_origin[2]);

            EMS ems3 = new EMS(cargo_origin, cargo.getLength(), box_origin[1] - cargo_origin[1], cargo.getWidth());

            EMS ems4 = new EMS(sumArrays(new int[]{box_origin[0], cargo_origin[1], cargo_origin[2]}, new int[]{box.getLength(), 0, 0}), 0, cargo.getHeight(), cargo.getWidth());
            ems4.setLength(subtractArrays( sumArrays( cargo_origin, new int[]{cargo.getLength(), 0, 0} ), ems4.getOrigin() )[0]);

            EMS ems5 = new EMS(sumArrays(new int[]{cargo_origin[0], cargo_origin[1], box_origin[2]}, new int[]{0, 0, box.getWidth()}), cargo.getLength(), cargo.getHeight(), 0);
            ems5.setWidth(subtractArrays( sumArrays( cargo_origin, new int[]{0, 0, cargo.getWidth()} ), ems5.getOrigin() )[2]);

            EMS ems6 = new EMS(sumArrays(new int[]{cargo_origin[0], box_origin[1], cargo_origin[2]}, new int[]{0, box.getHeight(), 0}), cargo.getLength(), 0, cargo.getWidth());
            ems6.setHeight(subtractArrays( sumArrays( cargo_origin, new int[]{0, cargo.getHeight(), 0} ), ems6.getOrigin() )[1]);

            emsToValidate.add(ems1);
            emsToValidate.add(ems2);
            emsToValidate.add(ems3);
            emsToValidate.add(ems4);
            emsToValidate.add(ems5);
            emsToValidate.add(ems6);
        }

        for (EMS e : emsToValidate) {
            if (validateEMS(e)) {
                ems.add(e);
            }
        }
        return ems;
    }

    /**
     * Check whether the EMS overlaps with other EMSs
     * @param ems1 original EMS
     * @param ems2 EMS to be compared
     * @return True/False
     */
    public boolean isEMSInAnotherEMS(EMS ems1, EMS ems2) {
        int[] ems1_vertex1 = ems1.getOrigin();
        int[] ems1_vertex2 = sumArrays(ems1.getOrigin(), new int[]{ems1.getLength(), ems1.getHeight(), ems1.getWidth()});

        int[] ems2_vertex1 = ems2.getOrigin();
        int[] ems2_vertex2 = sumArrays(ems2.getOrigin(), new int[]{ems2.getLength(), ems2.getHeight(), ems2.getWidth()});

        for (int i = 0; i < ems1_vertex1.length; i++) {
            if (ems1_vertex1[i] <= ems2_vertex1[i] && ems1_vertex2[i] >= ems2_vertex2[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Removes overlapping EMSs
     */
    public void removeEMS() {
        ArrayList<EMS> toRemove = new ArrayList<>();
        ArrayList<EMS> tmp = new ArrayList<>();

        for (EMS e : ems) {
            tmp.add(e);
        }

        ListIterator<EMS> iterator1 = ems.listIterator();

        while(iterator1.hasNext()) {
            EMS ems = iterator1.next();
            tmp.remove(ems);
            ListIterator<EMS> iterator2 = tmp.listIterator();

            while (iterator2.hasNext()) {
                EMS ems2 = iterator2.next();
                if (isEMSInAnotherEMS(ems, ems2)) {
                    toRemove.add(ems);
                }
            }
            tmp.add(ems);
        }

        for (EMS e : toRemove) {
            ems.remove(e);
        }
    }

    /**
     * Check whether the size of the box is equal to the size of EMS
     * @param cargo
     * @param box
     * @param ems
     * @return True/False
     */
    public boolean isBoxEqualToEMS(Cargo cargo, Box box, EMS ems) {
        int[] box_origin = cargo.getCoords(box);
        for (int i = 0; i < box_origin.length; i++) {
            if (box_origin[i] != ems.getOrigin()[i]) {
                return false;
            }
        }

        if (box.getLength() != ems.getLength() || box.getHeight() != ems.getHeight() || box.getWidth() != ems.getWidth()) {
            return false;
        }

        return true;
    }

    /**
     * Calculates how much distance is there to the origin of a given EMS
     * @param ems
     */
    public void calculateDistanceToOrigin(EMS ems) {
        int[] ems_origin = ems.getOrigin();
        distanceToOrigin = Math.sqrt((Math.pow(ems_origin[0], 2) + Math.pow(ems_origin[1], 2) + Math.pow(ems_origin[2], 2)));
    }

    public void printEMS() {
        for (EMS e : ems) {
            System.out.println(e);
        }
    }

    /**
     * Helper function, sums two primitive arrays
     * @param arr1
     * @param arr2
     * @return a sum of two arrays
     */
    public int[] sumArrays(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = arr1[i] + arr2[i];
        }
        return result;
    }

    /**
     * Helper function, subtracts two primitive arrays
     * @param arr1
     * @param arr2
     * @return a difference of two arrays
     */
    public int[] subtractArrays(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = arr1[i] - arr2[i];
        }
        return result;
    }

    /**
     * Check whether the EMS can exist
     * @param ems
     * @return True/False
     */
    public boolean validateEMS(EMS ems) {
        if (ems.getLength() <= 0 || ems.getWidth() <= 0 || ems.getHeight() <= 0) {
            return false;
        }
        return true;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int[] getOrigin() {
        return origin;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getDistanceToOrigin() {
        return distanceToOrigin;
    }

    @Override
    public String toString() {
        return "Origin: " + Arrays.toString(origin) + " length: " + length + " height: " + height + " width: " + width;
    }

    @Override
    public int compareTo(EMS other) {
        if (distanceToOrigin == other.getDistanceToOrigin()) {
            return 0;
        } else if (distanceToOrigin > other.getDistanceToOrigin()) {
            return 1;
        } else {
            return -1;
        }
    }
}
