//Name: Shayan Khan
//AndrewID: shayank

package hw3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLFiler extends DataFiler{

	@Override
	public boolean readFile(String filename) {

		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(new File(filename));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
			return false;
		}
		Element root = doc.getRootElement();
		Element profileElement = root.getChild("Profile");
		if (profileElement != null) {
			String gender = profileElement.getChildText("Gender");
			float age = Float.parseFloat(profileElement.getChildText("Age"));
			float weight = Float.parseFloat(profileElement.getChildText("Weight"));
			float height = Float.parseFloat(profileElement.getChildText("Height"));
			float physicalActivityLevel = Float.parseFloat(profileElement.getChildText("PhysicalActivityLevel"));
			Element ingredientElements = profileElement.getChild("IngredientsToWatch");
			StringBuilder ingredientsToWatch = new StringBuilder();
			String ingredient;
			for (Element e : ingredientElements.getChildren()) {
				ingredient = e.getText();
				if (ingredient != null && !ingredient.isEmpty()) {
					ingredientsToWatch.append(ingredient + ", ");
				}
			}
			//remove the last comma
			if (ingredientsToWatch.length() > 0) ingredientsToWatch.replace(ingredientsToWatch.toString().length()-2, ingredientsToWatch.toString().length()-1, "");
			switch (gender) {
			case "Female": NutriByte.person = 
					new Female(age, weight, height, physicalActivityLevel, ingredientsToWatch.toString());
			break;
			case "Male": NutriByte.person = 
					new Male(age, weight, height, physicalActivityLevel, ingredientsToWatch.toString());
			break;
			default: break;
			}
		}
		return true;
	}

	@Override
	public void writeFile(String filename) {
		Document doc = new Document();
		Element xmlRoot = new Element("Profile");
		doc.addContent(xmlRoot);
		addChildElement(xmlRoot, "Gender", NutriByte.person.getClass().getSimpleName());
		addChildElement(xmlRoot, "Age", Float.toString(NutriByte.person.age));
		addChildElement(xmlRoot, "Weight", Float.toString(NutriByte.person.weight));
		addChildElement(xmlRoot, "Height", Float.toString(NutriByte.person.height));

		Element ingredientsToWatchElement = new Element("IngredientsToWatch");
		xmlRoot.addContent(ingredientsToWatchElement);
		for (String s : NutriByte.person.ingredientsToWatch.split(",")) {
			addChildElement(ingredientsToWatchElement, "Ingredient", s);
		}
		XMLOutputter outputter = new XMLOutputter (Format.getPrettyFormat());

		try {
			FileWriter writer = new FileWriter(new File(filename));
			outputter.output(doc, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addChildElement(Element parent, String elementName, String textValue) {
		Element child = new Element(elementName);
		child.setText(textValue);
		parent.addContent(child);
	}
}
