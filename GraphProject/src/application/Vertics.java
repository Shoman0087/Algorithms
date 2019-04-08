package application;

import java.util.ArrayList;
import java.util.LinkedList;

import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

public class Vertics extends Circle implements Cloneable{
	
	ArrayList<DEdge> dEdge = new ArrayList<>();
	
	ArrayList<UEdge> uEdge = new ArrayList<>();
	
	Double x,y;
	
	Label name ;
	int id;
	
	
	public Vertics() {
		
	}
	
	public Vertics(Vertics v) {
		super(v.x,v.y,v.getRadius(),v.getFill());
		double x = v.x;
		double y = v.y;
		int id = v.id;
		Label label = new Label(v.name.getText());
		 this.x = x;
		 this.y = y;
		 this.name = label;
		 
	}
	
	public Vertics(Circle c, Label l,int id) {
		x = new Double(c.getCenterX());
		y = new Double(c.getCenterY());
		super.setCenterX(x);
		super.setCenterY(y);
		super.setRadius(c.getRadius() + 10);
		super.setFill(c.getFill());
		//super(c.getCenterX(),c.getCenterY(),c.getRadius(),c.getFill());			
		name = l;
		name.setLayoutX(x - 8);
		name.setLayoutY(y - 35);
		this.id = id;
	}
	
	public void addUEdge(UEdge e) {
		uEdge.add(e);
	}
	
	public void addDEdge(DEdge e) {
		dEdge.add(e);
	}
	
	protected Object clone() throws CloneNotSupportedException {
		 
		Vertics clone=(Vertics)super.clone();
	
		clone.name = new Label(name.getText());
		
		clone.dEdge = (ArrayList<DEdge>) dEdge.clone();
		clone.uEdge = (ArrayList<UEdge>) uEdge.clone();
		

	    return clone;
	 
	  }
	

}
