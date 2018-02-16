package GA;
import java.util.ArrayList;
import java.util.Arrays;

public class Cargo extends Dimension {
    private ArrayList<EMS> ems;
    private ArrayList<Box> boxes;
    private int totalValue;
    private double volume;

    public Cargo(double length, double height, double width) throws Exception {
        super(new double[]{0, 0, 0}, length, height, width);
        ems = new ArrayList<>();
        ems.add(new EMS(getOrigin(), length, height, width));
        boxes = new ArrayList<>();
        totalValue = 0;
        volume = length * height * width;
    }

    public void addBox(Box box) {
        boxes.add(box);
        ////System.out.println("Box " + box + " added");
        totalValue += box.getValue();
    }

    public ArrayList<EMS> createEMS(Dimension cargo, Box box) throws Exception {
        ////System.out.println("[*] Starting EMS creation process");
        ArrayList<EMS> newEms = new ArrayList<>();
        ArrayList<EMS> emsToValidate = new ArrayList<>();
        double[] cargo_origin = cargo.getOrigin();
        double[] box_origin = box.getOrigin();

        if (isBoxOutside(cargo, box)) {
            return newEms;
        } else {

//        if (!isBoxOutside(cargo, box)) {
            EMS ems1 = new EMS(cargo_origin, box_origin[0] - cargo_origin[0] , cargo.getHeight(), cargo.getWidth());

            EMS ems2 = new EMS(cargo_origin, cargo.getLength(), cargo.getHeight(), box_origin[2] - cargo_origin[2]);

            EMS ems3 = new EMS(cargo_origin, cargo.getLength(), box_origin[1] - cargo_origin[1], cargo.getWidth());

            EMS ems4 = new EMS(sumArrays(new double[]{box_origin[0], cargo_origin[1], cargo_origin[2]}, new double[]{box.getLength(), 0, 0}), 0, cargo.getHeight(), cargo.getWidth());
            ems4.setLength(subtractArrays( sumArrays( cargo_origin, new double[]{cargo.getLength(), 0, 0} ), ems4.getOrigin() )[0]);

            EMS ems5 = new EMS(sumArrays(new double[]{cargo_origin[0], cargo_origin[1], box_origin[2]}, new double[]{0, 0, box.getWidth()}), cargo.getLength(), cargo.getHeight(), 0);
            ems5.setWidth(subtractArrays( sumArrays( cargo_origin, new double[]{0, 0, cargo.getWidth()} ), ems5.getOrigin() )[2]);

            EMS ems6 = new EMS(sumArrays(new double[]{cargo_origin[0], box_origin[1], cargo_origin[2]}, new double[]{0, box.getHeight(), 0}), cargo.getLength(), 0, cargo.getWidth());
            ems6.setHeight(subtractArrays( sumArrays( cargo_origin, new double[]{0, cargo.getHeight(), 0} ), ems6.getOrigin() )[1]);

            emsToValidate.add(ems1);
            emsToValidate.add(ems2);
            emsToValidate.add(ems3);
            emsToValidate.add(ems4);
            emsToValidate.add(ems5);
            emsToValidate.add(ems6);
        }

        for (EMS e : emsToValidate) {
            if (validateEMS(e)) {
                newEms.add(e);
            }
        }

        return newEms;
    }

    public boolean isBoxOutside(Dimension cargo, Box box) {
        //System.out.print("[*] Checking whether the box is outside ... ");
        double[] vertex1 = cargo.getOrigin();
        double[] vertex2 = {vertex1[0] + cargo.getLength(), vertex1[1] + cargo.getHeight(), vertex1[2] + cargo.getWidth()};

        double[] box_vertex1 = box.getOrigin();
        double[] box_vertex2 = box_vertex1.clone();
        double[] box_vertex3 = box_vertex1.clone();
        double[] box_vertex4 = box_vertex1.clone();
        box_vertex2[1] += box.getHeight();
        box_vertex3[2] += box.getWidth();
        box_vertex4[0] += box.getLength();

        if (vertex1[1] >= box_vertex2[1] ||
                vertex2[1] <= box_vertex1[1] ||
                vertex2[0] <= box_vertex1[0] ||
                vertex1[0] >= box_vertex4[0] ||
                vertex1[2] >= box_vertex3[2] ||
                vertex2[2] <= box_vertex1[2]) {
            ////System.out.println("yes");
            return true;
        }

        ////System.out.println("no");
        return false;
    }

