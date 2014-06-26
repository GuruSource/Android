package com.example.sudoku;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.lang.String;


public class Suduku
{
	public static void main (String[] args)
	{
		Scanner input = new Scanner(System.in);
		System.out.println("******Welcome to Suduku checker******");
	while(true)
	{
		System.out.println("Please choose one \n1) 4x4 \n2) 9x9\n3) Exit ");
		int menu_input = input.nextInt();
		
		switch (menu_input){
			case 1:
				//4x4
				if(checkDup(getNum(4)))
					System.out.println("---SUDUKU!!---");
				else
					System.out.println("---NO SUDUKU!!---");
				break;
			case 2:
				//9x9
				if(checkDup(getNum(9)))
					System.out.println("---SUDUKU!!---");
				else
					System.out.println("---NO SUDUKU!!---");
				break;				
			case 3:
				//exit
				System.out.println("Bye Bye!");
				System.exit(0);
		}		
	}
	
	}
	public static void print(int s[][]){
		for(int num1[] : s)
		{
			System.out.print(" | ");
			for(int num2: num1)
			{
				System.out.print(num2 +" | ");
						
			}
			System.out.println();
		}
			
	}
	public static boolean checkDup(int[][] input) {
        for(int i=0;i<input.length;i++){
			Set Set1 = new HashSet();
	        for (int num : input[i]) {
	            if (!Set1.add(num)) 
	                return false;
	        }
	        
	        Set Set2=new HashSet();
	        for(int j=0;j<input.length;j++)
	        {
	        	if (!Set2.add(input[j][i])) 
	                return false;
	        }
	        }
	        return true;        
    }
	public static int[][] getNum(int type){
		
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
					print(b);
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
					print(b1);
			}
			return b1;			
		default:
			break;
		}
		return null;
	}
	
}

