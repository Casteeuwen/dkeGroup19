package Bruteforce;

import UI.Other_Main_Drawer;
import javafx.scene.paint.Color;

import java.lang.*;

public class Greedy{
	private static double[][] boxes;
	private static double[][] boxinfo;
	private static double[] LargeBox;
	private static int nob = 3;//nob = Number Of Boxes (types of boxes)
	private static double CommonLargestFactor;
	private static boolean[][][]lbox;
	private static double[][] solution;
	private static double value1;
	private static double value2;
	private static double value3;
	
	public static void startAlgo(double val1, double val2, double val3, int amount1, int amount2, int amount3, boolean useknap){
		value1 = val1;
		value2 = val2;
		value3 = val3;

		if(useknap){
			double knapsum = 0;
			KnapsackProblem.startKnap(val1,val2,val3);
			//amount here means maxamount
			int[] amouteach = KnapsackProblem.getBestAmountEachBox();
			System.out.println("Knapsack:");
			for (int i = 0; i<3; i++){
				System.out.println("You can use " + amouteach[i]+" parcels of type " +(i+1));
			}
			knapsum += amouteach[0]*val1;
			knapsum += amouteach[1]*val2;
			knapsum += amouteach[2]*val3;
			System.out.println("The knapsack algorithm gave a maximum value of " + knapsum + ", calculated for an unrestricted amount of each box");
			if (amount1 > amouteach[0]){amount1 = amouteach[0];}
			if (amount2 > amouteach[1]){amount2 = amouteach[1];}
			if (amount3 > amouteach[2]){amount3 = amouteach[2];}
		}



		solution = new double[0][6];
		boxes = new double[nob+1][4];
		LargeBox = new double[4];
		boxinfo = new double[nob+1][3];
		//FILL IN BELLOW:
		CommonLargestFactor = 0.5;
		boxes[1][1] = 1 / CommonLargestFactor;// <-- Box 1 height
		boxes[1][2] = 1 / CommonLargestFactor;// <-- Box 1 width
		boxes[1][3] = 2 / CommonLargestFactor;// <-- Box 1 length
		boxes[2][1] = 1 / CommonLargestFactor;// <-- Box 2 height
		boxes[2][2] = 1.5 / CommonLargestFactor;// <-- Box 2 width
		boxes[2][3] = 2 / CommonLargestFactor;// <-- Box 2 length
		boxes[3][1] = 1.5 / CommonLargestFactor;// <-- Box 3 height
		boxes[3][2] = 1.5 / CommonLargestFactor;// <-- Box 3 width
		boxes[3][3] = 1.5 / CommonLargestFactor;// <-- Box 3 length
		boxinfo[1][1] = val1 / boxes[1][1] / boxes[1][2] / boxes[1][3];// <<-- Score of Box 1
		boxinfo[2][1] = val2 / boxes[2][1] / boxes[2][2] / boxes[2][3];// <<-- Score of Box 2
		boxinfo[3][1] = val3 / boxes[3][1] / boxes[3][2] / boxes[3][3];// <<-- Score of Box 3
		boxinfo[1][2] = amount1;// <<-- Max amount of Box 1
		boxinfo[2][2] = amount2;// <<-- Max amount of Box 2
		boxinfo[3][2] = amount3;// <<-- Max amount of Box 3
//		boxinfo[1][2] = 10000000;// <<-- Max amount of Box 1
//		boxinfo[2][2] = 10000000;// <<-- Max amount of Box 2
//		boxinfo[3][2] = 10000000;// <<-- Max amount of Box 3
		LargeBox[1] = 4 / CommonLargestFactor;// <-- LargeBox height
		LargeBox[2] = 2.5 / CommonLargestFactor;// <-- LargeBox width
		LargeBox[3] = 16.5 / CommonLargestFactor;// <-- LargeBox length
		//FILL IN ABOVE
		lbox = new boolean[(int)LargeBox[1]][(int)LargeBox[2]][(int)LargeBox[3]];
		if(boxinfo[1][1] < boxinfo[2][1]){
			swap(1,2);
			double te = value1;
			value1 = value2;
			value2 = te;
		}
		if(boxinfo[2][1] < boxinfo[3][1]){
			swap(2,3);
			double te = value3;
			value3 = value2;
			value2 = te;
		}
		if(boxinfo[1][1] < boxinfo[2][1]){
			swap(1,2);
			double te = value1;
			value1 = value2;
			value2 = te;
		}
		FillInBox();
		displayBoxes();
		//System.out.println("solution array: ");
		for(int i = 0; i<solution.length; i++){
			//System.out.println();
			for(int j = 0; j<6; j++){
				//System.out.print(solution[i][j]+"  ");
			}
		}
	}
	
	
	private static void swap(int x, int y){// swaps the order of the boxes x and y (used in main method)
		double[] tempbox = new double[4];
		for(int i = 1; i < 4; i++){tempbox[i] = boxes[x][i];}//  }
		for(int i = 1; i < 4; i++){boxes[x][i] = boxes[y][i];}// }}box dimentions
		for(int i = 1; i < 4; i++){boxes[y][i] = tempbox[i];}//  }
		//info
		for(int i = 1; i < 3; i++){tempbox[i] = boxinfo[x][i];}//   }
		for(int i = 1; i < 3; i++){boxinfo[x][i] = boxinfo[y][i];}//}}box info
		for(int i = 1; i < 3; i++){boxinfo[y][i] = tempbox[i];}//   }
	}
	
	
	private static void FillInBox(){
		for( int u = 1; u < 4; u++){// <<-- Box number
			for( int i = 0; i < LargeBox[1]; i++){//			}
				for( int j = 0; j < LargeBox[2]; j++){//		}}selects all cubes in the large box
					for( int k = 0; k < LargeBox[3]; k++){//	}
						//for every cube in the large box
						// tries every orientations and places the box in the first available one
						if(boxinfo[u][2] > 0){
							if((fitcheck(u,0,i,j,k)) && (fitcheck(u,1,i,j,k)) && (fitcheck(u,2,i,j,k)) && (fitcheck(u,3,i,j,k)) && (fitcheck(u,4,i,j,k)) && (fitcheck(u,5,i,j,k))){}
						}
						// if the peice won't fit in any orientations, the "cube" is left empty
					}
				}
			}
		}
	}
	