    public boolean isEMSInAnotherEMS(EMS ems1, EMS ems2) {
//        //System.out.print("\n[*] Checking whether there are any EMS inside other EMS ... ");
        boolean[] check = new boolean[3];
        double[] ems1_vertex1 = ems1.getOrigin();
        double[] ems1_vertex2 = sumArrays(ems1.getOrigin(), new double[]{ems1.getLength(), ems1.getHeight(), ems1.getWidth()});

        double[] ems2_vertex1 = ems2.getOrigin();
        double[] ems2_vertex2 = sumArrays(ems2.getOrigin(), new double[]{ems2.getLength(), ems2.getHeight(), ems2.getWidth()});
        ////System.out.println("ems1: " + ems1 + " vertex1 " + Arrays.toString(ems1_vertex1) + "    vertex2 " + Arrays.toString(ems1_vertex2));
        ////System.out.println("ems2: " + ems2 + " vertex2 " + Arrays.toString(ems2_vertex1) + "    vertex2 " + Arrays.toString(ems2_vertex2));
        ////System.out.println();

//        if (ems1.getLength() <= ems2.getLength() && ems1.getHeight() <= ems2.getHeight() && ems1.getWidth() <= ems2.getWidth()) {
//            return true;
//        }
//        return false;

//         if (ems1_vertex1[0] >= ems2_vertex1[0] && ems1_vertex1[1] >= ems2_vertex1[1] && ems1_vertex1[2] >= ems2_vertex1[2]) {
//             boo = false;
//         }

         boolean firstCheck = ems1_vertex1[0] >= ems2_vertex1[0] && ems1_vertex1[1] >= ems2_vertex1[1] && ems1_vertex1[2] >= ems2_vertex1[2];
         boolean secondCheck = ems1_vertex2[0] <= ems2_vertex2[0] && ems1_vertex2[1] <= ems2_vertex2[1] && ems1_vertex2[2] <= ems2_vertex2[2];
//         if (ems1_vertex2[0] <= ems2_vertex2[0] && ems1_vertex2[1] <= ems2_vertex2[1] && ems1_vertex2[2] <= ems2_vertex2[2]) {
//            boo = false;
//         }


//        int j = 0;
//        for (int i = 0; i < ems1_vertex1.length; i++) {
//            if (ems1_vertex1[i] <= ems2_vertex1[i] && ems1_vertex2[i] >= ems2_vertex2[i]) {
////                ////System.out.println("no");
//                return false;
//            }

//            if (ems1_vertex1[i] >= ems2_vertex1[i]) {
//                check[j] = true;
//            } else {
//                check[j] = false;
//            }
//            j++;
//            if (ems1_vertex2[i] <= ems2_vertex2[i]) {
//                check[i] = true;
//            } else {
//                check[i] = false;
//            }
//            j++;
//        }

//        ////System.out.println("yes");
//        for (int i = 0; i < check.length; i++) {
//            if (!check[i]) {
//                return false;
//            }
//        }
//        return true;
        return firstCheck && secondCheck;
    }

    public void update(ArrayList<EMS> emsToUpdate, Box box) throws Exception {
        ////System.out.println("[*] Starting update process");
        ems = new ArrayList<>();
        ArrayList<EMS> newEMS = new ArrayList<>();
        ArrayList<Integer> indexesToRemove = new ArrayList<>();


        for (EMS e : emsToUpdate) {
            newEMS.add(e);
        }


        int i = 0;
        for (EMS e : emsToUpdate) {

            ////System.out.println("Updating " + e);

            ArrayList<EMS> tmpEMS = (createEMS(e, box));

            if (!tmpEMS.isEmpty() || isBoxEqualToEMS(box, e)) {

                indexesToRemove.add(i);

                for (EMS e2 : tmpEMS) {
                    newEMS.add(e2);
                }

//                newEMS = tmpEMS;
            }
            i++;
        }
        ////System.out.println("\nPreclean EMS: ");
        for (EMS e2 : newEMS) {
            ////System.out.println(e2);
        }

        if (!indexesToRemove.isEmpty()) {
            for (Integer j : indexesToRemove) {
                ////System.out.println("----> Removing " + newEMS.get(j));
                newEMS.remove(newEMS.get(j));
//                newEMS.remove(j);
            }
        }
        ////System.out.println("\nPreclean EMS2 : ");
        for (EMS e2 : newEMS) {
            ////System.out.println(e2);
        }



//        removeEMS(newEMS);
        ems = removeEMS(newEMS);

//        for (EMS e : newEMS) {
//            ems.add(e);
//        }
//        ems = removeEMS(newEMS);
        ////System.out.println("\nFinal EMS: ");
        for (EMS e2 : ems) {
            ////System.out.println(e2);
        }
//        return newEMS;
    }


