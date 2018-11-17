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
			NutriByte.person  = new Male(age, weight, height, physicalActivityLevel, ingredientsToWatch);
			NutriByte.view.genderComboBox.setValue("Male");
			
		}
		else {
			NutriByte.person = new Female(age, weight, height, physicalActivityLevel, ingredientsToWatch);
			NutriByte.view.genderComboBox.setValue("Female");
		}
		
		input.close();
		return true;

	}

}
