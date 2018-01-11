import java.util.Scanner;

public class KnapsackProblem{


  private static int x;
  private static int y;
  private static int z;
  private static int b;
  private static final int WEIGHT = 7;
  private static final int ITEM = 4;
  private static final int[][] ITEMS = {{1, x}, {3, y}, {4, z}, {5, b}};
  private static int weightSum = 0;
  private static int valueSum = 0;
  private static int[][] table = new int[ITEM+1][WEIGHT+2]; //one more column for the items themselves

  public static void main(String[] args){
    Scanner in = new Scanner(System.in);

    System.out.println("Type in values for all items");
    x = in.nextInt();
    y = in.nextInt();
    z = in.nextInt();
    b = in.nextInt();
    in.close();

    table[0][0] = 0; //set valye of position (0,0) to 0.
    for(int i = 1; i<(WEIGHT+2); i++){
      table[0][i] = i-1; //weights for reference on the first row
    }
    for(int i = 1; i<(ITEM+1); i++){
      table[i][0] = ITEMS[i-1][0]; //set first colum with weights of different items
    }
    for(int i = 1; i<(ITEM+1); i++){
      for(int j = 1; j<(WEIGHT+2); j++){
          if((i == 1) && (table[0][j] == 0)){
            table[i][j] = 0; //set to zero when weight limit is 0
          }
          if((i == 1) && (table[i][0] <= table[0][j])){
            table[i][j] = valueOf(table[i][0]); //fill boxes of row 1 with the value of the first item on the table.
          }
          if((table[0][j] < table[i][0]) && (i != 1)){
            table[i][j] = table[i-1][j]; //put the value of the box above it if weight is smaller than limit weight
          }
          if((table[0][j] >= table[i][0]) && (i != 1)){
            table[i][j] = Math.max(valueOf(table[i][0]) + table[i - 1][j - table[i][0]], table[i-1][j]); //compare the value of item + value on position x after algorithm, versus value above the number where we are at.
          }
      }
   }
   for(int i = 1; i<(ITEM+1); i++){ //print the table with the results
     for(int j = 1; j<(WEIGHT+2); j++){
        System.out.print(table[i][j]);
     }
     System.out.println();
   }

     if(table[ITEM][WEIGHT + 1] == table[ITEM-1][WEIGHT + 1]){ //if last value on table is equal to the one above it.
       if(table[ITEM-1][WEIGHT + 1] != table[ITEM-2][WEIGHT + 1]){ //if the value that was above the one before is not equal to the one above it, then that item on that row is picked.
         weightSum += table[ITEM-1][0]; //weight is added to weightSum
         valueSum += valueOf(table[ITEM-1][0]); //value is added to valueSum
         if(table[ITEM-2][WEIGHT + 1 - (table[ITEM-1][0])] != (table[ITEM-3][WEIGHT + 1 - (table[ITEM-1][0])])) { //if value one position above and table[ITEMS-1][0] positions left is not equal to the one above it
           weightSum += table[ITEM-2][0]; //weight is added to weightSum
           valueSum += valueOf(table[ITEM-2][0]); //value is added to valueSum
           if(table[ITEM-3][WEIGHT + 1 - (table[ITEM-1][0]) - (table[ITEM-2][0])] == 0){ //if value one position above and table[ITEMS-1][0] positions to the left is = 0.
             System.out.println("Done!");
           }
         }
       }
     }else{
        weightSum += table[ITEM][0]; //start making the sum of weights of chosen items
        valueSum += valueOf(table[ITEM][0]); //start making the sum of values of chosen items
     }

     /*
     The algorithm above works for the given exercise on the youtube video. There are still a lot of things to write on it if we want it to work
     for any other example. I'll try and find a recursive way to solve it because every if statement would have another new if statement if we
     wanted to do it manually like this. It looks already very messy so imagine if I kept going with the if statements.
     */

     System.out.println("total weight: " + weightSum);
     System.out.println("total value: " + valueSum);
  }

  public static int valueOf(int value){ //takes in the weight of the item, and returns its corresponding value
    if(value == 1){
      return x;
    }else if(value == 3){
      return y;
    }else if(value == 4){
      return z;
    }else if(value == 5){
      return b;
    }else{
    return 0; //just so the computer does not complain about return statement missing, but it will never get here (as long as weights are the ones above).
    }
  }
}