    public ArrayList<EMS> removeEMS(ArrayList<EMS> input) {
        ////System.out.println("[*] Cleaning EMS after update ");
        ArrayList<EMS> toRemove = new ArrayList<>();
//        ArrayList<EMS> tmp = new ArrayList<>();
//
//        for (EMS e : input) {
//            tmp.add(e);
//        }

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.size(); j++) {
                if (i != j) {
                    if (isEMSInAnotherEMS(input.get(i), input.get(j))) {
//                        ////System.out.println(input.size());
                        ////System.out.println("-> Removing " + input.get(i));
                        toRemove.add(input.get(i));
                        break;
//                    } else if (isEMSInAnotherEMS(input.get(j), input.get(i))) {
////                        ////System.out.println(input.size());
////                        ////System.out.println("-> Removing " + input.get(j));
//                        toRemove.add(input.get(j));
                    }
                }


            }
        }

        for (EMS e : toRemove) {
            input.remove(e);
        }

        return input;
    }

    public boolean isBoxEqualToEMS(Box box, EMS ems) {
        //System.out.print("[*] Checking whether the box is equal to the EMS...");
        double[] box_origin = box.getOrigin();
        for (int i = 0; i < box_origin.length; i++) {
            if (box_origin[i] != ems.getOrigin()[i]) {
                ////System.out.println(" no");
                return false;
            }
        }

        if (box.getLength() != ems.getLength() || box.getHeight() != ems.getHeight() || box.getWidth() != ems.getWidth()) {
            ////System.out.println(" no");
            return false;
        }

        ////System.out.println(" yes");
        return true;
    }

    public boolean validateEMS(EMS ems) {
        //System.out.print("[*] Validating " + ems + " ... ");
        if (ems.getLength() <= 0 || ems.getWidth() <= 0 || ems.getHeight() <= 0) {
            ////System.out.println("denied");
            return false;
        }
        ////System.out.println("accepted");
        return true;
    }


    public double[] sumArrays(double[] arr1, double[] arr2) {
        double[] result = new double[arr1.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = arr1[i] + arr2[i];
        }
        return result;
    }

    public double[] subtractArrays(double[] arr1, double[] arr2) {
        double[] result = new double[arr1.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = arr1[i] - arr2[i];
        }
        return result;
    }

    public void calculateDistanceToOrigin(ArrayList<EMS> input) {
        for (EMS e : input) {
            double[] ems_origin = e.getOrigin();
            e.setDistance(Math.sqrt((Math.pow(ems_origin[0], 2) + Math.pow(ems_origin[1], 2) + Math.pow(ems_origin[2], 2))));
        }
    }

    @Override
    public String toString() {
        return "Cargo " + super.toString();
    }

    public void setEms(ArrayList<EMS> ems) {
        this.ems = ems;
    }

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public String getBoxesInfo() {
        int counterA = 0, counterB = 0, counterC = 0;
        StringBuilder sb = new StringBuilder();
        for (Box b : boxes) {
            switch (b.getName()) {
                case "A":
                    counterA++;
                    break;
                case "B":
                    counterB++;
                    break;
                case "C":
                    counterC++;
                    break;
                default:
                    break;
            }

        }
        sb.append("Box A: " + counterA + "\n");
        sb.append("Box B: " + counterB + "\n");
        sb.append("Box C: " + counterC + "\n");
        return sb.toString();

    }

    public double getVolume() {
        return volume;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public ArrayList<EMS> getEms() {
        return ems;
    }

    public void printEMS() {
        for (EMS e : ems) {
            ////System.out.println(e);
        }
    }
}
