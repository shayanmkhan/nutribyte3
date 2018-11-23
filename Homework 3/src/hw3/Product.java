//Name: Shayan Khan
//AndrewID: shayank

package hw3;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Product {
	private StringProperty ndbNumber;
	private StringProperty productName;
	private StringProperty manufacturer;
	private StringProperty ingredients;
	
	private FloatProperty servingSize;
	private StringProperty servingUom;
	private FloatProperty householdSize;
	private StringProperty householdUom;
	
	private ObservableMap<String, ProductNutrient> productNutrients;
	
	Product() {
		ndbNumber = new SimpleStringProperty();
		productName = new SimpleStringProperty();
		manufacturer = new SimpleStringProperty();
		ingredients = new SimpleStringProperty();
		servingSize = new SimpleFloatProperty();
		servingUom = new SimpleStringProperty();
		householdSize = new SimpleFloatProperty();
		householdUom = new SimpleStringProperty();
		productNutrients = FXCollections.observableHashMap();
	}
	
	Product(String ndbNumber, String productName, String manufacturer, String ingredients) {
		this.ndbNumber = new SimpleStringProperty(ndbNumber);
		this.productName = new SimpleStringProperty(productName);
		this.manufacturer = new SimpleStringProperty(manufacturer);
		this.ingredients = new SimpleStringProperty(ingredients);
		
		servingSize = new SimpleFloatProperty();
		servingUom = new SimpleStringProperty();
		householdSize = new SimpleFloatProperty();
		householdUom = new SimpleStringProperty();
		productNutrients = FXCollections.observableHashMap();;
	}

	public String getNdbNumber() {
		return ndbNumber.get();
	}

	public void setNdbNumber(String ndbNumber) {
		this.ndbNumber.set(ndbNumber);
	}

	public String getProductName() {
		return productName.get();
	}

	public void setProductName(String productName) {
		this.productName.set(productName);
	}

	public String getManufacturer() {
		return manufacturer.get();
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer.set(manufacturer);
	}

	public String getIngredients() {
		return ingredients.get();
	}

	public void setIngredients(String ingredients) {
		this.ingredients.set(ingredients);
	}

	public float getServingSize() {
		return servingSize.get();
	}

	public void setServingSize(float servingSize) {
		this.servingSize.set(servingSize);;
	}

	public String getServingUom() {
		return servingUom.get();
	}

	public void setServingUom(String servingUom) {
		this.servingUom.set(servingUom);
	}

	public float getHouseholdSize() {
		return householdSize.get();
	}

	public void setHouseholdSize(float householdSize) {
		this.householdSize.set(householdSize);;
	}

	public String getHouseholdUom() {
		return householdUom.get();
	}

	public void setHouseholdUom(String householdUom) {
		this.householdUom.set(householdUom);
	}
	
	public void addProductNutrient(String nutrientCode, ProductNutrient productNutrient) {
		this.productNutrients.put(nutrientCode, productNutrient);
	}
	
	public ObservableMap<String, ProductNutrient> getProductNutrients() {
		return productNutrients;
	}

	class ProductNutrient {
		private StringProperty nutrientCode;
		private FloatProperty nutrientQuantity;
		
		public ProductNutrient() {
			this.nutrientCode = new SimpleStringProperty();
			this.nutrientQuantity = new SimpleFloatProperty();
		}
		
		public ProductNutrient(String nutrientCode, float nutrientQuantity) {
			this.nutrientCode = new SimpleStringProperty(nutrientCode);
			this.nutrientQuantity = new SimpleFloatProperty(nutrientQuantity);
		}
		
		public String getNutrientCode () {
			return nutrientCode.get();
		}
		
		public void setNutrientCode(String nutrientCode) {
			this.nutrientCode.set(nutrientCode);
		}
		
		public float getNutrientQuantity() {
			return nutrientQuantity.get();
		}
		
		public void setNutrientQuantity(float nutrientQuantity) {
			this.nutrientQuantity.set(nutrientQuantity);
		}
	}
	
	@Override
	public String toString() {
		return this.getProductName() + " by " + this.getManufacturer();
	}
}
