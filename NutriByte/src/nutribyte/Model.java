//Name: Shayan Khan
//AndrewID: shayank

package nutribyte;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class Model {
	static ObservableMap<String, Product> productsMap = FXCollections.observableHashMap();
	static ObservableMap<String, Nutrient> nutrientsMap = FXCollections.observableHashMap();
	ObservableList<Product> searchResultsList = FXCollections.observableArrayList();
	
	void readProducts(String filename) {
		CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader();
		
		try {
			CSVParser parser = CSVParser.parse(new FileReader(filename), format);
			for(CSVRecord record : parser) {
				//Add all products to the products map
				Product product = new Product(record.get(0), record.get(1), record.get(4), record.get(7));
				productsMap.put(record.get(0), product);
			}
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	}
	
	void readNutrients(String filename) {
		CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader();
		
		try {
			CSVParser parser = CSVParser.parse(new FileReader(filename), format);
			
			for(CSVRecord record : parser) {
				//Add new ProductNutrient object to corresponding Product object
				if(Float.parseFloat(record.get(4)) > 0) {
					Product product = productsMap.get(record.get(0));
					Product.ProductNutrient productNutrient = product.new ProductNutrient(record.get(1), Float.parseFloat(record.get(4)));
					product.addProductNutrient(record.get(1), productNutrient);
				}
				
				//Add only unique nutrients to the nutrients map
				if(nutrientsMap.containsKey(record.get(1)) == false) {
					Nutrient nutrient = new Nutrient(record.get(1), record.get(2), record.get(5));
					nutrientsMap.put(record.get(1), nutrient);
				}
			}
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	}
	
	void readServingSizes(String filename) {
		CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader();
		
		try {
			CSVParser parser = CSVParser.parse(new FileReader(filename), format);
			for(CSVRecord record : parser) {
				//If product exists in products map, add serving size info
				if(productsMap.containsKey(record.get(0))) {
					Product product = productsMap.get(record.get(0));
					
					//Serving size may be missing. If this is the case, set equal to zero
					if(record.get(1).length() > 0) product.setServingSize(Float.parseFloat(record.get(1)));
					else product.setServingSize(0);
					
					product.setServingUom(record.get(2));
					
					//Household size may be missing. If this is the case, set equal to zero
					if(record.get(3).length() > 0) product.setHouseholdSize(Float.parseFloat(record.get(3)));
					else product.setHouseholdSize(0);
					
					product.setHouseholdUom(record.get(4));
				}
			}
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	}
	
	boolean readProfiles(String filename) {
		//Read profile based on file extension
		if(filename.substring(filename.length() - 3).equals("csv")) {
			CSVFiler csvFiler = new CSVFiler();
			return csvFiler.readFile(filename);
		}
		
		else {
			XMLFiler xmlFiler = new XMLFiler();
			return xmlFiler.readFile(filename);
		}
	}
	
	void writeProfile(String filename) {
		CSVFiler csvFiler = new CSVFiler();
		csvFiler.writeFile(filename);
	}
	
}
