//Name: Shayan Khan
//AndrewID: shayank

package hw3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hw3.Product.ProductNutrient;
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
			//If gender is not selected, do nothing
			if(NutriByte.view.genderComboBox.getValue() == null) {
				return;
			}
			
			//Set values to 0 by default
			float age = 0;
			float weight = 0;
			float height = 0;
			
			//Set values only if they are entered
			if(NutriByte.view.ageTextField.getText().length() != 0) {
				age = Float.parseFloat(NutriByte.view.ageTextField.getText());
			}
			if(NutriByte.view.weightTextField.getText().length() != 0) {
				weight = Float.parseFloat(NutriByte.view.weightTextField.getText());
			}
			if(NutriByte.view.heightTextField.getText().length() != 0) {
				height = Float.parseFloat(NutriByte.view.heightTextField.getText());
			}
			
			//Set physical activity level to corresponding value in PhysicalActivityEnum. Set to "sedentary" by default.
			float physicalActivityLevel = 1;
			if(NutriByte.view.physicalActivityComboBox.getValue() != null) {
				String activitySelection = NutriByte.view.physicalActivityComboBox.getValue();
				for(NutriProfiler.PhysicalActivityEnum activityLevel : NutriProfiler.PhysicalActivityEnum.values()) {
					if(activityLevel.getName().equals(activitySelection)) {
						physicalActivityLevel = activityLevel.getPhysicalActivityLevel();
					}
				}
			}
			
			String ingredientsToWatch = NutriByte.view.ingredientsToWatchTextArea.getText();
			
			//Create Male or Female object based on input
			Person person;
			if(NutriByte.view.genderComboBox.getValue().toUpperCase().equals("MALE")) {
				person = new Male(age, weight, height, physicalActivityLevel, ingredientsToWatch);
			}
			else {
				person = new Female(age, weight, height, physicalActivityLevel, ingredientsToWatch);
			}
			
			//Create NutriProfile and populate TableView
			NutriProfiler.createNutriProfile(person);
			NutriByte.view.recommendedNutrientsTableView.setItems(person.recommendedNutrientsList);
		}			
	}

	class OpenMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			//Present file picker dialog, showing only CSV and XML files; Set initial directory to profiles folder
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select file");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"), new ExtensionFilter("XML Files", "*.xml"));
			fileChooser.setInitialDirectory(new File("profiles"));		
			
			//Save chosen filename. If no file is chosen, do nothing.
			Stage stage = new Stage();
			File file = fileChooser.showOpenDialog(stage);
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
		}
	}

	class NewMenuItemHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			//Set up view, and clear any existing items
			NutriByte.view.root.setCenter(NutriByte.view.nutriTrackerPane);
			NutriByte.view.initializePrompts();
			NutriByte.view.recommendedNutrientsTableView.getItems().clear();
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
	
	class SearchButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			String productSearch = NutriByte.view.productSearchTextField.getText();
			String nutrientSearch = NutriByte.view.nutrientSearchTextField.getText();
			String ingredientSearch = NutriByte.view.ingredientSearchTextField.getText();
						
			List<Product> productMatches = new ArrayList<>();
			List<Product> nutrientMatches = new ArrayList<>();
			List<Product> ingredientMatches = new ArrayList<>();

			//By default, copy all products to search result list
			for(Product product : Model.productsMap.values()) {
				NutriByte.model.searchResultsList.add(product);
			}
			
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
				//String nutrientCode = NutriProfiler.NutriEnum.valueOf(nutrientSearch.toUpperCase()).getNutrientCode();
				
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
			
			productMatches.retainAll(nutrientMatches);
			productMatches.retainAll(ingredientMatches);
			
			NutriByte.model.searchResultsList = FXCollections.observableArrayList(productMatches);
			
			/* For testing list
			for(Product product : NutriByte.model.searchResultsList) {
				System.out.println(product);
			}
			System.out.println(NutriByte.model.searchResultsList.size() + " matches found.");
			System.out.println("\n\n\n");
			*/
		}
		
	}
}