	private static boolean fitcheck(int B, int O, int i, int j, int k){// false = fit, true = can't fit (it makes a earlier part easier)
		double[] tempbox = new double[4];
		switch(O){
		case 0:tempbox[1] = boxes[B][1];
			tempbox[2] = boxes[B][2];
			tempbox[3] = boxes[B][3];
			break;
		case 1:tempbox[1] = boxes[B][1];
			tempbox[2] = boxes[B][3];
			tempbox[3] = boxes[B][2];
			break;
		case 2:tempbox[1] = boxes[B][2];
			tempbox[2] = boxes[B][1];
			tempbox[3] = boxes[B][3];
			break;
		case 3:tempbox[1] = boxes[B][2];
			tempbox[2] = boxes[B][3];
			tempbox[3] = boxes[B][1];
			break;
		case 4:tempbox[1] = boxes[B][3];
			tempbox[2] = boxes[B][1];
			tempbox[3] = boxes[B][2];
			break;
		case 5:tempbox[1] = boxes[B][3];
			tempbox[2] = boxes[B][2];
			tempbox[3] = boxes[B][1];
			break;
		}
		for(int a = 0; a < tempbox[1]; a++){
			for(int s = 0; s < tempbox[2]; s++){
				for(int d = 0; d < tempbox[3]; d++){
					if((i + a < LargeBox[1]) && (j + s < LargeBox[2]) && (k + d < LargeBox[3])){
						if (lbox[i + a][j + s][k + d]){return true;}
					}else{return true;}
				}
			}
		}
		for(int a = 0; a < tempbox[1]; a++){
			for(int s = 0; s < tempbox[2]; s++){
				for(int d = 0; d < tempbox[3]; d++){
					lbox[i + a][j + s][k + d] = true;
				}
			}
		}
		boxinfo[B][2]--;
		AddSolution(B, i, j, k, O);
		return false;
	}
	
	public static void AddSolution(int B, int i, int j, int k, int R){
		//System.out.println(B + " "+i+" "+j+" "+k+" "+R);
		double[][] temp = new double[solution.length+1][6];
		for(int a = 0; a< solution.length; a++){for(int s = 1; s < 6; s++){temp[a][s] = solution[a][s];}}
		temp[solution.length][1] = B;
		temp[solution.length][2] = R;
		temp[solution.length][3] = CommonLargestFactor * i;
		temp[solution.length][4] = CommonLargestFactor * j;
		temp[solution.length][5] = CommonLargestFactor * k;
		solution = temp;
		
	}
	
	public static double[] rotate(int B, int R){
		double[] tempbox = new double[4];
		switch(R){
		case 0:tempbox[1] = boxes[B][1];
			tempbox[2] = boxes[B][2];
			tempbox[3] = boxes[B][3];
			break;
		case 1:tempbox[1] = boxes[B][1];
			tempbox[2] = boxes[B][3];
			tempbox[3] = boxes[B][2];
			break;
		case 2:tempbox[1] = boxes[B][2];
			tempbox[2] = boxes[B][1];
			tempbox[3] = boxes[B][3];
			break;
		case 3:tempbox[1] = boxes[B][2];
			tempbox[2] = boxes[B][3];
			tempbox[3] = boxes[B][1];
			break;
		case 4:tempbox[1] = boxes[B][3];
			tempbox[2] = boxes[B][1];
			tempbox[3] = boxes[B][2];
			break;
		case 5:tempbox[1] = boxes[B][3];
			tempbox[2] = boxes[B][2];
			tempbox[3] = boxes[B][1];
			break;
		}
		for(int i = 1; i < 4; i++){tempbox[i] = tempbox[i] * CommonLargestFactor;}
		return tempbox;
	}

    public static void displayBoxes(){
		Other_Main_Drawer.clearScene();
		double sum = 0;
        //System.out.println(solution.length);

        for(int i = 0; i<solution.length; i++){
        	double[] thisboxhwl = new double[4];
            thisboxhwl = rotate((int)solution[i][1],(int)solution[i][2]);

            double height = thisboxhwl[1];
            double width = thisboxhwl[2];
            double depth = thisboxhwl[3];

            double y = solution[i][3];
            double x = solution[i][4];
            double z = solution[i][5];

            Color boxcolor;
            int boxtype = (int)solution[i][1];
            if(boxtype == 1){
                boxcolor = Other_Main_Drawer.BOX_GREEN;
                sum += value1;
            }
            else if(boxtype == 2){
                boxcolor = Other_Main_Drawer.BOX_RED;
                sum+= value2;
            }
            else if(boxtype == 3){
                boxcolor = Other_Main_Drawer.BOX_BLUE;
                sum+= value3;
            }
            else{
                boxcolor = Other_Main_Drawer.BOX_DARK;
            }

            Other_Main_Drawer.addBox(width,height,depth,x,y,z,boxcolor, true);
        }
		System.out.println(" ");
		System.out.println("Bruteforce.Greedy Algorithm result:");
        System.out.println("Actual value fitted in container: "+sum);
    }
}
	


































































