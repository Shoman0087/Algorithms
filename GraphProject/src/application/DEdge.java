package application;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DEdge extends Arrow implements Cloneable{
	
	Vertics src;
	Vertics dest;
	int weight;
	
	 Label w;
	

	
	public DEdge(Vertics src,Vertics dest ,int weight,Label w) {
		super();
		super.setStartX(src.x);
		super.setStartY(src.y);
		super.setEndX(dest.x);
		super.setEndY(dest.y);
		w.setFont(Font.font(10));
		this.src = src;
		this.dest = dest;
		this.weight = weight;
		this.w = w;
	}
	
	protected Object clone() throws CloneNotSupportedException {
		 
		DEdge clone=(DEdge)super.clone();
		clone.src = (Vertics) src.clone();
		clone.dest = (Vertics) dest.clone();
		clone.w = new Label(w.getText());
	    return clone;
	 
	  
	}
	
	
}
