package application;



import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Sudoko {
	
	
	
	boolean solution(int[][] arr,GridPane grid) {
		Pairs p = new Pairs();
		
		if (!isFull(arr,p))
			return true;
		
			
		for (int i = 1 ; i <= 9 ; i++) {
			if (check(arr, p.x, p.y, i)) {
				arr[p.x][p.y] = i;
			
				Platform.runLater(() ->
	            {
	            		buildGrid(grid, arr);
	            });
	            try
	            {
	                Thread.sleep(500);
	            } catch (InterruptedException ex)
	            {
	                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	            }

			
			if (solution(arr,grid))
				return true;
			
			arr[p.x][p.y] = 0;
			
			}
		}
		return false;
			
	}
	
	

	
	
	boolean isFull(int[][] arr, Pairs p) {
		
		for (p.y = 0 ; p.y < 9 ; p.y = p.y + 1) {
			for (p.x = 0 ; p.x < 9 ; p.x = p.x + 1) {
				if (arr[p.x][p.y] == 0)
					return true;
			}
		}
		return false;
	}
	
	boolean check(int[][] arr,int row,int column, int val) {
		return isColumnSafe(arr, column, val) 
				&& isRowSafe(arr, row, val)
				&& isBoxSafe(arr, row - row%3, column - column%3, val);
				
	}
	
	
	boolean isColumnSafe(int[][] arr,int column,int val) {
		for (int i = 0 ; i < 9 ; i++)
			if (arr[i][column] == val)
				return false;
		return true;
	}
	
	boolean isRowSafe(int[][] arr,int row,int val) {
		for (int i = 0 ; i < 9 ; i++)
			if (arr[row][i] == val)
				return false;
		return true;
	}
	
	boolean isBoxSafe(int[][] arr,int si,int sj,int val) {
		for (int i = 0 ; i < 3 ; i++)
			for (int j = 0 ; j < 3 ; j++)
				if (arr[i + si][j + sj] == val)
					return false;
		return true;
	}
	
	
public void buildGrid(GridPane grid , int[][] vals) {
		int i = 0 , j = 0,is = 0 , js = 0;
		
		for (int blockColumn = 0; blockColumn < 3 ; blockColumn++) {
		    for (int blockRow = 0; blockRow < 3; blockRow++) {

		        GridPane box = new GridPane();
		        box.setStyle("-fx-background-color: rgb(245,202,169), -fx-control-inner-background; -fx-background-insets: 0, 2; -fx-padding: 2;");
		        for (int column = 0; column < 3; column++) {
		            for (int row = 0 ; row < 3; row++) {
		            	TextField textField;
		            	if (vals[j+js][i+is] != 0) {
		                 textField = new TextField (vals[j+js][i+is] + "");
		                 textField.setStyle("-fx-control-inner-background:rgb(255,255,255);-fx-background-color:rgb(142,158,171);\n" + 
			            			"	-fx-border-color:rgb(0,0,0)");
		            	}
		            	else {
		            		textField = new TextField ();
		            		textField.setStyle("-fx-control-inner-background:rgb(255,255,255);-fx-background-color:rgb(255,255,255);\n" + 
			            			"	-fx-border-color: rgb(0,0,0)");
		            	}
		            	
		                textField.setAlignment(Pos.CENTER);
			            	
			            	textField.setEditable(false);
			            	textField.setMouseTransparent(true);
			            	textField.setFocusTraversable(false);
		                textField.setAlignment(Pos.CENTER);
		                textField.setMaxWidth(40);
		                textField.setMinWidth(40);
		                textField.setMaxHeight(40);
		                textField.setMinHeight(40);		                
		                GridPane.setConstraints(textField, column, row);
		                box.getChildren().add(textField);
		                i++;
		            }
		            i = 0;
		            j++;
		            
		        }
		        j = 0;
		        is += 3;
		        
		        GridPane.setConstraints(box, blockColumn, blockRow);
		        grid.getChildren().add(box);

		    }
		    is = 0;
		    js += 3;
		}
	}

}
