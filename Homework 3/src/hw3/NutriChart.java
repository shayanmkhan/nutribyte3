package hw3;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


//This class provides complete functionality to setup, update, and clear the bar chart
//An instance of this chart is assembled into the View in its grid.
public class NutriChart {
	final CategoryAxis xAxis = new CategoryAxis();
	final NumberAxis yAxis = new NumberAxis();
	final BarChart<String,Number> barChart = new BarChart<String, Number>(xAxis,yAxis);
	XYChart.Series<String, Number> recommendedSeries = new XYChart.Series<String, Number>();
	XYChart.Series<String, Number> actualSeries = new XYChart.Series<String, Number>();

	List<String> chartCategoryList = new ArrayList<>();
	List<Data<String, Number>> recommendedDataList = new ArrayList<>();
	List<Data<String, Number>> actualDataList = new ArrayList<>();

	//a helper method used by several handlers to create or refresh the bar chart
	void updateChart() {
		if (NutriByte.person != null) {
			//update the chart
			for (RecommendedNutrient rn : NutriByte.person.recommendedNutrientsList) { 		
				if (rn.getNutrientCode().equals(NutriProfiler.ENERGY_NUTRIENT_CODE) && NutriByte.person.dietNutrientsMap.containsKey(rn.getNutrientCode())) {
					actualDataList.get(0).setYValue(Math.round(NutriByte.person.dietNutrientsMap.get(rn.getNutrientCode()).getNutrientQuantity() / rn.getNutrientQuantity()*100));
				}
				if (rn.getNutrientCode().equals(NutriProfiler.NutriEnum.PROTEIN.getNutrientCode()) && NutriByte.person.dietNutrientsMap.containsKey(rn.getNutrientCode())) {
					actualDataList.get(1).setYValue(Math.round(NutriByte.person.dietNutrientsMap.get(rn.getNutrientCode()).getNutrientQuantity() / rn.getNutrientQuantity()*100)); 
				}
				if (rn.getNutrientCode().equals(NutriProfiler.NutriEnum.CARBOHYDRATE.getNutrientCode()) && NutriByte.person.dietNutrientsMap.containsKey(rn.getNutrientCode())) {
					actualDataList.get(2).setYValue(Math.round(NutriByte.person.dietNutrientsMap.get(rn.getNutrientCode()).getNutrientQuantity() / rn.getNutrientQuantity()*100)); 
				}
				if (rn.getNutrientCode().equals(NutriProfiler.NutriEnum.FIBER.getNutrientCode()) && NutriByte.person.dietNutrientsMap.containsKey(rn.getNutrientCode())) {
					actualDataList.get(3).setYValue(Math.round(NutriByte.person.dietNutrientsMap.get(rn.getNutrientCode()).getNutrientQuantity() / rn.getNutrientQuantity()*100)); 
				}
			}
		}
	}
	
	//resets all actual values in the bar chart to 0
	void clearChart() {
		for (Data<String, Number> data : actualDataList)
		data.setYValue(0);
	}

