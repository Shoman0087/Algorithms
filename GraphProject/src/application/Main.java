package application;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.Stack;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Main extends Application {
	

	String name;
	boolean run = false;
	boolean is_directed = true;
	boolean isWeighted = false;
	boolean isGraphClicked = false;
	boolean isFirstClick = false;
	boolean is_edge = false;
	boolean isCopy = false;
	boolean isPaste = false;
	boolean isCut = false;
	boolean isDijecstra = false;
	boolean isMST = false;
	boolean isTxt = false;
	boolean addCost = false;
	Pane pane;
	Pane pane2 = new Pane();
	Button newGraph;
	Button Edge;
	MenuItem paste;
	Graph graph = new Graph();
	Vertics p1,p2;
	int counter = 1;
	Vertics selectedV;
	Vertics onV;
	Vertics start,end;
	double xLoc,yLoc;
	Color col = Color.AZURE;
	Scene scene;
	
	ArrayList<DEdge> dedge = new ArrayList<>();
	ArrayList<UEdge> uedge = new ArrayList<>();
	GraphAlgorithms algo = new GraphAlgorithms();

	
	Vertics selectedVertics;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			setStart();
			

			
			if (run) {
				BorderPane root = new BorderPane();
				
				 pane = new Pane();
				 pane.setId("Center");
				root.setCenter(pane);
				
				 newGraph = new Button("Vertics");
				 newGraph.setGraphic(new ImageView(new Image("icons//graph.png")));
				
				 newGraph.setMinWidth(150);
				 newGraph.setMinHeight(70);
				 
				 Edge = new Button("Edge");
				 Edge.setGraphic(new ImageView(new Image("icons//edges.png")));
				 Edge.setMinWidth(150);
				 Edge.setMinHeight(70);
				 
				 Button reset = new Button("Rename");
				 reset.setGraphic(new ImageView(new Image("icons//edit.png")));
				 reset.setMinWidth(150);
				 reset.setMinHeight(70);
				 
				 ColorPicker colorPic = new ColorPicker();
				 
				 
				 colorPic.setValue(Color.AZURE);
				 colorPic.setMinWidth(150);
				 colorPic.setMinHeight(70);
				 
				 colorPic.setOnAction(e -> {
					 col = colorPic.getValue();
				 });
				 
				 Button addW = new Button("Weight");
				 addW.setMinWidth(150);
				 addW.setMinHeight(70);
				 addW.setOnAction(e -> {
					 addCost = true;
				 });
				 
				 
				 Text text = new Text("Color");
	
				 
				 
				 				 
				 colorPic.setOnAction(e -> {
					 text.setFill(colorPic.getValue());
					 col = colorPic.getValue();
				 });
				
				 
				
				VBox leftBox = new VBox(20);
				leftBox.setId("Left");
				leftBox.setMinWidth(150);
				leftBox.setAlignment(Pos.CENTER);
				
				
				leftBox.getChildren().addAll(newGraph,Edge,reset,addW,colorPic);
				
				root.setLeft(leftBox);
				
			
				
				
				MenuBar topBar = new MenuBar();
				topBar.setId("TOP");
				
				Menu file = new Menu("File");
				
				MenuItem newProject = new MenuItem("New");
				MenuItem open = new MenuItem("Open");
				MenuItem save = new MenuItem("Save");
				MenuItem clear = new MenuItem("Clear");
				MenuItem close = new MenuItem("Close");
				
				newProject.setGraphic(new ImageView(new Image("icons//new.png")));
				open.setGraphic(new ImageView(new Image("icons//open.png")));
				save.setGraphic(new ImageView(new Image("icons//save.png")));
				clear.setGraphic(new ImageView(new Image("icons//clear.png")));
				close.setGraphic(new ImageView(new Image("icons//close.png")));
				
				file.getItems().addAll(newProject,open,save,clear,close);
				
				Menu edit = new Menu("Edit");
				MenuItem copy = new MenuItem("Copy");
				 paste = new MenuItem("Paste");
				 paste.setDisable(true);
				MenuItem cut = new MenuItem("Cut");
//				
				edit.getItems().addAll(copy,paste,cut);
//				
				copy.setGraphic(new ImageView(new Image("icons//copy.png")));
				cut.setGraphic(new ImageView(new Image("icons//cut.png")));
				paste.setGraphic(new ImageView(new Image("icons//paste.png")));
			
				
				Menu run = new Menu("Run");
				MenuItem topSort = new MenuItem("Topological Sort");
				MenuItem shtPath = new MenuItem("Shortest Path");
				MenuItem mst = new MenuItem("MST");
				
				if(!is_directed) {
					topSort.setDisable(true);
					
				} else {
					mst.setDisable(true);
				}
				
				if (!isWeighted)
					mst.setDisable(true);
				
				run.getItems().addAll(topSort,shtPath,mst);
				
				topBar.getMenus().addAll(file,edit,run);
				
				root.setTop(topBar);
				
				topSort.setGraphic(new ImageView(new Image("icons//sort.png")));
				shtPath.setGraphic(new ImageView(new Image("icons//short.png")));
				mst.setGraphic(new ImageView(new Image("icons//mst.png")));
				
				
				newGraph.setOnAction(e -> putVertics());
				Edge.setOnAction(e -> setEdge());
				
				newProject.setOnAction(e -> newProject());
				save.setOnAction(e -> save());
				open.setOnAction(e -> open());
				clear.setOnAction(e -> clear());
				close.setOnAction(e -> {
					primaryStage.close();
				});
				
				reset.setOnAction(e -> {
					isTxt = true;
				});
				
				copy.setOnAction(e -> copy());
				paste.setOnAction(e -> paste());
				cut.setOnAction(e -> cut());
				shtPath.setOnAction(e -> {
					isDijecstra = true;
				});
				
				mst.setOnAction(e -> {
					isMST = true;
					Ed[] arr = algo.primMST(graph);
				//	pane.getChildren().clear();
					//drowVertics(graph);
					try {
						drowPath(graph,arr);
					} catch (CloneNotSupportedException e1) {
						e1.printStackTrace();
					}
					
				});
				
				topSort.setOnAction(e -> {
					Stack<Integer> st;
					try {
						st = algo.topologicalSort(graph);
					
					
					HBox buttomBox = new HBox(8);
					buttomBox.setAlignment(Pos.CENTER);
					while (!st.isEmpty()) {
						Label lab = new Label(graph.graph.get((int)st.pop()).name.getText());
						lab.setFont(Font.font(30));
						lab.setTextFill(Color.RED);
						buttomBox.getChildren().add(0, lab);
					}
					
					buttomBox.setLayoutX(200);
					buttomBox.setLayoutY(600);
					pane.getChildren().add(buttomBox);	
					} catch (Exception e1) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Warining");
						alert.setHeaderText(null);
						alert.setContentText(e1.getMessage());

						alert.showAndWait();
					}
					
				});
				
				pane.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
					if (e.getButton().equals(MouseButton.PRIMARY)) {
						if (isGraphClicked) {
							Circle c = new Circle(e.getX(),e.getY(),15,col);
							
							Vertics v = new Vertics(c,new Label(""),counter-1);	
							graph.addVertics(v);
							addEventHandler(pane,v);
							pane.getChildren().add(v);
							counter++;

							
							//pane.getChildren().add(nn);
							isGraphClicked = false;
							
						}
					} 
					
					xLoc = e.getX();
					yLoc = e.getY();
					if (isCopy || isCut) {
						isPaste = true;
						paste.setDisable(false);
					}
				});
				
				
	
			
			
			
			 scene = new Scene(root,1000,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			}
		} catch(Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warining");
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());

			alert.showAndWait();
		}
	}
	
	void setStart() {
		// Create the custom dialog.
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Login Dialog");
		dialog.setHeaderText("Look, a Custom Login Dialog");


		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();

		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		Label lName = new Label("Project Name");
		TextField projName = new TextField();
		projName.setPromptText("Project Name");
		
		Label edgeType = new Label("Edge Type");
		
		ToggleGroup group1 = new ToggleGroup();

		RadioButton directed = new RadioButton("Directed");
		directed.setUserData("Directed");
		directed.setToggleGroup(group1);
		directed.setSelected(true);

		RadioButton undirected = new RadioButton("Un-Directed");
		undirected.setUserData("Un-Directed");
		undirected.setToggleGroup(group1);
		
		Label weightType = new Label("Weight");
		
		ToggleGroup group2 = new ToggleGroup();

		RadioButton weighted = new RadioButton("Weighted");
		weighted.setUserData("Weighted");
		weighted.setToggleGroup(group2);
		

		RadioButton unweighted = new RadioButton("Un-Weighted");
		unweighted.setUserData("Un-Weighted");
		unweighted.setToggleGroup(group2);
		unweighted.setSelected(true);

		grid.add(lName, 0, 0);
		grid.add(projName, 1, 0);
		grid.add(edgeType, 0, 1);
		grid.add(directed, 1, 1);
		grid.add(undirected, 2, 1);
		grid.add(weightType, 0, 2);
		grid.add(weighted, 1, 2);
		grid.add(unweighted, 2, 2);
		
		

		 //Enable/Disable login button depending on whether a username was entered.
		Node startButton = dialog.getDialogPane().lookupButton(loginButtonType);

//		// Do some validation (using the Java 8 lambda syntax).
		lName.textProperty().addListener((observable, oldValue, newValue) -> {
			startButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> lName.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return lName.getText();
	    }
		    return null;
		});

		Optional<String> result = dialog.showAndWait();
		
		

		
		result.ifPresent(y -> {
			run = true;
			if (projName.getText() != null)
				name = projName.getText();
			else 
				name = "new Project";
			
			if (group1.getSelectedToggle().getUserData().toString().equals("Un-Directed") ) {
				is_directed = false;
			} else {
				is_directed = true;
			}
			
			if (group2.getSelectedToggle().getUserData().toString().equals("Weighted")) {
				isWeighted = true;
			} else {
				isWeighted = false;
			}
		});

		
	}
	
	public void addEventHandler(Pane parent, Node node) {
		
		
		// this event is for drag vertics 
		node.setOnMouseDragged(e -> {
			scene.setCursor(Cursor.CLOSED_HAND);
			double x = e.getX();
			double y = e.getY();
			

			
			if (node instanceof Vertics) {
				System.out.println("draged");
				if (is_directed) {
					for (int i = 0 ; i < dedge.size() ; i++) {
						if (dedge.get(i).getStartX() == ((Vertics) node).x && dedge.get(i).getStartY() == ((Vertics) node).y && (dedge.get(i).src == (Vertics)node || dedge.get(i).dest == (Vertics) node) ) {
							dedge.get(i).setStartX(x);
							dedge.get(i).setStartY(y);
							double newX = ((2 * dedge.get(i).getStartX()) / 3)  + (dedge.get(i).getEndX())/3;
							double newY = ((2 * dedge.get(i).getStartY()) / 3)  + (dedge.get(i).getEndY())/3;
							dedge.get(i).w.setLayoutX(newX);
							dedge.get(i).w.setLayoutY(newY);
							
						}
						
						if (dedge.get(i).getEndX() == ((Vertics) node).x && dedge.get(i).getEndY() == ((Vertics) node).y && (dedge.get(i).src == (Vertics)node || dedge.get(i).dest == (Vertics) node)) {
							dedge.get(i).setEndX(x);
							dedge.get(i).setEndY(y);
							
							double newX = ((2 * dedge.get(i).getStartX()) / 3)  + (dedge.get(i).getEndX())/3;
							double newY = ((2 * dedge.get(i).getStartY()) / 3)  + (dedge.get(i).getEndY())/3;
							dedge.get(i).w.setLayoutX(newX);
							dedge.get(i).w.setLayoutY(newY);
							
							
						}
						
						 
					}
					((Vertics) node).x = x;
					((Vertics) node).y = y;
					((Vertics) node).name.setLayoutX(x-8);
					((Vertics) node).name.setLayoutY(y-35);
					
				} else {
					for (int i = 0 ; i < uedge.size() ; i++) {
						if (uedge.get(i).getStartX() == ((Vertics) node).x && uedge.get(i).getStartY() == ((Vertics) node).y && (uedge.get(i).src == (Vertics)node || uedge.get(i).dest == (Vertics) node)) {
							uedge.get(i).setStartX(x);
							uedge.get(i).setStartY(y);
							double newX = ((uedge.get(i).getStartX()) / 2)  + (uedge.get(i).getEndX())/2;
							double newY = ((uedge.get(i).getStartY()) / 2)  + (uedge.get(i).getEndY())/2;
							uedge.get(i).w.setLayoutX(newX);
							uedge.get(i).w.setLayoutY(newY);
						}
						
						if (uedge.get(i).getEndX() == ((Vertics) node).x && uedge.get(i).getEndY() == ((Vertics) node).y && (uedge.get(i).src == (Vertics)node || uedge.get(i).dest == (Vertics) node)) {
							uedge.get(i).setEndX(x);
							uedge.get(i).setEndY(y);
							double newX = ((uedge.get(i).getStartX()) / 2)  + (uedge.get(i).getEndX())/2;
							double newY = ((uedge.get(i).getStartY()) / 2)  + (uedge.get(i).getEndY())/2;
							uedge.get(i).w.setLayoutX(newX);
							uedge.get(i).w.setLayoutY(newY);
						}
						
					}
					((Vertics) node).x = x;
					((Vertics) node).y = y;
					((Vertics) node).name.setLayoutX(x-8);
					((Vertics) node).name.setLayoutY(y-35);
				}
				
				
				
				
				System.out.println("ddddd");
				((Vertics) node).setCenterX(x);
				((Vertics) node).setCenterY(y);
			
			}
		});
		
		
		
		// this event is for handle actions on vertics and edges 
		node.setOnMouseClicked(e -> {
		
			if (node instanceof Vertics)
				onV = (Vertics) node;
			 if(e.getButton().equals(MouseButton.PRIMARY)) {
				 if (isDijecstra) {
						if (start == null)
							start = (Vertics) node;
						else if (end == null) {
							end = (Vertics) node;
							Ed[] arr;
							try {
							if (is_directed)
								 arr = algo.ddijekstra(graph, start, end);
							else 
								arr = algo.udijekstra(graph, start, end);
							
							
								System.out.println("drow");
							//pane.getChildren().clear();
							//drowVertics(graph);				
							drowPath(graph, arr);
							} catch (Exception exc) {
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Warining");
								alert.setHeaderText(null);
								alert.setContentText(exc.getMessage());

								alert.showAndWait();
							}
							start = null;
							end = null;
							isDijecstra = false;
						}
						
					}
				 
				 if (isTxt) {
					 	TextInputDialog dialog = new TextInputDialog("weight");
						dialog.setTitle("weight dialog");
						dialog.setHeaderText("Set Weight");
						dialog.setContentText("Please enter the weight:");
						Optional<String> result = dialog.showAndWait();
						String name = result.get();
						
						Label nn = new Label(name);
						nn.setLayoutX(((Vertics) node).x - 8);
						nn.setLayoutY(((Vertics) node).y-35);
						nn.setTextFill(Color.BLUE);
						pane.getChildren().add(nn);
						((Vertics) node).name = nn;
						isTxt = false;
				 }
				 if (isFirstClick  && is_edge && node instanceof Vertics) {
					 p2 = (Vertics) node;
					 
					 
					 if (is_directed && p1 != p2) {
						 if (isWeighted) {
							 TextInputDialog dialog = new TextInputDialog("weight");
								dialog.setTitle("weight dialog");
								dialog.setHeaderText("Set Weight");
								dialog.setContentText("Please enter the weight:");
								Optional<String> result = dialog.showAndWait();
								int weight = Integer.parseInt(result.get());
								if (result.isPresent() ) {
									Label ll = new Label(weight + "");
									ll.setLayoutX( ((2 * p2.x) / 3) + (p1.x) / 3 );
									ll.setLayoutY( ((2 * p2.y) / 3) + (p1.y) / 3 );
									ll.setTextFill(Color.RED);
									
									DEdge de = new DEdge(p1,p2,weight,ll);
									addEventHandler(parent, de);
									dedge.add(de);
									p1.addDEdge(de);									
									pane.getChildren().add(de);
									pane.getChildren().add(ll);
								}
						 } else {
							 DEdge de = new DEdge(p1,p2,1,new Label(""));
							 addEventHandler(parent, de);
							 dedge.add(de);
							 p1.addDEdge(de);
							 pane.getChildren().add(de);
						 }
					 } else if (p1 != p2) {
						// p2 = (Vertics) node;
						 if (isWeighted) {
							 TextInputDialog dialog = new TextInputDialog("weight");
								dialog.setTitle("weight dialog");
								dialog.setHeaderText("Set Weight");
								dialog.setContentText("Please enter the weight:");
								Optional<String> result = dialog.showAndWait();
								int weight = Integer.parseInt(result.get());
								if (result.isPresent() ) {
									Label ll = new Label(weight + "");
									ll.setLayoutX((p1.x + p2.x) / 2 );
									ll.setLayoutY((p1.y + p2.y) / 2);
									ll.setTextFill(Color.RED);
									
									UEdge de = new UEdge(p1,p2,weight,ll);
									addEventHandler(parent, de);
									uedge.add(de);
									p1.addUEdge(de);
									UEdge de2 = new UEdge(p2,p1,weight,ll);
									addEventHandler(parent, de2);
									uedge.add(de2);
									p2.addUEdge(de2);
									pane.getChildren().add(de);
									pane.getChildren().add(ll);
								}
						 } else {
							 	UEdge de = new UEdge(p1,p2,-1,new Label(""));
							 	addEventHandler(parent, de);
								uedge.add(de);
								p1.addUEdge(de);
								UEdge de2 = new UEdge(p2,p1,-1,new Label(""));
								addEventHandler(parent, de2);
								uedge.add(de2);
								p2.addUEdge(de2);
								pane.getChildren().add(de);
						 }
					 }
					 isFirstClick = false;
					 is_edge = false;
				 } else if (!isFirstClick  && is_edge && node instanceof Vertics) {
					 p1 = (Vertics) node;					 
					 isFirstClick = true;
				 } else if (node instanceof DEdge) {
					 if (isWeighted && addCost) {
						 TextInputDialog dialog = new TextInputDialog("weight");
							dialog.setTitle("weight dialog");
							dialog.setHeaderText("Set Weight");
							dialog.setContentText("Please enter the weight:");
							Optional<String> result = dialog.showAndWait();
							int weight = Integer.parseInt(result.get());
							delete(((DEdge)node).w);
							((DEdge)node).weight = weight;
							((DEdge)node).w.setText(weight+"");
							pane.getChildren().add(((DEdge)node).w);
					 }
					 addCost = false;
				 } else if (node instanceof UEdge) {
					 TextInputDialog dialog = new TextInputDialog("weight");
						dialog.setTitle("weight dialog");
						dialog.setHeaderText("Set Weight");
						dialog.setContentText("Please enter the weight:");
						Optional<String> result = dialog.showAndWait();
						int weight = Integer.parseInt(result.get());
						delete(((UEdge)node).w);
						((UEdge)node).weight = weight;
						((UEdge)node).w.setText(weight+"");
						pane.getChildren().add(((UEdge)node).w);
						addCost = false;
				 }
			 } else if (e.getButton().equals(MouseButton.SECONDARY)) {
				 delete(node);
			 }
		});
		
		node.setOnMouseMoved(e -> {
			scene.setCursor(Cursor.OPEN_HAND);
		});
		
		node.setOnMouseExited(e -> {
			scene.setCursor(Cursor.DEFAULT);
		});
	}
	

	
	void delete(Node node) {
		
		if (node instanceof Vertics) {
			 double x = ((Vertics)node).x;
			 double y = ((Vertics)node).y;
			 pane.getChildren().remove(node);
			 
			 for (int i = 0 ; i < graph.graph.size() ; i++) {
				 if (graph.graph.get(i).equals((Vertics)node)) {
					 graph.graph.remove(i);
					 break;
				 }
				 	
			 }
			 
			 for (int i = 0 ; i < pane.getChildren().size() ; i++) {
				 if (pane.getChildren().get(i) instanceof DEdge ) {
					if (((DEdge) pane.getChildren().get(i)).getStartX() == x && ((DEdge) pane.getChildren().get(i)).getStartY() == y) {      
						pane.getChildren().remove(i);
						if(isWeighted)
							pane.getChildren().remove(i);
						i--;
					} else if (((DEdge) pane.getChildren().get(i)).getEndX() == x && ((DEdge) pane.getChildren().get(i)).getEndY() == y) {
						pane.getChildren().remove(i);
						if(isWeighted)
							pane.getChildren().remove(i);
						i--;
					}
				} else if (pane.getChildren().get(i) instanceof UEdge) {
					if (((UEdge) pane.getChildren().get(i)).getStartX() == x && ((UEdge) pane.getChildren().get(i)).getStartY() == y) {      
						pane.getChildren().remove(i);
						if(isWeighted)
							pane.getChildren().remove(i);
						i--;
					} else if (((UEdge) pane.getChildren().get(i)).getEndX() == x && ((UEdge) pane.getChildren().get(i)).getEndY() == y) {
						pane.getChildren().remove(i);
						if(isWeighted)
							pane.getChildren().remove(i);
						i--;
					}
				} else if (pane.getChildren().get(i) instanceof Label) {
					if (((Label) pane.getChildren().get(i)).getLayoutX() == x-8 && ((Label) pane.getChildren().get(i)).getLayoutY() == y-35) {
						pane.getChildren().remove(i);
						i--;
					}
				}
			 }
			 
			if (!is_directed) {
				for (int i = 0 ; i < graph.graph.size() ; i++) {
					for (int j = 0 ; j < graph.graph.get(i).uEdge.size() ; j++) {
						if (graph.graph.get(i).uEdge.get(j).dest.x == x && graph.graph.get(i).uEdge.get(j).dest.y == y) {
							graph.graph.get(i).uEdge.remove(j);
						}
					}
				}
			} else {
				for (int i = 0 ; i < graph.graph.size() ; i++) {
					for (int j = 0 ; j < graph.graph.get(i).dEdge.size() ; j++) {
						if (graph.graph.get(i).dEdge.get(j).dest.x == x && graph.graph.get(i).dEdge.get(j).dest.y == y) {
							graph.graph.get(i).dEdge.remove(j);
						}
					}
			}
			}
		}else {
			System.out.println("aaaa");
			 pane.getChildren().remove(node);
			 
			 if (node instanceof UEdge) {
				 Label ll = ((UEdge)node).w;
				 pane.getChildren().remove(ll);
				 double x1 = ((UEdge)node).dest.x;
				 double y1 = ((UEdge)node).dest.y;
				 
				 double x2 = ((UEdge)node).src.x;
				 double y2 = ((UEdge)node).src.y;
				 
				 for (int i = 0 ; i < graph.graph.size() ; i++) {
					 for (int j = 0 ; j < graph.graph.get(i).uEdge.size() ; j++) {
						 if ((graph.graph.get(i).uEdge.get(j).src.x == x2 && graph.graph.get(i).uEdge.get(j).src.y == y2) 
								 || (graph.graph.get(i).uEdge.get(j).src.x == x1 && graph.graph.get(i).uEdge.get(j).src.y == y1)) {
							 graph.graph.get(i).uEdge.remove(j);
						 }
							 
					 }
				 } 
				 
			 } else if (node instanceof DEdge) {
				 Label ll = ((DEdge)node).w;
				 pane.getChildren().remove(ll);
				 double x1 = ((DEdge)node).src.x;
				 double y1 = ((DEdge)node).src.y; 
				 
				 for (int i = 0 ; i < graph.graph.size() ; i++) {
					 for (int j = 0 ; j< graph.graph.get(i).dEdge.size() ; j++) {
						 
						 if (graph.graph.get(i).dEdge.get(j).src.x == x1 && graph.graph.get(i).dEdge.get(j).src.y == y1) {
							 graph.graph.get(i).dEdge.remove(j);
							 System.out.println("removed");
						 }
					 }
				 }
			 }
			
		 }
	 
	}
	
	
	void copy() {
		
		isCopy = true;
		selectedV = onV;
	}
	
	void paste() {
		if (isPaste) {
			Circle cc = new Circle(xLoc,yLoc,15,Color.RED);

			
			Vertics vv = new Vertics(cc,new Label(""),counter);
			addEventHandler(pane, vv);
			pane.getChildren().add(vv);
			counter++;;
			if (is_directed) {
				
				for (int i = 0 ; i < selectedV.dEdge.size() ; i++) {
					if (isWeighted) {
						Label l2 = new Label(selectedV.dEdge.get(i).weight + "");
						l2.setLayoutX( ((2 * selectedV.dEdge.get(i).dest.x) / 3) + (vv.x) / 3 );
						l2.setLayoutY( ((2 * selectedV.dEdge.get(i).dest.y) / 3) + (vv.y) / 3 );
						l2.setTextFill(Color.RED);
						DEdge de = new DEdge(vv,selectedV.dEdge.get(i).dest,selectedV.dEdge.get(i).weight,l2);
						addEventHandler(pane, de);
						vv.addDEdge(de);
						dedge.add(de);
						pane.getChildren().add(de);
						pane.getChildren().add(l2);
					} else {

						DEdge de = new DEdge(vv,selectedV.dEdge.get(i).dest,selectedV.dEdge.get(i).weight,new Label(""));
						addEventHandler(pane, de);
						vv.addDEdge(de);
						dedge.add(de);
						pane.getChildren().add(de);
					}
				}
				
				
			} else {
				for (int i = 0 ; i < selectedV.uEdge.size() ; i++) {
					if (isWeighted) {
						Label l2 = new Label(selectedV.uEdge.get(i).weight + "");
						l2.setLayoutX( (( selectedV.uEdge.get(i).dest.x) / 2) + (vv.x) / 2 );
						l2.setLayoutY( (( selectedV.uEdge.get(i).dest.y) / 2) + (vv.y) / 2);
						l2.setTextFill(Color.RED);
						UEdge de = new UEdge(vv,selectedV.uEdge.get(i).dest,selectedV.uEdge.get(i).weight,l2);
						addEventHandler(pane, de);
						vv.addUEdge(de);
						uedge.add(de);
						pane.getChildren().add(de);
						pane.getChildren().add(l2);
					} else {

						UEdge de = new UEdge(vv,selectedV.uEdge.get(i).dest,selectedV.uEdge.get(i).weight,new Label(""));
						addEventHandler(pane, de);
						vv.addUEdge(de);
						uedge.add(de);
						pane.getChildren().add(de);
					}
				}
			}
		}
		
		isCopy = false;
		if (isCut) {
			isCut = false;
			delete(selectedV);
			isPaste = false;
			paste.setDisable(true);
		}
	}
	
	void cut() {
		isCut = true;
		selectedV = onV;
	}
	
	
	void putVertics() {
		isGraphClicked = true;
	}
	
	void setEdge() {
		is_edge = true;
	}
	
	 void newProject() {
		 if (graph.graph.size() == 0) {
			 is_directed = false;
			 is_edge = false;
			 isGraphClicked = false;
			 isWeighted = false;
			 isFirstClick = false;
			 graph = new Graph();
			 
			 
		 } else {
			 Alert alert = new Alert(AlertType.CONFIRMATION);
			 alert.setTitle("New Project");
			 alert.setHeaderText("Open new Project ?");
			 alert.setContentText("Choose your option.");
			 ButtonType save = new ButtonType("Save");
			 ButtonType dsave = new ButtonType("Don't Save");
			 ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			 alert.getButtonTypes().setAll(save, dsave, buttonTypeCancel);

			 Optional<ButtonType> result = alert.showAndWait();
			 
			 if (result.get() == save) {
				 save();
				 clear();
				 setStart();
				 
			 } else if (result.get() == dsave) {
				 clear();	
				 setStart();
			 } else if (result.get() == buttonTypeCancel) {
				 
			 }
			 
		 }
		
	 }
	 
	 void save() {
		 System.out.println(name);
		 for (int i = 0 ; i < graph.graph.size() ; i++)  {
				graph.graph.get(i).id = i;
				if (graph.graph.get(i).name.getText().equals("")) {
					graph.graph.get(i).name.setText("v+"+i);
				}
		 }
		 
		 
		 
		 File file = new File(name + ".txt");
		 
		 try {
			FileWriter out = new FileWriter(file);
			out.write(graph.graph.size() + "\n");
			if (is_directed)
				out.write("D\n");
			else
				out.write("U\n");
			
			if (isWeighted)
				out.write("W\n");
			else
				out.write("U\n");
			
			for (int i = 0 ; i < graph.graph.size() ; i++) {
				if (is_directed) {
					out.write(graph.graph.get(i).id + " " + graph.graph.get(i).dEdge.size() + "\n" + graph.graph.get(i).name.getText() + "\n");
					out.write(graph.graph.get(i).x + " " + graph.graph.get(i).y + "\n");
					
					for (int j = 0 ; j < graph.graph.get(i).dEdge.size() ; j++) {
						out.write(graph.graph.get(i).dEdge.get(j).dest.id + " " + graph.graph.get(i).dEdge.get(j).weight + "\n");
					}
				} else {
					out.write(graph.graph.get(i).id + " " + graph.graph.get(i).uEdge.size() + "\n");
					out.write(graph.graph.get(i).x + " " + graph.graph.get(i).y + "\n");
					
					for (int j = 0 ; j < graph.graph.get(i).uEdge.size() ; j++) {
						out.write(graph.graph.get(i).uEdge.get(j).dest.id + " " + graph.graph.get(i).uEdge.get(j).weight + "\n");
					}
				}
			}
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
	 }
	 
	 
	 void open() {
		 
		 if (graph.graph.size() != 0) {
			 Alert alert = new Alert(AlertType.CONFIRMATION);
			 alert.setTitle("New Project");
			 alert.setHeaderText("Open Project ?");
			 alert.setContentText("Choose your option.");
			 ButtonType save = new ButtonType("Save");
			 ButtonType dsave = new ButtonType("Don't Save");
			 ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			 alert.getButtonTypes().setAll(save, dsave, buttonTypeCancel);

			 Optional<ButtonType> result = alert.showAndWait();
			 
			 if (result.get() == save) {
				 save();
				 clear();
				 raedAndDrow();
				 
			 } else if (result.get() == dsave) {
				 clear();
				 raedAndDrow();
				
			 } else if (result.get() == buttonTypeCancel) {
				 
			 }
		 } else {
			 raedAndDrow();
		 }
		 
	 }
	 
	 void raedAndDrow() {
		 
			 File file;
			 FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				file = fileChooser.showOpenDialog(null);
				name = file.getName();
				

				
			try {
				Scanner scan = new Scanner(file);
				int n = scan.nextInt();
				counter = n+1;
				String d = scan.next();
				String w = scan.next();
				Ed[] arr = new Ed[n*n];
				int co = 0;
				
				if (d.charAt(0) == 'D') {
					is_directed = true;
				} else {
					is_directed = false;
				}
				
				
				if (w.charAt(0) == 'W') {
					isWeighted = true;
				
				} else {
					isWeighted = false;
				}
			//	;l;l
				for (int i = 0 ; i < n ; i++) {
					int v = scan.nextInt();
					int e = scan.nextInt();
					String name = scan.next();
					Circle c = new Circle(scan.nextDouble(),scan.nextDouble(),15,col);
					Label ll = new Label(name);
					ll.setLayoutX(c.getCenterX()-8);
					ll.setLayoutY(c.getCenterY()-35);
					Vertics ve = new Vertics(c,ll ,v);
					graph.addVertics(ve);
					//pane.getChildren().add(c);
					for (int j = 0 ; j < e ; j++) {
						arr[co] = new Ed(v,scan.nextInt(),scan.nextInt());
						co++;
					}
					
				}
				
				drowVertics(graph);
				drow(graph,arr,co,Color.BLACK);
				scan.close();
				System.out.println("done");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			 
			
		 
	 }
	 
	 
	 public void drowPath(Graph g, Ed[] arr) throws CloneNotSupportedException {
		 try {
			Pane p2 = new Pane();
			Stage st = new Stage();
			Scene s = new Scene(p2,800,600);
			
			for (int i = 0 ; i < g.graph.size() ; i++) {
				Vertics v = new Vertics(g.graph.get(i));
				 p2.getChildren().add(v);
			 }
			 
			 for (int i = 0 ; i < graph.graph.size() ; i++) {
				 for (int j = 0 ; j < graph.graph.get(i).dEdge.size() ; j++) {
					 DEdge de = new DEdge(new Vertics(graph.graph.get(i).dEdge.get(j).src)
							 , new Vertics(graph.graph.get(i).dEdge.get(j).dest), graph.graph.get(i).dEdge.get(j).weight
							 , new Label(graph.graph.get(i).dEdge.get(j).weight+""));
					 p2.getChildren().add(de);
					 if (isWeighted) {
						 Label lab = de.w;
						 lab.setLayoutX( ((2 * graph.graph.get(i).dEdge.get(j).src.x) / 3) + (graph.graph.get(i).dEdge.get(j).dest.x) / 3 );
						 lab.setLayoutY( ((2 * graph.graph.get(i).dEdge.get(j).src.y) / 3) + (graph.graph.get(i).dEdge.get(j).dest.y) / 3 );
						 lab.setTextFill(Color.RED);
						 p2.getChildren().add(lab);
					 }
				 }
				 for (int j = 0 ; j < graph.graph.get(i).uEdge.size() ; j++) {
					 UEdge de = new UEdge(new Vertics(graph.graph.get(i).uEdge.get(j).src)
							 , new Vertics(graph.graph.get(i).uEdge.get(j).dest), graph.graph.get(i).uEdge.get(j).weight
							 , new Label(graph.graph.get(i).uEdge.get(j).weight+""));
					 p2.getChildren().add(de);
					 Label lab = de.w;
					 lab.setLayoutX( (graph.graph.get(i).uEdge.get(j).src.x + graph.graph.get(i).uEdge.get(j).dest.x) / 2 );
					 lab.setLayoutY( (graph.graph.get(i).uEdge.get(j).src.y + graph.graph.get(i).uEdge.get(j).dest.y) / 2 );
					 lab.setTextFill(Color.RED);
					 p2.getChildren().add(lab);
				 }
				 
			 }
			 
			 for (int i = 0 ; i < arr.length ; i++) {
					
					if (is_directed) {
						for (int j = 0 ; j < graph.graph.size() ; j++) {
							for (int k = 0 ; k < graph.graph.get(j).dEdge.size() ; k++) {
								if ( graph.graph.get(j).dEdge.get(k).src.id == arr[i].src
										&& graph.graph.get(j).dEdge.get(k).dest.id == arr[i].dest) {
									Line l2 = new Line();
									l2.setStartX(graph.graph.get(j).dEdge.get(k).src.x);
									l2.setStartY(graph.graph.get(j).dEdge.get(k).src.y);
									l2.setEndX(graph.graph.get(j).dEdge.get(k).dest.x);
									l2.setEndY(graph.graph.get(j).dEdge.get(k).dest.y);
									l2.setStroke(Color.CHARTREUSE);
									l2.setStrokeWidth(3);
									p2.getChildren().add(l2);
									break;
								}
							}
						}
						
					} else {
						for (int j = 0 ; j < graph.graph.size() ; j++) {
							for (int k = 0 ; k < graph.graph.get(j).uEdge.size() ; k++) {
								if ( graph.graph.get(j).uEdge.get(k).src.id == arr[i].src
										&& graph.graph.get(j).uEdge.get(k).dest.id == arr[i].dest) {
									Line l2 = new Line();
									l2.setStartX(graph.graph.get(j).uEdge.get(k).src.x);
									l2.setStartY(graph.graph.get(j).uEdge.get(k).src.y);
									l2.setEndX(graph.graph.get(j).uEdge.get(k).dest.x);
									l2.setEndY(graph.graph.get(j).uEdge.get(k).dest.y);
									l2.setStroke(Color.CHARTREUSE);
									p2.getChildren().add(l2);
									break;
								} else if (graph.graph.get(j).uEdge.get(k).dest.id == arr[i].src
										&& graph.graph.get(j).uEdge.get(k).src.id == arr[i].dest) {
									Line l2 = new Line();
									l2.setStartX(graph.graph.get(j).uEdge.get(k).src.x);
									l2.setStartY(graph.graph.get(j).uEdge.get(k).src.y);
									l2.setEndX(graph.graph.get(j).uEdge.get(k).dest.x);
									l2.setEndY(graph.graph.get(j).uEdge.get(k).dest.y);
									l2.setStroke(Color.CHARTREUSE);
									p2.getChildren().add(l2);
								}
							}
						}
					}
					
				}
			 st.setScene(s);
			 st.show();

		 } catch(Exception e) {
			 e.printStackTrace();
		 }
		}
		
	 
	 
	 void drow(Graph g , Ed[] arr, int co,Color c) {
		 
		
		 
		 Vertics v1,v2;
		 for (int i = 0 ; i < co ; i++) {
			 v1 = g.graph.get(arr[i].src);
			 if (is_directed && isWeighted) {
				 v2 = g.graph.get(arr[i].dest );
				 Label ll = new Label(arr[i].weight + "");
				 ll.setLayoutX( ((2 * v2.x) / 3) + (v1.x) / 3 );
				 ll.setLayoutY( ((2 * v2.y) / 3) + (v1.y) / 3 );
				 ll.setTextFill(Color.RED);
				 DEdge ed = new DEdge(v1, v2, arr[i].weight, ll);
				 ed.setFill(Color.RED);
				 System.out.println("dadada");
				 addEventHandler(pane, ed);
				 v1.addDEdge(ed);
				 dedge.add(ed);
				 pane.getChildren().add(ed);
				 pane.getChildren().add(ll);
			 } else if (is_directed && !isWeighted) {
				 v2 = g.graph.get(arr[i].dest);

				 DEdge ed = new DEdge(v1, v2, 1, new Label(""));
				 addEventHandler(pane, ed);
				 v1.addDEdge(ed);
				 dedge.add(ed);
				 pane.getChildren().add(ed);
			 } else if (!is_directed && isWeighted) {
				 v2 = g.graph.get(arr[i].dest);
				 

				 Label ll = new Label(arr[i].weight + "");
					ll.setLayoutX((v1.x + v2.x) / 2 );
					ll.setLayoutY((v1.y + v2.y) / 2);
					ll.setTextFill(Color.RED);
					
				UEdge ed = new UEdge(v1, v2, arr[i].weight, ll);
				addEventHandler(pane, ed);
				v1.addUEdge(ed);
				uedge.add(ed);
				pane.getChildren().add(ed);
				pane.getChildren().add(ll);
			 } else if (!is_directed && !isWeighted) {
				 v2 = g.graph.get(arr[i].dest);
					
				UEdge ed = new UEdge(v1, v2, -1, new Label(""));
				addEventHandler(pane, ed);
				v1.addUEdge(ed);
				uedge.add(ed);
				pane.getChildren().add(ed);
			 }
		 }
		 
	 }
	 
	 void drow2(Graph g , Ed[] arr, int co,Color c) {
		  
		 Vertics v1,v2;
		 for (int i = 0 ; i < co ; i++) {
			 v1 = g.graph.get(arr[i].src);
			 v2 = g.graph.get(arr[i].dest);
			 Line ll = new Line(v1.x,v1.y,v2.x,v2.y);
			 ll.setStroke(Color.BLUE);
			 ll.setStrokeWidth(5);
			 pane.getChildren().add(ll);
			 			 
		 }
		 
	 }
	 
	 void drowVertics(Graph g) {
		 for (int i = 0 ; i < g.graph.size() ; i++) {

			 		 
			 addEventHandler(pane,g.graph.get(i));
			 pane.getChildren().add(g.graph.get(i));
			 pane.getChildren().add(g.graph.get(i).name);		
			 
		 }
	 }
	 
	 void clear () {
		 is_directed = false;
		 is_edge = false;
		 isGraphClicked = false;
		 isWeighted = false;
		 isFirstClick = false;
		 graph = new Graph();
		 pane.getChildren().clear();
	 }
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
