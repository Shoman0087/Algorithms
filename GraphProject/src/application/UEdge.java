package application;

import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

public class UEdge extends Line implements Cloneable{
	
	Vertics src;
	Vertics dest;
	int weight;
	 Label w;
	
	public UEdge() {
		
	}
	
	public UEdge(Vertics src,Vertics dest ,int weight , Label w) {
		super(src.x,src.y,dest.x,dest.y);
		super.setStrokeWidth(2);
		w.setFont(Font.font(10));
		this.src = src;
		this.dest = dest;
		this.weight = weight;
		this.w = w;
	}

	
	protected Object clone() throws CloneNotSupportedException {
		 
		UEdge clone=(UEdge)super.clone();
		clone.src = (Vertics) src.clone();
		clone.dest = (Vertics) dest.clone();
		clone.w = new Label(w.getText());
	    return clone;
	 
	  
	}
}
