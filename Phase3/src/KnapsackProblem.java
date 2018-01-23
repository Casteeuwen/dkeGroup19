import java.util.Scanner;

public class KnapsackProblem {

    private static final double WEIGHT = 165.0; //fixed weight limit
    private static double[][] ITEMS = new double[3][2]; //array storing 3 weights for Phase 3
    private static String[] itemName = {"A", "B", "C"}; //fixed name of items for Phase 3
    //new String[ITEMS.length];
    private static int[] maxInstancesOfItem = new int[ITEMS.length];
    /* WHAT maxInstancesOfItem REPRESENTS:
    (weight limit) / (weight of an item). This gives the max amount of times that an item can be picked to reach the limit weight by itself.
    This value is used for each item on the recursive method to find out the BEST number of times
    an item must be chosen to achieve maximum value.
    */
    private static int[] bestInstancesOfItem = new int[ITEMS.length];
    /* WHAT bestInstancesOfItem REPRESENTS:
    This array will store the final number of times an item is used to achieve maximum value.
    Hence why it is writen as bestInstancesOfItem.
    */
    private static int[] chosenInstancesOfItem = new int[ITEMS.length];
    /* WHAT chosenInstancesOfItem REPRESENTS:
    This array can be seen as something similar to bestInstancesOfItem, but it doesn't
    only keep track of the final values. It also updates them from the first item to
    the last one, and taking into account every possibility. Only if a few confitions are met (line 95 - 99),
    the values updated here will then be passed to bestInstancesOfItem, and these will get
    updated until the conditions are not met anymore (line 99 - 103). Once they are not met anymore,
    the values on bestInstancesOfItem will be the final ones to represent the used instances of each item.
    */
    private static double[] bestWV = new double[ITEMS[0].length];
    /* WHAT bestWV REPRESENTS:
    This array simply saves the constantly updated variables of value and weight of the overall system,
    where bestWV[0] = weight and bestWV[1] = value. These are then used to print the numbers for both
    variables on along with the other relevant information printed out.
    */
    private static double updatedValue; //updates the value.
    private static double updatedWeight; //updates the weight.

  /*
  The reason why updatedValue and updatedWeight are not used to print the results for total value and weight is
  because these variables constantly save values. At one point the variable that updates the weight will be > WEIGHT.
  This means that if you tried using these variables, the weight (volume) covered by the pieces was > than the limit weight,
  and that is impossible. That is why BestWV is used for that, because it only takes in numbers if the condition in line 99 is met.
  */

    public static void startKnap(double val1,double val2,double val3) {

        //Scanner in = new Scanner(System.in);




        ITEMS[0][1] = val1;
        ITEMS[1][1] = val2;
        ITEMS[2][1] = val3;



        ITEMS[0][0] = 1 * 1 * 2;
        ITEMS[1][0] = 1 * 1.5 * 2;
        ITEMS[2][0] = 1.5 * 1.5 * 1.5;
        Knapsack(); //call to method Knapsack
    }

    public static void Knapsack() {

        for (int i = 0; i < ITEMS.length; i++) { //values for maxInstancesOfItem --> (WEIGHT) / (weight of item[i])
            maxInstancesOfItem[i] = (int) (WEIGHT / ITEMS[i][0]);
        }

        KnapsackDP(0); //call to recursive method starting with first item (ITEMS[0][0 or 1])

        //System.out.println("The volume (weight) occupied with all selected items is: " + bestWV[0]); //prints weight covered by chosen items
        //System.out.println("Max value: " + bestWV[1]);
        //System.out.print("To obtain this value, the selection of items is the following: ");
        for (int i = 0; i < ITEMS.length; i++) {
            if (i == ITEMS.length - 1) { //if i = last item, don't write a comma afterwards (so that it looks like x A, x B, x C)
                //System.out.println(bestInstancesOfItem[i] + " " + itemName[i]); //print best choice for item i, along with its name (both if and else)
            } else {
                //System.out.print(bestInstancesOfItem[i] + " " + itemName[i] + ", ");
            }
        }
    }

    public static void KnapsackDP(int item) { //recursive method

        for (int i = 0; i <= maxInstancesOfItem[item]; i++) { //check for every instance of first item
            chosenInstancesOfItem[item] = i;

            if (item < ITEMS.length - 1) { //if item < 2
                KnapsackDP(item + 1); //call method again with item 1
            } else {
                updatedValue = 0.0; //updatedValue and updatedWeight reset to 0 always on the else.
                updatedWeight = 0.0;
                for (int j = 0; j < ITEMS.length; j++) { //for every item (and their instances), the total sum of values and weights is determined.
                    updatedValue += chosenInstancesOfItem[j] * ITEMS[j][1];
                    updatedWeight += chosenInstancesOfItem[j] * ITEMS[j][0];
                }
                if (updatedValue > bestWV[1] && updatedWeight <= WEIGHT) { //if values meet these conditions, then they get passed onto bestWV
                    bestWV[1] = updatedValue;
                    bestWV[0] = updatedWeight;
                    for (int j = 0; j < ITEMS.length; j++) {
                        bestInstancesOfItem[j] = chosenInstancesOfItem[j]; //store in bestInstancesOfItem[] the number of times a single item was succesfully used.
                    }
                }
            }
        }
    }

    public static int[] getBestAmountEachBox() {
        return bestInstancesOfItem;
    }
}