	//setupNutriChart is called only once from View to create and bind all the elements together
	//There are only four nutrients to be charted with recommended and actual values 
	@SuppressWarnings("unchecked")
	void setupNutriChart() {
		recommendedSeries.setName("Recommended quantity");
		actualSeries.setName("Actual quantity");
		barChart.getData().addAll(recommendedSeries, actualSeries);
		
		//add placeholder for actual value for Energy as 0 quantity, create data label, and pass them to displayLableForData() method for formatting. 
		final XYChart.Data<String, Number> actualEnergyData = new Data<String, Number>(Model.nutrientsMap.get(NutriProfiler.ENERGY_NUTRIENT_CODE).getNutrientName(), 0);
		final Text energyDataText = new Text();
		energyDataText.textProperty().bind(Bindings.format("%s%%", actualEnergyData.YValueProperty()));
		actualEnergyData.nodeProperty().addListener((observable, oldValue, newValue) -> displayLabelForData(actualEnergyData, energyDataText));
		actualDataList.add(actualEnergyData);
		
		//Protein actual data
		final XYChart.Data<String, Number> actualProteinData = new Data<String, Number>(Model.nutrientsMap.get(NutriProfiler.NutriEnum.PROTEIN.getNutrientCode()).getNutrientName(), 0);
		final Text proteinDataText = new Text();
		proteinDataText.textProperty().bind(Bindings.format("%s%%", actualProteinData.YValueProperty()));
		actualProteinData.nodeProperty().addListener((observable, oldValue, newValue) -> displayLabelForData(actualProteinData, proteinDataText));
		actualDataList.add(actualProteinData);
		
		//Carbohydrate actual data
		final XYChart.Data<String, Number> actualCarbohydrateData = new Data<String, Number>(Model.nutrientsMap.get(NutriProfiler.NutriEnum.CARBOHYDRATE.getNutrientCode()).getNutrientName(), 0);
		final Text carbohydrateDataText = new Text();
		carbohydrateDataText.textProperty().bind(Bindings.format("%s%%", actualCarbohydrateData.YValueProperty()));
		actualCarbohydrateData.nodeProperty().addListener((observable, oldValue, newValue) -> displayLabelForData(actualCarbohydrateData, carbohydrateDataText));
		actualDataList.add(actualCarbohydrateData);
		
		//Fiber actual data
		final XYChart.Data<String, Number> actualFiberData = new Data<String, Number>(Model.nutrientsMap.get(NutriProfiler.NutriEnum.FIBER.getNutrientCode()).getNutrientName(), 0);
		final Text fiberDataText = new Text();
		fiberDataText.textProperty().bind(Bindings.format("%s%%", actualFiberData.YValueProperty()));
		actualFiberData.nodeProperty().addListener((observable, oldValue, newValue) -> displayLabelForData(actualFiberData, fiberDataText));
		actualDataList.add(actualFiberData);
		
		//add recommended value and category names for Energy nutrient
		recommendedDataList.add(new Data<String, Number>(Model.nutrientsMap.get(NutriProfiler.ENERGY_NUTRIENT_CODE).getNutrientName(), 100));
		chartCategoryList.add(Model.nutrientsMap.get(NutriProfiler.ENERGY_NUTRIENT_CODE).getNutrientName());
		
		//add recommended value and category names for other three nutrients 
		for (NutriProfiler.NutriEnum nutriEnum : NutriProfiler.NutriEnum.values()) {
			switch (nutriEnum) {
				case PROTEIN:
				case CARBOHYDRATE: 
				case FIBER: {
					recommendedDataList.add(new Data<String, Number>(Model.nutrientsMap.get(nutriEnum.getNutrientCode()).getNutrientName(), 100)); 
					chartCategoryList.add(Model.nutrientsMap.get(nutriEnum.getNutrientCode()).getNutrientName());
					break;
				}
				default: break;
			}
		}
		
		//add data lists to the series, and chart categories to the xAxis.
		recommendedSeries.setData(FXCollections.observableArrayList(recommendedDataList));
		actualSeries.setData(FXCollections.observableArrayList(actualDataList));
		xAxis.setCategories(FXCollections.observableArrayList(chartCategoryList));
		yAxis.setTickLabelsVisible(false);
	}

	//source: https://gist.github.com/jewelsea/5094893
	//this method helps display data value labels on top of the bars
	private void displayLabelForData(XYChart.Data<String, Number> data, Text dataText) {
		final Node node = data.getNode();
		dataText.setFont(Font.font(10));
		node.parentProperty().addListener((observable, oldvalue, newvalue) -> {
			Group parentGroup = (Group) newvalue;
			parentGroup.getChildren().add(dataText);
		});

		node.boundsInParentProperty().addListener((observable, oldvalue, newvalue) -> {
			dataText.setLayoutX(Math.round(newvalue.getMinX() + newvalue.getWidth() / 2 - dataText.prefHeight(-1) / 2));
			dataText.setLayoutY(Math.round(newvalue.getMinY() - dataText.prefHeight(-1) * 0.25));
		});
	}
}
