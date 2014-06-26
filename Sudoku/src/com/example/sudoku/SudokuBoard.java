package com.example.sudoku;
public class SudokuBoard
{
	
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
}