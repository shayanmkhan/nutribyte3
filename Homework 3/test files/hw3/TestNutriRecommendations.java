package hw3;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestNutriRecommendations {
	
	static Person person1, person2, person3;
	static Model model;
	
	@BeforeClass
	public static void setupClass() {
		model = new Model();
		model.readProducts("data/Nutri2Products.csv");
		model.readNutrients("data/Nutri2Nutrients.csv");
		model.readServingSizes("data/Nutri2ServingSize.csv");
		person1 = new Male(25, 65, 150, 1.48f, "peanut");
		person2 = new Female(17, 60, 160, 1.1f, "sugar");
		person3 = new Female(85, 70, 155, 1.25f, "");
	}
	
	@Test
	public void test0_ageGroup_Male() {
		assertEquals(30, person1.ageGroup.getAge(), 0);
	}
	
	@Test
	public void test0_ageGroup_Female() {
		assertEquals(18, person2.ageGroup.getAge(), 0);
	}
	@Test
	public void test0_ageGroup_oldFemale() {
		assertEquals(150, person3.ageGroup.getAge(), 0);
	}
	 
	//------------------------------------------------
	@Test
	public void test1_energyRequirement_Male() {
		NutriProfiler.createNutriProfile(person1);
		assertEquals(3152.204, person1.recommendedNutrientsList.get(0).getNutrientQuantity(), .001);
	}
	
	@Test
	public void test1_energyRequirement_Female() {
		NutriProfiler.createNutriProfile(person2);
		assertEquals(1935.54, person2.recommendedNutrientsList.get(0).getNutrientQuantity(), .01);
	}
	//------------------------------------------------
	
	@Test
	public void test2_proteinRequirement_Male() {
		NutriProfiler.createNutriProfile(person1);
		assertEquals(52, person1.recommendedNutrientsList.get(1).getNutrientQuantity(), .01);
	}
	
	@Test
	public void test2_proteinRequirement_Female() {
		NutriProfiler.createNutriProfile(person2);
		assertEquals(42.6, person2.recommendedNutrientsList.get(1).getNutrientQuantity(), .01);
	}
	//------------------------------------------------
	@Test
	public void test3_carbRequirement_Male() {
		NutriProfiler.createNutriProfile(person1);
		assertEquals(130, person1.recommendedNutrientsList.get(2).getNutrientQuantity(), 0);
	}
	
	@Test
	public void test3_carbRequirement_Female() {
		NutriProfiler.createNutriProfile(person2);
		assertEquals(130, person2.recommendedNutrientsList.get(2).getNutrientQuantity(), 0);
	}
	//------------------------------------------------
	
	@Test
	public void test4_fiberRequirement_Male() {
		NutriProfiler.createNutriProfile(person1);
		assertEquals(38, person1.recommendedNutrientsList.get(3).getNutrientQuantity(), 0);
	}
	@Test
	public void test4_fiberRequirement_Female() {
		NutriProfiler.createNutriProfile(person2);
		assertEquals(26, person2.recommendedNutrientsList.get(3).getNutrientQuantity(), 0);
	}
	//------------------------------------------------
	
	@Test
	public void test5_histidineRequirement_Male() {
		NutriProfiler.createNutriProfile(person1);
		assertEquals(0.910, person1.recommendedNutrientsList.get(4).getNutrientQuantity(), .0001);
	}
	
	@Test
	public void test5_histidineRequirement_Female() {
		NutriProfiler.createNutriProfile(person2);
		assertEquals(0.840, person2.recommendedNutrientsList.get(4).getNutrientQuantity(), .0001);
	}
	
	//------------------------------------------------
	
	@Test
	public void test6_isoleucineRequirement_Male() {
		NutriProfiler.createNutriProfile(person1);
		assertEquals(1.235, person1.recommendedNutrientsList.get(5).getNutrientQuantity(), .0001);
	}
	@Test
	public void test6_isoleucineRequirement_Female() {
		NutriProfiler.createNutriProfile(person2);
		assertEquals(1.140, person2.recommendedNutrientsList.get(5).getNutrientQuantity(), .0001);
	}
	//------------------------------------------------
	@Test
	public void test7_leucineRequirement_Male() {
		NutriProfiler.createNutriProfile(person1);
		assertEquals(2.730, person1.recommendedNutrientsList.get(6).getNutrientQuantity(), .0001);
	}
	@Test
	public void test7_leucineRequirement_Female() {
		NutriProfiler.createNutriProfile(person2);
		assertEquals(2.640, person2.recommendedNutrientsList.get(6).getNutrientQuantity(), .0001);
	}
	//------------------------------------------------
	@Test
	public void test8_lysineRequirement_Male() {
		NutriProfiler.createNutriProfile(person1);
		assertEquals(2.470, person1.recommendedNutrientsList.get(7).getNutrientQuantity(), .0001);
	}
	@Test
	public void test8_lysineRequirement_Female() {
		NutriProfiler.createNutriProfile(person2);
		assertEquals(2.400, person2.recommendedNutrientsList.get(7).getNutrientQuantity(), .0001);
	}
	//------------------------------------------------
	@Test
	public void test9_methionineRequirement_Male() {
		NutriProfiler.createNutriProfile(person1);
		assertEquals(1.235, person1.recommendedNutrientsList.get(8).getNutrientQuantity(), .0001);
	}
	@Test
	public void test9_methionineRequirement_Female() {
		NutriProfiler.createNutriProfile(person2);
		assertEquals(1.140, person2.recommendedNutrientsList.get(8).getNutrientQuantity(), .0001);
	}
	//------------------------------------------------
	@Test
	public void test10_cysteineRequirement_Male() {
		NutriProfiler.createNutriProfile(person1);
		assertEquals(1.235, person1.recommendedNutrientsList.get(9).getNutrientQuantity(), .0001);
	}
	@Test
	public void test25_cysteineRequirement_Female() {
		NutriProfiler.createNutriProfile(person2);
		assertEquals(1.140, person2.recommendedNutrientsList.get(9).getNutrientQuantity(), .0001);
	}
	//------------------------------------------------
	
	@Test
	public void test11_phenylalanineRequirement_Male() {
		NutriProfiler.createNutriProfile(person1);
		assertEquals(2.145, person1.recommendedNutrientsList.get(10).getNutrientQuantity(), .0001);
	}
	@Test
	public void test11_phenylalanineRequirement_Female() {
		NutriProfiler.createNutriProfile(person2);
		assertEquals(2.1, person2.recommendedNutrientsList.get(10).getNutrientQuantity(), .0001);
	}
	//------------------------------------------------
	
	@Test
	public void test12_tyrosineRequirement_Male() {
		NutriProfiler.createNutriProfile(person1);
		assertEquals(2.145, person1.recommendedNutrientsList.get(11).getNutrientQuantity(), .0001);
	}
	@Test
	public void test12_tyrosineRequirement_Female() {
		NutriProfiler.createNutriProfile(person2);
		assertEquals(2.1, person2.recommendedNutrientsList.get(11).getNutrientQuantity(), .0001);
	}
	//------------------------------------------------
	@Test
	public void test13_threonineRequirement_Male() {
		NutriProfiler.createNutriProfile(person1);
		assertEquals(1.3, person1.recommendedNutrientsList.get(12).getNutrientQuantity(), .0001);
	}
	@Test
	public void test13_threonineRequirement_Female() {
		NutriProfiler.createNutriProfile(person2);
		assertEquals(1.26, person2.recommendedNutrientsList.get(12).getNutrientQuantity(), .0001);
	}
	//------------------------------------------------
	@Test
	public void test14_tryptophanRequirement_Male() {
		NutriProfiler.createNutriProfile(person1);
		assertEquals(0.325, person1.recommendedNutrientsList.get(13).getNutrientQuantity(), 0.0001);
	}
	@Test
	public void test14_tryptophanRequirement_Female() {
		NutriProfiler.createNutriProfile(person2);
		assertEquals(0.3, person2.recommendedNutrientsList.get(13).getNutrientQuantity(), .0001);
	}
	//------------------------------------------------
	@Test
	public void test15_valineRequirement_Male() {
		NutriProfiler.createNutriProfile(person1);
		assertEquals(1.560, person1.recommendedNutrientsList.get(14).getNutrientQuantity(), .0001);
	}
	
	@Test
	public void test15_valineRequirement_Female() {
		NutriProfiler.createNutriProfile(person2);
		assertEquals(1.440, person2.recommendedNutrientsList.get(14).getNutrientQuantity(), .0001);
	}
	
	
	
	
	
	
	
	
}
