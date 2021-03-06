//Name: Shayan Khan
//AndrewID: shayank

package nutribyte;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Controller {

	class RecommendNutrientsButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			if(validatePersonData() == false) return;
			
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
			
			//Create Male or Female object based on input
			if(gender.equalsIgnoreCase("male")) {
				NutriByte.person = new Male(age, weight, height, physicalActivityLevel, ingredientsToWatch);
			}
			else {
				NutriByte.person = new Female(age, weight, height, physicalActivityLevel, ingredientsToWatch);
			}
			
			//Create NutriProfile and populate TableView
			NutriProfiler.createNutriProfile(NutriByte.person);
			NutriByte.view.recommendedNutrientsTableView.setItems(NutriByte.person.recommendedNutrientsList);
			
			NutriByte.person.populateDietNutrientMap();
			NutriByte.view.nutriChart.updateChart();
		}			
	}

	class OpenMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			//Present file picker dialog, showing only CSV and XML files; Set initial directory to profiles folder
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select file");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"), new ExtensionFilter("XML Files", "*.xml"));
			fileChooser.setInitialDirectory(new File(NutriByte.NUTRIBYTE_PROFILE_PATH));		
			
			//Save chosen filename. If no file is chosen, do nothing.
			File file = fileChooser.showOpenDialog(new Stage());
			if(file == null) return;	
			String filename = file.getAbsolutePath();
			
			//Switch to main program window and clear all entries
			NutriByte.view.root.setCenter(NutriByte.view.nutriTrackerPane);
			NutriByte.view.initializePrompts();
			NutriByte.view.recommendedNutrientsTableView.getItems().clear();
			
			//Read profile from file into model
			NutriByte.model.readProfiles(filename);	
		
			//Set all GUI elements based on profile
			NutriByte.view.ageTextField.setText(Float.toString(NutriByte.person.age));
			NutriByte.view.weightTextField.setText(Float.toString(NutriByte.person.weight));
			NutriByte.view.heightTextField.setText(Float.toString(NutriByte.person.height));
			for(NutriProfiler.PhysicalActivityEnum activityLevel : NutriProfiler.PhysicalActivityEnum.values()) {
				if(activityLevel.getPhysicalActivityLevel() == NutriByte.person.physicalActivityLevel) {
					NutriByte.view.physicalActivityComboBox.setValue(activityLevel.getName());
				}
			}
			NutriByte.view.ingredientsToWatchTextArea.setText(NutriByte.person.ingredientsToWatch);
			
			//Create Nutrient Profile and populate TableView
			NutriProfiler.createNutriProfile(NutriByte.person);
			NutriByte.view.recommendedNutrientsTableView.setItems(NutriByte.person.recommendedNutrientsList);
			
			//Populate diet products table and NutriChart
			NutriByte.person.populateDietNutrientMap();
			NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
			NutriByte.view.nutriChart.updateChart();
		}
	}

	class NewMenuItemHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			//Set up view, and clear any existing items
			NutriByte.view.root.setCenter(NutriByte.view.nutriTrackerPane);
			NutriByte.view.initializePrompts();
			NutriByte.view.recommendedNutrientsTableView.getItems().clear();
			NutriByte.view.productSearchTextField.setText("");
			NutriByte.view.nutrientSearchTextField.setText("");
			NutriByte.view.ingredientSearchTextField.setText("");
			NutriByte.view.searchResultSizeLabel.setText("");
			NutriByte.view.productIngredientsTextArea.setText("");
			NutriByte.view.productsComboBox.setValue(null);
			NutriByte.view.productsComboBox.setItems(null);
			NutriByte.view.productNutrientsTableView.getItems().clear();
			NutriByte.view.servingSizeLabel.setText("");
			NutriByte.view.householdSizeLabel.setText("");
			NutriByte.view.servingUom.setText("");
			NutriByte.view.householdServingUom.setText("");
			NutriByte.view.productNutrientsTableView.getItems().clear();
			NutriByte.view.dietProductsTableView.getItems().clear();
			NutriByte.view.nutriChart.clearChart();
			
			
		}
	}
	
	class SaveMenuItemHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			
			if(validatePersonData() == false) return;
			
			FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Profile");
            fileChooser.setInitialDirectory(new File(NutriByte.NUTRIBYTE_PROFILE_PATH));
            fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV File", "*.csv"));
            File file = fileChooser.showSaveDialog(new Stage());
            
            if(file == null) return;
            
            NutriByte.model.writeProfile(file.getAbsolutePath());
			
		}
		
	}

	class AboutMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About");
			alert.setHeaderText("NutriByte");
			alert.setContentText("Version 2.0 \nRelease 1.0\nCopyleft Java Nerds\nThis software is designed purely for educational purposes.\nNo commercial use intended");
			Image image = new Image(getClass().getClassLoader().getResource(NutriByte.NUTRIBYTE_IMAGE_FILE).toString());
			ImageView imageView = new ImageView();
			imageView.setImage(image);
			imageView.setFitWidth(300);
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			alert.setGraphic(imageView);
			alert.showAndWait();
		}
	}
	
	class CloseMenuItemHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			NutriByte.view.root.setCenter(NutriByte.view.setupWelcomeScene());
		}
		
	}
	
	class SearchButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			String productSearch = NutriByte.view.productSearchTextField.getText();
			String nutrientSearch = NutriByte.view.nutrientSearchTextField.getText();
			String ingredientSearch = NutriByte.view.ingredientSearchTextField.getText();
						
			List<Product> productMatches = new ArrayList<>();
			List<Product> nutrientMatches = new ArrayList<>();
			List<Product> ingredientMatches = new ArrayList<>();
			
			if(productSearch.length() > 0) {
				for(Product product : Model.productsMap.values()) {
					if(product.getProductName().toUpperCase().contains(productSearch.toUpperCase())) {
						productMatches.add(product);
					}
				}
				
			}
			else {
				for(Product product : Model.productsMap.values()) {
					productMatches.add(product);
				}
			}
			
			if(nutrientSearch.length() > 0) {
			
				for(Product product : Model.productsMap.values()) {
					for(String nutrientCode : product.getProductNutrients().keySet()) {
						String nutrientName = Model.nutrientsMap.get(nutrientCode).getNutrientName();
						
						if(nutrientName.toUpperCase().contains(nutrientSearch.toUpperCase())) {
							nutrientMatches.add(product);
						}
					}
				}

			}
			else {
				for(Product product : Model.productsMap.values()) {
					nutrientMatches.add(product);
				}
			}
			
			if(ingredientSearch.length() > 0) {
				for(Product product : Model.productsMap.values()) {
					if(product.getIngredients().toUpperCase().contains(ingredientSearch.toUpperCase())) {
						ingredientMatches.add(product);
					}
				}
			}
			else {
				for(Product product : Model.productsMap.values()) {
					ingredientMatches.add(product);
				}
			}
			
			//Keep only the products that match the product, nutrient, and ingredient search terms
			productMatches.retainAll(nutrientMatches);
			productMatches.retainAll(ingredientMatches);
			
			NutriByte.model.searchResultsList = FXCollections.observableArrayList(productMatches);
			NutriByte.view.productsComboBox.setItems(NutriByte.model.searchResultsList);	
			NutriByte.view.productsComboBox.getSelectionModel().selectFirst();
			NutriByte.view.searchResultSizeLabel.setText(productMatches.size() + " product(s) found");
			
		}
		
	}
	
	class ClearButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			//Reset all search fields
			NutriByte.view.productSearchTextField.setText("");
			NutriByte.view.nutrientSearchTextField.setText("");
			NutriByte.view.ingredientSearchTextField.setText("");
			NutriByte.view.searchResultSizeLabel.setText("");
			NutriByte.view.productIngredientsTextArea.setText("");
			NutriByte.view.productsComboBox.setValue(null);
			NutriByte.view.productsComboBox.setItems(null);
			NutriByte.view.productNutrientsTableView.getItems().clear();
			NutriByte.view.servingSizeLabel.setText("");
			NutriByte.view.householdSizeLabel.setText("");
			NutriByte.view.servingUom.setText("");
			NutriByte.view.householdServingUom.setText("");
		}
		
	}
	
	class AddDietButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			Product dietProduct = NutriByte.view.productsComboBox.getSelectionModel().getSelectedItem();
			String servingSizeInput = NutriByte.view.dietServingSizeTextField.getText();
			String householdSizeInput = NutriByte.view.dietHouseholdSizeTextField.getText();
			float numServings = 0;
			
			if(servingSizeInput.length() == 0 && householdSizeInput.length() == 0) {
				numServings = 1;
				NutriByte.person.dietProductsList.add(dietProduct);
			}
			
			else if(servingSizeInput.length() > 0 && householdSizeInput.length() == 0) {
				float dietServingSize = Float.parseFloat(servingSizeInput);
				numServings = dietServingSize / dietProduct.getServingSize();
				
				dietProduct.setServingSize(dietServingSize);
				dietProduct.setHouseholdSize(dietProduct.getHouseholdSize() * numServings);
				
				NutriByte.person.dietProductsList.add(dietProduct);
			}
			
			else if(servingSizeInput.length() == 0 && householdSizeInput.length() > 0) {
				float dietHouseholdSize = Float.parseFloat(householdSizeInput);
				numServings = dietHouseholdSize / dietProduct.getHouseholdSize();
				
				dietProduct.setHouseholdSize(dietHouseholdSize);
				dietProduct.setServingSize(dietProduct.getServingSize() * numServings);
				
				NutriByte.person.dietProductsList.add(dietProduct);
			}
			
			else if(servingSizeInput.length() > 0 && householdSizeInput.length() > 0) {
				float dietServingSize = Float.parseFloat(servingSizeInput);
				numServings = dietServingSize / dietProduct.getServingSize();
				
				dietProduct.setServingSize(dietServingSize);
				dietProduct.setHouseholdSize(dietProduct.getHouseholdSize() * numServings);
				
				NutriByte.person.dietProductsList.add(dietProduct);
			}
			
			NutriByte.person.populateDietNutrientMap();
			
			NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
			NutriByte.view.nutriChart.updateChart();
		}
	}
	
	class RemoveDietButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			Product dietProduct = NutriByte.view.dietProductsTableView.getSelectionModel().getSelectedItem();
			
			NutriByte.person.dietProductsList.remove(dietProduct);
			
			if(NutriByte.person.dietProductsList.size() > 0) {
				NutriByte.person.populateDietNutrientMap();
				NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
				NutriByte.view.nutriChart.updateChart();
			}
			else {
				NutriByte.view.nutriChart.clearChart();
			}
			
		}
		
	}
	
	boolean validatePersonData() {
		try {
			//Validate gender
			if(NutriByte.view.genderComboBox.getValue() == null)
				throw new InvalidProfileException("Must select a gender!");

			//Validate age
			try {
				String ageText = NutriByte.view.ageTextField.getText();
				if(ageText.length() == 0) throw new InvalidProfileException("Must enter an age!");
				
				float age = Float.parseFloat(ageText);
				if(age <= 0) throw new InvalidProfileException("Age must be positive!");
				
			} catch(NumberFormatException e) {
				throw new InvalidProfileException("Age must be a number!");
			}
			
			//Validate weight
			try {
				String weightText = NutriByte.view.weightTextField.getText();
				if(weightText.length() == 0) throw new InvalidProfileException("Must enter a weight!");
				
				float weight = Float.parseFloat(weightText);
				if(weight <= 0) throw new InvalidProfileException("Weight must be positive!");
				
			} catch(NumberFormatException e) {
				throw new InvalidProfileException("Weight must be a number!");
			}
			
			//Validate height
			try {
				String heightText = NutriByte.view.heightTextField.getText();
				if(heightText.length() == 0) throw new InvalidProfileException("Must enter a height!");
				
				float height = Float.parseFloat(heightText);
				if(height <= 0) throw new InvalidProfileException("Height must be positive!");
				
			} catch(NumberFormatException e) {
				throw new InvalidProfileException("Height must be a number!");
			}
			
			
		} catch(InvalidProfileException e) {
			System.out.println("Invalid profile entered.");
			return false;
		}
		
		return true;
		
	}
}
