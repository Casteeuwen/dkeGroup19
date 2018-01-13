import java.util.Scanner;

public class KnapsackProblem{

  private static final int WEIGHT = 7; //fixed weight limit
  private static int[][] ITEMS = new int[4][2]; //array storing 4 items with weight and value respectively
  private static int[][] table = new int[ITEMS.length+1][WEIGHT+2]; //one more column for the items themselves

  public static void main(String[] args){

    Scanner in = new Scanner(System.in);

    for(int i = 0; i<ITEMS.length; i++){
      for(int j = 0; j<ITEMS[0].length; j++){
        if(j == 0){
          System.out.println("Type in weight for " + (i+1) + " item.");
          ITEMS[i][j] = in.nextInt();
        }else{
          System.out.println("Type in value for " + (i+1) + " item.");
          ITEMS[i][j] = in.nextInt();
        }
      }
    }

    table[0][0] = 0; //set valye of position (0,0) to 0.
    for(int i = 1; i<(WEIGHT+2); i++){
      table[0][i] = i-1; //weights for reference on the first row
    }
    for(int i = 1; i<(ITEMS.length+1); i++){
      table[i][0] = ITEMS[i-1][0]; //set first colum with weights of different items
    }
    for(int i = 1; i<(ITEMS.length+1); i++){
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
   for(int i = 1; i<(ITEMS.length + 1); i++){ //print the table with the results
     for(int j = 1; j<(WEIGHT+2); j++){
        System.out.print(table[i][j]);
     }
     System.out.println();
   }
    System.out.println("Max value = " + knapsack(ITEMS.length-1, WEIGHT));
  }

  public static int valueOf(int value){ //takes in the weight of the item, and returns its corresponding value
    if(value == ITEMS[0][0]){
      return ITEMS[0][1];
    }else if(value == ITEMS[1][0]){
      return ITEMS[1][1];
    }else if(value == ITEMS[2][0]){
      return ITEMS[2][1];
    }else if(value == ITEMS[3][0]){
      return ITEMS[3][1];
    }else{
    return 0; //just so the computer does not complain about return statement missing, but it will never get here (as long as weights are the ones above).
    }
  }

  public static int knapsack(int i, int W) { //recursive method to find maximum sum of value
    if(i < 0){ //base case
      return 0;
    }
    if(ITEMS[i][0] > W){ //if weight of item is > weight limit
      return knapsack(i-1, W);
    }else{ //if weight of item is <= weight limit
      return Math.max(knapsack(i-1, W), knapsack(i-1, W - ITEMS[i][0]) + ITEMS[i][1]);
    }
  }
}
