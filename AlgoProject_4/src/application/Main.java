package application;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.security.auth.DestroyFailedException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	BorderPane root;
	int[][] array ;
	int[][] arrayCopy;
	boolean isStart = false;
	boolean withhelp = false;
	boolean finalCheck = false;
	boolean is_manual = false;
	boolean done;
	Field[][] fieldsArray = new Field[9][9];
	int errorRow = -1;
	int errorColumn = -1;
	@Override
	public void start(Stage primaryStage) {
		try {
			root = new BorderPane();
			
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setGridLinesVisible(true);
			
			array = new int[9][9];
			
			buildGrid(grid,array);
			
			root.setCenter(grid);
			
			HBox bottomBox = new HBox(40);
			bottomBox.setAlignment(Pos.CENTER);
			
			VBox levelBox = new VBox(20);
			
			Button easy = new Button("Easy Game");
			easy.setMinWidth(120);
			Button hard = new Button("Hard Game");
			hard.setMinWidth(120);
			Button manual = new Button("Manual");
			manual.setMinWidth(120);
			
			levelBox.getChildren().addAll(easy,hard,manual);
			
			
			Button start = new Button("Start");
			HBox bottBox = new HBox();
			start.setMinWidth(200);
			bottBox.setAlignment(Pos.CENTER);
			bottBox.getChildren().add(start);
			
			
			
			VBox solutionBox = new VBox(20);
			
			Button checkSolution = new Button("Check Solution");
			checkSolution.setMinWidth(120);
			CheckBox help = new CheckBox("With Help");
			Button getSolution = new Button("Get Solution");
			getSolution.setMinWidth(120);
			
			
			
			solutionBox.getChildren().addAll(checkSolution,help,getSolution);
			solutionBox.setAlignment(Pos.CENTER);
			
			levelBox.setAlignment(Pos.CENTER);
		root.setLeft(levelBox);
		root.setRight(solutionBox);
		root.setBottom(bottBox);
			
			
			easy.setOnAction(e -> {
				array = chooseLevel("easy");
				buildGrid(grid, array);
			});
			
			hard.setOnAction(e -> {
				array = chooseLevel("hars");
				buildGrid(grid, array);
			});
			
			manual.setOnAction(e -> {
				is_manual = true;
				for (int i = 0 ; i < 9 ; i++) {
					for (int j = 0 ; j < 9 ; j++) {
						fieldsArray[i][j].setEditable(true);
					}
				}
			});
			 done = false;
			getSolution.setOnAction(e -> {
				Sudoko sol = new Sudoko();
				 new Thread(() ->
	                {
	                	 
	               done = sol.solution(arrayCopy, grid) ;
	               array = arrayCopy;
	                }).start();

				 
			});
			
			start.setOnAction(e -> {
				if (help.isSelected())
					withhelp = true;
				isStart = true;
				setStart();
			});
			
			checkSolution.setOnAction(e -> {
				if (isRight()) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("solved");
					alert.setHeaderText("NICE !!!");
					alert.setContentText(" you solved it ! ");

					alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("didn't solved");
					alert.setContentText(" Sorry , But you didn't solve it)");

					alert.showAndWait();
				}
			});
			
			
			
			Scene scene = new Scene(root,650,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void buildGrid(GridPane grid , int[][] vals) {
		
		int i = 0 , j = 0,is = 0 , js = 0;
		
		
		
		for (int blockColumn = 0; blockColumn < 3 ; blockColumn++) {
		    for (int blockRow = 0; blockRow < 3; blockRow++) {

		        GridPane box = new GridPane();
		        box.setStyle("-fx-background-color: rgb(245,202,169), -fx-control-inner-background; -fx-background-insets: 0, 2; -fx-padding: 2;");
		        for (int column = 0; column < 3; column++) {
		            for (int row = 0 ; row < 3; row++) {
		            	Field textField;
		            	if (vals[j+js][i+is] != 0)  {
		            		
		                 textField = new Field (j+js,i+is,vals[j+js][i+is] + "");
		             	textField.setStyle("-fx-control-inner-background:rgb(255,255,255);-fx-background-color:rgb(142,158,171);\n" + 
		            			"	-fx-border-color:rgb(0,0,0)");
		            	}
		            	else {
		            		textField = new Field (j+js,i+is);
		            		textField.setStyle("-fx-control-inner-background:rgb(255,255,255);-fx-background-color:rgb(255,255,255);\n" + 
			            			"	-fx-border-color: rgb(0,0,0)");
		            	}
		            	
		            
		            	fieldsArray[j+js][i+is] = textField;
		            	textField.setEditable(false);
		            	textField.setMouseTransparent(true);
		            	textField.setFocusTraversable(false);
	                textField.setAlignment(Pos.CENTER);
	                textField.setMaxWidth(40);
	                textField.setMinWidth(40);
	                textField.setMaxHeight(40);
	                textField.setMinHeight(40);
	             //   textField.setStyle("-fx-pref-width: 4em; -fx-pref-height: 4em;");
	                GridPane.setConstraints(textField, column, row);
	                box.getChildren().add(textField);
	                i++;
		                
		                addEventHndler(textField);
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
	
	public void setStart() {
		for (int i = 0 ; i < 9 ; i++)
			for (int j = 0 ; j < 9 ; j++) {
				if (fieldsArray[i][j].getText().equals("")) {
					fieldsArray[i][j].setEditable(true);
					fieldsArray[i][j].setMouseTransparent(false);
					fieldsArray[i][j].setFocusTraversable(true);
				}
			}
	}
	

	
	public void addEventHndler(Field node) {
		
	
		
		node.textProperty().addListener(new ChangeListener<String>() {
			
			
			
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		    	if (isStart && withhelp)
		    		check(node.row, node.column, newValue);
		    	
		    	if (is_manual) {
		    		if (!newValue.equals("") &&Character.isDigit(newValue.charAt(0)))
		    			readd();
		    		check(node.row, node.column, newValue);
		    	}
		    	
		        if (!newValue.matches("\\d*")) {
		            node.setText(newValue.replaceAll("[^\\d]", ""));
		            
		        } else {
		        		System.out.println("Text changed from "+oldValue+" to "+newValue);
		        }
		    }
		});
		}
	
	public void readd() {
	
			for (int i = 0 ; i < 9 ; i++) {
				for (int j = 0 ; j < 9 ; j++) {
					if (fieldsArray[i][j].getText().equals("")) {
						array[i][j] = 0;
						
					} else {
						array[i][j] = Integer.parseInt(fieldsArray[i][j].getText());
					}
				}
			}
		
	}
	
	
	public boolean check(int row,int column,String value) {
		System.out.println(row + "  >> " + column);
		boolean r = checkRow(row, column, value);
		boolean c = checkColumn(row, column, value);
		
		boolean b = checkBox(row - row%3, column - column%3, value,row,column);
		
		if (!r ) {
			System.out.println("asdf");
			String style = fieldsArray[row][column].getStyle();
			String[] attr = style.split(";");
			String[] arr = attr[1].split(":");
			
			String color = "";
			if (!arr[1].equals("rgb(142,158,171)"))
				color = increaseRed(arr[1]);
			else 
				color = "rgb(142,158,171)";
			System.out.println(color);
			fieldsArray[row][column].setStyle(attr[0] +";" + arr[0] + ":" + color + ";" + attr[2]);
		}
		return c && r && b;
	}
	
	public String increaseRed(String color) {
		String[] arr = color.split("[()]");
		
		arr = arr[1].split(",");
		int g = Integer.parseInt(arr[1]);
		if (g > 80)
			g -= 80;
		int b = Integer.parseInt(arr[2]);
		if (b > 80)
			b -= 80;
		String c = "rgb(" + arr[0] + "," + g + "," + b + ")";
		return c;
		
	}
	
	public String decreaseRed(String color) {
		String[] arr = color.split("[()]");
		
		arr = arr[1].split(",");
		int g = Integer.parseInt(arr[1]);
		if (g <= (255-80))
			g += 80;
		int b = Integer.parseInt(arr[2]);
		if (b <= (255-80))
			b += 80;
		String c = "rgb(" + arr[0] + "," + g + "," + b + ")";
		return c;
	}
	
	
	public boolean checkColumn(int row,int column,String value) {
		boolean error = false;
		if (value.equals("") || Character.isDigit(value.charAt(0))) {
			for (int i = 0 ; i < 9 ; i++) {
				if (i != row)
					if (!value.equals("") && fieldsArray[i][column].getText().equals(value)) {
						error = true;
						finalCheck = true;
						errorColumn = column;
						colorColumn(column,true);
						return false;
					}
			}
			
			if (!error) {
				errorColumn = -1;
				colorColumn(column, false);
				
			}
			
		}
		return true;
	}
	
	void colorColumn(int column , boolean inc) {
		for (int i = 0 ; i < 9 ; i++) {
			
			String style = fieldsArray[i][column].getStyle();
			String[] attr = style.split(";");
			System.out.println(style);
			String[] arr = attr[1].split(":");
			
			String color = "";
			if (!arr[1].equals("rgb(142,158,171)") && !arr[1].equals("rgb(142,158,240)")) {
				if (inc) {
					color = increaseRed(arr[1]);
				}else {
					color = decreaseRed(arr[1]);		
				}
				
			}
			else {
				if (inc)
					color = "rgb(142,158,240)";
				else 
					color = "rgb(142,158,171)";
				
			}
			
			
			fieldsArray[i][column].setStyle(attr[0] +";" + arr[0] + ":" + color + ";" + attr[2]);
			
		}
	}
	


	
	public boolean checkRow(int row,int column,String value) {
		boolean error = false;
		if (value.equals("") ||Character.isDigit(value.charAt(0))) {
			for (int i = 0 ; i < 9 ; i++) {
				if (i != column)
					if (!value.equals("") && fieldsArray[row][i].getText().equals(value)) {
						System.out.println(row + " >>>   " + i + "  >>" +fieldsArray[row][i].getText() +  "  >." + value);
						error = true;
						finalCheck = true;
						errorRow = row;
						colorRow(row,true);
						return false;
					}
			}
			if (!error) {
				errorRow = -1;
				colorRow(row, false);
			}
			
		}
		return true;
	}
	
	void colorRow(int row , boolean inc) {
		for (int i = 0 ; i < 9 ; i++) {
			String style = fieldsArray[row][i].getStyle();
			String[] attr = style.split(";");
			String[] arr = attr[1].split(":");
			
			String color = "";
			if (!arr[1].equals("rgb(142,158,171)") && !arr[1].equals("rgb(142,158,240)")) {
				if (inc) {
					color = increaseRed(arr[1]);
				}else {
					color = decreaseRed(arr[1]);		
				}
				
			}
			else {
				if (inc)
					color = "rgb(142,158,240)";
				else 
					color = "rgb(142,158,171)";
				
			}
			fieldsArray[row][i].setStyle(attr[0] +";" + arr[0] + ":" + color + ";" + attr[2]);
		}
			
	}
	
	boolean checkBox(int row,int column,String val,int r,int c) {
		boolean error = false;
		if (val.equals("") ||Character.isDigit(val.charAt(0)))  {
			for (int i = 0 ; i < 3 ; i++)
				for (int j = 0 ; j < 3 ; j++) {
					if (!((i+row == r) && (j+column == c)))
						if (!val.equals("") && fieldsArray[i+row][j+column].getText().equals(val)) {
							
							error = true;
							finalCheck = true;
							
							colorBox( row, column,true);
							return false;
						}
				}
		}
		
		if (!error) {
			
			colorBox(row, column, false);
		}
		return true;
	}
	
	void colorBox(int row, int column , boolean inc) {
		for (int i = 0 ; i < 3 ; i++)
			for (int j = 0 ; j < 3 ; j++) {
				String style = fieldsArray[i + row][j+column].getStyle();
				String[] attr = style.split(";");
				String[] arr = attr[1].split(":");
				
				String color = "";
				if (!arr[1].equals("rgb(142,158,171)") && !arr[1].equals("rgb(142,158,240)")) {
					if (inc) {
						color = increaseRed(arr[1]);
					}else {
						color = decreaseRed(arr[1]);		
					}
					
				}
				else {
					if (inc)
						color = "rgb(142,158,240)";
					else 
						color = "rgb(142,158,171)";
					
				}
				//fieldsArray[i + row][j+column].setStyle("-fx-control-inner-background:" + color + ";");
				
				if ((errorRow == -1 && errorColumn == -1) || inc)
					fieldsArray[i + row][j+column].setStyle(attr[0] +";" + arr[0] + ":" + color + ";" + attr[2]);
				else {
					if (i+row == errorRow || j+column == errorColumn ) {
						System.out.println("lllllll");
					} else {
						fieldsArray[i + row][j + column].setStyle(attr[0] +";" + arr[0] + ":" + color + ";" + attr[2]);
					}
				}
				
			}
	}
	
	
	public boolean isRight() {
		for (int i = 0 ; i < 9 ; i++) 
			for (int j = 0 ; j < 9 ; j++) {
				if (array[i][j] == 0)
					return false;
			}
		for (int i = 0 ; i < 9 ; i++)  {
			for (int j = 0 ;j < 9 ; j++) {
				boolean is = check(i,j,array[i][j]+"");
				if (!is) 
					return false;
				
			}
		}
		
		return true;
	}
	
	
	
	public int[][] chooseLevel(String level) {
		int[][] arr = new int[9][9];
		arrayCopy = new int[9][9];
		
		File in ;
		
		if (level.equals("easy")) {
			int num = (int) Math.round((Math.random() * 2) + 1);
			in = new File("src//easy//easy" + num + ".txt");
		} else {
			int num = (int) Math.round((Math.random() * 1) + 1);
			in = new File("src//hard//hard" + num + ".txt");
		}
		
		try {
			Scanner scan = new Scanner(in);
			for (int i = 0 ; i < 9 ; i++)
				for (int j = 0 ; j < 9 ; j++) {
					arr[i][j] = scan.nextInt();
					arrayCopy[i][j] = arr[i][j];
				}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
