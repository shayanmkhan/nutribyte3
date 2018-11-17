//Name: Shayan Khan
//AndrewID: shayank

package hw3;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Nutrient {
	private StringProperty nutrientCode;
	private StringProperty nutrientName;
	private StringProperty nutrientUom;
	
	public Nutrient() {
		nutrientCode = new SimpleStringProperty();
		nutrientName = new SimpleStringProperty();
		nutrientUom = new SimpleStringProperty();
	}
	
	public Nutrient(String nutrientCode, String nutrientName, String nutrientUom) {
		this.nutrientCode = new SimpleStringProperty(nutrientCode);
		this.nutrientName = new SimpleStringProperty(nutrientName);
		this.nutrientUom = new SimpleStringProperty(nutrientUom);
	}
	
	public String getNutrientCode() {
		return nutrientCode.get();
	}
	
	public void setNutrientCode(String nutrientCode) {
		this.nutrientCode.set(nutrientCode);
	}
	
	public StringProperty nutrientCodeProperty() {
		return nutrientCode;
	}
	
	public String getNutrientName() {
		return nutrientName.get();
	}
	
	public void setNutrientName(String nutrientName) {
		this.nutrientName.set(nutrientName);
	}
	
	public StringProperty nutrientNameProperty() {
		return nutrientName;
	}
	
	public String getNutrientUom() {
		return nutrientUom.get();
	}
	
	public void setNutrientUom(String nutrientUom) {
		this.nutrientUom.set(nutrientUom);
	}
	
	public StringProperty nutrientUomProperty() {
		return nutrientUom;
	}
}
