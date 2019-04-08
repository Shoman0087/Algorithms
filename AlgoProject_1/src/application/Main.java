package application;
	






import java.util.Stack;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class Main extends Application {
	
	static int [][] dpTable; // table that gives me the minimum number of distance
	static char [][] tracking; // table that gives me the tracking of my steps
 	static String str1; // storing the first string
	static String str2; // storing the second string
	
	@Override
	public void start(Stage primaryStage) {

		try {
			BorderPane root = new BorderPane();
			
			root.setId("root");
			
			// welcome text
		//	Text welcome = new Text("Weolcome to My Programe");
		//	welcome.setTextAlignment(TextAlignment.CENTER);
			
			
			
			HBox topBox = new HBox();
			topBox.setId("topBox");
			topBox.setAlignment(Pos.CENTER);
			topBox.setMinHeight(50);
		//	topBox.getChildren().add(welcome);
			root.setTop(topBox);
			
			
			Label enterString1 = new Label("From : ");
			enterString1.setAlignment(Pos.CENTER_LEFT);
	
			TextField string1 = new TextField();	
			
			
			Label enterString2 = new Label("To : ");
			enterString2.setAlignment(Pos.CENTER_LEFT);
			
			TextField string2 = new TextField();
			Button click = new Button("   Convert   ");
			
			
			
			
			GridPane grid = new GridPane();
			grid.setId("center");
			grid.setAlignment(Pos.TOP_CENTER);
			grid.setPadding(new Insets(30,20,0,20));
			grid.setVgap(20);
			grid.setHgap(10);
			
			grid.add(enterString1, 0, 0);
			grid.add(enterString2, 0, 1);
			
			grid.add(string1, 1, 0);
			grid.add(string2, 1, 1);
			grid.add(click, 1, 2);
			
			root.setCenter(grid);

			
			click.setOnAction(e -> {
				Stage stage2 = new Stage();
				
				BorderPane pane1 = new BorderPane();
				GridPane pane2 = new GridPane();
				pane2.setId("grid");
				pane2.setAlignment(Pos.CENTER);
				str1 = string1.getText(); // get the first string from the text field
				str2 = string2.getText(); // get the second string from the text field
				solve(str1,str2); // the method that find the minimum number of steps to convert the first string to the second string
				
				for (int s = 0 ; s < str1.length()+1 ; s++) { 
					for (int g = 0 ; g < str2.length()+1 ; g++) 
						System.out.print(dpTable[s][g] + " ");
					System.out.println();
				}
				
				for (int s = 0 ; s < str1.length()+1 ; s++) {
					for (int g = 0 ; g < str2.length()+1 ; g++) 
						System.out.print(tracking[s][g] + " ");
					System.out.println();
				}
				
				System.out.println("doneeee");
				 
				ImageView image = new ImageView(new Image("arrow.png"));
				
				
				int j = str2.length();
				int i = str1.length();
				Stack<Text> stack = new Stack<>();
				
				int delete = 0;
				int insert = 0;
				int subtitute = 0;
				
				while (i != 0 && j != 0) {
					System.out.println(i + " " + j) ;
					//System.out.println("test");
					Text txt = new Text();
					txt.setId("tt");

						 if (tracking[i][j] == 'c') {
							txt.setText(str1.charAt(i-1)+"");
							i--;
							j--;
						} else if (tracking[i][j] == 'i') {
							insert++;
							txt.setText(str2.charAt(j-1) + "");
							txt.setFill(Color.BLUE);
							j--;
						} else if (tracking[i][j] == 'd') {
							delete++;
							txt.setText( str1.charAt(i-1) + "");
							txt.setFill(Color.RED);
							i--;
						} else {
							subtitute++;
							txt.setText(str2.charAt(j-1) + "");
							txt.setFill(Color.BLUEVIOLET);
							i--;
							j--;
						}
					
					txt.setFont(Font.font ("Verdana", 30));
					stack.push(txt);
					
					if ((j == 0 && i > 1)) {
						break;
						
					} else if (i == 0 && j > 1) {
						
						break;
					}
				}
				j++;
				i++;
				
			if (j == 1 && i != 1) {
				while (i > 1) {
					i--;
					System.out.println("i test");
					Text txt = new Text();
					txt.setId("tt");
					
					delete++;
					txt.setText(str1.charAt(i-1) + "");
					txt.setFill(Color.RED);
					txt.setFont(Font.font ("Verdana", 30));
					stack.push(txt);
				
				}
			} 
				if (j != 1 && i == 1) {
				while (j > 1) {
					j--;
					System.out.println("j test");
					Text txt = new Text();
					System.out.println(i + " " + j);
					txt.setId("tt");
					insert++;
					txt.setText(str2.charAt(j-1) + "");
					txt.setFill(Color.BLUE);
					txt.setFont(Font.font ("Verdana", 30));
					stack.push(txt);
					
				}
				 
			} 
				
	
				
				Scene scene2 = new Scene(pane1,500,500);
				scene2.getStylesheets().add(getClass().getResource("des.css").toExternalForm());
				stage2.setScene(scene2);
				stage2.show();
				
				VBox vbox = new VBox();
				vbox.setAlignment(Pos.CENTER);
				HBox box3 = new HBox();
				box3.setAlignment(Pos.CENTER);
				box3.getChildren().add(image);
				
				
				HBox box2 = new HBox(5);
				box2.setAlignment(Pos.CENTER);
				for (int k = 1 ; k <= str1.length() ; k++) {
					Text t = new Text(str1.charAt(k-1) + "");
					t.setId("tt");
					t.setFont(Font.font ("Verdana", 30));
					box2.getChildren().add(t);
				}
				
				vbox.getChildren().addAll(box2,box3);
				

				int size = stack.size();
				
				HBox box4 = new HBox(5);
				box4.setAlignment(Pos.CENTER);
				int c = 1;
				while (!stack.isEmpty()) {					
					Text t = stack.pop();
					t.setId("tt");
					box4.getChildren().add(t);
					c++;
				}
			
				
				vbox.getChildren().add(box4);
				
				ImageView image2 = new ImageView(new Image("arrow.png"));
				HBox box5 = new HBox();
				box5.setAlignment(Pos.CENTER);
				box5.getChildren().add(image2);
				vbox.getChildren().add(box5);
				
				HBox box6 = new HBox(5);
				box6.setAlignment(Pos.CENTER);
				for (int k = 1 ; k <= str2.length() ; k++) {
					Text t = new Text(str2.charAt(k-1) + "");
					t.setId("tt");
					t.setFont(Font.font ("Verdana", 30));

					box6.getChildren().add(t);
				}
				vbox.getChildren().add(box6);
				
				pane2.getChildren().add(vbox);
				pane1.setCenter(pane2);
				
				
				VBox leftBox = new VBox(30);
				leftBox.setAlignment(Pos.CENTER);
				
				Rectangle r1 = new Rectangle(20,20);
				Label l1 = new Label("Delete = " + delete);
				l1.setFont(Font.font ("Verdana", 20));
				HBox r1Box = new HBox(10);
				r1Box.setPadding(new Insets(0,0,0,20));
				r1Box.getChildren().addAll(r1,l1);
				r1.setFill(Color.RED);
				
				
				Rectangle r2 = new Rectangle(20,20);
				Label l2 = new Label("Insert = " + insert);
				l2.setFont(Font.font ("Verdana", 20));
				HBox r2Box = new HBox(10);
				r2Box.setPadding(new Insets(0,0,0,20));
				r2Box.getChildren().addAll(r2,l2);
				r2.setFill(Color.BLUE);
				
				
				Rectangle r3 = new Rectangle(20,20);
				Label l3 = new Label("Subtitute = " + subtitute);
				l3.setFont(Font.font ("Verdana", 20));
				HBox r3Box = new HBox(10);
				r3Box.setPadding(new Insets(0,0,0,20));
				r3Box.getChildren().addAll(r3,l3);
				r3.setFill(Color.BLUEVIOLET);
				
				leftBox.getChildren().addAll(r1Box,r2Box,r3Box);
				pane1.setLeft(leftBox);
				
				
				
				
			});
			
			
			Scene scene = new Scene(root,450,250);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		 
		
		
	}
	
	
	static int getMin(int x1,int x2 , int x3) {
		if (x1 < x2 && x1 < x3)
			return x1;
		else if (x2 < x1 && x2 < x3)
			return x2;
		else 
			return x3;
			
			
	}
	
	
	
	static void solve(String str1 , String str2) {
		
		
		 dpTable = new int[str1.length()+1][str2.length()+1]; // initialize the table with the length of the string + 1
		 tracking = new char[str1.length()+1][str2.length()+1]; // // /// // // ......
		
		
		char[] string1 = str1.toCharArray(); // convert the string to array to simplify my work
		char[] string2 = str2.toCharArray(); // // // /// .....
		
		
			
			for (int i = 0 ; i < str1.length()+1 ; i++)  
				dpTable[i][0] = i; // initialize the first column with numbers from 0 to length of string 1 
			
			
			for (int i = 0 ; i < str2.length()+1 ; i++) 
				dpTable[0][i] = i; // initialize the first row with numbers from 0 to length of string 2 
			
			for (int i = 0 ; i < str1.length()+1 ; i++) 
			tracking[i][0] = 'd'; // fill the first column of tracking table with d (delete) for the case that the second string is empty and the first is not
			
			
			for (int i = 0 ; i < str2.length()+1 ; i++) 
				tracking[0][i] = 'i'; // // fill the first row of tracking table with i (insert) for the case that the first string is empty and the other string is not
			

				for (int i = 1 ; i < str1.length()+1 ; i++) { // loop that goes through rows
					for (int j = 1 ; j < str2.length()+1 ; j++) { // loop that goes through columns in each row
						if (string1[i-1] == string2[j-1]) { // if the tow characters are equals then the value of the current step equals to the value of the previous step when i-1 and j-1
							dpTable[i][j] = dpTable[i-1][j-1];
							tracking[i][j] = 'c'; // in this case the operation is copy which is coast zero
							
						} else { // if the two characters are not equals then there will be other steps
		
							/*
							 * 
							 * if the two characters are not equals then 
							 * we find the minimum of left, top and diagonal and do the following for each case
							 * 1 - if the step i and j-1 (left) is the minimum then the value of the current step is value[i][j-1] + 1 and the operation is (insert)
							 * 		in this case we increase the coast by 1 which is the coast of insertion
							 * 
							 * 2-  if the step i-1 and j (top) is the minimum then the value of the current step is value[i-1][j] + 1 and the operation is (delete)
							 * 		in this case we increase the coast by 1 which is the coast of deletion
							 * 
							 * 3- if the step i-1 and j-1 is (diagonal) the minimum then the value of the current step is value[i-1][j-1] + 1 and the operation is (substitute)
							 * 		in this case we increase the coast by 1 which is the coast of substitution 
							 * 
							 */
							
							
								int x = getMin(dpTable[i-1][j], dpTable[i][j-1], dpTable[i-1][j-1]);
								
								dpTable[i][j] = x + 1;
								if (x == dpTable[i-1][j]) 
									tracking[i][j] = 'd';
								else if (x == dpTable[i][j-1])
									tracking[i][j] = 'i';
								else 
									tracking[i][j] = 's';
							}
						
					}
				}
			
		
	}
	
	
	static void tracking() {
		

		
		int j = tracking[0].length - 1;
		int i = tracking.length - 1;
		StringBuilder str = new StringBuilder();
		
		
		while (i > 0 || j > 0) {
			if (tracking[i][j] == 'c') {
				str.insert(0, str1.charAt(i-1));
				System.out.println("copy >> " + str1.charAt(i-1) + " >> " + str) ;
				i--;
				j--;
			} else if (tracking[i][j] == 'i') {
				str.insert(0, str2.charAt(j-1));
				System.out.println("insert >> " + str2.charAt(j-1)+ " >> " + str);
				j--;
			} else if (tracking[i][j] == 'd') {
				System.out.println("delete >> " + str1.charAt(i-1)+ " >> " + str);
				i--;
			} else {
				str.insert(0, str2.charAt(j-1));
				System.out.println("subtitute >> " + str1.charAt(i-1) + " with >> " + str2.charAt(j-1)+ " >> " + str);
				i--;
				j--;
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
