import java.text.*;

public class UnboundedKnapsack {

    protected Item [] items = { //array that holds objects of class Item.
                               new Item("A", 3, 2.0, 2.0), //
                               new Item("B", 4, 3.0, 3.0),
                               new Item("C", 5, 3.375, 3.375)
                               };
    protected final int n = items.length; // the number of items
    protected Item sack = new Item("sack", 0, 165.0, 165.0);
    protected Item best = new Item("best", 0, 0.0, 0.000);
    protected int[] maxItems = new int[n];  // maximum number of items
    protected int[] indexItems = new int[n];  // current indexes of items
    protected int[] bestAmount = new int[n];  // best amounts

    public UnboundedKnapsack(){
        // initializing:
        for (int i = 0; i < n; i++) { // min between (weight of sack / weight of item i) and (volume of sack / volume of item i)
            maxItems[i] = Math.min(
                           (int)(sack.getWeight() / items[i].getWeight()),
                           (int)(sack.getVolume() / items[i].getVolume())
                        ); //smallest value is stored on maxItems[i].
        } // for (i)

        // calc the solution:
        KnapsackRecursion(0);

        // Print out the solution:
        NumberFormat nf = NumberFormat.getInstance();
        System.out.println("Maximum value achievable is: " + best.getValue());
        System.out.print("This is achieved by carrying (one solution): ");
        for (int i = 0; i < n; i++) {
            System.out.print(bestAmount[i] + " " + items[i].getName() + ", ");
        }
        System.out.println();
        System.out.println("The weight to carry is: " + nf.format(best.getWeight()) +
                           "   and the volume used is: " + nf.format(best.getVolume())
                          );

    }

    // calculation the solution with recursion method
    // item : the number of item in the "items" array
    public void KnapsackRecursion(int item){ //start from lowest to highest index of items.
        for(int i = 0; i <= maxItems[item]; i++){
        /*When int item = 0, for loop will happen 83 times,
        because maxItems[0] holds the value of 82.5. This is because
        weight of the sack / weigth of first item (items[0]) = 165 / 2 = 82.5,
        (and with values for volume, result is the same always since volume = weight for Phase 3)
        so maxItems[0] = min(82.5, 82,5) = 82.5. Loop's running interval [0, 82]  */
            indexItems[item] = i; //value of i is saved on the index item (0 in the first call) of indexItems[].
            if(item < n-1){ //if the item's labeled number is strictly smaller than 2 (0 or 1 only), then call method again with same item's number + 1.
                KnapsackRecursion(item+1); //recursive call of the next item (first time loop runs, it will now call calcWithRecursion(1)).
            }else{ //if item >= 2
                int currVal = 0;   // current value
                double currWei = 0.0; // current weight
                double currVol = 0.0; // current Volume
                for(int j = 0; j < n; j++){ //for loop that allows picking one single items as many times as allowed.
                    currVal += indexItems[j] * items[j].getValue();
                    currWei += indexItems[j] * items[j].getWeight();
                    currVol += indexItems[j] * items[j].getVolume();
                }
                /*
                Previous for loop runs through each item and sets its new value, weight, and volume.
                This for loop shows the characteristics of the unbounded Knapsack Problem, since value, weight and volume
                will be getting added if an item is used more than once as currVal, currWei and currVol keep adding
                the same numbers to the previously saved number. (This shows that if item 0 was hypothetically used twice, then the for loop
                will run two times so that value, weight, and volume are added twice to their respective variables).
                */

                if(currVal > best.getValue() && currWei <= sack.getWeight() && currVol <= sack.getVolume()){
                    best.setValue(currVal);
                    best.setWeight(currWei);
                    best.setVolume(currVol);
               /*
               Here, object of Item "best" represents the highScore of a game of Tetris (kinda),
               all three variables get updated on "best" as long as those three conditions are
               always met. The reason why currVal refers to the "best" object on the first condition and currWei and currVol
               refer to the "sack" object on the second and third conditions, is because the "sack" object has no value, but "best" does(it is intially set to 0).
               Therefore current value gets compared with "best" object, and the other variables get compared
               to the "sack" object.
               Still, in the end, if those conditions are met, all three variables are updated on the "best" object
               through the three methods setValue(), setWeight() and setVolume().
              */
                    for (int j = 0; j < n; j++){
                      bestAmount[j] = indexItems[j]; //store in bestAmount[] the number of times a single item was succesfully used.
                    }
                } // if (...)
            } // else
        } // for (i)
    } // calcWithRecursion()

    public static void main(String[] args){
        new UnboundedKnapsack(); //create an object of class UnboundedKnapsack.
    }
}
