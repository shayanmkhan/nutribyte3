//Name: Shayan Khan
//AndrewID: shayank

package hw3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public abstract class Person {

	float age, weight, height, physicalActivityLevel; //age in years, weight in kg, height in cm
	String ingredientsToWatch;
	float[][] nutriConstantsTable = new float[NutriProfiler.RECOMMENDED_NUTRI_COUNT][NutriProfiler.AGE_GROUP_COUNT];

	NutriProfiler.AgeGroupEnum ageGroup;
	
	ObservableList<RecommendedNutrient> recommendedNutrientsList = FXCollections.observableArrayList();
	ObservableList<Product> dietProductsList = FXCollections.observableArrayList();
	ObservableMap<String, RecommendedNutrient> dietNutrientsMap = FXCollections.observableHashMap();

	abstract void initializeNutriConstantsTable();
	abstract float calculateEnergyRequirement();

	Person(float age, float weight, float height, float physicalActivityLevel, String ingredientsToWatch) {
		this.age = age;
		this.weight = weight;
		this.height = height;
		this.physicalActivityLevel = physicalActivityLevel;
		this.ingredientsToWatch = ingredientsToWatch;
		
		//Set age group based on age
		if(age <= 0.25) this.ageGroup = NutriProfiler.AgeGroupEnum.MAX_AGE_3M;
		else if(age <= 0.5) this.ageGroup = NutriProfiler.AgeGroupEnum.MAX_AGE_6M;
		else if(age <= 1) this.ageGroup = NutriProfiler.AgeGroupEnum.MAX_AGE_1Y;
		else if(age <= 3) this.ageGroup = NutriProfiler.AgeGroupEnum.MAX_AGE_3Y;
		else if(age <= 8) this.ageGroup = NutriProfiler.AgeGroupEnum.MAX_AGE_8Y;
		else if(age <= 13) this.ageGroup = NutriProfiler.AgeGroupEnum.MAX_AGE_13Y;
		else if(age <= 18) this.ageGroup = NutriProfiler.AgeGroupEnum.MAX_AGE_18Y;
		else if(age <= 30) this.ageGroup = NutriProfiler.AgeGroupEnum.MAX_AGE_30Y;
		else if(age <= 50) this.ageGroup = NutriProfiler.AgeGroupEnum.MAX_AGE_50Y;
		else this.ageGroup = NutriProfiler.AgeGroupEnum.MAX_AGE_ABOVE;
		
	}

	//returns an array of nutrient values of size NutriProfiler.RECOMMENDED_NUTRI_COUNT. 
	//Each value is calculated as follows:
	//For Protein, it multiples the constant with the person's weight.
	//For Carb and Fiber, it simply takes the constant from the 
	//nutriConstantsTable based on NutriEnums' nutriIndex and the person's ageGroup
	//For others, it multiples the constant with the person's weight and divides by 1000.
	//Try not to use any literals or hard-coded values for age group, nutrient name, array-index, etc. 
	
	float[] calculateNutriRequirement() {
		float[] requirements = new float[NutriProfiler.RECOMMENDED_NUTRI_COUNT];
		
		for(int i = 0; i < requirements.length; i++) {
			//Pull value from nutriConstantsTable based on age group
			float nutriConstant = nutriConstantsTable[i][ageGroup.getAgeGroupIndex()];
			
			//Calculate nutritional requirement based on each nutrient's formula
			if(i == 0) requirements[i] = nutriConstant * weight;
			else if(i == 1 || i == 2) requirements[i] = nutriConstant;
			else requirements[i] = nutriConstant * weight / 1000;
		}
		
		return requirements;
	}
	
	void populateDietNutrientMap(float numServings) {
		for(Product dietProduct : dietProductsList) {
			for(Product.ProductNutrient nutrient : dietProduct.getProductNutrients().values())  {
				if(dietNutrientsMap.containsKey(nutrient.getNutrientCode()) == false) {
					float nutrientQuantity = nutrient.getNutrientQuantity() * numServings;
					
					RecommendedNutrient rn = new RecommendedNutrient(nutrient.getNutrientCode(), nutrientQuantity);
					
					dietNutrientsMap.put(nutrient.getNutrientCode(), rn);
					
				}
				
				else {
					float nutrientQuantity = nutrient.getNutrientQuantity() * numServings;
					
					float totalQuantity = dietNutrientsMap.get(nutrient.getNutrientCode()).getNutrientQuantity() + nutrientQuantity;
					
					dietNutrientsMap.get(nutrient.getNutrientCode()).setNutrientQuantity(totalQuantity);
				}
			}
		}
	}
}
