
package com.example.sudoku;

import java.util.HashSet;
import java.util.Set;

public class SudokuValidation{
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
}