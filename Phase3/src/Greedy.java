import java.util.Scanner; 
import java.lang.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Greedy{
	private static double[][] boxes;
	private static double[][] boxinfo;
	private static double[] LargeBox;
	private static int nob = 3;//nob = Number Of Boxes (types of boxes)
	private static int CommonLargestFactor;
	private static boolean[][][]lbox;
	private static double[][] solution;
	public static void main(String[] args){
		solution = new double[0][6];
		boxes = new double[nob+1][4];
		LargeBox = new double[4];
		boxinfo = new double[nob+1][3];
		//FILL IN BELLOW:
		int CommonLargestFactor = 1;
		boxes[1][1] = 1 / CommonLargestFactor;// <-- Box 1 height
		boxes[1][2] = 1 / CommonLargestFactor;// <-- Box 1 width
		boxes[1][3] = 1 / CommonLargestFactor;// <-- Box 1 length
		boxes[2][1] = 1 / CommonLargestFactor;// <-- Box 2 height
		boxes[2][2] = 1 / CommonLargestFactor;// <-- Box 2 width
		boxes[2][3] = 1 / CommonLargestFactor;// <-- Box 2 length
		boxes[3][1] = 1 / CommonLargestFactor;// <-- Box 3 height
		boxes[3][2] = 1 / CommonLargestFactor;// <-- Box 3 width
		boxes[3][3] = 1 / CommonLargestFactor;// <-- Box 3 length
		boxinfo[1][1] = 1 / boxes[1][1] / boxes[1][2] / boxes[1][3];// <<-- Score of Box 1
		boxinfo[2][1] = 1 / boxes[2][1] / boxes[2][2] / boxes[2][3];// <<-- Score of Box 2
		boxinfo[3][1] = 1 / boxes[3][1] / boxes[3][2] / boxes[3][3];// <<-- Score of Box 3
		boxinfo[1][2] = 1;// <<-- Max amount of Box 1
		boxinfo[2][2] = 1;// <<-- Max amount of Box 2
		boxinfo[3][2] = 1;// <<-- Max amount of Box 3
		LargeBox[1] = 1 / CommonLargestFactor;// <-- LargeBox height
		LargeBox[2] = 1 / CommonLargestFactor;// <-- LargeBox width
		LargeBox[3] = 1 / CommonLargestFactor;// <-- LargeBox length
		//FILL IN ABOVE
		lbox = new boolean[(int)LargeBox[1]][(int)LargeBox[2]][(int)LargeBox[3]];
		if(boxinfo[1][1] < boxinfo[1][2]){swap(1,2);}
		if(boxinfo[1][2] < boxinfo[1][2]){swap(2,3);}
		if(boxinfo[1][1] < boxinfo[1][2]){swap(1,2);}
		FillInBox();
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
		for( int u = 1; u < 3; u++){// <<-- Box number
			for( int i = 0; i < LargeBox[1]; i++){//			}
				for( int j = 0; j < LargeBox[2]; j++){//		}}selects all cubes in the large box
					for( int k = 0; k < LargeBox[3]; k++){//	}
						//for every cube in the large box
						// tries every orientations and places the box in the first available one
						if((fitcheck(u,0,i,j,k)) && (fitcheck(u,1,i,j,k)) && (fitcheck(u,2,i,j,k)) && (fitcheck(u,3,i,j,k)) && (fitcheck(u,4,i,j,k)) && (fitcheck(u,5,i,j,k))){}
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
		AddSolution(B, i, j, k, O);
		return false;
	}
	
	public static void AddSolution(int B, int i, int j, int k, int R){
		double[][] temp = new double[solution.length+1][6];
		for(int a = 0; i< solution.length; i++){for(int s = 1; s < 7; s++){temp[a][s] = solution[a][s];}}
		temp[solution.length][1] = B;
		temp[solution.length][2] = R;
		temp[solution.length][3] = i;
		temp[solution.length][4] = j;
		temp[solution.length][5] = k;
		solution = temp;
	}
}
	


































































