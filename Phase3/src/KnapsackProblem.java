import java.util.Scanner;

public class KnapsackProblem{


  private static int x;
  private static int y;
  private static int z;
  private static int b;
  private static final int WEIGHT_LIMIT = 7;
  private static final int NUMBER_OF_ITEMS = 4;
  private static final int[][] ITEMS = {{1, x}, {3, y}, {4, z}, {5, b}};
  private static int weightSum = 0;
  private static int valueSum = 0;
  private static int[][] table = new int[NUMBER_OF_ITEMS+1][WEIGHT_LIMIT+2]; //one more column for the items themselves

  public static void main(String[] args){
    Scanner in = new Scanner(System.in);

    System.out.println("Type in values for all items");
    x = in.nextInt();
    y = in.nextInt();
    z = in.nextInt();
    b = in.nextInt();
    in.close();

    table[0][0] = 0; //set valye of position (0,0) to 0.
    for(int i = 1; i<(WEIGHT_LIMIT+2); i++){
      table[0][i] = i-1; //weights for reference on the first row
    }
    for(int i = 1; i<(NUMBER_OF_ITEMS+1); i++){
      table[i][0] = ITEMS[i-1][0]; //set first colum with weights of different items
    }
    for(int i = 1; i<(NUMBER_OF_ITEMS+1); i++){
      for(int j = 1; j<(WEIGHT_LIMIT+2); j++){
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
            table[i][j] = Math.max(valueOf(table[i][0]) + table[i - 1][j - table[i][0]], table[i-1][j]); //compare the value of item + value on position x after algorithm, versus value above the one we want to write.
          }
      }
   }
   for(int i = 1; i<(NUMBER_OF_ITEMS+1); i++){ //print the table with the results
     for(int j = 1; j<(WEIGHT_LIMIT+2); j++){
        System.out.print(table[i][j]);
     }
     System.out.println();
   }

     if(table[NUMBER_OF_ITEMS][WEIGHT_LIMIT + 1] == table[NUMBER_OF_ITEMS-1][WEIGHT_LIMIT + 1]){
       if(table[NUMBER_OF_ITEMS-1][WEIGHT_LIMIT + 1] != table[NUMBER_OF_ITEMS-2][WEIGHT_LIMIT + 1]){
         weightSum += table[NUMBER_OF_ITEMS-1][0];
         valueSum += valueOf(table[NUMBER_OF_ITEMS-1][0]);
         if(table[NUMBER_OF_ITEMS-2][WEIGHT_LIMIT + 1 - table[NUMBER_OF_ITEMS-1][0]] != table[NUMBER_OF_ITEMS-3][WEIGHT_LIMIT + 1 - table[NUMBER_OF_ITEMS-1][0]]){
           weightSum += table[NUMBER_OF_ITEMS-2][0];
           valueSum += valueOf(table[NUMBER_OF_ITEMS-2][0]);
           if(table[NUMBER_OF_ITEMS-3][WEIGHT_LIMIT + 1 - (table[NUMBER_OF_ITEMS-1][0]) - (table[NUMBER_OF_ITEMS-2][0])] == 0){
             System.out.println("Done!");
           }
         }
       }
     }else{
        weightSum += table[NUMBER_OF_ITEMS][0]; //start making the sum of weights of chosen items
        valueSum += valueOf(table[NUMBER_OF_ITEMS][0]);
     }

     System.out.println("total weight: " + weightSum);
     System.out.println("total value: " + valueSum);
  }

  public static int valueOf(int value){
    if(value == 1){
      return x;
    }else if(value == 3){
      return y;
    }else if(value == 4){
      return z;
    }else if(value == 5){
      return b;
    }else{
    return 0;
    }
  }
}
