//Name: Shayan Khan
//AndrewID: shayank

package nutribyte;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import nutribyte.Product.ProductNutrient;

public class NutriByte extends Application{
	static Model model = new Model();  	//made static to make accessible in the controller
	static View view = new View();		//made static to make accessible in the controller
	static Person person;				//made static to make accessible in the controller
	
	
	Controller controller = new Controller();	//all event handlers 

	/**Uncomment the following three lines if you want to try out the full-size data files */
//	static final String PRODUCT_FILE = "data/Products.csv";
//	static final String NUTRIENT_FILE = "data/Nutrients.csv";
//	static final String SERVING_SIZE_FILE = "data/ServingSize.csv";
	
	/**The following constants refer to the data files to be used for this application */
	static final String PRODUCT_FILE = "data/Nutri2Products.csv";
	static final String NUTRIENT_FILE = "data/Nutri2Nutrients.csv";
	static final String SERVING_SIZE_FILE = "data/Nutri2ServingSize.csv";
	
	static final String NUTRIBYTE_IMAGE_FILE = "NutriByteLogo.png"; //Refers to the file holding NutriByte logo image 

	static final String NUTRIBYTE_PROFILE_PATH = "profiles";  //folder that has profile data files

	static final int NUTRIBYTE_SCREEN_WIDTH = 1015;
	static final int NUTRIBYTE_SCREEN_HEIGHT = 675;

	@Override
	public void start(Stage stage) throws Exception {
		model.readProducts(PRODUCT_FILE);
		model.readNutrients(NUTRIENT_FILE);
		model.readServingSizes(SERVING_SIZE_FILE );
		view.setupMenus();
		view.setupNutriTrackerGrid();
		view.root.setCenter(view.setupWelcomeScene());
		Background b = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
		view.root.setBackground(b);
		Scene scene = new Scene (view.root, NUTRIBYTE_SCREEN_WIDTH, NUTRIBYTE_SCREEN_HEIGHT);
		view.root.requestFocus();  //this keeps focus on entire window and allows the textfield-prompt to be visible
		setupBindings();
		stage.setTitle("NutriByte 2.0");
		stage.setScene(scene);
		stage.getIcons().add(new Image(NUTRIBYTE_IMAGE_FILE));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	void setupBindings() {
		view.newNutriProfileMenuItem.setOnAction(controller.new NewMenuItemHandler());
		view.openNutriProfileMenuItem.setOnAction(controller.new OpenMenuItemHandler());
		view.saveNutriProfileMenuItem.setOnAction(controller.new SaveMenuItemHandler());
		view.exitNutriProfileMenuItem.setOnAction(event -> Platform.exit());
		view.aboutMenuItem.setOnAction(controller.new AboutMenuItemHandler());
		view.closeNutriProfileMenuItem.setOnAction(controller.new CloseMenuItemHandler());
		
		view.recommendedNutrientNameColumn.setCellValueFactory(recommendedNutrientNameCallback);
		view.recommendedNutrientQuantityColumn.setCellValueFactory(recommendedNutrientQuantityCallback);
		view.recommendedNutrientUomColumn.setCellValueFactory(recommendedNutrientUomCallback);
		
		view.productNutrientNameColumn.setCellValueFactory(productNutrientNameCallback);
		view.productNutrientQuantityColumn.setCellValueFactory(productNutrientQuantityCallback);
		view.productNutrientUomColumn.setCellValueFactory(productNutrientUomCallback);

		view.createProfileButton.setOnAction(controller.new RecommendNutrientsButtonHandler());
		view.searchButton.setOnAction(controller.new SearchButtonHandler());
		view.clearButton.setOnAction(controller.new ClearButtonHandler());
		view.addDietButton.setOnAction(controller.new AddDietButtonHandler());
		view.removeDietButton.setOnAction(controller.new RemoveDietButtonHandler());
		
		//Add listener for changes in product selection
		view.productsComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			if(view.productsComboBox.getSelectionModel().getSelectedItem() != null) {
				Product currentItem = view.productsComboBox.getSelectionModel().getSelectedItem();
				
				view.productIngredientsTextArea.setText("Product ingredients: " + currentItem.getIngredients());
				view.servingSizeLabel.setText(currentItem.getServingSize() + " " + currentItem.getServingUom());
				view.householdSizeLabel.setText(currentItem.getHouseholdSize() + " " + currentItem.getHouseholdUom());
				view.servingUom.setText(currentItem.getServingUom());
				view.householdServingUom.setText(currentItem.getHouseholdUom());
				
				ObservableList<ProductNutrient> productNutrients = FXCollections.observableArrayList(currentItem.getProductNutrients().values());
				view.productNutrientsTableView.setItems(productNutrients);
			}
		});
		
	}
	
	//Callbacks for custom cell value factories
	Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>> recommendedNutrientNameCallback = 
			new Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<RecommendedNutrient, String> param) {
			Nutrient nutrient = Model.nutrientsMap.get(param.getValue().getNutrientCode());
			return nutrient.nutrientNameProperty();
		}
	};
	
	Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>> recommendedNutrientQuantityCallback = 
			new Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<RecommendedNutrient, String> param) {
			float quantity = param.getValue().getNutrientQuantity();
			
			StringProperty quantityProperty = new SimpleStringProperty(String.format("%.2f", quantity));
			
			return quantityProperty;
		}
	};
	
	Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>> recommendedNutrientUomCallback = 
			new Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<RecommendedNutrient, String> param) {
			Nutrient nutrient = Model.nutrientsMap.get(param.getValue().getNutrientCode());
			return nutrient.nutrientUomProperty();
		}
	};
	
	Callback<CellDataFeatures<ProductNutrient, String>, ObservableValue<String>> productNutrientNameCallback = 
			new Callback<CellDataFeatures<ProductNutrient, String>, ObservableValue<String>>() {

		@Override
		public ObservableValue<String> call(CellDataFeatures<ProductNutrient, String> param) {
			Nutrient nutrient = Model.nutrientsMap.get(param.getValue().getNutrientCode());
			return nutrient.nutrientNameProperty();
		}

		
	};
	
	Callback<CellDataFeatures<ProductNutrient, String>, ObservableValue<String>> productNutrientQuantityCallback = 
			new Callback<CellDataFeatures<ProductNutrient, String>, ObservableValue<String>>() {

		@Override
		public ObservableValue<String> call(CellDataFeatures<ProductNutrient, String> param) {
			float quantity = param.getValue().getNutrientQuantity();
			
			StringProperty quantityProperty = new SimpleStringProperty(String.format("%.2f", quantity));
			
			return quantityProperty;
		}

		
	};
	
	Callback<CellDataFeatures<ProductNutrient, String>, ObservableValue<String>> productNutrientUomCallback = 
			new Callback<CellDataFeatures<ProductNutrient, String>, ObservableValue<String>>() {

		@Override
		public ObservableValue<String> call(CellDataFeatures<ProductNutrient, String> param) {
			Nutrient nutrient = Model.nutrientsMap.get(param.getValue().getNutrientCode());
			return nutrient.nutrientUomProperty();
		}

		
	};
	
}
