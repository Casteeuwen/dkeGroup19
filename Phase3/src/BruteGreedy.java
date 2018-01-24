import javafx.scene.paint.Color;

import java.util.Scanner;
import java.lang.*;
import javax.swing.*;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class BruteGreedy{
	private static double bestscore;
	private static int Maxtries;
	private static double[][] bestsolution;
	private static double[][] boxes;
	private static double[][] boxinfo;
	private static double[] LargeBox;
	private static int nob = 3;//nob = Number Of Boxes (types of boxes)
	private static double CommonLargestFactor;
	private static boolean[][][]lbox;
	private static double[][] solution;
	public static void startAlgo(){
		System.out.println("brute called");
		Maxtries = 10000;
		bestsolution = new double[0][6];
		solution = new double[0][6];
		boxes = new double[nob+1][4];
		LargeBox = new double[4];
		boxinfo = new double[nob+1][3];
		bestscore = 0;
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
		boxinfo[1][1] = 1 / boxes[1][1] / boxes[1][2] / boxes[1][3];// <<-- Score of Box 1
		boxinfo[2][1] = 1 / boxes[2][1] / boxes[2][2] / boxes[2][3];// <<-- Score of Box 2
		boxinfo[3][1] = 1 / boxes[3][1] / boxes[3][2] / boxes[3][3];// <<-- Score of Box 3
		boxinfo[1][2] = 10;// <<-- Max amount of Box 1
		boxinfo[2][2] = 10;// <<-- Max amount of Box 2
		boxinfo[3][2] = 10;// <<-- Max amount of Box 3
		LargeBox[1] = 4 / CommonLargestFactor;// <-- LargeBox height
		LargeBox[2] = 2.5 / CommonLargestFactor;// <-- LargeBox width
		LargeBox[3] = 16.5 / CommonLargestFactor;// <-- LargeBox length
		//FILL IN ABOVE
		lbox = new boolean[(int)LargeBox[1]][(int)LargeBox[2]][(int)LargeBox[3]];
		if(boxinfo[1][1] < boxinfo[1][2]){swap(1,2);}
		if(boxinfo[1][2] < boxinfo[1][2]){swap(2,3);}
		if(boxinfo[1][1] < boxinfo[1][2]){swap(1,2);}
		FillInBox((int)boxinfo[1][2], (int)boxinfo[2][2], (int)boxinfo[3][2], 0, 0, 0, 0);
		System.out.println("solution array: ");
		for(int i = 0; i<solution.length; i++){
			System.out.println();
			for(int j = 0; j<6; j++){
				System.out.print(solution[i][j]+"  ");
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
	
	
	private static void FillInBox(int B1, int B2, int B3, int i, int j, int k, int score){
		
		
		for( int u = 1; u < 4; u++){
			boolean canrun = false;
			if((u  == 1) && (B1 > 0)){canrun = true;}
			if((u  == 2) && (B2 > 0)){canrun = true;}
			if((u  == 3) && (B3 > 0)){canrun = true;}
			if(canrun){
				for(int count = 0; count < 6; count++){
					boolean filled = false;
					if(fitcheck(u,count,i,j,k)){}else{
						if(u == 1){B1--; score += boxinfo[1][1];}
						if(u == 2){B2--; score += boxinfo[2][1];}
						if(u == 3){B3--; score += boxinfo[3][1];}
						filled = true;
					}
					if(k < LargeBox[3] - 1){
						if(i < LargeBox[1]){i++;}else{
							i = 0;
							if(j < LargeBox[2]){j++;}else{
								j = 0;
								if(k < LargeBox[3]){k++;}
							}
						}
						if(Maxtries > 0){
							Maxtries--;
							FillInBox(B1, B2, B3, i, j, k, score);
						}
					}else{
					//Winner stage
						if(score > bestscore){
							bestscore = score;
							bestsolution = DupeSolution();
							displayBoxes();
							System.out.println("Found better Solution");
						}
					}
					if(filled){
						RemoveSolution();
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
	private static double[][] DupeSolution(){
		double[][] temp = new double[solution.length][6];
		for(int a = 0; a< solution.length; a++){for(int s = 1; s < 6; s++){temp[a][s] = solution[a][s];}}
		return temp;
	}
	public static void RemoveSolution(){
		double[][] temp = new double[solution.length-1][6];
		for(int a = 0; a< solution.length - 1; a++){for(int s = 1; s < 6; s++){temp[a][s] = solution[a][s];}}
		solution = temp;
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
        System.out.println("displayboxes called");
		Other_Main_Drawer.clearScene();
		System.out.println(bestsolution.length);
        for(int i = 0; i<bestsolution.length; i++){
        	double[] thisboxhwl = new double[4];
            thisboxhwl = rotate((int)bestsolution[i][1],(int)bestsolution[i][2]);

            double height = thisboxhwl[1];
            double width = thisboxhwl[2];
            double depth = thisboxhwl[3];

            double y = bestsolution[i][3];
            double x = bestsolution[i][4];
            double z = bestsolution[i][5];

            Color boxcolor;
            int boxtype = (int)bestsolution[i][1];
            if(boxtype == 1){
                boxcolor = Other_Main_Drawer.BOX_BLUE;
            }
            else if(boxtype == 2){
                boxcolor = Other_Main_Drawer.BOX_GREEN;
            }
            else if(boxtype == 3){
                boxcolor = Other_Main_Drawer.BOX_RED;
            }
            else{
                boxcolor = Other_Main_Drawer.BOX_DARK;
            }

            Other_Main_Drawer.addBox(width,height,depth,x,y,z,boxcolor);
        }
    }
}
	


































































