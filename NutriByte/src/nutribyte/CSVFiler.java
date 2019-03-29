//Name: Shayan Khan
//AndrewID: shayank

package nutribyte;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CSVFiler extends DataFiler {

	@Override
	public void writeFile(String filename) {
		String gender = NutriByte.view.genderComboBox.getValue();
		float age = Float.parseFloat(NutriByte.view.ageTextField.getText());
		float weight = Float.parseFloat(NutriByte.view.weightTextField.getText());
		float height = Float.parseFloat(NutriByte.view.heightTextField.getText());
		
		//Set physical activity level to corresponding value in PhysicalActivityEnum.
		float physicalActivityLevel = 1;
		String activitySelection = NutriByte.view.physicalActivityComboBox.getValue();
		for(NutriProfiler.PhysicalActivityEnum activityLevel : NutriProfiler.PhysicalActivityEnum.values()) {
			if(activityLevel.getName().equals(activitySelection)) {
				physicalActivityLevel = activityLevel.getPhysicalActivityLevel();
			}
		}
		
		String ingredientsToWatch = NutriByte.view.ingredientsToWatchTextArea.getText();
		
		String personData = gender + ", " + age + ", " + weight + ", " + height + ", " + physicalActivityLevel + ", " 
				+ ingredientsToWatch;
		
		StringBuilder sb = new StringBuilder();
		for(Product dietProduct : NutriByte.person.dietProductsList) {
			sb.append(dietProduct.getNdbNumber() + ", " + dietProduct.getServingSize() + ", " + dietProduct.getHouseholdSize() + "\n");
		}
		String dietProductsData = sb.toString();
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(filename));
			writer.write(personData + "\n" + dietProductsData);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	//Returns true if file is read successfully, false otherwise
	public boolean readFile(String filename) {
		//Open given file
		Scanner input = null;
		try {
			input = new Scanner(new File(filename));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		}
		
		//Save person data from first line of input
		String personData = input.nextLine();
		
		//Validate person data
		if(validatePersonData(personData) == false) {
			input.close();
			return false;
		}
		
		//If there are no additional lines in the input file, return
		if(!input.hasNextLine()) {
			input.close();
			return true;
		}
		
		//Save product data from each subsequent line of input
		StringBuilder sb = new StringBuilder();
		while(input.hasNextLine()) {
			sb.append(input.nextLine() + "\n");
		}
		String[] productData = sb.toString().split("\n");
		
		//Validate product data
		validateProductData(productData);
		
		input.close();
		return true;

	}
	
	//Validates the profile given; returns true if profile is valid, false otherwise
	boolean validatePersonData(String data) {
		//Break input string into an array of attributes
		String[] attributes = data.split(",\\s*");
		
		//Initialize attribute variables
		String gender = "", ingredientsToWatch = "";
		float age = 0, weight = 0, height = 0, physicalActivityLevel = 0;
		
		try {
			//Check that profile has minimum number of attributes
			if(attributes.length < 5) 
				throw new InvalidProfileException("Profile is missing some data!");

			//Store and validate gender
			gender = attributes[0];
			if(!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")) 
				throw new InvalidProfileException("Invalid gender! Gender must be \"Male\" or \"Female\"");
			
			//Store and validate age, weight, and height
			try {
				age = Float.parseFloat(attributes[1]);
			} catch(NumberFormatException e) {
				throw new InvalidProfileException("Invalid data for age: " + attributes[1] + "\nAge must be a number!");
			}
			try {
				weight = Float.parseFloat(attributes[2]);
			} catch(NumberFormatException e) {
				throw new InvalidProfileException("Invalid data for weight: " + attributes[2] + "\nWeight must be a number!");
			}
			try {
				height = Float.parseFloat(attributes[3]);
			} catch(NumberFormatException e) {
				throw new InvalidProfileException("Invalid data for height: " + attributes[3] + "\nHeight must be a number!");
			}

			//Store and validate physicalActivityLevel
			physicalActivityLevel = Float.parseFloat(attributes[4]);
			if (physicalActivityLevel != 1 && physicalActivityLevel != 1.1f && physicalActivityLevel != 1.25f 
					&& physicalActivityLevel != 1.48f) {
				throw new InvalidProfileException("Invalid physical activity level: "  + attributes[4] + 
						"\nMust be: 1, 1.1, 1.25, or 1.48");
			}
						
		} catch(InvalidProfileException e) {
			System.out.println("Invalid profile received.");
			return false;
		}
		
		//Add ingredients to watch, if they are included
		if(attributes.length > 5) {
			StringBuilder sb = new StringBuilder();
			for(int i = 5; i < attributes.length; i++) {
				sb.append(attributes[i] + ", ");
			}
			sb.setLength(sb.length() - 2);	//Delete trailing comma
			ingredientsToWatch = sb.toString();
		}
		
		//Create new person object based on the gender input, and set it as the program's global person
		if(gender.equalsIgnoreCase("male")) {
			NutriByte.person = new Male(age, weight, height, physicalActivityLevel, ingredientsToWatch);
			NutriByte.view.genderComboBox.setValue("Male");
		}
		else {
			NutriByte.person = new Female(age, weight, height, physicalActivityLevel, ingredientsToWatch);
			NutriByte.view.genderComboBox.setValue("Female");
		}
		
		return true;	
	}
	
	//Returns true if at least one valid product is found, false otherwise
	boolean validateProductData(String[] data) {
		int numValidProducts = 0;
		
		//Validate each line of data
		for(String line : data) {
			String[] attributes = line.split(",\\s*");
			
			//Initialize attribute variables
			String ndbNumber = "";
			float servingSize = 0, householdSize = 0;
			
			try {
				//Check for missing or extra data
				if(attributes.length != 3) {
					throw new InvalidProfileException("Cannot read: " + line + 
							"\nThe data must be in format \"String, Number, Number\" for NDB Number, Serving Size, Household Size");
				}
				
				//Store and validate ndbNumber
				ndbNumber = attributes[0];
				if(!Model.productsMap.containsKey(ndbNumber)) 
					throw new InvalidProfileException("NDB Number " + ndbNumber + " not found in database!");
				
				//Store and validate servingSize and householdSize
				try {
					servingSize = Float.parseFloat(attributes[1]);
				} catch(NumberFormatException e) {
					throw new InvalidProfileException("Invalid data for serving size: " + attributes[1] + 
							"\nServing size must be a number!");
				}
				try {
					householdSize = Float.parseFloat(attributes[2]);
				} catch(NumberFormatException e) {
					throw new InvalidProfileException("Invalid data for household size: " + attributes[2] + 
							"\nHousehold size must be a number!");
				}		
				
			} catch(InvalidProfileException e) {
				System.out.println("Invalid profile received.");
				continue;
			}
			
			//Create new product object based on input, and store in person's diet list
			Product product = Model.productsMap.get(ndbNumber);
			product.setServingSize(servingSize);
			product.setHouseholdSize(householdSize);
			NutriByte.person.dietProductsList.add(product);
			
			numValidProducts++;	
		}
		
		if(numValidProducts > 0) return true;
		else return false;
	}

}
