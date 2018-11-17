package hw3;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestModelNutri2Data {

	static Model model;
	static final String PRODUCT_FILE = "data/Nutri2Products.csv";
	static final String NUTRIENT_FILE = "data/Nutri2Nutrients.csv";
	static final String SERVING_SIZE_FILE = "data/Nutri2ServingSize.csv";

	@BeforeClass
	public static void setupClass() {
		model = new Model();
		model.readProducts(PRODUCT_FILE);
		model.readNutrients(NUTRIENT_FILE);
		model.readServingSizes(SERVING_SIZE_FILE);
	}

	@Test
	public void test1_productsSize() {
		assertEquals(43, Model.productsMap.size());
	}

	@Test
	public void test2_nutrientsSize() {
		assertEquals(95, Model.nutrientsMap.size());
	}

	@Test
	public void test3_productServingSizes() {
		Product p = Model.productsMap.get("45005282");
		assertEquals(21, p.getServingSize(), 0);
		assertEquals("g", p.getServingUom());
		assertEquals(1, p.getHouseholdSize(), 0);
		assertEquals("Tbsp", p.getHouseholdUom());
	}

	@Test
	public void test4_productNutrientsSize() {
		Product p = Model.productsMap.get("45005282");
		assertEquals(4, p.getProductNutrients().size()); //there are actually 8 nutrients for this product, but 4 of them have 0 quantity
	}

	@Test
	public void test5_productNutrientsContent() {
		Product p = Model.productsMap.get("45261826");
		Map<String, Product.ProductNutrient> pn = p.getProductNutrients();
		assertEquals(4, p.getProductNutrients().size()); //there are actually 5 nutrients for this product, but 1 of them has 0 quantity
		assertTrue(pn.containsKey("203"));
		assertTrue(pn.containsKey("205"));
		assertTrue(pn.containsKey("208"));
		assertTrue(pn.containsKey("211"));
	}
}
