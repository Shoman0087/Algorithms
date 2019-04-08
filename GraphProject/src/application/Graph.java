package application;

import java.util.ArrayList;

import javafx.scene.shape.Circle;

public class Graph {
	
	ArrayList<Vertics> graph = new ArrayList<>();
	
	Graph() {
		
	}
	
	public void addVertics(Vertics v) {
		graph.add(v);
	}

}
