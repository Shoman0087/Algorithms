package application;

import javafx.scene.control.TextField;

public class Field extends TextField{
	int row,column;
	
	public Field() {
		// TODO Auto-generated constructor stub
	}
	
	public Field(int row,int column,String val) {
		this.row = row;
		this.column = column;
		super.setText(val);
	}
	public Field(int row,int column) {
		this.row = row;
		this.column = column;
	}
	
	
}
