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
	public boolean readFile(String filename) {
		//Open given file
		Scanner input = null;
		try {
			input = new Scanner(new File(filename));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		}
		
		//Save first line of the file, and store its contents in an array
		String line = input.nextLine();
		String[] attributes = line.split(", ");
		
		//Store each attribute in a variable
		float age = Float.parseFloat(attributes[1]);
		float weight = Float.parseFloat(attributes[2]);
		float height = Float.parseFloat(attributes[3]);
		float physicalActivityLevel = Float.parseFloat(attributes[4]);
		
		//Add ingredients to watch, if they are included
		String ingredientsToWatch = "";
		if(attributes.length > 5) {
			StringBuilder sb = new StringBuilder();
			for(int i = 5; i < attributes.length; i++) {
				sb.append(attributes[i] + ", ");
			}
			sb.setLength(sb.length() - 2);	//Delete trailing comma
			ingredientsToWatch = sb.toString();
		}
		
		//Create either a Male or Female object based on given attributes
		if(attributes[0].toUpperCase().equals("MALE")) {
			NutriByte.person = new Male(age, weight, height, physicalActivityLevel, ingredientsToWatch);
			NutriByte.view.genderComboBox.setValue("Male");
			
		}
		else {
			NutriByte.person = new Female(age, weight, height, physicalActivityLevel, ingredientsToWatch);
			NutriByte.view.genderComboBox.setValue("Female");
		}
		
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
				throw new InvalidProfileException("Invalid gender!");
			
			//Check that no values are negative
			if(age < 0 || weight < 0 || height < 0 || physicalActivityLevel < 0) 
				throw new InvalidProfileException("Values cannot be negative!");
			
			//Check that physicalActivityLevel is within set of acceptable values
			if (physicalActivityLevel != 1 && physicalActivityLevel != 1.1 && physicalActivityLevel != 1.25 && physicalActivityLevel != 1.48) 
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

}
