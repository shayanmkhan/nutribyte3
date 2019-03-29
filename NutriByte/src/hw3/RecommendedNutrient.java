//Name: Shayan Khan
//AndrewID: shayank

package hw3;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecommendedNutrient {
	private StringProperty nutrientCode;
	private FloatProperty nutrientQuantity;
	
	public RecommendedNutrient() {
		nutrientCode = new SimpleStringProperty();
		nutrientQuantity = new SimpleFloatProperty();
	}
	
	public RecommendedNutrient(String nutrientCode, float nutrientQuantity) {
		this.nutrientCode = new SimpleStringProperty(nutrientCode);
		this.nutrientQuantity = new SimpleFloatProperty(nutrientQuantity);
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
	
	public float getNutrientQuantity() {
		return nutrientQuantity.get();
	}
	
	public void setNutrientQuantity(float nutrientQuantity) {
		this.nutrientQuantity.set(nutrientQuantity);
	}
	
	public FloatProperty nutrientQuantityProperty() {
		return nutrientQuantity;
	}
	
}
