//Name: Shayan Khan
//AndrewID: shayank

package hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSVFiler extends DataFiler {

	@Override
	public void writeFile(String filename) {
		return;		
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
		String[] attributes = data.split(", ");
		
		//Initialize attribute variables
		String gender = "", ingredientsToWatch = "";
		float age = 0, weight = 0, height = 0, physicalActivityLevel = 0;
		
		try {
			//Check that profile has minimum number of attributes
			if(attributes.length < 5) 
				throw new InvalidProfileException("Profile is missing some data!");

			//Store each attribute in corresponding variable
			gender = attributes[0];
			age = Float.parseFloat(attributes[1]);
			weight = Float.parseFloat(attributes[2]);
			height = Float.parseFloat(attributes[3]);
			physicalActivityLevel = Float.parseFloat(attributes[4]);
			
			//Add ingredients to watch, if they are included
			if(attributes.length > 5) {
				StringBuilder sb = new StringBuilder();
				for(int i = 5; i < attributes.length; i++) {
					sb.append(attributes[i] + ", ");
				}
				sb.setLength(sb.length() - 2);	//Delete trailing comma
				ingredientsToWatch = sb.toString();
			}
			
			//Check that gender is either "Male" or "Female"
			if(!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")) 
				throw new InvalidProfileException("Invalid gender! Gender must be \"Male\" or \"Female\"");
			
			//Check that no values are negative
			if(age < 0 || weight < 0 || height < 0 || physicalActivityLevel < 0) 
				throw new InvalidProfileException("Values cannot be negative!");
			
			//Check that physicalActivityLevel is within set of acceptable values
			if (physicalActivityLevel != 1 && physicalActivityLevel != 1.1f && physicalActivityLevel != 1.25f && physicalActivityLevel != 1.48f) 
				throw new InvalidProfileException("Invalid physical activity level!");
			
		} catch(Exception e) {
			System.out.println("Invalid profile received.");
			return false;
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
	
	boolean validateProductData(String[] data) {
		int numValidProducts = 0;
		
		//Validate each line of data
		for(String line : data) {
			String[] attributes = line.split(", ");
			
			try {
				if(attributes.length != 3) throw new InvalidProfileException("Product has wrong number of attributes!");
				
				String ndbNumber = attributes[0];
				float servingSize = Float.parseFloat(attributes[1]);
				float householdSize = Float.parseFloat(attributes[2]);
				
				//Check that ndbNumber exists in database
				if(!Model.productsMap.containsKey(ndbNumber)) 
					throw new InvalidProfileException("ndbNumber " + ndbNumber + " not found in database!");
				
				//Create new product object based on input, and store in person's diet list
				Product product = Model.productsMap.get(ndbNumber);
				product.setServingSize(servingSize);
				product.setHouseholdSize(householdSize);
				NutriByte.person.dietProductsList.add(product);
				
				numValidProducts++;			
				
			} catch(Exception e) {
				System.out.println("Invalid profile received.");
				continue;
			}
		}
		
		if(numValidProducts > 0) return true;
		else return false;
	}

}
