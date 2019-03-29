package hw3;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestModelFullSet {

	static Model model;
	static final String PRODUCT_FILE = "data/Products.csv";
	static final String NUTRIENT_FILE = "data/Nutrients.csv";
	static final String SERVING_SIZE_FILE = "data/ServingSize.csv";
	
	@BeforeClass
	public static void setupClass() {
		model = new Model();
		model.readProducts(PRODUCT_FILE);
		model.readNutrients(NUTRIENT_FILE);
		model.readServingSizes(SERVING_SIZE_FILE);
	}

	@Test
	public void test1_productsSize() {
		assertEquals(239089, Model.productsMap.size());
	}
	
	@Test
	public void test2_nutrientsSize() {
		assertEquals(95, Model.nutrientsMap.size());
	}
	
}
