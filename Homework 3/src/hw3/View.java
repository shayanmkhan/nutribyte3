package hw3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class View {
	BorderPane root = new BorderPane();

	BorderPane nutriTrackerPane = new BorderPane();
	GridPane nutriProfilerGrid = new GridPane();

	//topGrid components
	MenuItem newNutriProfileMenuItem= new MenuItem("New...");
	MenuItem openNutriProfileMenuItem = new MenuItem("Open...");
	MenuItem saveNutriProfileMenuItem = new MenuItem("Save...");
	MenuItem closeNutriProfileMenuItem = new MenuItem("Close");
	MenuItem exitNutriProfileMenuItem = new MenuItem("Exit");
	MenuItem aboutMenuItem = new MenuItem("About");

	//nutriProfiler grid components
	ComboBox<String> genderComboBox = new ComboBox<>(); 
	TextField ageTextField = new TextField();
	TextField weightTextField = new TextField();
	TextField heightTextField = new TextField();
	ComboBox<String> physicalActivityComboBox = new ComboBox<>();
	TextArea ingredientsToWatchTextArea = new TextArea();

	TableView<RecommendedNutrient> recommendedNutrientsTableView = new TableView<>();
	TableColumn<RecommendedNutrient, String> recommendedNutrientNameColumn = new TableColumn<>("Nutrient");
	TableColumn<RecommendedNutrient, String> recommendedNutrientQuantityColumn = new TableColumn<>("Recommended \nquantity per day");

	TableColumn<RecommendedNutrient, String> recommendedNutrientUomColumn = new TableColumn<>("UOM");

	NutriChart nutriChart = new NutriChart();

	Button createProfileButton = new Button("Recommend Nutrients");

	//productGrid components

	TextField productSearchTextField = new TextField();
	TextField nutrientSearchTextField = new TextField();
	TextField ingredientSearchTextField = new TextField();
	Button searchButton = new Button ("Search");
	Button clearButton = new Button ("Clear search");
	Label searchResultSizeLabel = new Label();
	ComboBox<Product> productsComboBox = new ComboBox<>(); 

	Label servingSizeLabel = new Label();
	Label householdSizeLabel = new Label();
	Label servingUom = new Label();
	Label householdServingUom = new Label();

	TableView<Product.ProductNutrient> productNutrientsTableView = new TableView<>();
	TableColumn<Product.ProductNutrient, String> productNutrientNameColumn = new TableColumn<>("Product Nutrients");  //uses Callback to get nutrientName from Model.nutrients
	TableColumn<Product.ProductNutrient, String> productNutrientQuantityColumn = new TableColumn<>("Quantity\n/ 100 Product UOM");		//uses callback to calculate nutrient-quantity
	TableColumn<Product.ProductNutrient, String> productNutrientUomColumn = new TableColumn<>("Nutrient UOM");				//uses callback to get nutrient uom from Model.nutrients

	TextArea productIngredientsTextArea = new TextArea();

	TextField dietServingSizeTextField = new TextField();
	TextField dietHouseholdSizeTextField = new TextField();
	Label dietServingUomLabel = new Label();
	Label dietHouseholdUomLabel = new Label();

	Button addDietButton = new Button("Add product to diet");
	Button removeDietButton = new Button ("Remove product from Diet");

	TableView<Product> dietProductsTableView = new TableView<>();
	TableColumn<Product, String> dietProductNameColumn = new TableColumn<>("Product");
	TableColumn<Product, Float> dietServingSizeColumn = new TableColumn<>("Serving Size");
	TableColumn<Product, String> dietServingUomColumn = new TableColumn<>("UOM");
	TableColumn<Product, Float> dietHouseholdSizeColumn = new TableColumn<>("Household Size");
	TableColumn<Product, String> dietHouseholdUomColumn = new TableColumn<>("UOM");

	//shows the opening scene
	StackPane setupWelcomeScene() {
		StackPane pane = new StackPane();
		pane.setPrefSize(NutriByte.NUTRIBYTE_SCREEN_WIDTH - 20, NutriByte.NUTRIBYTE_SCREEN_HEIGHT - 20);
		ImageView imageView = new ImageView();

		imageView.setFitHeight(pane.getPrefHeight()-10);
		imageView.setFitWidth(pane.getPrefWidth()-10);
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		Image image = new Image(getClass().getClassLoader().getResource(NutriByte.NUTRIBYTE_IMAGE_FILE).toString());

		imageView.setImage(image);
		pane.getChildren().add(imageView);
		return pane;
	}

	//sets up the menus
	void setupMenus() {
		GridPane topGrid = new GridPane();
		topGrid.setVgap(5);
		topGrid.setHgap(5);
		MenuBar menuBar = new MenuBar();
		Menu nutriProfileMenu = new Menu("NutriProfile");
		Menu aboutMenu = new Menu("About");

		nutriProfileMenu.getItems().addAll(newNutriProfileMenuItem, openNutriProfileMenuItem, saveNutriProfileMenuItem, new SeparatorMenuItem(), closeNutriProfileMenuItem, exitNutriProfileMenuItem);
		aboutMenu.getItems().add(aboutMenuItem);
		menuBar.getMenus().addAll(nutriProfileMenu, aboutMenu);
		root.setTop(menuBar);
	}

	@SuppressWarnings("unchecked")
	void setupNutriTrackerGrid() {
		GridPane productGrid = new GridPane();

		/**setup productGrid */
		productGrid.setVgap(5);
		productGrid.setHgap(5);

		//add all controls to productGrid
		productGrid.add(productSearchTextField, 0, 0);
		productGrid.add(nutrientSearchTextField, 1, 0);
		productGrid.add(ingredientSearchTextField, 2, 0);
		productGrid.add(searchResultSizeLabel, 3, 0);

		productGrid.add(searchButton, 0, 1, 2, 1);
		productGrid.add(clearButton, 2, 1, 2, 1);

		productGrid.add(productsComboBox, 0, 2, 4, 1);

		productGrid.add(new Label("Standard serving size"), 4, 0);
		productGrid.add(servingSizeLabel, 5, 0);
		productGrid.add(new Label("Household size"), 6, 0);
		productGrid.add(householdSizeLabel, 7, 0);

		productGrid.add(productNutrientsTableView, 0, 3, 4, 1);
		productGrid.add(productIngredientsTextArea, 0, 4, 7, 1);

		productGrid.add(dietServingSizeTextField, 4, 1);
		productGrid.add(dietServingUomLabel, 5, 1);
		productGrid.add(dietHouseholdSizeTextField, 6, 1);
		productGrid.add(dietHouseholdUomLabel, 7, 1);
		productGrid.add(addDietButton, 4, 2, 2, 1);
		productGrid.add(removeDietButton, 6, 2, 2, 1);
		productGrid.add(dietProductsTableView, 4, 3, 4, 1);

		//setup product-nutrients table view
		productNutrientsTableView.getColumns().setAll(productNutrientNameColumn, productNutrientQuantityColumn, productNutrientUomColumn);
		productNutrientsTableView.getColumns().get(0).setPrefWidth(220);
		productNutrientsTableView.getColumns().get(1).setPrefWidth(125);
		productNutrientsTableView.getColumns().get(2).setPrefWidth(100);

		// set seven columns' width: product search field, nutrient search field, search result, search button x 2, clear button x 2
		for (int i = 0; i < 7; i++) productGrid.getColumnConstraints().add(new ColumnConstraints(123));  

		dietProductsTableView.getColumns().addAll(dietProductNameColumn, dietServingSizeColumn, dietServingUomColumn, dietHouseholdSizeColumn, dietHouseholdUomColumn);

		dietProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		dietServingSizeColumn.setCellValueFactory(new PropertyValueFactory<>("servingSize"));
		dietServingUomColumn.setCellValueFactory(new PropertyValueFactory<>("servingUom"));
		dietHouseholdSizeColumn.setCellValueFactory(new PropertyValueFactory<>("householdSize"));
		dietHouseholdUomColumn.setCellValueFactory(new PropertyValueFactory<>("householdUom"));

		dietProductsTableView.getColumns().get(0).setPrefWidth(175);
		dietProductsTableView.getColumns().get(1).setPrefWidth(90);
		dietProductsTableView.getColumns().get(2).setPrefWidth(50);
		dietProductsTableView.getColumns().get(3).setPrefWidth(110);
		dietProductsTableView.getColumns().get(4).setPrefWidth(50);

		//all sizes
		productGrid.setPrefHeight(300);
		productsComboBox.setMaxWidth(500);
		productNutrientsTableView.setMaxSize(500, 150);
		productNutrientsTableView.setMinSize(500, 150);
		dietProductsTableView.setMaxSize(490, 150);
		dietProductsTableView.setMinSize(490, 150);

		productIngredientsTextArea.setMaxSize(1000, 150);
		productIngredientsTextArea.setMinSize(1000, 150);

		searchButton.setPrefWidth(250);
		clearButton.setPrefWidth(250);
		addDietButton.setPrefWidth(250);
		removeDietButton.setPrefWidth(250);

		setupNutriProfilerGrid();
		nutriTrackerPane.setTop(nutriProfilerGrid);
		nutriTrackerPane.setCenter(productGrid);

		BorderPane.setMargin(productGrid, new Insets(5, 5, 5, 5));

		Background b1 = new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY));
		nutriProfilerGrid.setBackground(b1);

		Background b2 = new Background(new BackgroundFill(Color.BLANCHEDALMOND, CornerRadii.EMPTY, Insets.EMPTY));
		productGrid.setBackground(b2);

		Background b3 = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
		productIngredientsTextArea.setBackground(b3);
		productIngredientsTextArea.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, 
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		initializePrompts();
	}

	@SuppressWarnings("unchecked")
	void setupNutriProfilerGrid() {
		nutriProfilerGrid.setVgap(5);
		nutriProfilerGrid.setHgap(5);
		nutriProfilerGrid.setPrefHeight(225);

		nutriChart.barChart.setPrefSize(500, 125);

		recommendedNutrientsTableView.getColumns().setAll(recommendedNutrientNameColumn, recommendedNutrientQuantityColumn, recommendedNutrientUomColumn );

		nutriProfilerGrid.add(genderComboBox, 0, 0, 2, 1);
		nutriProfilerGrid.add(ageTextField, 0, 1);
		nutriProfilerGrid.add(new Label("years"), 1, 1);
		nutriProfilerGrid.add(weightTextField, 0, 2);
		nutriProfilerGrid.add(new Label("kg"), 1, 2);
		nutriProfilerGrid.add(heightTextField, 0, 3);
		nutriProfilerGrid.add(new Label("cm"), 1, 3);
		nutriProfilerGrid.add(physicalActivityComboBox, 0, 4, 2, 1);
		nutriProfilerGrid.add(new Label("Ingredients to watch"), 0, 5);
		nutriProfilerGrid.add(ingredientsToWatchTextArea, 0, 6);
		nutriProfilerGrid.add(createProfileButton, 2, 0, 3, 1);
		nutriProfilerGrid.add(recommendedNutrientsTableView, 2, 1, 3, 6);
		nutriProfilerGrid.add(nutriChart.barChart, 5, 0, 3, 7);

		// set all grid columns' width 
		genderComboBox.setPrefWidth(150);
		physicalActivityComboBox.setPrefWidth(150);
		nutriProfilerGrid.getColumnConstraints().add(new ColumnConstraints(150)); //inputs
		nutriProfilerGrid.getColumnConstraints().add(new ColumnConstraints(30)); //units
		nutriProfilerGrid.getColumnConstraints().add(new ColumnConstraints(150)); //recommendedNutrientTableview
		nutriProfilerGrid.getColumnConstraints().add(new ColumnConstraints(100)); //recommendedNutrientTableview
		nutriProfilerGrid.getColumnConstraints().add(new ColumnConstraints(50)); //recommendedNutrientTableview

		createProfileButton.setPrefWidth(320); 

		recommendedNutrientsTableView.getColumns().get(0).setPrefWidth(150);
		recommendedNutrientsTableView.getColumns().get(1).setPrefWidth(100);
		recommendedNutrientsTableView.getColumns().get(2).setPrefWidth(50);

		nutriChart.setupNutriChart();
		BorderPane.setMargin(nutriProfilerGrid, new Insets(5,5,5,5));
	}

	void initializePrompts() {
		genderComboBox.getSelectionModel().clearSelection();
		ObservableList<String> genderOptions = FXCollections.observableArrayList("Female", "Male");
		genderComboBox.setItems(genderOptions);
		genderComboBox.setPromptText("Gender");

		physicalActivityComboBox.getSelectionModel().clearSelection();
		ObservableList<String> physicalActivityOptions = FXCollections.observableArrayList("Sedentary", "Low active", "Active", "Very active");
		physicalActivityComboBox.setItems(physicalActivityOptions);
		physicalActivityComboBox.getSelectionModel().selectFirst();

		ingredientsToWatchTextArea.clear();
		ingredientsToWatchTextArea.setPromptText("Enter ingredients separated by commas");
		ingredientsToWatchTextArea.setWrapText(true);
		
		productIngredientsTextArea.setEditable(false);
		productIngredientsTextArea.setPromptText("Product ingredients");
		productIngredientsTextArea.setWrapText(true);

		ageTextField.clear();
		ageTextField.setPromptText("Enter age in years");
		weightTextField.clear();
		weightTextField.setPromptText("Enter weight in kg");
		heightTextField.clear();
		heightTextField.setPromptText("Enter height in cm");

		productsComboBox.setPromptText("Select product");
		productSearchTextField.setPromptText("Product to search");
		nutrientSearchTextField.setPromptText("Nutrient to search");
		ingredientSearchTextField.setPromptText("Ingredient to search");
		dietServingSizeTextField.setPromptText("Diet serving size");
		dietHouseholdSizeTextField.setPromptText("Diet household size");
	}
}
