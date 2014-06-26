package com.example.sudoku;

import java.util.Scanner;

public class SudokuGame
{
public static int[][] getNum(int type){
		
	SudokuBoard p = new SudokuBoard();

	
		System.out.println("How do you like to enter the nos?\n1) Row-wise\n2) Col-wise");
		Scanner input = new Scanner(System.in);
		switch(input.nextInt()){
		case 1:
			//row-wise
			String[][] a =new String[type][type];
			int[][] b =new int[type][type];
			Scanner input1 = new Scanner(System.in);
			for(int i=0;i<type;i++)
			{	
					System.out.printf("Please enter the values of row %d\n", i+1);
					a[i]=input1.nextLine().split(",");
					for(int j=0;j<type;j++)
					{
						if (Integer.parseInt(a[i][j]) >0 & Integer.parseInt(a[i][j]) <type+1)
							b[i][j]=Integer.parseInt(a[i][j]);
						else
							
						{
							System.out.println("Please enter only valid nos");
							i--;
							break;
						}
					}
					p.print(b);
			}
			return b;
		case 2:
			//col-wise
			String[][] a1 =new String[type][type];
			int[][] b1 =new int[type][type];
			Scanner input2 = new Scanner(System.in);
			for(int i=0;i<type;i++)
			{	
					System.out.printf("Please enter the values of column %d\n", i+1);
					a1[i]=input2.nextLine().split(",");
					for(int j=0;j<type;j++)
					{
						if (Integer.parseInt(a1[i][j]) >0 & Integer.parseInt(a1[i][j]) <type+1)
							b1[j][i]=Integer.parseInt(a1[i][j]);
						else		
						{
							System.out.println("Please enter only valid nos");
							i--;
							break;
						}
					}
					p.print(b1);
			}
			return b1;			
		default:
			break;
		}
		return null;
	}
}